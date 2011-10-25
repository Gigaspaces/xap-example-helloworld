/*
 * Copyright 2007 GigaSpaces Technologies LTD. All rights reserved.
 *
 * THIS SOFTWARE IS PROVIDED "AS IS," WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED INCLUDING BUT NOT LIMITED TO WARRANTIES OF MERCHANTABILITY AND 
 * FITNESS FOR A PARTICULAR PURPOSE OR NON-INFRINGEMENT. GIGASPACES WILL NOT 
 * BE LIABLE FOR ANY DAMAGE OR LOSS IN CONNECTION WITH THE SOFTWARE.
 */

package com.gigaspaces.examples.tutorials.parallelprocessing.common;

import com.gigaspaces.annotation.pojo.SpaceClass;
import com.gigaspaces.annotation.pojo.SpaceId;

/**
 * OrderEvent object important properties include the orderID
 * of the object, userName (also used to perform routing when working with partitioned space),
 * and a status indicating if this OrderEvent object in new, processed, or rejected.
 * <p>
 * <code>@SpaceClass</code> annotation in this example is only to indicate that this class is a space class.
 */ 
@SpaceClass
public class OrderEvent {
	
	public static final String STATUS_NEW = "New";	
	public static final String STATUS_APPROVED = "Approved";	
	public static final String STATUS_PROCESSED = "Processed";
	public static final String STATUS_REJECTED = "Rejected";
    
    /**
     * ID of the order.
     */
    private String orderID;
    
    /**
     * ID of the client that placed the order.
     */
    private String clientID;
    
    /**
     * User name of the order.
     */
    private String userName;
    
    /**
     * 	Order status, Possible values: New, Approved, Processed, Rejected
     * */
    private String status;		
    
    /**
     * Constructs a new OrderEvent object. 
     * */
    public OrderEvent() {
    }

    /** 
     * Constructs a new OrderEvent object with the given userName and clientID
     * and operation.
     * @param userName
     * @param clientID
     */
    public OrderEvent(String userName, String clientID) {
        this.userName = userName;
        this.clientID = clientID;
        this.status = STATUS_NEW;
    }

    /** 
     * Gets the ID of the orderEvent.<p>
     * <code>@SpaceID</code> annotation indicates that its value will be auto generated 
     * when it is written to the space. 
     */
    @SpaceId(autoGenerate = true)
    public String getOrderID() {
        return orderID;
    }

    /**
     * Sets the ID of the orderEvent.
     * @param orderID
     */
    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    /** 
     * @return userName - Gets the user name of the orderEvent object.
     */
    public String getUserName() {
        return userName;
    }

    /** 
     * @param userName - set the user name of the orderEvent object.
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }
    
    /**
     *	Outputs the orderEvent object attributes.
     */
    public String toString() {
        return "userName[" + userName + "] status[" + status + "]";
    }

	/** 
	 *	@return status - the orderEvent status. 
	 */
	public String getStatus() {
		return status;
	}

	/**
	 *  @param status - Sets the orderEvent status.
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/** 
     *	@return clientID - Gets the clientID of the orderEvent object.
     */
	public String getClientID() {
		return clientID;
	}

	/**
	 *  @param clientID - Sets the orderEvent clientID.
	 */
	public void setClientID(String clientID) {
		this.clientID = clientID;
	}
}
