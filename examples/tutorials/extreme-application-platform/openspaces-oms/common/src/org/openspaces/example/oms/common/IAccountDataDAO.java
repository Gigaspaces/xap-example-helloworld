/*
 * Copyright 2007 GigaSpaces Technologies Ltd. All rights reserved.
 *
 * THIS SOFTWARE IS PROVIDED "AS IS," WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED INCLUDING BUT NOT LIMITED TO WARRANTIES OF MERCHANTABILITY AND 
 * FITNESS FOR A PARTICULAR PURPOSE OR NON-INFRINGEMENT. GIGASPACES WILL NOT 
 * BE LIABLE FOR ANY DAMAGE OR LOSS IN CONNECTION WITH THE SOFTWARE.
 */

package org.openspaces.example.oms.common;

/**
 * accountData data access object interface.
 */ 
public interface IAccountDataDAO {
	
	/**
	 * Checks if the accountData object for a given userName is found. 
	 * @param userName
	 * @return true if account found, otherwise returns false.
	 */
	boolean isAccountFound(String userName);
	
	/**
	 * Updates the stored accountData object with the new parameters. 
	 * @param accountData - accountData object containing the new parameters to use for updating,
	 * 						accountData.userName attribute is used as the unique accountData identifier.
	 */
	void updateAccountData(AccountData accountData);

	/**
	 * Gets the accountData object matching the userName, blocks until found.
	 * @param userName
	 * @return AccountData
	 */
	AccountData getAccountData(String userName);
	
	/**
	 * Saves the accountData object.
	 * @param accountData - accountData to be saved.
	 */
	void save(AccountData accountData);
}
