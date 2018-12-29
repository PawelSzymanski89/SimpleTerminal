package settings;

import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

public class AppSettings {

    //SETTINGS KEYS
    public static String KEY_COM = "key_com";
    public static String KEY_COM_PORT = "key_com_port";
    public static String KEY_BAUD = "key_baud";

    private Preferences preferences;

    public AppSettings() {
        preferences = Preferences.userRoot().node("<temporary>");
    }

    public void savePref(String key, String value) {
        preferences.put(key, value);
        try {
            preferences.flush();
        } catch (BackingStoreException e) {
            System.out.println("Settings save error;");
        }
        System.out.println("Saved: " + key + " " + value);
    }

    public void savePref(String key, int value) {
        preferences.putInt(key, value);
        try {
            preferences.flush();
        } catch (BackingStoreException e) {
            System.out.println("Settings save error;");
        }
        System.out.println("Saved: " + key + " " + value);
    }

    public void savePref(String key, boolean value) {
        preferences.putBoolean(key, value);
        try {
            preferences.flush();
        } catch (BackingStoreException e) {
            System.out.println("Settings save error;");
        }
        System.out.println("Saved: " + key + " " + value);
    }

    public String readPref(String key, String defValue) {
        return preferences.get(key, defValue);
    }

    public int readPref(String key, int defValue) {
        return preferences.getInt(key, defValue);
    }

    public boolean readPref(String key, boolean defValue) {
        return preferences.getBoolean(key, defValue);
    }
}
