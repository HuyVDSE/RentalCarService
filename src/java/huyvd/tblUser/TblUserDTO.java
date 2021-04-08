/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huyvd.tblUser;

import java.io.Serializable;
import java.sql.Date;

/**
 *
 * @author BlankSpace
 */
public class TblUserDTO implements Serializable{
    private String email;
    private String password;
    private String fullname;
    private String phone;
    private String address;
    private Date createDate;
    private String status;
    private String role;

    public TblUserDTO(String email, String password, String fullname, 
	    String phone, String address, Date createDate, String status, String role) {
	this.email = email;
	this.password = password;
	this.fullname = fullname;
	this.phone = phone;
	this.address = address;
	this.createDate = createDate;
	this.status = status;
	this.role = role;
    }   

    /**
     * @return the email
     */
    public String getEmail() {
	return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
	this.email = email;
    }

    /**
     * @return the password
     */
    public String getPassword() {
	return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
	this.password = password;
    }

    /**
     * @return the phone
     */
    public String getPhone() {
	return phone;
    }

    /**
     * @param phone the phone to set
     */
    public void setPhone(String phone) {
	this.phone = phone;
    }

    /**
     * @return the address
     */
    public String getAddress() {
	return address;
    }

    /**
     * @param address the address to set
     */
    public void setAddress(String address) {
	this.address = address;
    }

    /**
     * @return the createDate
     */
    public Date getCreateDate() {
	return createDate;
    }

    /**
     * @param createDate the createDate to set
     */
    public void setCreateDate(Date createDate) {
	this.createDate = createDate;
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
     * @return the role
     */
    public String getRole() {
	return role;
    }

    /**
     * @param role the role to set
     */
    public void setRole(String role) {
	this.role = role;
    }

    /**
     * @return the fullname
     */
    public String getFullname() {
	return fullname;
    }

    /**
     * @param fullname the fullname to set
     */
    public void setFullname(String fullname) {
	this.fullname = fullname;
    }
    
}
