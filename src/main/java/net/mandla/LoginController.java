/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package net.mandla;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 *
 * @author mandl
 */
public class LoginController {

   @FXML
   private ProgressBar progressBar;
   @FXML
   public TextField tfUsername;
   @FXML
   public PasswordField pfPassword;

   @FXML
   public void handleLoginButton(ActionEvent event) {

      String username = tfUsername.getText().trim();
      String password = pfPassword.getText().trim();

      showProgress(true);
      if (username.isEmpty() || password.isEmpty()) {
         showDialog("Input Fields", "Enter all fields", "Error");

         showProgress(false);
      } else {
         Backendless.UserService.login(username, password, new AsyncCallback<BackendlessUser>() {
            @Override
            public void handleResponse(BackendlessUser response) {
               try {
                  setStageForMain(event,response.getEmail(),response.getProperty("name").toString());
                  
                  showProgress(false);
               } catch (IOException ex) {
                   showProgress(false);
                  Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
               }
            }

            @Override
            public void handleFault(BackendlessFault fault) {
                showProgress(false);
               showDialog("Error", fault.getMessage(), "Error");
            }
         });
      }
   }

   @FXML
   public void handleRegisterButton(ActionEvent event) {

      //
   }

   public static void showDialog(String title, String message, String type) {
      Platform.runLater(() -> {

         Alert alert = null;
         if (type.equalsIgnoreCase("Error")) {
            alert = new Alert(Alert.AlertType.ERROR);
         } else if (type.equalsIgnoreCase("Warning")) {
            alert = new Alert(Alert.AlertType.WARNING);
         } else if (type.equalsIgnoreCase("Information")) {
            alert = new Alert(Alert.AlertType.INFORMATION);
         }//
         alert.setTitle(title);
         alert.setHeaderText(null);
         alert.setContentText(message);
         alert.showAndWait();
      });
   }

   public void setStageForMain(ActionEvent event,String email,String name) throws IOException {
      // Load the Main.fxml file
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/net.mandla.ui/dashboard.fxml"));
      Parent root = loader.load();

      // Access the controller of the Main.fxml file
      Controller mainController = loader.getController();

      // Pass the data to the MainController
      mainController.initializeData(email, name);

      Platform.runLater(() -> {
      // Create a new scene with the loaded FXML and replace the current scene
      Scene scene = new Scene(root);
      Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
      stage.setScene(scene);
      stage.show();
      });
   }//end method
   public void showProgress(boolean show){
      
      progressBar.setVisible(true);
        
        // Perform login logic
        
        // Update the progress bar value as needed
        progressBar.setProgress(0.5);
        
        // Continue with the login process
        
        // Upon completion, hide the progress bar
        progressBar.setVisible(false);
   }
}//end class
