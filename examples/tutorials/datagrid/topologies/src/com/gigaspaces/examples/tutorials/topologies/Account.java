/*
 * Copyright 2007 GigaSpaces Technologies Ltd. All rights reserved.
 *
 * THIS SOFTWARE IS PROVIDED "AS IS," WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED INCLUDING BUT NOT LIMITED TO WARRANTIES OF MERCHANTABILITY AND 
 * FITNESS FOR A PARTICULAR PURPOSE OR NON-INFRINGEMENT. GIGASPACES WILL NOT 
 * BE LIABLE FOR ANY DAMAGE OR LOSS IN CONNECTION WITH THE SOFTWARE.
 */
package com.gigaspaces.examples.tutorials.topologies;

import com.gigaspaces.annotation.pojo.SpaceId;			// Using SpaceId annotation (JDK 5).
import com.gigaspaces.annotation.pojo.SpaceRouting;

public class Account {
    
	private String userName= null; 			  // User name.
	private Integer accountID=null;			  // Account ID.
	private Integer balance = null;			  // Account Balance. 
    
	public Account(){}
	
    public Account(Integer accountID, Integer balance)
	{
    	this.userName = "USER" + accountID.toString();
    	this.accountID = accountID;
		this.balance = balance;
	} 
    
    public Integer getBalance()	
	{
		return balance;
	}
    public void setBalance(Integer balance)
	{
		this.balance=balance;
	}
    
    public String getUserName()	
	{
		return userName;
	}
    public void setUserName(String userName)
	{
		this.userName=userName;
	}
    
    @SpaceId		//	Sets this field (accountID) to be used in the construction 
    				//	of the Account object's unique space ID (UID).
	@SpaceRouting	//	Sets this field (accountID) as the routing index for partitioning.
    public Integer getAccountID()	
	{
		return accountID;
	}
    public void setAccountID(Integer accountID)
	{
		this.accountID=accountID;
	}
        
    public String toString()
	{
		return "User Name "+userName+", AccountID "+accountID+", Balance "+balance;
	}
}
