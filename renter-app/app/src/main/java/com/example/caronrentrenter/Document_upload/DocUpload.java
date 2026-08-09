package com.example.caronrentrenter.Document_upload;

public class DocUpload {

    private String dlNumber;

    private String dlPic;

    public DocUpload() {
    }

    public DocUpload(String dlNumber, String dlPic) {
        this.dlNumber = dlNumber;
        this.dlPic = dlPic;
    }

    public String getDlNumber() {
        return dlNumber;
    }

    public void setDlNumber(String dlNumber) {
        this.dlNumber = dlNumber;
    }

    public String getDlPic() {
        return dlPic;
    }

    public void setDlPic(String dlPic) {
        this.dlPic = dlPic;
    }
}