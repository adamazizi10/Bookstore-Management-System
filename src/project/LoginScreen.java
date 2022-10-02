package project;

/**
 *
 * @author Rashique 
 */
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import javafx.application.Application;
import static javafx.geometry.HPos.RIGHT;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class LoginScreen extends Application {
    
    private String user;
    private String pass;
    public ArrayList<User> customerInfo;
    public Stage window;
    GridPane gridExtra = new GridPane();
    TextField userTextField;
    PasswordField pwBox;        
            
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Bookstore App");
        window = primaryStage;
        
        primaryStage.setOnCloseRequest(e -> {
            primaryStage.close();
        });       
        
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        
        Text scenetitle = new Text("Welcome to the BookStore App");
        scenetitle.setFont(Font.font("Times New Roman", FontWeight.NORMAL, 20));
        grid.add(scenetitle, 0, 0, 2, 1);

        Label userName = new Label("Username:");
        grid.add(userName, 0, 3);

        userTextField = new TextField();
        grid.add(userTextField, 1, 3);

        Label pw = new Label("Password:");
        grid.add(pw, 0, 4);

        pwBox = new PasswordField();
        grid.add(pwBox, 1, 4);
        
        Button btn = new Button("Login");
        HBox hbBtn = new HBox(10);
        hbBtn.getChildren().add(btn);
        grid.add(hbBtn, 1, 6);
        
        gridExtra = grid;
        
        btn.setOnAction(e -> {
                user = userTextField.getText();
                pass = pwBox.getText();
                System.out.println(user);
                System.out.println(pass);
                
                if (user.equals("admin") && pass.equals("admin")) {
                    OwnerStartScreen oSs = new OwnerStartScreen();
                    oSs.start(primaryStage);
                }
                else {
                    ReadingCustomerInfor();
                }
        });
            
        Scene scene = new Scene(grid, 300, 275);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    public void ReadingCustomerInfor() {
        int count = 0;
        boolean validAccount = false;

        try {
            //Read and append to array
            File f = new File("customers.txt");

            Scanner scan = new Scanner(f);            

            this.customerInfo = new ArrayList<User>();
            while(scan.hasNextLine()) {                
                customerInfo.add(new User(scan.nextLine(), scan.nextLine(), Integer.parseInt(scan.nextLine()))); 
                count++;       
            }
            
            //compare array to user input
            for (int i = 0; i < count; i++) {
                if (customerInfo.get(i).username.equals(user) && customerInfo.get(i).password.equals(pass)) {
                    validAccount = true;
                    CustomerStartScreen cSS = new CustomerStartScreen();
                    cSS.start(window, user);
                }
            }
            
            final Text actiontarget = new Text();
            gridExtra.add(actiontarget, 0, 8);
            gridExtra.setColumnSpan(actiontarget, 2);
            gridExtra.setHalignment(actiontarget, RIGHT);
            actiontarget.setId("actiontarget");
        
            if (validAccount == false) {
                actiontarget.setFill(Color.FIREBRICK);
                actiontarget.setText("Invalid Username or Password!");
                userTextField.clear();
                pwBox.clear();
            }
                
        } catch (IOException e) {
            System.out.println("An error occurred.");
        }
    }
    
    
    public static void main(String[] args) {
        launch(args);
    }

}
