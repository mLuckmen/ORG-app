package id.ac.telkomuniversity.dph3a4.org.Utils;

import android.content.Context;
import android.content.SharedPreferences;

import id.ac.telkomuniversity.dph3a4.org.Model.User;

public class SharedPrefManager {
    public static final String SP_ORG_APP = "OrgApp";

    public static final String SP_LOGGED_IN = "spLoggedIn";

    public static SharedPrefManager mInstance;

    Context mContext;

    SharedPreferences sp;
    SharedPreferences.Editor spEditor;

    public SharedPrefManager(Context context) {
        this.mContext = context;

        sp = mContext.getSharedPreferences(SP_ORG_APP, Context.MODE_PRIVATE);
        spEditor = sp.edit();
    }

    public static synchronized SharedPrefManager getInstance(Context mContext){
        if (mInstance == null) {
            mInstance = new SharedPrefManager(mContext);
        }
        return mInstance;
    }

    public void saveUser(int nim, String username, String password, String nama, String noWA, String noHP, String idLine, String foto, String prodi) {
        spEditor.putInt("nim", nim);
        spEditor.putString("username", username);
        spEditor.putString("password", password);
        spEditor.putString("nama", nama);
        spEditor.putString("noWA", noWA);
        spEditor.putString("noHP", noHP);
        spEditor.putString("idLine", idLine);
        spEditor.putString("foto", foto);
        spEditor.putString("prodi", prodi);

        spEditor.apply();
    }

    public boolean isLoggedIn() {
        return sp.getInt("nim", -1) != -1;
    }

    public User getUser() {
        return new User(
                sp.getInt("nim", 0),
                sp.getString("username", null),
                sp.getString("password", null),
                sp.getString("nama", null),
                sp.getString("jabatan", null),
                sp.getString("noWa", null),
                sp.getString("noHP", null),
                sp.getString("idLine", null),
                sp.getString("foto", null),
                sp.getString("prodi", null),
                sp.getString("nim_pengurus", null)
        );
    }

    public void clearSP() {
        spEditor.clear();
        spEditor.apply();
    }

    public void saveSPBoolean(String keySP, boolean value) {
        spEditor.putBoolean(keySP, value);
        spEditor.commit();
    }

    public Boolean getSpLoggedIn() {
        return sp.getBoolean(SP_LOGGED_IN, false);
    }
}
