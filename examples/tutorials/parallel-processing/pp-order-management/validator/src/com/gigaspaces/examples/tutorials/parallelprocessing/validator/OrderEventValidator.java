package com.gigaspaces.examples.tutorials.parallelprocessing.validator;

import org.openspaces.events.adapter.SpaceDataEvent;

import com.gigaspaces.examples.tutorials.parallelprocessing.common.OrderEvent;

/**
 * Simple bean used to validate the orderEvent objects.
 */
public class OrderEventValidator {

    private long workDuration = 100;
    
	/**
     * Sets the simulated work duration (in milliseconds). Default to 100.
     */
    public void setWorkDuration(long workDuration) {
        this.workDuration = workDuration;
    }

    /**
     * Validate the given OrderEvent object and return the validated OrderEvent with
     * status field set to Approved/Rejected according to the validation.
     *
     * Can be invoked using OpenSpaces Events when a matching event
     * occurs or using OpenSpaces remoting.
     */
    @SpaceDataEvent	//	This annotation marks the method as the event listener.
    public OrderEvent validatesOrderEvent(OrderEvent orderEvent) {
            	    	
    	// sleep to simulate some work
        try {
            Thread.sleep(workDuration);
        } catch (InterruptedException e) {
            // do nothing
        }
     
        System.out.println("VALIDATOR validating orderEvent: "+orderEvent);
        
        //	For the sake of the example the status is arbitrarily randomized.
		if ( Math.random() >= 0.5)
		{
			orderEvent.setStatus(OrderEvent.STATUS_APPROVED);
		}
		else 
		{
			orderEvent.setStatus(OrderEvent.STATUS_REJECTED);
		}       
        
		System.out.println("VALIDATOR set orderEvent status to: "+orderEvent.getStatus());
		
        //  orderID is declared as primary key and as auto-generated. 
    	//	It must be null before writing an operation.
    	orderEvent.setOrderID(null);
    	
        return orderEvent;
    }
}
