
import com.sun.org.apache.bcel.internal.classfile.Node;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        launch();
    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/view/manu.fxml"));
        loader.load();
        primaryStage.setScene(new Scene(loader.getRoot()));
        primaryStage.show();

    }
}
