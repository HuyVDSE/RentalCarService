/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huyvd.cart;

import java.io.Serializable;

/**
 *
 * @author BlankSpace
 */
public class CartError implements Serializable{
    private String rentalDateErr;
    private String returnDateErr;
    private String notValidDiscountCodeErr;
    private String expiredDiscountCodeErr;
    private String inputRentalDateErr;
    private String inputReturnDateErr;
    private String quantityErr;

    /**
     * @return the quantityErr
     */
    public String getQuantityErr() {
	return quantityErr;
    }

    /**
     * @param quantityErr the quantityErr to set
     */
    public void setQuantityErr(String quantityErr) {
	this.quantityErr = quantityErr;
    }

    /**
     * @return the rentalDateErr
     */
    public String getRentalDateErr() {
	return rentalDateErr;
    }

    /**
     * @param rentalDateErr the rentalDateErr to set
     */
    public void setRentalDateErr(String rentalDateErr) {
	this.rentalDateErr = rentalDateErr;
    }

    /**
     * @return the returnDateErr
     */
    public String getReturnDateErr() {
	return returnDateErr;
    }

    /**
     * @param returnDateErr the returnDateErr to set
     */
    public void setReturnDateErr(String returnDateErr) {
	this.returnDateErr = returnDateErr;
    }

    /**
     * @return the inputRentalDateErr
     */
    public String getInputRentalDateErr() {
	return inputRentalDateErr;
    }

    /**
     * @param inputRentalDateErr the inputRentalDateErr to set
     */
    public void setInputRentalDateErr(String inputRentalDateErr) {
	this.inputRentalDateErr = inputRentalDateErr;
    }

    /**
     * @return the inputReturnDateErr
     */
    public String getInputReturnDateErr() {
	return inputReturnDateErr;
    }

    /**
     * @param inputReturnDateErr the inputReturnDateErr to set
     */
    public void setInputReturnDateErr(String inputReturnDateErr) {
	this.inputReturnDateErr = inputReturnDateErr;
    }

    /**
     * @return the notValidDiscountCodeErr
     */
    public String getNotValidDiscountCodeErr() {
	return notValidDiscountCodeErr;
    }

    /**
     * @param notValidDiscountCodeErr the notValidDiscountCodeErr to set
     */
    public void setNotValidDiscountCodeErr(String notValidDiscountCodeErr) {
	this.notValidDiscountCodeErr = notValidDiscountCodeErr;
    }

    /**
     * @return the expiredDiscountCodeErr
     */
    public String getExpiredDiscountCodeErr() {
	return expiredDiscountCodeErr;
    }

    /**
     * @param expiredDiscountCodeErr the expiredDiscountCodeErr to set
     */
    public void setExpiredDiscountCodeErr(String expiredDiscountCodeErr) {
	this.expiredDiscountCodeErr = expiredDiscountCodeErr;
    }
    
}
