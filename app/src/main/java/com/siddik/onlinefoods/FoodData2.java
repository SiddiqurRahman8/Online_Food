package com.siddik.onlinefoods;

public class FoodData2 {
 private String itemName;
 private String cname;
 private String phone;
 private String address;
 private String quantity;
 private String itemPrice;
 private String itemImage;
 private String key;

    public FoodData2(String itemName, String cname, String phone, String address, String quantity, String itemPrice, String itemImage, String key) {
        this.itemName = itemName;
        this.cname = cname;
        this.phone = phone;
        this.address = address;
        this.quantity = quantity;
        this.itemPrice = itemPrice;
        this.itemImage = itemImage;
        this.key = key;
    }

    public FoodData2(String toString, String toString1, String toString2, String toString3, String toString4, String toString5, String imageUrl) {
    }

    public String getItemName() {
        return itemName;
    }

    public String getCname() {
        return cname;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getItemPrice() {
        return itemPrice;
    }

    public String getItemImage() {
        return itemImage;
    }

    public String getKey() {
        return key;
    }
}

