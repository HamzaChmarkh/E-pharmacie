package com.example.mypharmacy;

import java.util.ArrayList;
import java.util.Date;

public class Payment {
    private String cardNumber;
    private String ville;
    private String rue;
    private String date;

    private  String username;

    private String total;
    private ArrayList<String> items;


    public Payment(String cardNumber, String ville, String rue, String date,String total, String username,ArrayList<String> items) {
        this.cardNumber = cardNumber;
        this.ville = ville;
        this.rue = rue;
        this.date = date;
        this.total=total;
        this.username=username;
        this.items = items;


    }


    public ArrayList<String> getItems() {
        return items;
    }

    public void setItems(ArrayList<String> items) {
        this.items = items;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    // Getters and setters
    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }



    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getRue() {
        return rue;
    }

    public void setRue(String rue) {
        rue = rue;
    }
}