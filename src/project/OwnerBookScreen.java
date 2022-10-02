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
import javafx.geometry.Insets;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 *
 * @author Rashique 
 */
public class OwnerBookScreen extends Application {

    String bookNameAdded;
    double bookPriceAdded;
    boolean checkAddedBookPrice = false;
    boolean checkAddedBookName = false;
    TableView<Book> table;
    public ArrayList<Book> bookList;
    public Scene sceneOwnerBook = null;
    private static Stage ownerBookStage;
    
    @Override
    public void start(Stage primaryStage) {
        ownerBookStage = primaryStage;
            
        ownerBookStage.setOnCloseRequest(e -> {
            ownerBookStage.close();
        });
        
        //Book Name Column
        TableColumn<Book, String> bookNameColumn = new TableColumn<>("Book Name");
        bookNameColumn.setMinWidth(400);
        bookNameColumn.setCellValueFactory(new PropertyValueFactory<>("bookName"));
        
        //Book Price Column
        TableColumn<Book, Double> bookPriceColumn = new TableColumn<>("Book Price");
        bookPriceColumn.setMinWidth(145);
        bookPriceColumn.setCellValueFactory(new PropertyValueFactory<>("bookPrice"));
        
        //Table Setup
        table = new TableView<>();
        table.getColumns().addAll(bookNameColumn, bookPriceColumn);
        
        //Initial grid setup
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(8);
        grid.setHgap(10);
        
        Text addBookLabel = new Text("Add A Book:");
        addBookLabel.setStyle("-fx-font-weight: bold");
        GridPane.setConstraints(addBookLabel, 0, 1);
        
        //Add Book Name
        Label addBookNameLabel = new Label("Book Name:");
        GridPane.setConstraints(addBookNameLabel, 0, 4);
        
        //Add Book Name Input
        TextField addBookNameInput = new TextField();
        GridPane.setConstraints(addBookNameInput, 1, 4);
        addBookNameInput.setPromptText("Name");
        addBookNameInput.setMinWidth(100);
        
        //Add Book Price
        Label addBookPriceLabel = new Label("Book Price: $");
        GridPane.setConstraints(addBookPriceLabel, 5, 4);
        
        //Add Book Price Input
        TextField addBookPriceInput = new TextField();
        GridPane.setConstraints(addBookPriceInput, 6, 4);
        addBookPriceInput.setPromptText("Price");         
        
        //Initial Table
        AutoInsertTable();      
        
        //Add Book Button
        Button btnAddBook = new Button("Add Book");
        GridPane.setConstraints(btnAddBook, 0, 7);
        btnAddBook.setOnAction(e -> {
            try
            {
                Double.parseDouble(addBookPriceInput.getText());
                checkAddedBookPrice = true;
            }
            catch(NumberFormatException s)
            {
                checkAddedBookPrice = false;
            }
            
            if ((checkAddedBookPrice == true) && !(addBookNameInput.getText().equals(""))) {
                bookNameAdded = addBookNameInput.getText();
                bookPriceAdded = Double.parseDouble(addBookPriceInput.getText());
                       
                System.out.println("\nBook Being Added:");
                System.out.println("Name: " + bookNameAdded + "  Price: $" + bookPriceAdded);
                System.out.println("Status: Added!");
                System.out.println("");
               
                Book book = new Book();
                book.setBookName(bookNameAdded);
                book.setBookPrice(bookPriceAdded);
                bookList.add(new Book(bookNameAdded, bookPriceAdded));
                table.getItems().add(book);
                
                PrintWriter pw = null;
        
                File file = new File("books.txt");
                FileWriter fr = null;
                try {
                    fr = new FileWriter(file, true);
                    BufferedWriter br = new BufferedWriter(fr);
                    PrintWriter pr = new PrintWriter(br);
                    if (file.length() == 0) {
                        pr.print(bookNameAdded + "\n" + bookPriceAdded);
                    }
                    else {
                        pr.print("\n" + bookNameAdded + "\n" + bookPriceAdded);
                    }
                    pr.close();
                    br.close();
                    fr.close();
                } catch (IOException ex) {
                    Logger.getLogger(OwnerBookScreen.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            else if ((checkAddedBookPrice == true) && (addBookNameInput.getText().equals(""))){
                JOptionPane.showMessageDialog(null, "Please enter a valid book name!");

                System.out.println("\nBook Being Added:");
                System.out.println("Name: " + addBookNameInput.getText() + "  Price: $" + addBookPriceInput.getText());
                System.out.println("Status: Not Added!");
                System.out.println("Reason: Invalid Book Name!");
                System.out.println("");
            }
            else if ((checkAddedBookPrice == false) && !(addBookNameInput.getText().equals(""))){
                JOptionPane.showMessageDialog(null, "Please enter a valid book price!");
          
                System.out.println("\nBook Being Added:");
                System.out.println("Name: " + addBookNameInput.getText() + "  Price: $" + addBookPriceInput.getText());
                System.out.println("Status: Not Added!");
                System.out.println("Reason: Invalid Book Price!");
                System.out.println("");
            }
            else if ((checkAddedBookPrice == false) && (addBookNameInput.getText().equals(""))){
                JOptionPane.showMessageDialog(null, "Please enter a valid book name and price!");
                                
                System.out.println("\nBook Being Added:");
                System.out.println("Name: " + addBookNameInput.getText() + "  Price: $" + addBookPriceInput.getText());
                System.out.println("Status: Not Added!");
                System.out.println("Reason: Invalid Book Name And Price!");
                System.out.println("");
            }
            
            addBookNameInput.clear();
            addBookPriceInput.clear();
        });
        
        //Delete Book Button
        Button btnDeleteBook = new Button("Delete Book");
        GridPane.setConstraints(btnDeleteBook, 0, 13);
        btnDeleteBook.setOnAction(e -> { 
 
            ObservableList<Book> booksSelected, allBooks;
            allBooks = table.getItems();
            booksSelected = table.getSelectionModel().getSelectedItems();
            
            for (int i = 0; i < booksSelected.size(); i++) {
                    TablePosition pos = table.getSelectionModel().getSelectedCells().get(i);
                    int row = pos.getRow();
                    Book item = table.getItems().get(row);
                    
                    System.out.println("\nBook To Be Deleted:");
                    System.out.println("Book Name: " + item.getBookName() + "   Book Price: $" + item.getBookPrice());
                    System.out.println("Status: Deleted!");
                    System.out.println("");
 
                    bookList.remove(row);
                    PrintWriter pw;
                    try {
                        pw = new PrintWriter("books.txt");
                        pw.close();
                        WriteListToText();
                    } 
                    catch (FileNotFoundException ex) {
                        Logger.getLogger(OwnerBookScreen.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
            }   
            
            booksSelected.forEach(allBooks::remove);
        });

        //Back Button
        Button btnBack = new Button("Back");
        GridPane.setConstraints(btnBack, 0, 15);
        btnBack.setOnAction(e -> { 
            System.out.println("Going Back To Owner Menu!");
            
            OwnerStartScreen oSs = new OwnerStartScreen();
            oSs.start(primaryStage);
        });
        
        //grid Final Setup
        grid.getChildren().addAll(addBookLabel, addBookNameLabel, addBookNameInput, addBookPriceLabel, addBookPriceInput, btnAddBook, btnDeleteBook, btnBack);
        
        //VBox Setup for table
        VBox vBox = new VBox(10);
        vBox.setPadding(new Insets(20, 20, 20, 20));
        vBox.getChildren().addAll(table, grid);
        
        sceneOwnerBook = new Scene(vBox, 602, 602);
        ownerBookStage.setTitle("Bookstore App");
        ownerBookStage.setScene(sceneOwnerBook);
        ownerBookStage.show();
    }
    
    public void AutoInsertTable() {    
        int count = 0;
        
        try {     
            File f = new File("books.txt");
            
            Scanner scan = new Scanner(f);            
            
            this.bookList = new ArrayList<Book>();
            while(scan.hasNextLine()) {                
                bookList.add(new Book((String) scan.nextLine(), Double.parseDouble(scan.nextLine()))); 
                count++;       
            }
            
        } catch (IOException e) {
            System.out.println("An error occurred.");
        }
        
        AddToTable(count);
    }
    
    public void AddToTable(int count) {
        for (int i = 0; i < count; i++) {
            table.getItems().add(bookList.get(i));
        }
    }
    
    public void WriteListToText() {

        File file = new File("books.txt");
        FileWriter fr = null;
        
        try {
            fr = new FileWriter(file, true);
            BufferedWriter br = new BufferedWriter(fr);
            PrintWriter pr = new PrintWriter(br);
            
            for (int i = 0; i < bookList.size(); i++) {
                if (i == 0) {
                    pr.print(bookList.get(i).bookName + "\n" + bookList.get(i).bookPrice);
                }
                else {
                    pr.print("\n" + bookList.get(i).bookName + "\n" + bookList.get(i).bookPrice);
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
    
}

