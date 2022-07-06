package com.example.demo.model;

public class ReturnResponse {

    private Boolean apiCallStatus;
    private String apiMessage;
    private Object apiBodyData;
    private int code;

    private int returnRecords;

    public ReturnResponse(Boolean apiCallStatus, String apiMessage, Object apiBodyData, int code) {
        this.apiCallStatus = apiCallStatus;
        this.apiMessage = apiMessage;
        this.apiBodyData = apiBodyData;
        code = code;
    }

    public int getReturnRecords() {
        return returnRecords;
    }

    public void setReturnRecords(int returnRecords) {
        this.returnRecords = returnRecords;
    }

    public Boolean getApiCallStatus() {
        return apiCallStatus;
    }

    public void setApiCallStatus(Boolean apiCallStatus) {
        this.apiCallStatus = apiCallStatus;
    }

    public String getApiMessage() {
        return apiMessage;
    }

    public void setApiMessage(String apiMessage) {
        this.apiMessage = apiMessage;
    }

    public Object getApiBodyData() {
        return apiBodyData;
    }

    public void setApiBodyData(Object apiBodyData) {
        this.apiBodyData = apiBodyData;
    }

    public int code() {
        return code;
    }

    public void setErrorCode(int errorCode) {
        code = errorCode;
    }

    @Override
    public String toString() {
        return "ReturnResponse{" +
                "apiCallStatus=" + apiCallStatus +
                ", apiMessage='" + apiMessage + '\'' +
                ", apiBodyData=" + apiBodyData +
                ", ErrorCode=" + code +
                '}';
    }
}
