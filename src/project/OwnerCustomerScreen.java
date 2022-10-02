/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project;

/**
 *
 * @author Rashique 
 *
 */
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
 
public class OwnerCustomerScreen extends Application {
 
    private TableView<User> table = new TableView<User>();
    private final ObservableList<User> data = FXCollections.observableArrayList();
    final HBox hb = new HBox();
    public ArrayList<User> customerList;   
 
    @Override
    public void start(Stage stage) {
        
        stage.setOnCloseRequest(e -> {
            stage.close();
        });
        
        Scene scene = new Scene(new Group());
        stage.setTitle("Bookstore App");
        stage.setWidth(602);
        stage.setHeight(602);
 
        final Label label = new Label("Customer Info");
        label.setFont(new Font("Times New Roman", 20));
 
        table.setEditable(true);
 
        TableColumn usernameCol = new TableColumn("Username");
        usernameCol.setMinWidth(100);
        usernameCol.setCellValueFactory(
                new PropertyValueFactory<User, String>("username"));
 
        TableColumn passwordCol = new TableColumn("Password");
        passwordCol.setMinWidth(100);
        passwordCol.setCellValueFactory(
                new PropertyValueFactory<User, String>("password"));
 
        TableColumn pointsCol = new TableColumn("Points");
        pointsCol.setMinWidth(200);
        pointsCol.setCellValueFactory(
                new PropertyValueFactory<User, String>("points"));
 
        table.setItems(data);
        table.getColumns().addAll(usernameCol, passwordCol, pointsCol);
        
        
        final TextField addUsername = new TextField();
        addUsername.setPromptText("Username");
        addUsername.setMaxWidth(usernameCol.getPrefWidth());
        final TextField addPassword = new TextField();
        addPassword.setMaxWidth(passwordCol.getPrefWidth());
        addPassword.setPromptText("Password");
       
        //Initial Customer Table Setup
        AutoInsertTable();
 
        final Button addButton = new Button("Add");
        addButton.setOnAction(e -> {
                
                data.add(new User(addUsername.getText(), addPassword.getText(), 0));
                table.setItems(data);
                customerList.add(new User(addUsername.getText(), addPassword.getText(), 0));
                
                PrintWriter pw = null;
        
                File file = new File("customers.txt");
                FileWriter fr = null;
                try {
                    fr = new FileWriter(file, true);
                    BufferedWriter br = new BufferedWriter(fr);
                    PrintWriter pr = new PrintWriter(br);
                    if (file.length() == 0) {
                        pr.print(addUsername.getText() + "\n" + addPassword.getText() + "\n" + "0");
                    }
                    else {
                        pr.print("\n" + addUsername.getText() + "\n" + addPassword.getText() + "\n" + "0");
                    }
                    pr.close();
                    br.close();
                    fr.close();
                } catch (IOException ex) {
                    Logger.getLogger(OwnerCustomerScreen.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                addUsername.clear();
                addPassword.clear();
                
        });
            
        
        
        final Button delButton = new Button("Delete");
        
        delButton.setOnAction(e -> {
            
            ObservableList<User> customersSelected, allCustomers;
            allCustomers = table.getItems();
            customersSelected = table.getSelectionModel().getSelectedItems();
            
            for (int i = 0; i < customersSelected.size(); i++) {
                    TablePosition pos = table.getSelectionModel().getSelectedCells().get(i);
                    int row = pos.getRow();
                    User item = table.getItems().get(row);
                    
                    System.out.println("\nCustomer To Be Deleted:");
                    System.out.println("Customer Username: " + item.getUsername() + "   Customer Pasword: " + item.getPassword());
                    System.out.println("Status: Deleted!");
                    System.out.println("");
 
                    customerList.remove(row);
                    PrintWriter pw;
                    try {
                        pw = new PrintWriter("customers.txt");
                        pw.close();
                        WriteListToText();
                    } 
                    catch (FileNotFoundException ex) {
                        Logger.getLogger(OwnerCustomerScreen.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
            }   
            
            customersSelected.forEach(allCustomers::remove);
            
        });
        
        final Button backButton = new Button("Back");
        
        backButton.setOnAction(e -> {
            OwnerStartScreen oSs = new OwnerStartScreen();
            oSs.start(stage);
        });
        
        hb.getChildren().addAll(addUsername, addPassword, addButton, delButton, backButton);
        hb.setSpacing(4);
        
        
        
        
        final VBox vbox = new VBox();
        vbox.setSpacing(6);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(label, table, hb);
 
        ((Group) scene.getRoot()).getChildren().addAll(vbox);
 
        stage.setScene(scene);
        stage.show();
    
    }

        
        
    public void AutoInsertTable() {    
        int count = 0;

        try {     
            File f = new File("customers.txt");

            Scanner scan = new Scanner(f);            

            this.customerList = new ArrayList<User>();
            while(scan.hasNextLine()) {                
                customerList.add(new User(scan.nextLine(), scan.nextLine(), Integer.parseInt(scan.nextLine()))); 
                count++;       
            }
                
        } catch (IOException e) {
            System.out.println("An error occurred.");
        }

        AddToTable(count);
    }
    
    public void AddToTable(int count) {
        for (int i = 0; i < count; i++) {
            table.getItems().add(customerList.get(i));
        }
    }

    public void WriteListToText() {

        File file = new File("customers.txt");
        FileWriter fr = null;

        try {
            fr = new FileWriter(file, true);
            BufferedWriter br = new BufferedWriter(fr);
            PrintWriter pr = new PrintWriter(br);

            for (int i = 0; i < customerList.size(); i++) {
                if (i == 0) {
                    pr.print(customerList.get(i).username + "\n" + customerList.get(i).password + "\n" + customerList.get(i).points);
                }
                else {
                    pr.print("\n" + customerList.get(i).username + "\n" + customerList.get(i).password + "\n" + customerList.get(i).points);
                }
            }

            pr.close();
            br.close();
            fr.close();
        } 
        catch (IOException ex) {
            Logger.getLogger(OwnerCustomerScreen.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

