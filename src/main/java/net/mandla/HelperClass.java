/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package net.mandla;

import com.backendless.Backendless;
import java.util.List;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import net.mandla.Tables.Task;

/**
 *
 * @author mandl
 */
public class HelperClass {

   
    public static List<Task> taskList;
    public final String API_KEY = "9C614324-BC38-4198-AEED-A4D556E1A3E3";
    public final String APP_ID = "C6386559-AE18-8E86-FFE4-2FB4E1AAD900";
    
    public  void initializeBackendless()
    {
        Backendless.initApp(APP_ID, API_KEY);
    }
    
    public static void clear(Control... controls)
    {
       for (Control control : controls) {
          if (control instanceof TextField) {
             ((TextField) control).clear();
          } else if (control instanceof ComboBox) {
             ((ComboBox) control).setValue("Select");
          } else if (control instanceof TextArea) {
             ((TextArea) control).clear();
          } else if (control instanceof DatePicker) {
             ((DatePicker) control).setValue(null);
          }
       }
    }
}

