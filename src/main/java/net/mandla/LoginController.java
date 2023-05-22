/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package net.mandla;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 *
 * @author mandl
 */
public class LoginController {

   
   public static  void showDialog(String title,String message,String type)
   {
       Platform.runLater(() -> {
          
               Alert alert = null;
          if(type.equalsIgnoreCase("Error")){
                  alert = new Alert(Alert.AlertType.ERROR);
          }else if(type.equalsIgnoreCase("Warning"))
               {
                  alert = new Alert(Alert.AlertType.WARNING);
               }else if(type.equalsIgnoreCase("Information"))
               {
                  alert = new Alert(Alert.AlertType.INFORMATION);
               }
                  alert.setTitle(title);
                  alert.setHeaderText(null);
                  alert.setContentText(message);
                  alert.showAndWait();    
                 });
   }
   
   @FXML
   public TextField tfUsername;
   @FXML
   public PasswordField pfPassword;
   @FXML
   public void handleLoginButton(ActionEvent event) {

      String username = tfUsername.getText().trim();
      String password = pfPassword.getText().trim();
      
      if(username.isEmpty()|| password.isEmpty()){
         showDialog("Input Fields", "Enter all fields", "Error");
        
      }
      else{
         Backendless.UserService.login(username, password, new AsyncCallback<BackendlessUser>() {
            @Override
            public void handleResponse(BackendlessUser response) {
               showDialog("Success", response.getEmail() + " Logged in successfully", "Information");
            }

            @Override
            public void handleFault(BackendlessFault fault) {
               showDialog("Error", fault.getMessage() , "Error");
            }
         });
        
      }
   }

   @FXML
   public void handleRegisterButton(ActionEvent event) {

   }
}
