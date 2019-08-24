package com.exam.hakhiem.productsapp.Model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Product implements Serializable {

    public static final String TABLE_PRODUCT = "Products";

    public static final String COLUMN_PRODUCT_ID = "ProductId";
    public static final String COLUMN_NAME = "Name";
    public static final String COLUMN_PRICE = "Price";
    public static final String COLUMN_IMAGE = "Image";
    public static final String COLUMN_DATE = "Date";

    private String productId;
    private String name;
    private float price;
    private String image;
    private Date date;

    // Create table SQL query
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_PRODUCT + "("
                    + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_PRODUCT_ID + " TEXT,"
                    + COLUMN_NAME + " TEXT,"
                    + COLUMN_PRICE + " REAL,"
                    + COLUMN_IMAGE + " TEXT,"
                    + COLUMN_DATE + " TEXT"
                    + ")";

    public Product() {
    }

    public Product(String productId, String name, float price, String image, Date date) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.image = image;
        this.date = date;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
