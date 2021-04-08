/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huyvd.tblCar;

import java.io.Serializable;
import java.sql.Date;

/**
 *
 * @author BlankSpace
 */
public class TblCarDTO implements Serializable{
    private String carID;
    private String name;
    private String image;
    private String color;
    private Date year;
    private double price;
    private int quantity;
    private String categoryID;
    private String description;

    public TblCarDTO() {
    }
    
    public TblCarDTO(String carID, String name, String image, String color, 
	    Date year, double price, int quantity, String categoryID, String description) {
	this.carID = carID;
	this.name = name;
	this.image = image;
	this.color = color;
	this.year = year;
	this.price = price;
	this.quantity = quantity;
	this.categoryID = categoryID;
	this.description = description;
    }

    /**
     * @return the carID
     */
    public String getCarID() {
	return carID;
    }

    /**
     * @param carID the carID to set
     */
    public void setCarID(String carID) {
	this.carID = carID;
    }

    /**
     * @return the name
     */
    public String getName() {
	return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
	this.name = name;
    }

    /**
     * @return the image
     */
    public String getImage() {
	return image;
    }

    /**
     * @param image the image to set
     */
    public void setImage(String image) {
	this.image = image;
    }

    /**
     * @return the color
     */
    public String getColor() {
	return color;
    }

    /**
     * @param color the color to set
     */
    public void setColor(String color) {
	this.color = color;
    }

    /**
     * @return the year
     */
    public Date getYear() {
	return year;
    }

    /**
     * @param year the year to set
     */
    public void setYear(Date year) {
	this.year = year;
    }

    /**
     * @return the price
     */
    public double getPrice() {
	return price;
    }

    /**
     * @param price the price to set
     */
    public void setPrice(double price) {
	this.price = price;
    }

    /**
     * @return the quantity
     */
    public int getQuantity() {
	return quantity;
    }

    /**
     * @param quantity the quantity to set
     */
    public void setQuantity(int quantity) {
	this.quantity = quantity;
    }

    /**
     * @return the categoryID
     */
    public String getCategoryID() {
	return categoryID;
    }

    /**
     * @param categoryID the categoryID to set
     */
    public void setCategoryID(String categoryID) {
	this.categoryID = categoryID;
    }

    /**
     * @return the description
     */
    public String getDescription() {
	return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
	this.description = description;
    }
    
}
