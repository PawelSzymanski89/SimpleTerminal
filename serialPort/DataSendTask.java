package serialPort;

import com.victorlaerte.asynctask.AsyncTask;
import controllers.MainController;

public class DataSendTask extends AsyncTask {

    private String dataToSend;
    private MainController mainController;

    public DataSendTask(MainController mainController) {
        this.mainController = mainController;
    }

    @Override
    public void onPreExecute() {

    }

    @Override
    public Object doInBackground(Object[] objects) {
        Communication c = mainController.getCommunication();
        if (c.getSerialPort() != null){
            if (c.getSerialPort().isOpened()) c.write(dataToSend);
        }
        return null;
    }

    @Override
    public void onPostExecute(Object o) {
        System.out.println("EOT");
    }

    @Override
    public void progressCallback(Object[] objects) {

    }

    public void destroy(){

    }

    public void setDataToSend(String dataToSend) {
        this.dataToSend = dataToSend;
    }
}
