package project;

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
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author Rashique
 */

public class CustomerStartScreen extends Application {

    boolean checkAddedBookPrice = false;
    boolean checkAddedBookName = false;
    TableView<Book> table;
    private ArrayList<Book> customerBookList;
    public Scene sceneOwnerBook = null;
    private static Stage ownerBookStage;
    public ArrayList<User> customerCustomerList;
    private String nameHere;
    public int pointsStatus;
    
    
    
    public void start(Stage primaryStage, String name) {
        ownerBookStage = primaryStage;
        nameHere = name;
            
        ownerBookStage.setOnCloseRequest(e -> {
            ownerBookStage.close();
        });
        
        //Initial grid setup
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(8);
        grid.setHgap(10);
        
        //Filling in Customer Array
        AutoCustomerArray(0);    
        
        int indexPoints = 0;
        
        for (int j = 0; j < customerCustomerList.size(); j++) {
                if(customerCustomerList.get(j).username.equals(nameHere)) {
                    indexPoints = j;
            }
        }
        
        //State Design Pattern Implementation
        String status = "";
        if (customerCustomerList.get(indexPoints).points < 1000) {
            Customer customer = new Customer();
            customer.setStatus(new Silver() {});
            customer.statusCheck();
            status = customer.getStatus();
        }
        else if (customerCustomerList.get(indexPoints).points >= 1000) {
            Customer customer = new Customer();
            customer.setStatus(new Gold() {});
            customer.statusCheck();
            status = customer.getStatus();
        }
        
        System.out.println(status);
        
        //Initial Text Field
        Text userInfoLabel = new Text("\nWelcome " + nameHere + ". You have " + customerCustomerList.get(indexPoints).points + " points. Your status is " + status + ".\n\n");
        userInfoLabel.setFont(Font.font("Times New Roman", FontWeight.NORMAL, 18));
        
        //Book Name Column
        TableColumn<Book, String> bookNameColumn = new TableColumn<>("Book Name");
        bookNameColumn.setMinWidth(350);
        bookNameColumn.setCellValueFactory(new PropertyValueFactory<>("bookName"));
        
        //Book Price Column
        TableColumn<Book, Double> bookPriceColumn = new TableColumn<>("Book Price");
        bookPriceColumn.setMinWidth(145);
        bookPriceColumn.setCellValueFactory(new PropertyValueFactory<>("bookPrice"));
        
        //Book Price Column
        TableColumn bookSelectColumn = new TableColumn<>("Select");
        bookSelectColumn.setMinWidth(50);
        bookSelectColumn.setCellValueFactory(new PropertyValueFactory<>("select"));
        
        //Table Setup
        table = new TableView<>();
        table.getColumns().addAll(bookNameColumn, bookPriceColumn, bookSelectColumn);
        
        //Initial Table
        AutoInsertTableBooks();      
        
        //Buy Button
        Button btnBuy = new Button("Buy");
        GridPane.setConstraints(btnBuy, 0, 2);
        btnBuy.setOnAction(e -> { 
            System.out.println("Buying!");
            
            ObservableList<Book> allBooks;
            allBooks = table.getItems();
            
            ObservableList<Book> dataListRemove = FXCollections.observableArrayList();
            
            double totalPrice = 0;
            int index = 0;
            
            for(Book books : allBooks) {
                if (books.getSelect().isSelected()) {
                    for (int j = 0; j < customerBookList.size(); j++) {
                        if(customerBookList.get(j).bookName.equals(books.bookName)) {
                            totalPrice += customerBookList.get(j).bookPrice;
                            customerBookList.remove(j);
                        }
                    }
                    dataListRemove.add(books);
                }
            }
            
            PrintWriter pw;
            try {
                pw = new PrintWriter("books.txt");
                pw.close();
                WriteListToTextBooks();
            } 
            catch (FileNotFoundException ex) {
                Logger.getLogger(OwnerBookScreen.class.getName()).log(Level.SEVERE, null, ex);
            }    
            
            allBooks.removeAll(dataListRemove);
            
            int addPoints = 0;
            addPoints = (int) (totalPrice * 10);
            AutoCustomerArray(1);
            WriteListToTextCustomers(addPoints);
            
            for (int j = 0; j < customerCustomerList.size(); j++) {
                if(customerCustomerList.get(j).username.equals(nameHere)) {
                    index = j;
                }
            }
   
            CustomerCostScreen cCS = new CustomerCostScreen();
            cCS.start(primaryStage, totalPrice, customerCustomerList.get(index).points);
        });
        
        //Logout Button
        Button btnRedeemAndBuy = new Button("Redeem points and Buy");
        GridPane.setConstraints(btnRedeemAndBuy, 0, 4);
        btnRedeemAndBuy.setOnAction(e -> { 
            System.out.println("Redeeming And Buying!");

            ObservableList<Book> allBooks;
            allBooks = table.getItems();
            
            ObservableList<Book> dataListRemove = FXCollections.observableArrayList();
            
            double totalPrice = 0;
            
            for(Book books : allBooks) {
                if (books.getSelect().isSelected()) {
                    for (int j = 0; j < customerBookList.size(); j++) {
                        if(customerBookList.get(j).bookName.equals(books.bookName)) {
                            totalPrice += customerBookList.get(j).bookPrice;
                            customerBookList.remove(j);
                        }
                    }
                    dataListRemove.add(books);
                }
            }
            
            PrintWriter pw;
            try {
                pw = new PrintWriter("books.txt");
                pw.close();
                WriteListToTextBooks();
            } 
            catch (FileNotFoundException ex) {
                Logger.getLogger(OwnerBookScreen.class.getName()).log(Level.SEVERE, null, ex);
            }    
            
            allBooks.removeAll(dataListRemove);
            
            int addPoints = 0;
            int index = 0;
            double ogPrice = totalPrice;
            
            AutoCustomerArray(1);
            for (int k = 0; k < customerCustomerList.size(); k++) {
                if (customerCustomerList.get(k).username.equals(nameHere)) {
                    totalPrice = totalPrice - ((int) (customerCustomerList.get(k).points / 100));
                    if (totalPrice < 0) {
                        totalPrice = 0;
                    }
                    addPoints = (int) (totalPrice * 10);
                    index = k;
                    
                    if ((customerCustomerList.get(k).points >= 100) && (totalPrice != 0)) {
                        customerCustomerList.get(k).points -= (100 *((int) (customerCustomerList.get(k).points / 100)));
                    }
                    else if ((customerCustomerList.get(k).points >= 100) && (totalPrice == 0)) {
                        customerCustomerList.get(k).points -= (int) ((100 * ogPrice));
                    }
                }
            }
            
            WriteListToTextCustomers(addPoints);
   
            CustomerCostScreen cCS = new CustomerCostScreen();
            cCS.start(primaryStage, totalPrice, customerCustomerList.get(index).points);
   
        });
        
        //Logout Button
        Button btnLogout = new Button("Logout");
        GridPane.setConstraints(btnLogout, 0, 6);
        btnLogout.setOnAction(e -> { 
            System.out.println("Going Back To Login Screen!");
            
            LoginScreen lS = new LoginScreen();
            lS.start(primaryStage);
        }); 
        
        //grid Final Setup
        grid.getChildren().addAll(userInfoLabel, btnBuy, btnRedeemAndBuy, btnLogout);
        
        //VBox Setup for table
        VBox vBox = new VBox(10);
        vBox.setPadding(new Insets(20, 20, 20, 20));
        vBox.getChildren().addAll(userInfoLabel, table, grid);
        
        sceneOwnerBook = new Scene(vBox, 602, 602);
        ownerBookStage.setTitle("Bookstore App");
        ownerBookStage.setScene(sceneOwnerBook);
        ownerBookStage.show();
    }
    
    public void AutoInsertTableBooks() {    
        int count = 0;
        
        try {     
            File f = new File("books.txt");
            
            Scanner scan = new Scanner(f);            
            
            this.customerBookList = new ArrayList<Book>();
            while(scan.hasNextLine()) {                
                customerBookList.add(new Book((String) scan.nextLine(), Double.parseDouble(scan.nextLine()))); 
                count++;       
            }
            
        } catch (IOException e) {
            System.out.println("An error occurred.");
        }
        
        AddToTable(count);
    }
    
    public void AutoCustomerArray(int check) {    
        try {     
            File f = new File("customers.txt");

            Scanner scan = new Scanner(f);            

            this.customerCustomerList = new ArrayList<User>();
            while(scan.hasNextLine()) {                
                customerCustomerList.add(new User(scan.nextLine(), scan.nextLine(), Integer.parseInt(scan.nextLine())));    
            }              
        } catch (IOException e) {
            System.out.println("An error occurred.");
        }
        
        if (check == 1) {
            PrintWriter pw1;
            try {
                pw1 = new PrintWriter("customers.txt");
                pw1.close();                    
            } 
            catch (FileNotFoundException ex) {
                Logger.getLogger(OwnerCustomerScreen.class.getName()).log(Level.SEVERE, null, ex);
            }
        }  
    }
        
    public void AddToTable(int count) {
        for (int i = 0; i < count; i++) {
            table.getItems().add(customerBookList.get(i));
        }
    }
    
    public void WriteListToTextBooks() {

        File file = new File("books.txt");
        FileWriter fr = null;
        
        try {
            fr = new FileWriter(file, true);
            BufferedWriter br = new BufferedWriter(fr);
            PrintWriter pr = new PrintWriter(br);
            
            for (int i = 0; i < customerBookList.size(); i++) {
                if (i == 0) {
                    pr.print(customerBookList.get(i).bookName + "\n" + customerBookList.get(i).bookPrice);
                }
                else {
                    pr.print("\n" + customerBookList.get(i).bookName + "\n" + customerBookList.get(i).bookPrice);
                }
            }
            
            pr.close();
            br.close();
            fr.close();
        } 
        catch (IOException ex) {
            Logger.getLogger(OwnerBookScreen.class.getName()).log(Level.SEVERE, null, ex);
        }
      
    }
    
    public void WriteListToTextCustomers(int points) {

        File file = new File("customers.txt");
        FileWriter fr = null;

        try {
            fr = new FileWriter(file, true);
            BufferedWriter br = new BufferedWriter(fr);
            PrintWriter pr = new PrintWriter(br);

            for (int i = 0; i < customerCustomerList.size(); i++) {
                
                if (nameHere.equals(customerCustomerList.get(i).username)) {
                        customerCustomerList.get(i).points += points;
                }
                
                if (i == 0) { 
                    pr.print(customerCustomerList.get(i).username + "\n" + customerCustomerList.get(i).password + "\n" + customerCustomerList.get(i).points);
                }
                else {
                    pr.print("\n" + customerCustomerList.get(i).username + "\n" + customerCustomerList.get(i).password + "\n" + customerCustomerList.get(i).points);
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
    
    public static void main(String[] args) {
       launch( args );
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}