/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huyvd.tblOrder;

import java.io.Serializable;
import java.sql.Date;

/**
 *
 * @author BlankSpace
 */
public class TblOrderDTO implements Serializable{
    private String orderId;
    private String userId;
    private Date rentalDate;
    private Date returnDate;
    private String status;
    private double totalAmount;
    private double totalAfterDiscount;

    public TblOrderDTO(String orderId, String userId, Date rentalDate, Date returnDate,
	    String status, double totalAmount, double totalAfterDiscount) {
	this.orderId = orderId;
	this.userId = userId;
	this.rentalDate = rentalDate;
	this.returnDate = returnDate;
	this.status = status;
	this.totalAmount = totalAmount;
	this.totalAfterDiscount = totalAfterDiscount;
    }

    /**
     * @return the orderId
     */
    public String getOrderId() {
	return orderId;
    }

    /**
     * @param orderId the orderId to set
     */
    public void setOrderId(String orderId) {
	this.orderId = orderId;
    }

    /**
     * @return the userId
     */
    public String getUserId() {
	return userId;
    }

    /**
     * @param userId the userId to set
     */
    public void setUserId(String userId) {
	this.userId = userId;
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
     * @return the status
     */
    public String getStatus() {
	return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(String status) {
	this.status = status;
    }

    /**
     * @return the totalAmount
     */
    public double getTotalAmount() {
	return totalAmount;
    }

    /**
     * @param totalAmount the totalAmount to set
     */
    public void setTotalAmount(double totalAmount) {
	this.totalAmount = totalAmount;
    }

    /**
     * @return the totalAfterDiscount
     */
    public double getTotalAfterDiscount() {
	return totalAfterDiscount;
    }

    /**
     * @param totalAfterDiscount the totalAfterDiscount to set
     */
    public void setTotalAfterDiscount(double totalAfterDiscount) {
	this.totalAfterDiscount = totalAfterDiscount;
    }
    
}
