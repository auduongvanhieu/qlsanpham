package com.exam.hakhiem.productsapp.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.exam.hakhiem.productsapp.Model.Product;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DbHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "product_db";


    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        // create products table
        db.execSQL(Product.CREATE_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + Product.TABLE_PRODUCT);
        // Create tables again
        onCreate(db);
    }

    public long insertProduct(Product pro) {
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        // `id` and `timestamp` will be inserted automatically.
        // no need to add them

        values.put(Product.COLUMN_PRODUCT_ID, pro.getProductId());
        values.put(Product.COLUMN_NAME, pro.getName());
        values.put(Product.COLUMN_PRICE, pro.getPrice());
        values.put(Product.COLUMN_IMAGE, pro.getImage());
        values.put(Product.COLUMN_PRODUCT_ID, pro.getProductId());

        // insert row
        long id = db.insert(pro.TABLE_PRODUCT, null, values);

        // close db connection
        db.close();

        // return newly inserted row id
        return id;
    }

    public Product getProduct(String productId) {
        // get readable database as we are not inserting anything
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(Product.TABLE_PRODUCT,
                new String[]{Product.COLUMN_PRODUCT_ID, Product.COLUMN_NAME, Product.COLUMN_PRICE, Product.COLUMN_IMAGE, Product.COLUMN_DATE},
                Product.COLUMN_PRODUCT_ID + "=?",
                new String[]{String.valueOf(productId)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        // prepare pro object
        Product pro = new Product(
                cursor.getString(cursor.getColumnIndex(Product.COLUMN_PRODUCT_ID)),
                cursor.getString(cursor.getColumnIndex(Product.COLUMN_NAME)),
                cursor.getFloat(cursor.getColumnIndex(Product.COLUMN_PRICE)),
                cursor.getString(cursor.getColumnIndex(Product.COLUMN_IMAGE)),
                new Date(cursor.getString(cursor.getColumnIndex(Product.COLUMN_DATE)))
                );

        // close the db connection
        cursor.close();

        return pro;
    }

    public List<Product> getListProduct() {
        List<Product> pros = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + Product.TABLE_PRODUCT + " ORDER BY " +
                Product.COLUMN_DATE + " DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Product pro = new Product();
                pro.setProductId(cursor.getString(cursor.getColumnIndex(Product.COLUMN_PRODUCT_ID)));
                pro.setName(cursor.getString(cursor.getColumnIndex(Product.COLUMN_NAME)));
                pro.setPrice(cursor.getFloat(cursor.getColumnIndex(Product.COLUMN_PRICE)));
                pro.setImage(cursor.getString(cursor.getColumnIndex(Product.COLUMN_IMAGE)));
                pro.setDate(new Date(cursor.getString(cursor.getColumnIndex(Product.COLUMN_DATE))));

                pros.add(pro);
            } while (cursor.moveToNext());
        }

        // close db connection
        db.close();

        // return pros list
        return pros;
    }

    public int updateProduct(Product pro) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Product.COLUMN_NAME, pro.getName());
        values.put(Product.COLUMN_PRICE, pro.getPrice());
        values.put(Product.COLUMN_IMAGE, pro.getImage());
        values.put(Product.COLUMN_DATE, pro.getDate().toString());

        // updating row
        return db.update(Product.TABLE_PRODUCT, values, Product.COLUMN_PRODUCT_ID + " = ?",
                new String[]{String.valueOf(pro.getProductId())});
    }

    public void deleteProduct(Product pro) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Product.TABLE_PRODUCT, Product.COLUMN_PRODUCT_ID + " = ?",
                new String[]{String.valueOf(pro.getProductId())});
        db.close();
    }

    public DbHelper(@androidx.annotation.Nullable Context context, @androidx.annotation.Nullable String name, @androidx.annotation.Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public DbHelper(@androidx.annotation.Nullable Context context, @androidx.annotation.Nullable String name, @androidx.annotation.Nullable SQLiteDatabase.CursorFactory factory, int version, @androidx.annotation.Nullable DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

}
