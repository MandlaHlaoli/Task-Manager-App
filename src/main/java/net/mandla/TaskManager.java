/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package net.mandla;

import com.backendless.Backendless;
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class TaskManager extends Application {

   @Override
   public void start(Stage stage) throws Exception {

      try {
         Parent root;
         Scene scene;

         String path = "/net.mandla.ui/login.fxml";
         root = FXMLLoader.load(getClass().getResource(path));
         scene = new Scene(root,1000,400);
         stage.setScene(scene);
         stage.show();
      } catch (IOException e) {
         System.err.println("Error : " + e.getMessage());
      }
   }

   public static void main(String[] args) {
      String API_KEY = "9C614324-BC38-4198-AEED-A4D556E1A3E3";
      String APP_ID = "C6386559-AE18-8E86-FFE4-2FB4E1AAD900";

      Backendless.initApp(APP_ID, API_KEY);

      launch(args);
   }
}



