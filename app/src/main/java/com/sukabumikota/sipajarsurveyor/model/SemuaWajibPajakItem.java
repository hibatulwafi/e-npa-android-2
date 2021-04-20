package com.sukabumikota.sipajarsurveyor.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class SemuaWajibPajakItem implements Parcelable{
    @SerializedName("wp_id")
    private String wp_id;
    @SerializedName("npwpd")
    private String npwpd;
    @SerializedName("nama")
    private String nama;
    @SerializedName("alamat")
    private String alamat;
    @SerializedName("tanggal_daftar")
    private String tanggal_daftar;
    @SerializedName("email")
    private String email;
    @SerializedName("telp")
    private String telp;

    public String getWp_id() {
        return wp_id;
    }

    public void setWp_id(String wp_id) {
        this.wp_id = wp_id;
    }

    public String getNpwpd() {
        return npwpd;
    }

    public void setNpwpd(String npwpd) {
        this.npwpd = npwpd;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getTanggal_daftar() {
        return tanggal_daftar;
    }

    public void setTanggal_daftar(String tanggal_daftar) {
        this.tanggal_daftar = tanggal_daftar;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelp() {
        return telp;
    }

    public void setTelp(String telp) {
        this.telp = telp;
    }
    @Override
    public String toString(){
        return "wajibpajak{" +
                "wp_id= '" +wp_id + '\'' +
                ",npwpd = '" +npwpd + '\'' +
                ",nama = '" +nama + '\'' +
                ",alamat = '" +alamat + '\'' +
                ",tanggal_daftar = '" +tanggal_daftar + '\'' +
                ",email = '" +email + '\'' +
                ",telp = '" +telp + '\'' +
                "}";

    }

    protected SemuaWajibPajakItem(Parcel in) {
        this.wp_id = in.readString();
        this.npwpd = in.readString();
        this.nama = in.readString();
        this.alamat = in.readString();
        this.tanggal_daftar = in.readString();
        this.email = in.readString();
        this.telp = in.readString();

    }

    public static final Parcelable.Creator<SemuaWajibPajakItem> CREATOR = new Parcelable.Creator<SemuaWajibPajakItem>() {
        @Override
        public SemuaWajibPajakItem createFromParcel(Parcel in) {
            return new SemuaWajibPajakItem(in);
        }

        @Override
        public SemuaWajibPajakItem[] newArray(int size) {
            return new SemuaWajibPajakItem[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.wp_id);
        dest.writeString(this.npwpd);
        dest.writeString(this.nama);
        dest.writeString(this.alamat);
        dest.writeString(this.tanggal_daftar);
        dest.writeString(this.email);
        dest.writeString(this.telp);

    }
}