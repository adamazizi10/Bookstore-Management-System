/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author Rashique 
 */
public class OwnerStartScreen extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        
        primaryStage.setOnCloseRequest(e -> {
            primaryStage.close();
        });
        
        Button btnBooks = new Button();
        btnBooks.setText("Books       ");
        btnBooks.setOnAction(e -> { 
            System.out.println("Going To Book Screen!");
            
            OwnerBookScreen g = new OwnerBookScreen();
            g.start(primaryStage);
        });
        
        Button btnCustomers = new Button();
        btnCustomers.setText("Customers");
        btnCustomers.setOnAction(e -> { 
            System.out.println("Going To Customers Screen!");
            
            OwnerCustomerScreen oCs = new OwnerCustomerScreen();
            oCs.start(primaryStage);
        });
 
        Button btnLogout = new Button();
        btnLogout.setText("Logout      ");
        btnLogout.setOnAction(e -> {
            LoginScreen lS = new LoginScreen();
            lS.start(primaryStage);
        });
        
        VBox root = new VBox(20);
        root.getChildren().addAll(btnBooks, btnCustomers, btnLogout);
        root.setAlignment(Pos.CENTER);
        Scene scene = new Scene(root, 280, 150);        
        
        primaryStage.setTitle("Bookstore App");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
}