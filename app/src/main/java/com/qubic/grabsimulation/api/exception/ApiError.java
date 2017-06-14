package com.qubic.grabsimulation.api.exception;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class ApiError extends Exception implements Parcelable {

    public static int NETWORK_ERROR = 0;
    public static int UNEXPECTED_ERROR = 1;

    private Integer code;
    private String message;
    private List<String> errors;

    public Integer getIntCode() {
        return code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }

    public String getPreferedErrorMessage() {
        List<String> errors = getErrors();
        if (null != errors && errors.size() > 0) {
            return errors.get(0);
        }

        return getMessage();
    }

    public ApiError(Integer code, String message, List<String> errors) {
        this.code = code;
        this.message = message;
        this.errors = errors;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.code);
        dest.writeString(this.message);
        dest.writeList(this.errors);
    }

    protected ApiError(Parcel in) {
        this.code = in.readInt();
        this.message = in.readString();
        this.errors = new ArrayList<String>();
        in.readList(this.errors, Error.class.getClassLoader());
    }

    public static final Creator<ApiError> CREATOR = new Creator<ApiError>() {
        @Override
        public ApiError createFromParcel(Parcel source) {
            return new ApiError(source);
        }

        @Override
        public ApiError[] newArray(int size) {
            return new ApiError[size];
        }
    };
}
