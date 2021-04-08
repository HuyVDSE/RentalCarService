/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huyvd.tblCategory;

import huyvd.util.MyConnection;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;

/**
 *
 * @author BlankSpace
 */
public class TblCategoryDAO implements Serializable {

    private List<TblCategoryDTO> listCategory;

    public List<TblCategoryDTO> getListCategory() {
	return listCategory;
    }

    public void loadCategory() throws SQLException, NamingException {
	Connection cn = null;
	PreparedStatement pst = null;
	ResultSet rs = null;

	try {
	    cn = MyConnection.makeConnection();
	    if (cn != null) {
		String sql = "SELECT categoryId, categoryName "
			+ "FROM tblCategory ";
		pst = cn.prepareStatement(sql);
		rs = pst.executeQuery();
		while (rs.next()) {		    
		    String categoryId = rs.getString("categoryId");
		    String categoryName = rs.getString("categoryName");
		    TblCategoryDTO dto = new TblCategoryDTO(categoryId, categoryName);
		    
		    if (this.listCategory == null) {
			this.listCategory = new ArrayList<>();
		    }
		    
		    this.listCategory.add(dto);
		}
	    }
	} finally {
	    if (rs != null) {
		rs.close();
	    }
	    if (pst != null) {
		pst.close();
	    }
	    if (cn != null) {
		cn.close();
	    }
	}
    }
}
