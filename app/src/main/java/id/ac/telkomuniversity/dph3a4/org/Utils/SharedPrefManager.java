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

    public void saveUser(int nim, String username, String password, String nama, String jabatan, String noWa, String noHP, String idLine, String foto, String prodi, String nim_pengurus) {
        spEditor.putInt("nim", nim);
        spEditor.putString("username", username);
        spEditor.putString("password", password);
        spEditor.putString("nama", nama);
        spEditor.putString("jabatan", jabatan);
        spEditor.putString("noWa", noWa);
        spEditor.putString("noHP", noHP);
        spEditor.putString("idLine", idLine);
        spEditor.putString("foto", foto);
        spEditor.putString("prodi", prodi);
        spEditor.putString("nim_pengurus", nim_pengurus);

//        spEditor.putInt("nim", user.getNim());
//        spEditor.putString("username", user.getUsername());
//        spEditor.putString("password", user.getPassword());
//        spEditor.putString("nama", user.getNama());
//        spEditor.putString("jabatan", user.getJabatan());
//        spEditor.putString("noWa", user.getNoWA());
//        spEditor.putString("noHP", user.getNoHP());
//        spEditor.putString("idLine", user.getIdLine());
//        spEditor.putString("foto", user.getFoto());
//        spEditor.putString("prodi", user.getProdi());
//        spEditor.putString("nim_pengurus", user.getNim_pengurus());


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
