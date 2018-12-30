package controllers;

import com.jfoenix.controls.JFXToggleButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import jssc.SerialPortList;
import serialPort.Communication;
import serialPort.DataSendTask;
import serialPort.InputStreamMonitor;
import serialPort.OutputStreamLoop;
import settings.AppSettings;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    public Communication getCommunication() {
        return communication;
    }

    private Communication communication;
    private ObservableList<String> portNames;
    private AppSettings appSettings;

    @FXML
    private TextArea incomingArea, logArea;

    @FXML
    private TextField writeArea, timeArea;

    @FXML
    private CheckBox autoSubmit;

    @FXML
    private Button submitButton, openBtn, closeBtn, refreshBtn;

    @FXML
    private ChoiceBox portList;

    @FXML
    public JFXToggleButton loopToggle;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        communication = new Communication();
        appSettings = new AppSettings();
        portNames = FXCollections.observableArrayList();
        portNames.setAll(SerialPortList.getPortNames());

        portList.setItems(portNames);
        String storedPort = appSettings.readPref(AppSettings.KEY_COM_PORT,"");
        if (portList.getItems().contains(storedPort)) portList.getSelectionModel().select(storedPort);
        else if(!storedPort.equals("")) addToLog("Device not found on " + storedPort);

        timeArea.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                timeArea.setText(newValue.replaceAll("[^\\d]", ""));
                addToLog("only numbers allowed");
            }else if(timeArea.getText().startsWith("0")){
                addToLog("number can't start with 0");
                timeArea.setText(timeArea.getText().replaceFirst("0",""));
            }
        });

        guiStateInitializable();
    }

    @FXML
    void closeConnection() {
        if (communication.stop()){
            guiStateInitializable();
        }else {
            addToLog("Closing connection error");
        }

    }

    @FXML
    void openConnection() {
        String portName = portList.getSelectionModel().getSelectedItem().toString();
        appSettings.savePref(AppSettings.KEY_COM_PORT, portName);
        addToLog("Opening: " + portName);
        if (communication.begin(portName)){
            addToLog("Port opened!");
            InputStreamMonitor inputStreamMonitor = new InputStreamMonitor(this);
            inputStreamMonitor.execute();
            guiStateConnected();
        }
        else addToLog("Port open fail!");
    }

    @FXML
    void submit() {
        System.out.println("Write: " +  writeArea.getText());
        DataSendTask dataSendTask = new DataSendTask(this);
        dataSendTask.setDataToSend(writeArea.getText());
        dataSendTask.execute();
    }

    @FXML
    void refreshPortList() {
        portNames.setAll(SerialPortList.getPortNames());
        portList.setItems(portNames);

    }

    @FXML
    void userTypeAction() {
        String userType = writeArea.getText();
        String lastChar = userType.substring(userType.length()-1);
        if (autoSubmit.isSelected()){
            System.out.println("Auto write: " +  lastChar);
            communication.write(lastChar);
        }
    }

    @FXML
    void loopSwipe(ActionEvent event) {
            OutputStreamLoop osl = new OutputStreamLoop(this);
            JFXToggleButton toggle = (JFXToggleButton) event.getSource();
            if (toggle.isSelected()){
                osl.execute();
            }else {
                osl.destroy();
            }
    }


    public void addToLog(String logString){
        logArea.appendText(logString + "\n");
    }

    public void setIncoming(String incomingText){
        incomingArea.appendText(incomingText);
    }

    public int getLoopTime() {
        String tempString = timeArea.getText();
        if (tempString == null || tempString.equals("")) return 0;
        return Integer.parseInt(tempString);

    }

    public void guiStateInitializable(){
        incomingArea.setDisable(true);
        writeArea.setDisable(true);
        submitButton.setDisable(true);
        autoSubmit.setDisable(true);
        timeArea.setDisable(true);
        loopToggle.setDisable(true);
        closeBtn.setDisable(true);
        openBtn.setDisable(false);
        portList.setDisable(false);
        refreshBtn.setDisable(false);
    }

    public void guiStateConnected(){
        incomingArea.setDisable(false);
        writeArea.setDisable(false);
        submitButton.setDisable(false);
        autoSubmit.setDisable(false);
        timeArea.setDisable(false);
        loopToggle.setDisable(false);
        closeBtn.setDisable(false);
        openBtn.setDisable(true);
        portList.setDisable(true);
        refreshBtn.setDisable(true);
    }



}
