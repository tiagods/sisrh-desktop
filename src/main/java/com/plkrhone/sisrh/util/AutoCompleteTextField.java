package com.plkrhone.sisrh.util;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import com.jfoenix.controls.JFXTextField;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Side;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.Label;

public class AutoCompleteTextField<T>{
  /** The existing autocomplete entries. */
  private final SortedSet<String> entries;
  /** The popup used to select an entry. */
  private ContextMenu entriesPopup;
  private JFXTextField textField;
  /** Construct a new AutoCompleteTextField. */
  public AutoCompleteTextField(JFXTextField textField, List<T> object) {
    super();
    this.textField=textField;
    entries = new TreeSet<>();
    object.forEach(c->entries.add(c.toString()));
    entriesPopup = new ContextMenu();
    
    populatePopup(new ArrayList<>(entries));
    entriesPopup.show(textField, Side.BOTTOM, 0, 0);
    
    textField.textProperty().addListener(new ChangeListener<String>(){
      @Override
      public void changed(ObservableValue<? extends String> observableValue, String s, String s2) {
//        if (textField.getText().length() == 0){
//          entriesPopup.hide();
//        } 
//        else{
          LinkedList<String> searchResult = new LinkedList<>();
          searchResult.addAll(entries.subSet(textField.getText(), textField.getText() + Character.MAX_VALUE));
          if (entries.size() >= 0){
            populatePopup(searchResult);
            if (!entriesPopup.isShowing()){
              entriesPopup.show(textField, Side.BOTTOM, 0, 0);
            }
          } 
          else{
            entriesPopup.hide();
          }
//        }
      }
    });

    textField.focusedProperty().addListener((observableValue, aBoolean, aBoolean2) -> entriesPopup.hide());

  }

  /**
   * Get the existing set of autocomplete entries.
   * @return The existing autocomplete entries.
   */
  public SortedSet<String> getEntries() { return entries; }

  /**
   * Populate the entry set with the given search results.  Display is limited to 10 entries, for performance.
   * @param searchResult The set of matching strings.
   */
  private void populatePopup(List<String> searchResult) {
    List<CustomMenuItem> menuItems = new LinkedList<>();
    // If you'd like more entries, modify this line.
    int maxEntries = 10;
    int count = Math.min(searchResult.size(), maxEntries);
    for (int i = 0; i < count; i++)
    {
      final String result = searchResult.get(i);
      Label entryLabel = new Label(result);
      CustomMenuItem item = new CustomMenuItem(entryLabel, true);
      item.setOnAction(actionEvent -> {
          textField.setText(result);
          entriesPopup.hide();
      });
      menuItems.add(item);
    }
    entriesPopup.getItems().clear();
    entriesPopup.getItems().addAll(menuItems);
  }
}
