/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package net.mandla;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.DataQueryBuilder;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import net.mandla.Tables.Task;

/**
 *
 * @author mandl
 */
public class Controller {
   @FXML
    private Button editButton;
    @FXML
    private Button deleteButton;
   @FXML
   private TextField tfTitle;
   @FXML
   private TextArea tfDescription;
   @FXML
   private DatePicker dpDueDate;
   @FXML
   private ComboBox<String> comboBox;
   @FXML
   private ListView taskList ;
   
   private ObservableList<Task> observableList;
   
   String priority = null;
   
   public Controller(){
      
    

   getResults();
      
   }
   
   @FXML
   private void handleAddTaskButtonAction(ActionEvent event) {
 
      
      String title = tfTitle.getText();
      String description = tfDescription.getText();
      String dueDate = dpDueDate.getValue().toString();
      
       if (dpDueDate.getValue() == null ||
               title.isEmpty() || description.isEmpty()) 
       {
        tfTitle.setStyle("-fx-border-color: red;");
        if(description.isEmpty())
        {
           tfDescription.setStyle("-fx-border-color: red;");
           if(dpDueDate.getValue() == null){
              dpDueDate.setStyle("-fx-border-color: red;");
           }   
        }            
    } 
       else{
           // Save 
           Task task = new Task();
           
           task.setTitle(title);
           task.setDescription(description);
           task.setDueDate(dueDate);
           task.setPriority(priority);  
           
           Backendless.Data.of(Task.class).save(task, new AsyncCallback<Task>() {
              @Override
              public void handleResponse(Task response) {
                 
                 Platform.runLater(() -> {
            // Create an alert dialog
             Alert alert = new Alert(AlertType.CONFIRMATION);
                  alert.setTitle("Information Dialog");
                  alert.setHeaderText(null);
                  alert.setContentText(response.getTitle() + " Saved!");
                  alert.showAndWait();
                                 
                  HelperClass.clear(tfDescription,tfTitle,dpDueDate,comboBox);
                 // getResults();
                 });
                 
              }
              @Override
              public void handleFault(BackendlessFault fault) {
                 
                 Platform.runLater(() -> {
                  Alert alert = new Alert(AlertType.ERROR);
                  alert.setTitle("Error Dialog");
                  alert.setHeaderText(null);
                  alert.setContentText("Error : " + fault.getMessage());
                  alert.showAndWait();
                  
                 });
              }
           });
       }//end if
   }// end method
 
   @FXML
    private void initialize() {
        comboBox.getItems().addAll("Low", "Medium", "High");
        comboBox.setValue("Selected");

        comboBox.setOnAction(event -> {
        priority = comboBox.getValue();
             
        });
    }
 
    
    public void getResults() { 
      DataQueryBuilder queryBuilder = DataQueryBuilder.create();

        // Specify the properties to retrieve
        queryBuilder.setProperties("title", "description", "dueDate", "priority");

        Backendless.Data.of(Task.class).find(queryBuilder, new AsyncCallback<List<Task>>() {
         @Override
         public void handleResponse(List<Task> response) {
                      
            List<String> taskInfo = new ArrayList<>();

            for (Task task : response) {
                String taskDetails = "Title: " + task.getTitle() + "\n"
                        + "Description: " + task.getDescription() + "\n"
                        + "Due Date: " + task.getDueDate() + "\n"
                        + "Priority: " + task.getPriority() + "\n";

                taskInfo.add(taskDetails);
            }

            taskList.setItems(FXCollections.observableArrayList(taskInfo));
        
         }

         @Override
         public void handleFault(BackendlessFault fault) {
            
            System.err.println("Error : " + fault.getMessage());
         }
        });
   }
    
   @FXML
    public void handleListViewClick(MouseEvent event) {
        // Get the selected item from the ListView
        String selectedItem = taskList.getSelectionModel().getSelectedItem().toString();
h();
        if (selectedItem != null) {
            // Enable the Edit and Delete buttons
            editButton.setDisable(false);
            deleteButton.setDisable(false);
        } else {
            // No item selected, disable the Edit and Delete buttons
            editButton.setDisable(true);
            deleteButton.setDisable(true);
        }
    }

    // Rest of the code...
    @FXML
public void handleEditButtonAction(ActionEvent event) {
     String title = tfTitle.getText();
      String description = tfDescription.getText();
      String dueDate = dpDueDate.getValue().toString();
      
       if (dpDueDate.getValue() == null ||
               title.isEmpty() || description.isEmpty()) 
       {
        tfTitle.setStyle("-fx-border-color: red;");
        if(description.isEmpty())
        {
           tfDescription.setStyle("-fx-border-color: red;");
           if(dpDueDate.getValue() == null){
              dpDueDate.setStyle("-fx-border-color: red;");
           }   
        }            
    } 
       else{
           // Save 
           Map<String, Object> changes = new HashMap<>();
           
          
           changes.put( "title", title );
           changes.put( "description", description );
           changes.put( "dueDate", dueDate );
           changes.put( "priority", priority );
          
           String whereClause = "title = '"+title+"'";
           
           Backendless.Data.of(Task.class).update(whereClause, changes, new AsyncCallback<Integer>() {
              @Override
              public void handleResponse(Integer response) {
                  System.out.println("Update : " + response);
              }

              @Override
              public void handleFault(BackendlessFault fault) {
                 System.out.println("Error : " +fault.getMessage());
              }
           });
           
       }//end if
}
private void h(){
String selectedItem = taskList.getSelectionModel().getSelectedItem().toString();
    if (selectedItem != null) {
        // Extract the title, description, due date, and priority from the selected task
        String[] taskDetails = selectedItem.split("\n");
        String title = null;
        String description = null;
        String dueDate = null;
        String priority = null;

        for (String detail : taskDetails) {
            if (detail.startsWith("Title: ")) {
                title = detail.substring("Title: ".length());
            } else if (detail.startsWith("Description: ")) {
                description = detail.substring("Description: ".length());
            } else if (detail.startsWith("Due Date: ")) {
                dueDate = detail.substring("Due Date: ".length());
            } else if (detail.startsWith("Priority: ")) {
                priority = detail.substring("Priority: ".length());
            }
        }

        if (title != null && description != null && dueDate != null && priority != null) {
            // Implement the logic to handle the edit action with the retrieved details
       
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        // Convert string to LocalDate
        LocalDate localDate = LocalDate.parse(dueDate, formatter);
        
           tfTitle.setText(title);
           tfDescription.setText(description);
           dpDueDate.setValue(localDate);
           comboBox.setValue(priority);
           
        }
    }
}
@FXML
public void handleDeleteButtonAction(ActionEvent event) {
    String selectedItem = taskList.getSelectionModel().getSelectedItem().toString();
    if (selectedItem != null) {
        // Show a confirmation dialog before deleting the task
        Alert confirmationAlert = new Alert(AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Delete Task");
        confirmationAlert.setHeaderText("Confirm Deletion");
        confirmationAlert.setContentText("Are you sure you want to delete the selected task?");

        // Wait for the user to confirm the deletion
        ButtonType confirmButton = confirmationAlert.showAndWait().orElse(ButtonType.CANCEL);

        if (confirmButton == ButtonType.OK) {
            // Implement the logic to delete the selected task
            System.out.println("Delete: " + selectedItem);
        }
    }
}
}



    

