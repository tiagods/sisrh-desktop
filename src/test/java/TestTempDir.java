import java.io.File;

/**
 * Created by Tiago on 27/07/2017.
 */
public class TestTempDir {
    public static void main(String[] args){
        File base = new File(System.getProperty("java.io.tmpdir")+"\\gestorrh");
        if(!base.exists()){
            base.mkdir();
        }
        System.out.print(base.getAbsolutePath());
    }
}
