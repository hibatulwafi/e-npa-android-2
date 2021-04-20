package com.sukabumikota.sipajarsurveyor.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseWajibPajak {

    @SerializedName("wajibpajak")
    private List<SemuaWajibPajakItem> wajibpajak;

    @SerializedName("error")
    private boolean error;

    @SerializedName("message")
    private String message;

    public List<SemuaWajibPajakItem> getWajibpajak() {
        return wajibpajak;
    }

    public void setWajibpajak(List<SemuaWajibPajakItem> wajibpajak) {
        this.wajibpajak = wajibpajak;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }



    @Override
    public String toString(){
        return
                "ResponseWajibPajak{" +
                        "wajibpajak = '" +wajibpajak + '\'' +
                        ",error = '" +error + '\'' +
                        ",message = '" +message + '\''+
                        "}";
    }
}
