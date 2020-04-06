package id.ac.telkomuniversity.dph3a4.org.Utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefManager {
    public static final String SP_ORG_APP = "OrgApp";

    public static final String SP_USERNAME = "spUsername";
    public static final String SP_NAME = "spName";

    public static final String SP_LOGGED_IN = "spLoggedIn";

    SharedPreferences sp;
    SharedPreferences.Editor spEditor;

    public SharedPrefManager(Context context) {
        sp = context.getSharedPreferences(SP_ORG_APP, Context.MODE_PRIVATE);
        spEditor = sp.edit();
    }

    public void saveSPString(String keySP, String value) {
        spEditor.putString(keySP, value);
        spEditor.commit();
    }

    public void saveSPInt(String keySP, int value) {
        spEditor.putInt(keySP, value);
        spEditor.commit();
    }

    public void saveSPBoolean(String keySP, boolean value) {
        spEditor.putBoolean(keySP, value);
        spEditor.commit();
    }

    public String getSPUsername() {
        return sp.getString(SP_USERNAME, "");
    }

    public String getSPName() {
        return sp.getString(SP_NAME, "");
    }

    public Boolean getSpLoggedIn() {
        return sp.getBoolean(SP_LOGGED_IN, false);
    }
}
