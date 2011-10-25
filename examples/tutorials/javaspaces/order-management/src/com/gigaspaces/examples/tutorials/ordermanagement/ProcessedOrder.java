/*
 * Copyright 2007 GigaSpaces Technologies Ltd. All rights reserved.
 *
 * THIS SOFTWARE IS PROVIDED "AS IS," WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED INCLUDING BUT NOT LIMITED TO WARRANTIES OF MERCHANTABILITY AND 
 * FITNESS FOR A PARTICULAR PURPOSE OR NON-INFRINGEMENT. GIGASPACES WILL NOT 
 * BE LIABLE FOR ANY DAMAGE OR LOSS IN CONNECTION WITH THE SOFTWARE.
 */
package com.gigaspaces.examples.tutorials.ordermanagement;

import net.jini.core.entry.Entry;	//	Entry class.

/**
 * Title:        	GigaSpaces Order Management Example - ProcessedOrder object.<p>
 * Description:   	ProcessedOrder objects are written to space by the processor and taken by the client.
 * 					 
 * @since 6.0
 */
public class ProcessedOrder implements Entry 
{
	public String clientID = null; 	// ID of the client that placed the original order.
	public Integer answer = null; 	// Process result.
	public Integer number = null;	// Order number (used to display the fifo function).
	public String hostname = null;	// The processor hostname.
	
	/**
	 * Empty non-argument constructor required for an entry implementation.
	 */
	public ProcessedOrder()
	{
	}
	
	/**
	 * ProcessedOrder constructor
	 * @param clientID
	 */
	public ProcessedOrder(String clientID)
	{
		this.clientID=clientID;
	}
	
	/**
	 * ProcessedOrder constructor
	 * @param clientID - ID of the client that placed the original order.
	 * @param number
	 * @param hostname
	 */
	public ProcessedOrder(String clientID, Integer number, String hostname)
	{
		this.clientID=clientID;
		this.number=number;
		this.hostname=hostname;
	}
	
	/**
	 * ProcessedOrder constructor
	 * @param clientID
	 * @param number
	 * @param hostname
	 * @param answer
	 */
	public ProcessedOrder(String clientID, Integer number, String hostname, Integer answer)
	{
		this.clientID=clientID;
		this.number=number;
		this.hostname=hostname;
		this.answer=answer;
	}
}
