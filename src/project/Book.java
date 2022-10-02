/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project;

import javafx.scene.control.CheckBox;

/**
 *
 * @author Rashique
 */
public class Book {
    protected String bookName;
    protected double bookPrice;
    private CheckBox select;
    
    public Book() {
        this.bookName = "";
        this.bookPrice = 0;
    }
    
    public Book(String bName, double bPrice) {
        this.bookName = bName;
        this.bookPrice = bPrice;
        this.select = new CheckBox();
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public double getBookPrice() {
        return bookPrice;
    }

    public void setBookPrice(double bookPrice) {
        this.bookPrice = bookPrice;
    }
    
    public CheckBox getSelect() {
        return select;
    }

    public void setSelect(CheckBox select) {
        this.select = select;
    }
    
}
