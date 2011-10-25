/*
 * Copyright 2008 GigaSpaces Technologies Ltd. All rights reserved.
 *
 * THIS SOFTWARE IS PROVIDED "AS IS," WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED INCLUDING BUT NOT LIMITED TO WARRANTIES OF MERCHANTABILITY AND 
 * FITNESS FOR A PARTICULAR PURPOSE OR NON-INFRINGEMENT. GIGASPACES WILL NOT 
 * BE LIABLE FOR ANY DAMAGE OR LOSS IN CONNECTION WITH THE SOFTWARE.
 */

package com.gigaspaces.examples.tutorials.parallelprocessing.client;

import org.openspaces.core.GigaSpace;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import com.gigaspaces.examples.tutorials.parallelprocessing.common.OrderEvent;

import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.rmi.server.UID;

/**
 * A feeder bean that starts a scheduled task that writes a new OrderEvent object to the space.
 *
 * <p>The space is injected into this bean using OpenSpaces support for @GigaSpaceContext
 * annotation.
 *
 * <p>The scheduled support uses the java.util.concurrent Scheduled Executor Service. It
 * is started and stopped based on Spring life-cycle events.
 */
public class OrderEventFeeder implements InitializingBean, DisposableBean {

	private Random randomGen = new Random();

    private ScheduledExecutorService executorService;

    //	Delayed result bearing action
    private ScheduledFuture<?> sf;
    
    /**
     * Delay between scheduled tasks
     */
    private long defaultDelay = 1000;
    
    /**
     * The scheduled orderEvent feeding task.
     */ 
    private OrderEventFeederTask orderEventFeederTask;   
    
    private GigaSpace gigaSpace;
    
    /**
     * Unique ID for this client
     */
    private UID clientID;
    
    public void setGigaSpace(GigaSpace gigaSpace) {
		this.gigaSpace = gigaSpace;
	}

	/**
     * @param defaultDelay - Sets default delay between feeding tasks.
     */
    public void setDefaultDelay(long defaultDelay) {
        this.defaultDelay = defaultDelay;
    }
    
	/**
	 * The first method run upon bean Initialization when implementing InitializingBean.
	 * Starts a scheduled orderEvent feeding task. 
	 */
	public void afterPropertiesSet() throws Exception {
    	
		//	Create unique ID for this client
		clientID = new UID(); 
		
		System.out.println("CLIENT ["+clientID.toString()+"] Starting feeder with cycle <" + defaultDelay + ">");
        
		//	Create a thread pool containing 1 thread capable of performing scheduled tasks
        executorService = Executors.newScheduledThreadPool(1);
        
        orderEventFeederTask = new OrderEventFeederTask();
        
        //	Schedule the thread to execute the task at fixed rate with the default delay defined 
        sf = executorService.scheduleAtFixedRate(
        										orderEventFeederTask	// The task to schedule
        										,defaultDelay 			// Initial Delay before starting
        										,defaultDelay			// Delay between tasks
        										,TimeUnit.MILLISECONDS	// Time unit for the delay
        										);
    }

    public void destroy() throws Exception {
    	//	Shutting down the thread pool upon bean disposal
    	sf.cancel(true);
        sf = null;
        executorService.shutdown();
    }

    public class OrderEventFeederTask implements Runnable {
    	
    	//	Counts number of fed orderEvents
        private int counter;
                 
        public void run() {
            try {       
            	//	Create a new orderEvent with randomized userName and stamps the clientID
            	OrderEvent orderEvent = new OrderEvent("USER" +randomGen.nextInt(), clientID.toString());
                //	Write the new orderEvent to the space
            	gigaSpace.write(orderEvent);
                System.out.println("CLIENT wrote orderEvent: "+orderEvent);
            } 
            catch (Exception e) {
                e.printStackTrace();
            }
        }

        public int getCounter() {
            return counter;
        }
    }

    public int getFeedCount() {
        return orderEventFeederTask.getCounter();
    }

	public void setClientID(UID clientID) {
		this.clientID = clientID;
	}

	public UID getClientID() {
		return clientID;
	}
}
