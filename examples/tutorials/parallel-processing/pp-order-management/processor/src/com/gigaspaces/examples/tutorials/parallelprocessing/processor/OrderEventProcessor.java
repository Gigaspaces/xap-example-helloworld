/*
 * Copyright 2008 GigaSpaces Technologies LTD. All rights reserved.
 *
 * THIS SOFTWARE IS PROVIDED "AS IS," WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED INCLUDING BUT NOT LIMITED TO WARRANTIES OF MERCHANTABILITY AND 
 * FITNESS FOR A PARTICULAR PURPOSE OR NON-INFRINGEMENT. GIGASPACES WILL NOT 
 * BE LIABLE FOR ANY DAMAGE OR LOSS IN CONNECTION WITH THE SOFTWARE.
 */

package com.gigaspaces.examples.tutorials.parallelprocessing.processor;

import org.openspaces.events.adapter.SpaceDataEvent;

import com.gigaspaces.examples.tutorials.parallelprocessing.common.OrderEvent;

/**
 * The processor uses OpenSpaces support for annotation mark-up allowing to use @SpaceDataEvent to
 * mark a method as an event listener. Note, processOrderEvent does not use any space
 * API on the OrderEvent object (though it can), receiving the OrderEvent object to be processed 
 * and returning the result that will be automatically written back to the space.
 * 
 * <p>You can set the simulated work done when processOrderEvent is called by setting the work 
 * duration (defaults to 100 ms).
 * 
 * <p>Note, changing the event container is just a matter of xml configuration (for example,
 * switching from polling container to notify container) and does not affect this class.
 */
public class OrderEventProcessor {

    private long workDuration = 100;
    
    /** 
     * Sets the simulated work duration (in milliseconds). Default to 100. 
     **/
    public void setWorkDuration(long workDuration) {
        this.workDuration = workDuration;
    }

    /**  
     * Process the given OrderEvent object and returns the processed OrderEvent.
     * <p>
     * Annotated @SpaceDataEvent - Marks this method to be executed when an event occurs. 
     **/
    @SpaceDataEvent
    public OrderEvent processOrderEvent(OrderEvent orderEvent) {
        
    	// sleep to simulate some work
        try {
            Thread.sleep(workDuration);
        } catch (InterruptedException e) {
            // do nothing
        }
        
        System.out.println("PROCESSOR Processing orderEvent: "+orderEvent);
        
        //	In this simple example the only processing work will be changing the orederEvent status.
        orderEvent.setStatus(OrderEvent.STATUS_PROCESSED);

        //  orderID is declared as primary key and as auto-generated. 
    	//	It must be null before writing an operation.
    	orderEvent.setOrderID(null);       
        
    	return orderEvent;
    }
}
