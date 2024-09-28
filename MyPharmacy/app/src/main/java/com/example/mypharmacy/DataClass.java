package com.example.mypharmacy;




public class DataClass {

    private String dataTitle;
    private String dataDesc;
    private String dataLang;
    private String dataImage;
    private String key;
    private String dataCate;

    private String date;
    private String username;



    public DataClass(String title, String s, String lang, String image, String currentDate, String username,String cate) {

        this.dataTitle = title;
        this.dataDesc = s;
        this.dataLang = lang;
        this.dataImage = image;
        this.date = currentDate;
        this.username = username;
        this.dataCate = cate;
    }

    public DataClass(String title, String s, String lang, String image, String currentDate, String username) {
        this.dataTitle = title;
        this.dataDesc = s;
        this.dataLang = lang;
        this.dataImage = image;
        this.date = currentDate;
        this.username = username;
    }


    public String getDataCate() {
        return dataCate;
    }

    public void setDataCate(String dataCate) {
        this.dataCate = dataCate;
    }

    public String getKey() {
        return key;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getDataTitle() {
        return dataTitle;
    }

    public String getDataDesc() {
        return dataDesc;
    }

    public String getDataLang() {
        return dataLang;
    }

    public String getDataImage() {
        return dataImage;
    }

    public String getImageUrl() {
        return dataImage;
    }

    // Getter method for language
    public String getLanguage() {
        return dataLang;
    }

    // Getter method for description
    public String getDescription() {
        return dataDesc;
    }

    public DataClass(String dataTitle, String dataDesc, String dataLang, String dataImage) {
        this.dataTitle = dataTitle;
        this.dataDesc = dataDesc;
        this.dataLang = dataLang;
        this.dataImage = dataImage;
    }



    public DataClass(){

    }
}
