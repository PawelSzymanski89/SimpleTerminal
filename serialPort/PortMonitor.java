package serialPort;

import com.victorlaerte.asynctask.AsyncTask;
import controllers.MainController;
import jssc.SerialPort;
import jssc.SerialPortException;

public class PortMonitor extends AsyncTask {

    MainController mainController;

    public PortMonitor(MainController mainController) {
        this.mainController = mainController;
    }

    @Override
    public void onPreExecute() {

    }

    @Override
    public Object doInBackground(Object[] objects) {

        if (mainController!=null){
            Communication c = mainController.getCommunication();
            while (c.getSerialPort().isOpened()){
                try {
                    String readed = c.read();
                    mainController.setIncoming(readed);
                } catch (SerialPortException e) {
                    e.printStackTrace();
                }
            }

        }

        return null;
    }

    @Override
    public void onPostExecute(Object o) {
        mainController.addToLog("Port CLOSED!");
    }

    @Override
    public void progressCallback(Object[] objects) {

    }
}
