/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package notifcationexamples;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import taskers.*;

/**
 * FXML Controller class
 *
 * @author dalemusser
 */
public class NotificationsUIController implements Initializable, Notifiable {

    @FXML
    private TextArea textArea;
    
    @FXML
    private Button btn1;
    
    @FXML
    private Button btn2;
    
    @FXML
    private Button btn3;
    
    private Task1 task1;
    private Task2 task2;
    private Task3 task3;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
    public void start(Stage stage) {
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we) {
                if (task1 != null) task1.end();
                if (task2 != null) task2.end();
                if (task3 != null) task3.end();
            }
        });
    }
    
    @FXML void clickTask1(ActionEvent event) {
        if (task1 == null) startTask1();
        else endTask1();
    }
    
    @FXML void clickTask2(ActionEvent event) {
        if (task2 == null) startTask2();
        else endTask2();
    }
    
    @FXML void clickTask3(ActionEvent event) {
        if (task3 == null) startTask3();
        else endTask3();
    }
    
    @FXML
    public void startTask1() {
        System.out.println("start task 1");
        if (task1 == null) {
            task1 = new Task1(2147483647, 1000000);
            task1.setNotificationTarget(this);
            task1.start();
            btn1.setText("End Task1");
        }
    }
    
    @Override
    public void notify(String message) {
        if (message.equals("Task1 done.")) {
            task1 = null;
            btn1.setText("Start Task 1");
        }
        textArea.appendText(message + "\n");
    }
    
    @FXML
    public void startTask2() {
        System.out.println("start task 2");
        if (task2 == null) {
            task2 = new Task2(2147483647, 1000000);
            task2.setOnNotification((String message) -> {
                textArea.appendText(message + "\n");
                if (message.equals("Task2 done.")) {
                    task2 = null;
                    btn2.setText("Start Task 2");
                }
            });
            
            task2.start();
            btn2.setText("End Task 2");
        }     
    }
    
    @FXML
    public void startTask3() {
        System.out.println("start task 3");
        if (task3 == null) {
            task3 = new Task3(2147483647, 1000000);
            // this uses a property change listener to get messages
            task3.addPropertyChangeListener((PropertyChangeEvent evt) -> {
                String message = (String)evt.getNewValue();
                textArea.appendText(message + "\n");
                if (message.equals("Task3 done.")) {
                    task3 = null;
                    btn3.setText("Start Task 3");
                }
            });
            
            task3.start();
            btn3.setText("End Task 3");
        }
    }
    
    @FXML
    public void endTask1() {
        if (task1 != null) {
            task1.end();
            task1 = null;
            btn1.setText("Start Task 1");
        }
    }
    
    @FXML
    public void endTask2() {
        if (task2 != null) {
            task2.end();
            task2 = null;
            btn2.setText("Start Task 2");
        }
    }
    
    @FXML
    public void endTask3() {
        if (task3 != null) {
            task3.end();
            task3 = null;
            btn3.setText("Start Task 3");
        }
    }
}
