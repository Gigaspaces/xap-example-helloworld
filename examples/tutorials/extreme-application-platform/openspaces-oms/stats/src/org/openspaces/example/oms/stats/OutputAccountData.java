package org.openspaces.example.oms.stats;

import org.openspaces.events.adapter.SpaceDataEvent;
import org.openspaces.example.oms.common.AccountData;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * A simple bean outputting the accountData updated balance.
 *
 */
public class OutputAccountData {

    private AtomicInteger accountDataUpdatedCounter = new AtomicInteger(0);

    @SpaceDataEvent	//	Indicates that this method should be invoked upon notification.
    public void outputInfo(AccountData accountData) {
        accountDataUpdatedCounter.incrementAndGet();
        System.out.println("---{ AccountData balance for ["+accountData.getUserName()+"] was updated to ["
                           +accountData.getBalance()+"] ,Total account updates [" + accountDataUpdatedCounter + "] }---");
    }

    public int getAccountDataUpdatedCount() {
        return accountDataUpdatedCounter.intValue();
    }
}
