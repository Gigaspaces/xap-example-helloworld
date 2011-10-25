package org.openspaces.example.oms.stats;

import org.openspaces.core.GigaSpace;
import org.openspaces.core.context.GigaSpaceContext;
import org.openspaces.events.adapter.SpaceDataEvent;
import org.openspaces.example.oms.common.OrderEvent;
import org.springframework.beans.factory.InitializingBean;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * A simple bean counting the number of orderEvent writes , processes and rejection due to
 * account not found. 
 * Holds a 3 simple counters that are incremented each time a matching event occurs.
 * Outputting the orderEvent updated status.
 *
 * <p>Also note, the orderEvent that will be counted depends on the
 * configuration. For example, this example uses the "non clustered" view
 * of the space while running within an embedded space. This means this
 * coutner will count only the relevant partition processed data. It is
 * just a matter of configuration to count the number of processed data
 * across a cluster.
 *
 */
public class OutputOrderEventCounter implements InitializingBean {
	
	private AtomicInteger orderEventWrittenCounter = new AtomicInteger(0);
    private AtomicInteger orderEventProcessedCounter = new AtomicInteger(0);
    private AtomicInteger orderEventAccountNotFoundCounter = new AtomicInteger(0);
    
    @GigaSpaceContext(name = "gigaSpace")
    private GigaSpace gigaSpace;
    
    public void afterPropertiesSet() throws Exception {
		//	Upon bean instantiating counts how many New, Processed and AccountNotFound 
    	//	are in the space and setting the relevant counters.
    	OrderEvent orderEvent = new OrderEvent();
    	orderEvent.setStatus("New");
    	int counter = gigaSpace.count(orderEvent);
    	orderEventWrittenCounter = new AtomicInteger(counter);
    	orderEvent.setStatus("Processed");
    	counter = gigaSpace.count(orderEvent);
    	orderEventProcessedCounter = new AtomicInteger(counter);
    	
    	orderEvent.setStatus("AccountNotFound");
    	counter = gigaSpace.count(orderEvent);
    	orderEventAccountNotFoundCounter = new AtomicInteger(counter);
	}
    
    @SpaceDataEvent
    public void outputInfo(OrderEvent orderEvent) {
        System.out.println("---{ OrderEvent ["+orderEvent.getStatus()+ 
        					"], Total operations on orderEvents [" + orderEventWrittenCounter + "] }---");
        if (orderEvent.getStatus().equals("New")){
        	orderEventWrittenCounter.incrementAndGet();
        }
        else {
        	if (orderEvent.getStatus().equals("Processed")){
            	orderEventProcessedCounter.incrementAndGet();
        	}
        	else {
            	if (orderEvent.getStatus().equals("AccountNotFound")){
                	orderEventAccountNotFoundCounter.incrementAndGet();
            	}
        	}
        } 	
    }

    public int getOrderEventWrittenCounter() {
        return orderEventWrittenCounter.intValue();
    }
    
    public int getOrderEventProcessedCounter() {
        return orderEventProcessedCounter.intValue();
    }
    
    public int getOrderEventAccountNotFoundCounter() {
        return orderEventAccountNotFoundCounter.intValue();
    }
}
