/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huyvd.cart;

import huyvd.tblCar.TblCarDAO;
import huyvd.tblCar.TblCarDTO;
import java.io.Serializable;
import java.sql.Date;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.naming.NamingException;

/**
 *
 * @author BlankSpace
 */
public class CartObject implements Serializable{
    private List<cartItem> itemList;
    private Date rentalDate;
    private Date returnDate;
    private int totalDays;
    private String discountCode;
    private int discount; //default discount value
    
    public List<cartItem> getItemList() {
	return itemList;
    }

    public int getTotalItems() {
	return itemList.size();
    }
    
    public void addItemToCart(String itemId) throws SQLException, NamingException {
	//1. check item List
	if (this.itemList == null) {
	    this.itemList = new ArrayList<>();
	}
	
	//2. check item is exist in cart
	int quantity = 1; //set default quantity
	cartItem item = searchItemInCart(itemId);
	
	if (item == null) {
	    TblCarDAO dao = new TblCarDAO();
	    TblCarDTO dto = dao.getCar(itemId);
	    item = new cartItem(dto, quantity);
	    itemList.add(item);
	} else {
	    quantity = item.getQuantity() + 1;
	    item.setQuantity(quantity);
	    item.updateTotal();
	}
    }
    
    private cartItem searchItemInCart(String itemId) {
	for (cartItem item : itemList) {
	    if (item.getCar().getCarID().equals(itemId)) {
		return item;
	    }
	}
	return null;
    }
    
    public void removeItem(String itemId) {
	if (this.itemList == null) {
	    return;
	}//end if itemList exist
	
	cartItem removedItem = searchItemInCart(itemId);
	if (removedItem != null) {
	    this.itemList.remove(removedItem);
	    
	    if (this.itemList.isEmpty()) {
		this.itemList = null;
	    }
	}
    }
    
    public void updateItemQuantity(String itemId, int newQuantity) {
	cartItem item = searchItemInCart(itemId);
	item.setQuantity(newQuantity);
	item.updateTotal();
    }
    
    public double getTotalPriceOfCart() {
	double totalPrice = 0;
	for (cartItem item : itemList) {
	    totalPrice += item.getTotal();
	}
	return totalPrice * totalDays;
    }
    
    public double getTotalPriceOfCartAfterDiscount() {
	double totalPrice = 0;
	for (cartItem item : itemList) {
	    totalPrice += item.getTotal();
	}
	return (totalPrice * totalDays) - (totalPrice * totalDays * discount/100);
    }
    
    //create cart id with format ddmmyy-hhmmss-xxxxxx(x is random digit)
    public String generateCartId() {
	SimpleDateFormat sdf = new SimpleDateFormat("ddMMyy-hhmmss");
	java.util.Date currentDate = new java.util.Date();

	String datePart = sdf.format(currentDate);
	String randomPart = "";

	for (int i = 0; i < 6; i++) {
	    int randomNum = new Random().nextInt(10);
	    randomPart += randomNum;
	}

	return datePart + "-" + randomPart;
    }
    
    /**
     * @return the totalDays
     */
    public int getTotalDays() {
	return totalDays;
    }

    /**
     * @param totalDays the totalDays to set
     */
    public void setTotalDays(int totalDays) {
	this.totalDays = totalDays;
    }

    /**
     * @return the rentalDate
     */
    public Date getRentalDate() {
	return rentalDate;
    }

    /**
     * @param rentalDate the rentalDate to set
     */
    public void setRentalDate(Date rentalDate) {
	this.rentalDate = rentalDate;
    }

    /**
     * @return the returnDate
     */
    public Date getReturnDate() {
	return returnDate;
    }

    /**
     * @param returnDate the returnDate to set
     */
    public void setReturnDate(Date returnDate) {
	this.returnDate = returnDate;
    }

    /**
     * @return the discount
     */
    public int getDiscount() {
	return discount;
    }

    /**
     * @param discount the discount to set
     */
    public void setDiscount(int discount) {
	this.discount = discount;
    }

    /**
     * @return the discountCode
     */
    public String getDiscountCode() {
	return discountCode;
    }

    /**
     * @param discountCode the discountCode to set
     */
    public void setDiscountCode(String discountCode) {
	this.discountCode = discountCode;
    }
}
