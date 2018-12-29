package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import jssc.SerialPort;
import jssc.SerialPortList;
import serialPort.Communication;
import serialPort.PortMonitor;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {


    public Communication getCommunication() {
        return communication;
    }

    private Communication communication;
    private ObservableList<String> portNames;

    @FXML
    private TextArea incomingArea, logArea;

    @FXML
    private TextField writeArea;

    @FXML
    private CheckBox autoSubmit;

    @FXML
    private Button submitButton, openBtn, closeBtn;

    @FXML
    private ChoiceBox portList;

    @FXML
    void closeConnection(ActionEvent event) {
        communication.stop();
    }

    @FXML
    void openConnection(ActionEvent event) {
        String portName = portList.getSelectionModel().getSelectedItem().toString();
        addToLog("Opening: " + portName);
        if (communication.begin(portName)){
            addToLog("Port opened!");
            PortMonitor portMonitor = new PortMonitor(this);
            portMonitor.execute();

        }
        else addToLog("Port open fail!");
    }

    @FXML
    void submit() {
        System.out.println("Write: " +  writeArea.getText());
        communication.write(writeArea.getText());
    }


    @FXML
    void refreshPortList(ActionEvent event) {
        portNames.setAll(SerialPortList.getPortNames());
        portList.setItems(portNames);

    }


    @FXML
    void userTypeAction(KeyEvent event) {
        String userType = writeArea.getText();
        String lastChar = userType.substring(userType.length()-1);
        if (autoSubmit.isSelected()){
            System.out.println("Auto write: " +  lastChar);
            communication.write(lastChar);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        communication = new Communication();
        portNames = FXCollections.observableArrayList();
        portNames.setAll(SerialPortList.getPortNames());
        portList.setItems(portNames);
    }

    public void addToLog(String logString){
        logArea.appendText(logString + "\n");
    }

    public void setIncoming(String incomingText){
        incomingArea.appendText(incomingText);
    }

}
