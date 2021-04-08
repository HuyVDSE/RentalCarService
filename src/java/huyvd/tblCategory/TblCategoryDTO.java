/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huyvd.tblCategory;

import java.io.Serializable;

/**
 *
 * @author BlankSpace
 */
public class TblCategoryDTO implements Serializable{
    private String categoryID;
    private String categoryName;

    public TblCategoryDTO(String categoryID, String categoryName) {
	this.categoryID = categoryID;
	this.categoryName = categoryName;
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
     * @return the categoryName
     */
    public String getCategoryName() {
	return categoryName;
    }

    /**
     * @param categoryName the categoryName to set
     */
    public void setCategoryName(String categoryName) {
	this.categoryName = categoryName;
    }
    
}
