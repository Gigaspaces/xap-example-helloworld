package org.openspaces.example.oms.runtime;

import org.openspaces.events.adapter.SpaceDataEvent;
import org.openspaces.example.oms.common.IAccountDataDAO;
import org.openspaces.example.oms.common.OrderEvent;
import org.springframework.beans.factory.annotation.Required;

public class OrderEventValidator {

    private long workDuration = 100;
    
    /**
     *	DAO object used to access the AccountData objects implicitly. 
     *	Will be injected from the pu.xml.
     *	Interface enables different DAO Implementations.
     */
    private IAccountDataDAO accountDataDAO;
    
    @Required
    public void setAccountDataDAO(IAccountDataDAO accountDataDAO) {
		this.accountDataDAO = accountDataDAO;
	}

	/**
     * Sets the simulated work duration (in milliseconds). Default to 100.
     */
    public void setWorkDuration(long workDuration) {
        this.workDuration = workDuration;
    }

    /**
     * Validate the given OrderEvent object and returning the validated OrderEvent.
     *
     * Can be invoked using OpenSpaces Events when a matching event
     * occurs or using OpenSpaces Remoting.
     */
    @SpaceDataEvent	//	This annotation marks the method as the event listener.
    public OrderEvent validatesOrderEvent(OrderEvent orderEvent) {
            	    	
    	// sleep to simluate some work
        try {
            Thread.sleep(workDuration);
        } catch (InterruptedException e) {
            // do nothing
        }
     
        System.out.println("---[Validator: Validating orderEvent:"+orderEvent+" ]---");
        
        //	Getting the AccountData object matching the orderEvent userName through the DAO
        boolean isAccountFound = accountDataDAO.isAccountFound(orderEvent.getUserName()); 
        if (isAccountFound == true){
        	//	Matching accountData found - changing orderEvent status to pending.
        	orderEvent.setStatus("Pending");
        	System.out.println("---[Validator: OrderEvent approved, status set to PENDING]---");	
        }
        else {
        	//	No matching accountData found - changing orderEvent status to account not found.
        	orderEvent.setStatus("AccountNotFound");
        	System.out.println("---[Validator: OrderEvent rejected, ACCOUNT NOT FOUND]---");
        }
        
        //  orderID is declared as primary key and as auto-generated. 
    	//	It must be null before writing an operation.
    	orderEvent.setOrderID(null);
    	
        return orderEvent;
    }

    /**
     * Prints out the OrderEvent object passed as a parameter. Usually invoked
     * when using OpenSpaces remoting.
     */
    public void sayData(OrderEvent orderEvent) {
        System.out.println("+++[Saying: "+orderEvent+"]+++");
    }
}
