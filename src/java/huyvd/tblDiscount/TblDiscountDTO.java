/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huyvd.tblDiscount;

import java.io.Serializable;
import java.sql.Date;

/**
 *
 * @author BlankSpace
 */
public class TblDiscountDTO implements Serializable{
    private String code;
    private Date expiredDate;
    private int value;

    public TblDiscountDTO(String code, Date expiredDate, int value) {
	this.code = code;
	this.expiredDate = expiredDate;
	this.value = value;
    }

    /**
     * @return the code
     */
    public String getCode() {
	return code;
    }

    /**
     * @param code the code to set
     */
    public void setCode(String code) {
	this.code = code;
    }

    /**
     * @return the expiredDate
     */
    public Date getExpiredDate() {
	return expiredDate;
    }

    /**
     * @param expiredDate the expiredDate to set
     */
    public void setExpiredDate(Date expiredDate) {
	this.expiredDate = expiredDate;
    }

    /**
     * @return the value
     */
    public int getValue() {
	return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(int value) {
	this.value = value;
    }

}
