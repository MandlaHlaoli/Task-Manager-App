/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package net.mandla.Tables;

/**
 *
 * @author mandl
 */
public class Task {
   private String title;
   private String description;
   private String dueDate;
   private String priority;
   

   public Task(String title, String description, String dueDate, String priority) {
      this.title = title;
      this.description = description;
      this.dueDate = dueDate;
      this.priority = priority;
   }

     // Default constructor
   public Task() {
      this.title = "";
      this.description = "";
      this.dueDate = null;
      this.priority = "";
   }

   public String getTitle() {
      return title;
   }

   public String getDescription() {
      return description;
   }

   public String getDueDate() {
      return dueDate;
   }

   public String getPriority() {
      return priority;
   }

   public void setTitle(String title) {
      this.title = title;
   }

   public void setDescription(String description) {
      this.description = description;
   }

   public void setDueDate(String dueDate) {
      this.dueDate = dueDate;
   }

   public void setPriority(String priority) {
      this.priority = priority;
   }
}
