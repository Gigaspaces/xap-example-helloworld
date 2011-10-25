/*
 * Copyright 2007 GigaSpaces Technologies Ltd. All rights reserved.
 *
 * THIS SOFTWARE IS PROVIDED "AS IS," WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED INCLUDING BUT NOT LIMITED TO WARRANTIES OF MERCHANTABILITY AND 
 * FITNESS FOR A PARTICULAR PURPOSE OR NON-INFRINGEMENT. GIGASPACES WILL NOT 
 * BE LIABLE FOR ANY DAMAGE OR LOSS IN CONNECTION WITH THE SOFTWARE.
 */

package org.openspaces.example.oms.common;

import com.gigaspaces.annotation.pojo.SpaceClass;
import com.gigaspaces.annotation.pojo.SpaceId;
import com.gigaspaces.annotation.pojo.SpaceRouting;

/**
 * OrderEvent object important properties include the orderID
 * of the object, userName (also used to perform routing when working with partitioned space),
 * price ,operation , and a status indicating if this OrderEvent object
 * was processed, or rejected for a reason.
 * <p>
 * <code>@SpaceClass</code> annotation in this example is only to indicate that this class is a space class.
 */ 
@SpaceClass
public class OrderEvent {
	
	public static final String STATUS_NEW = "New";	
	public static final String STATUS_PENDING = "Pending";	
	public static final String STATUS_PROCESSED = "Processed";
	public static final String STATUS_INSUFFICIENT_FUNDS = "InsufficientFunds";
	public static final String STATUS_ACCOUNT_NOT_FOUND = "AccountNotFound";
	
	public static final int BUY_OPERATION = 1;
	public static final int SELL_OPERATION = 2;
	
	/**
	 * Static values representing the differnet values the operation property can have.
	 * */
    public static short[] OPERATIONS = {BUY_OPERATION, SELL_OPERATION};
    
    /**
     * Indicates if this is a buy or sell order.
     */
    private Short operation;
    
    /**
     * ID of the order.
     */
    private String orderID;
    
    /**
     * User name of the order.
     */
    private String userName;
    
    /**
     * Price of order.
     */
    private Integer price;
    
    /**
     * 	Possible values: New, Pending, Processed, InsufficientFunds, AccountNotFound
     * */
    private String status;		
    
    /**
     * Constructs a new OrderEvent object. 
     * */
    public OrderEvent() {
    }

    /** 
     * Constructs a new OrderEvent object with the given userName, price
     * and operation.
     * @param userName
     * @param price
     * @param operation - Sell or buy operation.
     */
    public OrderEvent(String userName, Integer price, short operation) {
        this.userName = userName;
    	this.price = price;
        this.operation = operation;
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
     * @param account
     */
    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    /** 
     * @return userName - Gets the user name of the orderEvent object.<p>
     * Used as the routing field when working with a partitioned space. 
     */
    @SpaceRouting
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
     * Outputs the orderEvent object attributes.
     */
    public String toString() {
        return "userName[" + userName + "] operation[" + operation + "] price[" + price + "] status[" + status + "]";
    }

	/** 
	 * @return the orderEvent operation (buy or sell).
	 */
	public Short getOperation() {
		return operation;
	}

	/** 
	 * @param operation - Sets the orderEvent operation ("Buy" or "Sell") 
	 */
	public void setOperation(Short operation) {
		this.operation = operation;
	}

	/** 
	 * @return price - Gets the orderEvent price. 
	 */
	public Integer getPrice() {
		return price;
	}

	/** 
	 * @param price - Sets the orderEvent price. 
	 * */
	public void setPrice(Integer price) {
		this.price = price;
	}

	/** 
	 * @return status - the orderEvent status. 
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
}
