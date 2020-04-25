package id.ac.telkomuniversity.dph3a4.org.Model;

public class OrganisationModel {
    private int idOrganisasi;
    private String namaOrganisasi, deskripsi, ketua, logo;

    public int getIdOrganisasi() {
        return idOrganisasi;
    }

    public void setIdOrganisasi(int idOrganisasi) {
        this.idOrganisasi = idOrganisasi;
    }

    public String getNamaOrganisasi() {
        return namaOrganisasi;
    }

    public void setNamaOrganisasi(String namaOrganisasi) {
        this.namaOrganisasi = namaOrganisasi;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getKetua() {
        return ketua;
    }

    public void setKetua(String ketua) {
        this.ketua = ketua;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }
}
