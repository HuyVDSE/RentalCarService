/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huyvd.feedback;

import java.io.Serializable;

/**
 *
 * @author BlankSpace
 */
public class FeedbackDTO implements Serializable{
    private String username;
    private String content;
    private int point;

    public FeedbackDTO(String username, String content, int point) {
	this.username = username;
	this.content = content;
	this.point = point;
    }

    /**
     * @return the username
     */
    public String getUsername() {
	return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
	this.username = username;
    }

    /**
     * @return the content
     */
    public String getContent() {
	return content;
    }

    /**
     * @param content the content to set
     */
    public void setContent(String content) {
	this.content = content;
    }

    /**
     * @return the point
     */
    public int getPoint() {
	return point;
    }

    /**
     * @param point the point to set
     */
    public void setPoint(int point) {
	this.point = point;
    }
    
}
