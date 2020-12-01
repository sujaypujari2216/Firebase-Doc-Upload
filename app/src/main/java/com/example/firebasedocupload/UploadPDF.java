package com.example.firebasedocupload;

public class UploadPDF {

    public String name, url;

    public UploadPDF() {
    }

    public UploadPDF(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }
}
