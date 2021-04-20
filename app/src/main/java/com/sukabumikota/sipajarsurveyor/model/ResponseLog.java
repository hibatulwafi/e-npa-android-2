package com.sukabumikota.sipajarsurveyor.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class ResponseLog {
    @SerializedName("logdata")
    private List<SemuaLogItem> logdata;

    @SerializedName("error")
    private boolean error;

    @SerializedName("message")
    private String message;

    public List<SemuaLogItem> getLog() {
        return logdata;
    }

    public void setLog(List<SemuaLogItem> logdata) {
        this.logdata = logdata;
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
                "ResponseLog{" +
                        "logdata = '" +logdata + '\'' +
                        ",error = '" +error + '\'' +
                        ",message = '" +message + '\''+
                        "}";
    }
}
