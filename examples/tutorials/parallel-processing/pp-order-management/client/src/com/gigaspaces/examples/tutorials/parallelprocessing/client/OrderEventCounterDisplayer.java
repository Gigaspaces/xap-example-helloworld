package com.gigaspaces.examples.tutorials.parallelprocessing.client;

import org.openspaces.events.adapter.SpaceDataEvent;

import com.gigaspaces.examples.tutorials.parallelprocessing.common.OrderEvent;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * A simple bean counting and outputting:
 * number of processed and rejected orderEvents taken by the client.
 * 
 * Holds 2 simple counters that are incremented each time a matching event occurs.
 * Outputting the orderEvent updated status.
 */
public class OrderEventCounterDisplayer {
	
    private AtomicInteger orderEventProcessedCounter = new AtomicInteger(0);
    private AtomicInteger orderEventRejectedCounter = new AtomicInteger(0);
        
    @SpaceDataEvent
    public void outputInfo(OrderEvent orderEvent) {
        
        if (orderEvent.getStatus().equals(OrderEvent.STATUS_PROCESSED)){
        	orderEventProcessedCounter.incrementAndGet();
        }
        else {
        	if (orderEvent.getStatus().equals(OrderEvent.STATUS_REJECTED)){
            	orderEventRejectedCounter.incrementAndGet();
        	}
        }
        System.out.println("CLIENT took "+orderEvent.getStatus()+ 
				" OrderEvent , Total rejected taken ["+orderEventRejectedCounter+"], Total processed taken ["
				+orderEventProcessedCounter+"]");
    }  
}
