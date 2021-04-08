/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huyvd.tblOrderDetail;

import java.io.Serializable;

/**
 *
 * @author BlankSpace
 */
public class TblOrderDetailDTO implements Serializable{
    private String orderId;
    private String carId;
    private int quantity;
    private double total;
    
    public TblOrderDetailDTO(String orderId, String carId, int quantity, double total) {
	this.orderId = orderId;
	this.carId = carId;
	this.quantity = quantity;
	this.total = total;
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
     * @return the carId
     */
    public String getCarId() {
	return carId;
    }

    /**
     * @param carId the carId to set
     */
    public void setCarId(String carId) {
	this.carId = carId;
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
     * @return the total
     */
    public double getTotal() {
	return total;
    }

    /**
     * @param total the total to set
     */
    public void setTotal(double total) {
	this.total = total;
    }
}
