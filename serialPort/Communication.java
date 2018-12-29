package serialPort;

import jssc.SerialPort;
import jssc.SerialPortException;

import javax.validation.constraints.Null;

public class Communication {

    private SerialPort serialPort;


    public SerialPort getSerialPort() {
        return serialPort;
    }

    private boolean isSerialAlive() {
        if (serialPort == null) return false;
        return serialPort.isOpened();
    }

    public boolean begin(String portName) {
        serialPort = new SerialPort(portName);
        try {
            return serialPort.openPort();
        } catch (SerialPortException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean stop() {
        if (serialPort != null) return false;
        if (serialPort.isOpened()) {
            try {
                return serialPort.closePort();
            } catch (SerialPortException e) {
                System.out.println(e.getMessage());
            }
        }
        return false;
    }

    public String read() throws SerialPortException {
        StringBuilder stringBuilder = new StringBuilder();
        if (isSerialAlive()) {
            try{
                String temp = new String(serialPort.readBytes());
                if (temp != null && temp.length()>0) stringBuilder.append(temp);
            }catch (NullPointerException e){
                return "";
            }

        }
        return stringBuilder.toString();
    }

    public boolean write(String string) {
        if (isSerialAlive()) {
            try {
                return serialPort.writeBytes(string.getBytes());
            } catch (SerialPortException e) {
                System.out.println(e.getMessage());
            }
        }
        return false;
    }

}

