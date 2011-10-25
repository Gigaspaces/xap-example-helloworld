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
 * AccountData represents an account data object that is preloaded to the runtime processing 
 * unit's embedded space by the feeder processing unit's AccountDataLoader bean.
 * In this example the account data objects consist the in-memory data grid, and are used by
 * runtime processing unit's OrderEventValidator bean to validate the OrderEvents.
 * The account data object's balance is updated by the runtime processing unit's OrderEventProcessor
 * bean to reflect buy/sell operations. 
 * <p>
 * Properties include the account's userName(used as the Account unique ID, also used as a routing 
 * index to perform routing when working with partitioned space) and the balance.
 * <p>
 * <code>@SpaceClass</code> annotation in this example is only to Illustrate that this class is a space class. 
 */
@SpaceClass
public class AccountData {
	
	/**
	 * User name for the Account (Serves also as the account unique ID, and routing index).
	 */
	private String userName;
	
	/**
	 * Balance for the Account.
	 */
	private Integer balance;
	
	/**
	 * AccountData no-args constructor.
	 */
	public AccountData() {
	}
	
	/**
	 * AccountData constructor.
	 * @param userName
	 * @param balance
	 */
	public AccountData(String userName, Integer balance) {
		this.userName = userName;
		this.balance = balance;
	}

	/**
	 * @return the balance of the account.
	 */
	public Integer getBalance() {
		return balance;
	}

	/**
	 * @param balance - Sets the balance for the account.
	 */
	public void setBalance(Integer balance) {
		this.balance = balance;
	}

	/**
	 * @return the user name of the account.
	 * <p>
	 * <code>SpaceId</code> controls the unique identity of the account within
	 * the Space.
	 * <p>
	 * <code>SpaceRouting</code> annotation controls which partition this
	 * account will be written to when using a partitioned Space (using hash
	 * based routing).
	 */
	@SpaceId
	@SpaceRouting
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName - Sets the user name for the account.
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	/**
	 * Outputs the account properties (userName, balance).
	 */
	public String toString() {
		return "userName[" + userName + "] balance[" + balance + "]";
	}

}
