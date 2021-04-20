package com.sukabumikota.sipajarsurveyor.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class SemuaLogItem implements Parcelable {
    @SerializedName("id")
    private String id;
    @SerializedName("nama")
    private String nama;
    @SerializedName("tanggal")
    private String tanggal;

    public String getId() {return id;}

    public void setId(String id) {this.id = id;}

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    @Override
    public String toString(){
        return "log{" +
                ",id = '" +id + '\'' +
                ",nama = '" +nama + '\'' +
                ",tanggal = '" +tanggal + '\'' +
                "}";
    }

    protected SemuaLogItem(Parcel in) {
        this.id = in.readString();
        this.nama = in.readString();
        this.tanggal = in.readString();

    }

    public static final Creator<SemuaLogItem> CREATOR = new Creator<SemuaLogItem>() {
        @Override
        public SemuaLogItem createFromParcel(Parcel in) {
            return new SemuaLogItem(in);
        }

        @Override
        public SemuaLogItem[] newArray(int size) {
            return new SemuaLogItem[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.nama);
        dest.writeString(this.tanggal);

    }
}
