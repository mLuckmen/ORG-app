package id.ac.telkomuniversity.dph3a4.org.Model;

public class User {

    private int nim;
    private String username, password, nama, jabatan, noWA, noHP, idLine, foto, prodi, nim_pengurus;

    public User(int nim, String username, String password, String nama, String jabatan, String noWA, String noHP, String idLine, String foto, String prodi, String nim_pengurus) {
        this.nim = nim;
        this.username = username;
        this.password = password;
        this.nama = nama;
        this.jabatan = jabatan;
        this.noWA = noWA;
        this.noHP = noHP;
        this.idLine = idLine;
        this.foto = foto;
        this.prodi = prodi;
        this.nim_pengurus = nim_pengurus;
    }

    public int getNim() {
        return nim;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getNama() {
        return nama;
    }

    public String getJabatan() {
        return jabatan;
    }

    public String getNoWA() {
        return noWA;
    }

    public String getNoHP() {
        return noHP;
    }

    public String getIdLine() {
        return idLine;
    }

    public String getFoto() {
        return foto;
    }

    public String getProdi() {
        return prodi;
    }

    public String getNim_pengurus() {
        return nim_pengurus;
    }
}
