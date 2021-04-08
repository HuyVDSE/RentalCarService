/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huyvd.tblUser;

import java.io.Serializable;

/**
 *
 * @author BlankSpace
 */
public class UserErrors implements Serializable{
    private String captchaError;
    private String invalidUserError;
    private String emailError;
    private String nameLengthError;
    private String emailExisted;
    private String passwordLengthError;
    private String passwordConfirmNotMatched;
    private String addressLengthError;
    private String phoneFormatError;
    private String phoneLengthError;
    private String activeCodeError;

    /**
     * @return the captchaError
     */
    public String getCaptchaError() {
	return captchaError;
    }

    /**
     * @param captchaError the captchaError to set
     */
    public void setCaptchaError(String captchaError) {
	this.captchaError = captchaError;
    }

    /**
     * @return the invalidUserError
     */
    public String getInvalidUserError() {
	return invalidUserError;
    }

    /**
     * @param invalidUserError the invalidUserError to set
     */
    public void setInvalidUserError(String invalidUserError) {
	this.invalidUserError = invalidUserError;
    }

    /**
     * @return the emailError
     */
    public String getEmailError() {
	return emailError;
    }

    /**
     * @param emailError the emailError to set
     */
    public void setEmailError(String emailError) {
	this.emailError = emailError;
    }

    /**
     * @return the nameLengthError
     */
    public String getNameLengthError() {
	return nameLengthError;
    }

    /**
     * @param nameLengthError the nameLengthError to set
     */
    public void setNameLengthError(String nameLengthError) {
	this.nameLengthError = nameLengthError;
    }

    /**
     * @return the passwordLengthError
     */
    public String getPasswordLengthError() {
	return passwordLengthError;
    }

    /**
     * @param passwordLengthError the passwordLengthError to set
     */
    public void setPasswordLengthError(String passwordLengthError) {
	this.passwordLengthError = passwordLengthError;
    }

    /**
     * @return the passwordConfirmNotMatched
     */
    public String getPasswordConfirmNotMatched() {
	return passwordConfirmNotMatched;
    }

    /**
     * @param passwordConfirmNotMatched the passwordConfirmNotMatched to set
     */
    public void setPasswordConfirmNotMatched(String passwordConfirmNotMatched) {
	this.passwordConfirmNotMatched = passwordConfirmNotMatched;
    }

    /**
     * @return the addressLengthError
     */
    public String getAddressLengthError() {
	return addressLengthError;
    }

    /**
     * @param addressLengthError the addressLengthError to set
     */
    public void setAddressLengthError(String addressLengthError) {
	this.addressLengthError = addressLengthError;
    }

    /**
     * @return the phoneFormatError
     */
    public String getPhoneFormatError() {
	return phoneFormatError;
    }

    /**
     * @param phoneFormatError the phoneFormatError to set
     */
    public void setPhoneFormatError(String phoneFormatError) {
	this.phoneFormatError = phoneFormatError;
    }

    /**
     * @return the phoneLengthError
     */
    public String getPhoneLengthError() {
	return phoneLengthError;
    }

    /**
     * @param phoneLengthError the phoneLengthError to set
     */
    public void setPhoneLengthError(String phoneLengthError) {
	this.phoneLengthError = phoneLengthError;
    }

    /**
     * @return the emailExisted
     */
    public String getEmailExisted() {
	return emailExisted;
    }

    /**
     * @param emailExisted the emailExisted to set
     */
    public void setEmailExisted(String emailExisted) {
	this.emailExisted = emailExisted;
    }

    /**
     * @return the activeCodeError
     */
    public String getActiveCodeError() {
	return activeCodeError;
    }

    /**
     * @param activeCodeError the activeCodeError to set
     */
    public void setActiveCodeError(String activeCodeError) {
	this.activeCodeError = activeCodeError;
    }
    
    
}
