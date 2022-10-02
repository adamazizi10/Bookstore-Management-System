/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author Rashique 
 */
public class CustomerCostScreen extends Application {
    
    public void start(Stage primaryStage, double tc, int points) {
        
        primaryStage.setOnCloseRequest(e -> {
            primaryStage.close();
        });


        //Initial grid setup
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(8);
        grid.setHgap(10);
        
        //Customer Transaction Label
        Text transactionLabel = new Text("Customer Transaction Receipt");
        transactionLabel.setFont(Font.font("Times New Roman", FontWeight.NORMAL, 20));
        transactionLabel.setUnderline(true);
        GridPane.setConstraints(transactionLabel, 1, 1);
        
        //Total Cost Label
        Text totalCostLabel = new Text("Total Cost: $" + tc);
        totalCostLabel.setFont(Font.font("Times New Roman", FontWeight.NORMAL, 16));
        GridPane.setConstraints(totalCostLabel, 1, 5);
        
        //Points Label
        Text pointsLabel = new Text("Points: " + points);
        pointsLabel.setFont(Font.font("Times New Roman", FontWeight.NORMAL, 16));
        GridPane.setConstraints(pointsLabel, 1, 8);        
        
        //State Design Pattern
        String status = "";
        if (points < 1000) {
            Customer customer = new Customer();
            customer.setStatus(new Silver() {});
            customer.statusCheck();
            status = customer.getStatus();
        }
        else if (points >= 1000) {
            Customer customer = new Customer();
            customer.setStatus(new Gold() {});
            customer.statusCheck();
            status = customer.getStatus();
        } 
        
        //Status Label
        Text statusLabel = new Text("Status: " + status);
        statusLabel.setFont(Font.font("Times New Roman", FontWeight.NORMAL, 16));
        GridPane.setConstraints(statusLabel, 2, 8);
        
        //Logout Button
        Button btnLogout = new Button("Logout");
        GridPane.setConstraints(btnLogout, 1, 12);
        btnLogout.setOnAction(e -> {
            LoginScreen lS = new LoginScreen();
            lS.start(primaryStage);
        });
    
        //grid Final Setup
        grid.getChildren().addAll(transactionLabel, totalCostLabel, pointsLabel, statusLabel, btnLogout);
        
        Scene sceneCustomerCost = new Scene(grid, 452, 222);
        primaryStage.setTitle("Bookstore App");
        primaryStage.setScene(sceneCustomerCost);
        primaryStage.show();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
