/*
 * Copyright 2007 GigaSpaces Technologies Ltd. All rights reserved.
 *
 * THIS SOFTWARE IS PROVIDED "AS IS," WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED INCLUDING BUT NOT LIMITED TO WARRANTIES OF MERCHANTABILITY AND 
 * FITNESS FOR A PARTICULAR PURPOSE OR NON-INFRINGEMENT. GIGASPACES WILL NOT 
 * BE LIABLE FOR ANY DAMAGE OR LOSS IN CONNECTION WITH THE SOFTWARE.
 */
package com.gigaspaces.examples.tutorials.ordermanagement;

import net.jini.core.entry.Entry;	

/**
 * Title:        	GigaSpaces Order Management Example - Order object.<p>
 * Description:   	The order object is written to the space first by the client, then
 * 					taken and validated(approved or rejected) by the Validator, then approved orders are 
 * 					taken by the processor and rejected orders are taken back by the client.
 * 					 
 * @since 6.0
 */
public class Order implements Entry 
{
	public static final String STATUS_NEW = "New";
	public static final String STATUS_REJECTED = "Rejected";
	public static final String STATUS_APPROVED = "Approved";
	
	public Integer data = null;				 // Order data.
	public String status= null; 			 // Order status, values possible: New/Rejected/Approved.
	public String clientID= null; 			 // ID of the client that placed the order.
	public Integer number = null;			 // Order number (used to display the fifo function).
    public String validatedByHostname = null;// Hostname of the validator.	
    
	/**
	 * Empty non-arguments constructor required for an entry implementation.
	 */
	public Order()
	{
	}
	
	/**
	 * Constructor for creating status based matching - Order template.
	 * @param status - Initial status.
	 */
	public Order(String status)
	{
		this.status = status;
	}
	
	/**
	 * Constructor for creating status and clientID based matching Order template.
	 * @param status - Initial status.
	 * @param clientID - ID of the client that placed the order.
	 */
	public Order(String status, String clientID)
	{
		this.status = status;
		this.clientID = clientID;
	}
	
	/**
	 * Constructor for creating status and clientID based matching Order template.
	 * @param status - Initial status.
	 * @param clientID - ID of the client that placed the order.
	 * @param data - Order data.
	 * @param number - Order number (to demonstrate fifo in this example).
	 */
	public Order(String status, String clientID, Integer number, Integer data)
	{
		this.status = status;
		this.clientID = clientID;
		this.data = data;
		this.number = number;
	}
	
	/**
	 * Processing the order
	 * @param hostname
	 * @return processed ProcessedOrder with the saved answer,
	 * 		in this example the Processor simply multiplies the order data by 2
	 */	
	public ProcessedOrder process(String hostname)
	{
		ProcessedOrder processedOrder = new ProcessedOrder(
				clientID, //	Stamping which client placed this order (with its unique client ID).
				number,   //	Setting the processed order number to match the original order.
				hostname, //	Stamping which processor processed this order (with its hostname).
				new Integer(data.intValue() * 2) //	Answer
		);

		return processedOrder ;
	}
	
	
	/**
	 * Validating the order
	 * Randomizing 1/2 approved and 1/2 rejected.
	 * @param hostname
	 * @return status
	 */
	public String validate(String hostname)
	{
		//	For the sake of the example the status is arbitrarily randomized.
		if ( Math.random() >= 0.5)
		{
			status=Order.STATUS_APPROVED;
		}
		else 
		{
			status=Order.STATUS_REJECTED;
		}
		
		//	Stamping which validator validated this order (with its hostname).
		validatedByHostname=hostname;
		
		return status;
	}
}
