/*
 * Copyright 2007 GigaSpaces Technologies Ltd. All rights reserved.
 *
 * THIS SOFTWARE IS PROVIDED "AS IS," WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED INCLUDING BUT NOT LIMITED TO WARRANTIES OF MERCHANTABILITY AND 
 * FITNESS FOR A PARTICULAR PURPOSE OR NON-INFRINGEMENT. GIGASPACES WILL NOT 
 * BE LIABLE FOR ANY DAMAGE OR LOSS IN CONNECTION WITH THE SOFTWARE.
 */

package org.openspaces.example.oms.common;

import net.jini.core.lease.Lease;

import org.openspaces.core.GigaSpace;
import org.openspaces.core.context.GigaSpaceContext;
import org.openspaces.example.oms.common.AccountData;

import com.j_spaces.core.client.ReadModifiers;
import com.j_spaces.core.client.UpdateModifiers;

/**
 * A gigapsaces based implementation of the account data access abstraction.
 */
public class AccountDataDAO implements IAccountDataDAO {

	/**
	 *  gigaSpace is injected through the pu.xml using the setter. 
	 */
	@GigaSpaceContext(name = "gigaSpace")
	private GigaSpace gigaSpace;
	
	/**
	 * @param gigaSpace - Sets gigaSpace
	 */
	public void setGigaSpace(GigaSpace gigaSpace) {
		this.gigaSpace = gigaSpace;
	}
	
	/**
	 * isAccountFound<p>
	 * Checks if account found in space, works even if the account is blocked.
	 * 
	 * @param userName
	 * @return true if account with the following userName is found in the space, otherwise returns false.  
	 */
	public boolean isAccountFound(String userName) {
		AccountData accountDataTemplate = new AccountData();
		accountDataTemplate.setUserName(userName);
		// read the accountData with dirty read making to read even blocked accounts.
		AccountData accountData = gigaSpace.read(accountDataTemplate, 0/* nowait */, ReadModifiers.DIRTY_READ);
		if (accountData != null)
			return true;
		else
			return false;
	}

	/**
	 * Reads the accountData with exclusive read, making it invisible to other
	 * processor threads, Blocks until found.
	 * 
	 * @param userName - user name of the account.
	 * @return accountData - the accountData read.
	 */
	public AccountData getAccountData(String userName) {
		AccountData accountDataTemplate = new AccountData();
		accountDataTemplate.setUserName(userName);
		// read the accountData with exclusive read making it invisible to other
		// processor threads, block until found
		AccountData accountData = gigaSpace.read(accountDataTemplate, Long.MAX_VALUE, ReadModifiers.EXCLUSIVE_READ_LOCK);
		return accountData;
	}

	/**
	 * Updates the accountData object according to its userName unique
	 * attribute. Blocks until updates.
	 */
	public void updateAccountData(AccountData accountData) {
		// Writes the account data to the space, using <code>Long.MAX_VALUE_VALUE</code>
		// as the update timeout (which basically means forever).
		gigaSpace.write(accountData, Lease.FOREVER, Long.MAX_VALUE, UpdateModifiers.UPDATE_ONLY);
	}

	/**
	 * Writes the accountData object to the space.
	 * 
	 * @param accountData - accountData to be written.
	 */
	public void save(AccountData accountData) {
		gigaSpace.write(accountData);
	}
}
