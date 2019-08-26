package com.plkrhone.sisrh.util;

import de.felixroske.jfxsupport.PropertyReaderHelper;
import de.felixroske.jfxsupport.SplashScreen;
import javafx.application.Application;
import javafx.application.HostServices;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.awt.*;
import java.util.List;
import java.util.*;
import java.util.concurrent.CompletableFuture;

public abstract class AbstractJavaFxSupport extends Application {
  private static Logger LOGGER = LoggerFactory.getLogger(AbstractJavaFxSupport.class);

  private static String[] savedArgs = new String[0];

  static Class<? extends AbstractSupportView> savedInitialView;
  static SplashScreen splashScreen;
  private static ConfigurableApplicationContext applicationContext;


  private static List<Image> icons = new ArrayList<>();
  private final List<Image> defaultIcons = new ArrayList<>();

  private final CompletableFuture<Runnable> splashIsShowing;

  protected AbstractJavaFxSupport() {
    splashIsShowing = new CompletableFuture<>();
  }

  public static Stage getStage() {
    return GUIState.getStage();
  }

  public static Scene getScene() {
    return GUIState.getScene();
  }

  public static HostServices getAppHostServices() {
    return GUIState.getHostServices();
  }

  public static SystemTray getSystemTray() {
    return GUIState.getSystemTray();
  }

  /**
   * @param window The FxmlView derived class that should be shown.
   * @param mode   See {@code javafx.stage.Modality}.
   */
  public static Stage showView(final Class<? extends AbstractSupportView> window, final Modality mode) {
    final AbstractSupportView view = applicationContext.getBean(window);
    Scene newScene;
    Stage newStage = new Stage();
    setIconDefault(newStage);
    setIconDefault(getStage());
    if (view.getView().getScene() != null) {
      // This view was already shown so
      // we have a scene for it and use this one.
      newScene = view.getView().getScene();
    } else {
      newScene = new Scene(view.getView());
    }
    newStage.setScene(newScene);
    newStage.initModality(mode);
    newStage.initOwner(getStage());
    newStage.setTitle(view.getDefaultTitle());
    newStage.initStyle(view.getDefaultStyle());
    newStage.show();
    return newStage;
  }

  private void loadIcons(ConfigurableApplicationContext ctx) {
    try {
      final List<String> fsImages = PropertyReaderHelper.get(ctx.getEnvironment(), Constant.KEY_APPICONS);

      if (!fsImages.isEmpty()) {
        fsImages.forEach((s) ->
                {
                  Image img = new Image(getClass().getResource(s).toExternalForm());
                  icons.add(img);
                }
        );
      } else { // add factory images
        icons.addAll(defaultIcons);
      }
    } catch (Exception e) {
      LOGGER.error("Failed to load icons: ", e);
    }


  }

  /*
   * (non-Javadoc)
   *
   * @see javafx.application.Application#init()
   */
  @Override
  public void init() throws Exception {
    // Load in JavaFx Thread and reused by Completable Future, but should no be a big deal.
    defaultIcons.addAll(loadDefaultIcons());
    CompletableFuture.supplyAsync(() ->
            SpringApplication.run(this.getClass(), savedArgs)
    ).whenComplete((ctx, throwable) -> {
      if (throwable != null) {
        LOGGER.error("Failed to load spring application context: ", throwable);
        Platform.runLater(() -> showErrorAlert(throwable));
      } else {
        Platform.runLater(() -> {
          loadIcons(ctx);
          launchApplicationView(ctx);
        });
      }
    }).thenAcceptBothAsync(splashIsShowing, (ctx, closeSplash) -> {
      Platform.runLater(closeSplash);
    });
  }


  /*
   * (non-Javadoc)
   *
   * @see javafx.application.Application#start(javafx.stage.Stage)
   */
  @Override
  public void start(final Stage stage) throws Exception {

    GUIState.setStage(stage);
    GUIState.setHostServices(this.getHostServices());
    final Stage splashStage = new Stage(StageStyle.TRANSPARENT);
    if (AbstractJavaFxSupport.splashScreen.visible()) {
      final Scene splashScene = new Scene(splashScreen.getParent(), Color.TRANSPARENT);
      splashStage.setScene(splashScene);
      splashStage.getIcons().addAll(defaultIcons);
      setIconDefault(splashStage);
      splashStage.initStyle(StageStyle.TRANSPARENT);
      beforeShowingSplash(splashStage);
      splashStage.show();
    }

    splashIsShowing.complete(() -> {
      showInitialView();
      if (AbstractJavaFxSupport.splashScreen.visible()) {
        splashStage.hide();
        splashStage.setScene(null);
      }
    });
  }


  /**
   * Show initial view.
   */
  private void showInitialView() {
    final String stageStyle = applicationContext.getEnvironment().getProperty(Constant.KEY_STAGE_STYLE);
    if (stageStyle != null) {
      GUIState.getStage().initStyle(StageStyle.valueOf(stageStyle.toUpperCase()));
    } else {
      GUIState.getStage().initStyle(StageStyle.DECORATED);
    }
    beforeInitialView(GUIState.getStage(), applicationContext);
    showView(savedInitialView);
  }


  /**
   * Launch application view.
   */
  private void launchApplicationView(final ConfigurableApplicationContext ctx) {
    AbstractJavaFxSupport.applicationContext = ctx;
  }

  /**
   * Show view.
   *
   * @param newView the new view
   */
  public static void showView(final Class<? extends AbstractSupportView> newView) {
    getView(newView).show();
  }

  public static Stage getView(final Class<? extends AbstractSupportView> newView) {
    final AbstractSupportView view = applicationContext.getBean(newView);

    if (GUIState.getScene() == null) {
      GUIState.setScene(new Scene(view.getView()));
    } else {
      GUIState.getScene().setRoot(view.getView());
    }
    GUIState.getStage().setScene(GUIState.getScene());

    applyEnvPropsToView();

    GUIState.getStage().getIcons().addAll(icons);
    return GUIState.getStage();
  }
  /**
   * Show error alert that close app.
   *
   * @param throwable cause of error
   */
  private static void showErrorAlert(Throwable throwable) {
    Alert alert = new Alert(Alert.AlertType.ERROR, "Oops! An unrecoverable error occurred.\n" +
            "Please contact your software vendor.\n\n" +
            "The application will stop now.\n\n" +
            "Error: " + throwable.getMessage());
    alert.showAndWait().ifPresent(response -> Platform.exit());
  }

  /**
   * Apply env props to view.
   */
  private static void applyEnvPropsToView() {
    PropertyReaderHelper.setIfPresent(applicationContext.getEnvironment(), Constant.KEY_TITLE, String.class,
            GUIState.getStage()::setTitle);

    PropertyReaderHelper.setIfPresent(applicationContext.getEnvironment(), Constant.KEY_STAGE_WIDTH, Double.class,
            GUIState.getStage()::setWidth);

    PropertyReaderHelper.setIfPresent(applicationContext.getEnvironment(), Constant.KEY_STAGE_HEIGHT, Double.class,
            GUIState.getStage()::setHeight);

    PropertyReaderHelper.setIfPresent(applicationContext.getEnvironment(), Constant.KEY_STAGE_RESIZABLE, Boolean.class,
            GUIState.getStage()::setResizable);
  }

  /*
   * (non-Javadoc)
   *
   * @see javafx.application.Application#stop()
   */
  @Override
  public void stop() throws Exception {
    super.stop();
    if (applicationContext != null) {
      applicationContext.close();
    } // else: someone did it already
  }

  /**
   * Sets the title. Allows to overwrite values applied during construction at
   * a later time.
   *
   * @param title the new title
   */
  protected static void setTitle(final String title) {
    GUIState.getStage().setTitle(title);
  }

  /**
   * Launch app.
   *
   * @param appClass the app class
   * @param view     the view
   * @param args     the args
   */
  public static void launch(final Class<? extends Application> appClass,
                            final Class<? extends AbstractSupportView> view, final String[] args) {

    launch(appClass, view, new SplashScreen(), args);
  }
  /**
   * Launch app.
   * @deprecated To be more in line with javafx.application please use launch
   * @param appClass the app class
   * @param view     the view
   * @param args     the args
   */
  @Deprecated
  public static void launchApp(final Class<? extends Application> appClass,
                               final Class<? extends AbstractSupportView> view, final String[] args) {

    launch(appClass, view, new SplashScreen(), args);
  }

  /**
   * Launch app.
   *
   * @param appClass     the app class
   * @param view         the view
   * @param splashScreen the splash screen
   * @param args         the args
   */
  public static void launch(final Class<? extends Application> appClass,
                            final Class<? extends AbstractSupportView> view, final SplashScreen splashScreen, final String[] args) {
    savedInitialView = view;
    savedArgs = args;

    if (splashScreen != null) {
      AbstractJavaFxSupport.splashScreen = splashScreen;
    } else {
      AbstractJavaFxSupport.splashScreen = new SplashScreen();
    }

    if (SystemTray.isSupported()) {
      GUIState.setSystemTray(SystemTray.getSystemTray());
    }

    Application.launch(appClass, args);
  }
  /**
   * Launch app.
   *
   * @deprecated To be more in line with javafx.application please use launch
   * @param appClass     the app class
   * @param view         the view
   * @param splashScreen the splash screen
   * @param args         the args
   */
  @Deprecated
  public static void launchApp(final Class<? extends Application> appClass,
                               final Class<? extends AbstractSupportView> view, final SplashScreen splashScreen, final String[] args) {
    launch(appClass, view, splashScreen, args);
  }

  /**
   * Gets called after full initialization of Spring application context
   * and JavaFX platform right before the initial view is shown.
   * Override this method as a hook to add special code for your app. Especially meant to
   * add AWT code to add a system tray icon and behavior by calling
   * GUIState.getSystemTray() and modifying it accordingly.
   * <p>
   * By default noop.
   *
   * @param stage can be used to customize the stage before being displayed
   * @param ctx   represents spring ctx where you can loog for beans.
   */
  public void beforeInitialView(final Stage stage, final ConfigurableApplicationContext ctx) {
  }

  public void beforeShowingSplash(Stage splashStage) {

  }
  public static void setIconDefault(Stage stage) {
    stage.getIcons().add((new Image(AbstractJavaFxSupport.class.getResource("/icons/theme.png").toExternalForm())));
  }

  Map<Class,Stage> stageList = new HashMap<>();

  public Collection<Image> loadDefaultIcons() {
    return Arrays.asList(new Image(getClass().getResource("/icons/theme.png").toExternalForm()));
  }

  public enum GUIState {

    INSTANCE;
    private static Scene scene;

    private static Stage stage;

    private static String title;

    private static HostServices hostServices;

    private static SystemTray systemTray;

    public static String getTitle() {
      return title;
    }

    public static Scene getScene() {
      return scene;
    }

    public static Stage getStage() {
      return stage;
    }

    public static void setScene(final Scene scene) {
      GUIState.scene = scene;
    }

    public static void setStage(final Stage stage) {
      GUIState.stage = stage;
    }

    public static void setTitle(final String title) {
      GUIState.title = title;
    }

    public static HostServices getHostServices() {
      return hostServices;
    }

    static void setHostServices(HostServices hostServices) {
      GUIState.hostServices = hostServices;
    }

    public static SystemTray getSystemTray() {
      return systemTray;
    }

    static void setSystemTray(SystemTray systemTray) {
      GUIState.systemTray = systemTray;
    }

  }
  class Constant {
    public final static String KEY_STAGE_STYLE = "javafx.stage.style";
    public final static String KEY_TITLE = "javafx.title";
    public final static String KEY_STAGE_WIDTH = "javafx.stage.width";
    public final static String KEY_STAGE_HEIGHT = "javafx.stage.height";
    public final static String KEY_STAGE_RESIZABLE = "javafx.stage.resizable";
    public final static String KEY_APPICONS = "javafx.appicons";
  }
}