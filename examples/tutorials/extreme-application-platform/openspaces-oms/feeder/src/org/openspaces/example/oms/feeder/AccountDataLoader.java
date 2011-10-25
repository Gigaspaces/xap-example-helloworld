/*
 * Copyright 2007 GigaSpaces Technologies Ltd. All rights reserved.
 *
 * THIS SOFTWARE IS PROVIDED "AS IS," WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED INCLUDING BUT NOT LIMITED TO WARRANTIES OF MERCHANTABILITY AND 
 * FITNESS FOR A PARTICULAR PURPOSE OR NON-INFRINGEMENT. GIGASPACES WILL NOT 
 * BE LIABLE FOR ANY DAMAGE OR LOSS IN CONNECTION WITH THE SOFTWARE.
 */

package org.openspaces.example.oms.feeder;

import org.openspaces.example.oms.common.AccountData;
import org.openspaces.example.oms.common.IAccountDataDAO;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.util.Assert;

/**
 * A loader bean that writes AccountData objects with unique userNames to the
 * space. Since the write is executed directly in the afterPropertiesSet method
 * (and not in a new thread), the processing unit waits until the loading is
 * finished before initializing the next bean.
 */
public class AccountDataLoader implements InitializingBean {

	/**
	 * Number of accounts to be loaded by the loader, hardcoded to 100, can be overridden 
	 * in the pu.xml (by setting the prop key "numberOfAccounts")
	 */
	private int numberOfAccounts = 100;

	/**
	 * DAO object used to access the AccountData objects implicitly. Will be
	 * injected from the pu.xml. Interface enables different DAO
	 * Implementations.
	 */
	private IAccountDataDAO accountDataDAO;

	/**
	 * Sets the DAO object used to access the accountData objects.
	 * @param accountDataDAO<p>
	 * <code>@Required</code> annotation provides the 'blow up if this 
	 * required property has not been set' logic. 
	 */
	@Required
	public void setAccountDataDAO(IAccountDataDAO accountDataDAO) {
		this.accountDataDAO = accountDataDAO;
	}

	/**
	 * Allows to control the number of accounts that will be initally
	 * loaded to the Space. Defaults to <code>100</code>.
	 */
	public void setNumberOfAccounts(int numberOfAccounts) {
		this.numberOfAccounts = numberOfAccounts;
	}

	/**
	 * The first method run upon bean Initialization when implementing InitializingBean.
	 * Writes <numberOfAccounts> AccountData through the accountDataDAO. 
	 */
	public void afterPropertiesSet() throws Exception {
		// Checks and outputs if accountDataDAO is null.
		Assert.notNull(accountDataDAO, "accountDao is required property");
		System.out.println("---[Start writing AccountData objects]---");
		// Writing <numberOfAccounts> accountData objects to the space.
		AccountData accountData = new AccountData();
		for (int i = 1; i <= numberOfAccounts; i++) {
			accountData.setUserName("USER" + i);
			accountData.setBalance(1000);
			// Saving the accountData
			accountDataDAO.save(accountData);
		}

		System.out.println("---[Wrote " + numberOfAccounts + " AccountData objects]---");
	}
}
