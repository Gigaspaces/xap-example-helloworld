/*
 * Copyright 2007 GigaSpaces Technologies Ltd. All rights reserved.
 *
 * THIS SOFTWARE IS PROVIDED "AS IS," WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED INCLUDING BUT NOT LIMITED TO WARRANTIES OF MERCHANTABILITY AND 
 * FITNESS FOR A PARTICULAR PURPOSE OR NON-INFRINGEMENT. GIGASPACES WILL NOT 
 * BE LIABLE FOR ANY DAMAGE OR LOSS IN CONNECTION WITH THE SOFTWARE.
 */

package org.openspaces.example.oms.runtime;

import org.openspaces.events.adapter.SpaceDataEvent;
import org.openspaces.example.oms.common.AccountData;
import org.openspaces.example.oms.common.IAccountDataDAO;
import org.openspaces.example.oms.common.OrderEvent;


/**
 * An implementation of IOrderEventProcessor. Can set the simulated work done when
 * processOrderEvent is called by setting the work duration (defaults to 100 ms).
 *
 * <p>This implementaiton is used to demonstrate OpenSpaces Events, using simple Spring configuration to cause
 * processOrderEvent to be invoked when a matching event occurs. The processor uses
 * OpenSpaces support for annotation markup allowing to use @SpaceDataEvent to
 * mark a method as an event listener. Note, processOrderEvent does not use any space
 * API on the OrderEvent object (though it can), receiving the OrderEvent object to be processed 
 * and returning the result that will be automatically written back to the space.
 *
 * <p>Note, changing the event container is just a matter of configuration (for example,
 * switching from polling container to notify container) and does not affect this class.
 *
 * <p>Also note, the deployment model or the Space topology does not affect this orderEvent processor
 * as well. The data processor can run on a remote space, embedded within a space, and using
 * any Space cluster topology (partitioned, replicated, primary/backup). It is all just a
 * matter of configuraion.
 *
 */
public class OrderEventProcessor {

    private long workDuration = 100;

    /**
     *	DAO object used to access the AccountData objects implicitly. 
     *	Will be injected from the pu.xml.
     *	Interface enables different DAO Implementations.
     */
    private IAccountDataDAO accountDataDAO;
    
    public void setAccountDataDAO(IAccountDataDAO accountDataDAO) {
		this.accountDataDAO = accountDataDAO;
	}
    
    /** Sets the simulated work duration (in milliseconds). Defaut to 100. */
    public void setWorkDuration(long workDuration) {
        this.workDuration = workDuration;
    }

    /**  Process the given OrderEvent object and returning the processed OrderEvent.
         Can be invoked using OpenSpaces Events when a matching event
         occurs or using OpenSpaces Remoting. */
    @SpaceDataEvent
    public OrderEvent processOrderEvent(OrderEvent orderEvent) {
        // sleep to simluate some work
        try {
            Thread.sleep(workDuration);
        } catch (InterruptedException e) {
            // do nothing
        }
        System.out.println("---[Processor: Processing orderEvent:"+orderEvent+" ]---");
        
        //	read the accountData with exclusive read making it invisible to other processor threads, block until found
        AccountData accountData = accountDataDAO.getAccountData(orderEvent.getUserName());
            	
        if (accountData != null) {
        	System.out.println("---[Processor: Found accountData matching the orderEvent: "+accountData+ "]---");	
        
        	if (orderEvent.getOperation() == OrderEvent.BUY_OPERATION) { 
        		//	orderEvent operation is buy
        		
        		if (accountData.getBalance() >= orderEvent.getPrice()){	
        			//	balance is enough to buy
        			accountData.setBalance(accountData.getBalance()-orderEvent.getPrice());
        			orderEvent.setStatus("Processed");
        			System.out.println("---[Processor: OrderEvent PROCESSED successfully!]---");
        			//	update the accountData object with the new balance 
        			accountDataDAO.updateAccountData(accountData);
        		}
        		else {
        			//	balance insufficient
        			orderEvent.setStatus("InsufficientFunds");
        			System.out.println("---[Processor: OrderEvent rejected due to INSUFFICIENT FUNDS]---");
        		}
        	}
        	else {
        		//	orderEvent operation is sell
        		accountData.setBalance(accountData.getBalance()+orderEvent.getPrice());
        		orderEvent.setStatus("Processed");
        		System.out.println("---[Processor: OrderEvent PROCESSED successfully!]---");
        		//	update the accountData object with the new balance 
        		accountDataDAO.updateAccountData(accountData);
        	}
        }

        //  orderID is declared as primary key and as auto-generated. 
    	//	It must be null before writing an operation.
    	orderEvent.setOrderID(null);       
        
    	return orderEvent;
    }

    /** Prints out the OrderEvent object passed as a parameter. Usually invoked
        when using OpenSpaces remoting. */
    public void sayData(OrderEvent orderEvent) {
        System.out.println("+++[Saying: "+orderEvent);
    }
}
