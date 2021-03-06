/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huyvd.cart;

import huyvd.tblCar.TblCarDTO;
import java.io.Serializable;

/**
 *
 * @author BlankSpace
 */
public class cartItem implements Serializable{
    private TblCarDTO car;
    private int quantity;
    private double total;

    public cartItem(TblCarDTO car, int quantity) {
	this.car = car;
	this.quantity = quantity;
	this.total = car.getPrice() * quantity;
    }

    /**
     * @return the car
     */
    public TblCarDTO getCar() {
	return car;
    }

    /**
     * @param car the car to set
     */
    public void setCar(TblCarDTO car) {
	this.car = car;
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
    
    public void updateTotal() {
	this.total = car.getPrice() * quantity;
    }
}
