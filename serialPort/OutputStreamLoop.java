package serialPort;

import com.victorlaerte.asynctask.AsyncTask;
import controllers.MainController;

public class OutputStreamLoop extends AsyncTask {

    private MainController mainController;
    private boolean isDestroyed;

    public OutputStreamLoop(MainController mainController) {
        this.mainController = mainController;
    }

    @Override
    public void onPreExecute() {
        isDestroyed = false;
    }

    @Override
    public Object doInBackground(Object[] objects) {
        Communication c = mainController.getCommunication();
        if (c.getSerialPort() != null){
            if (c.getSerialPort().isOpened()) {
                int timeLoop = mainController.getLoopTime();
                if (c.getSerialPort().isOpened()) {

                }
            }else {
                mainController.addToLog("Connect to serial before start loop!");
                mainController.loopToggle.setSelected(false);
            }
        }else {
            mainController.addToLog("Connect to serial before start loop!");
            mainController.loopToggle.setSelected(false);
        }
        return null;
    }

    @Override
    public void onPostExecute(Object o) {

    }

    @Override
    public void progressCallback(Object[] objects) {

    }

    public void destroy(){

    }
}
