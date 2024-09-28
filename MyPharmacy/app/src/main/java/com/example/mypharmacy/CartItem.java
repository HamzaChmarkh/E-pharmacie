package com.example.mypharmacy;

public class CartItem {
    private String dataTitle;
    private double totalPrice;
    private String dataDesc;
    private String price;
    private String dataImage;
    private String key;
    private String date;
    private String username;

    private int quantity; // Nouvelle propriété de quantité


    public CartItem() {
    }

    public CartItem(String dataTitle, String dataDesc, String dataLang, String dataImage, String key, String date, String username , int quantity) {
        this.dataTitle = dataTitle;
        this.dataDesc = dataDesc;
        this.price = dataLang;
        this.dataImage = dataImage;
        this.key = key;
        this.date = date;
        this.username = username;
        this.quantity = quantity;
        this.totalPrice = calculateTotalPrice();
    }

    public int getQuantity() {
        return quantity;
    }



    public String getDataTitle() {
        return dataTitle;
    }

    public void setDataTitle(String dataTitle) {
        this.dataTitle = dataTitle;
    }

    public String getDataDesc() {
        return dataDesc;
    }

    public void setDataDesc(String dataDesc) {
        this.dataDesc = dataDesc;
    }

    public String getPrice() {
        return price;
    }

    public void setDataLang(String dataLang) {
        this.price = dataLang;
    }

    public String getDataImage() {
        return dataImage;
    }

    public void setDataImage(String dataImage) {
        this.dataImage = dataImage;
    }

    public String getImageUrl() {
        return dataImage;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
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
    private double calculateTotalPrice() {
        double itemPrice;
        try {
            itemPrice = Double.parseDouble(price);
        } catch (NumberFormatException e) {
            itemPrice = 0; // Gérer les prix invalides
        }
        return itemPrice * quantity;
    }
    public double getTotalPrice() {
        return totalPrice;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
        this.totalPrice = calculateTotalPrice(); // Recalculer le prix total
    }
}