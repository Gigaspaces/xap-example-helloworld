/*
 * Copyright 2007 GigaSpaces Technologies Ltd. All rights reserved.
 *
 * THIS SOFTWARE IS PROVIDED "AS IS," WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED INCLUDING BUT NOT LIMITED TO WARRANTIES OF MERCHANTABILITY AND 
 * FITNESS FOR A PARTICULAR PURPOSE OR NON-INFRINGEMENT. GIGASPACES WILL NOT 
 * BE LIABLE FOR ANY DAMAGE OR LOSS IN CONNECTION WITH THE SOFTWARE.
 */

package org.openspaces.example.oms.feeder;

import org.openspaces.core.GigaSpace;
import org.openspaces.core.context.GigaSpaceContext;
import org.openspaces.example.oms.common.OrderEvent;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * A feeder bean that starts a scheduled task that writes a new OrderEvent object to the space.
 *
 * <p>The space is injected into this bean using OpenSpaces support for @GigaSpaceContext
 * annoation.
 *
 * <p>The scheduled support uses the java.util.concurrent Scheduled Executor Service. It
 * is started and stopped based on Spring lifeceycle events.
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
	 * Number of accounts loaded by the loader, injected from the pu.xml to calculate
	 * the number of non-matching (without matching accounts) orderEvents to feed.
	 */
    private Integer numberOfAccounts;
        
    /**
     * Percent of AccountData objects to be rejected due to missing account.
     * This is used artificially in this example to create orderEvents that 
     * will not have any matching account.
     */	 
    private Integer dropOffPercent=20;
    
    /**
     * This number is used to create dropOffPercent rejected orderEvents
     */
    private Integer numberOfAccountsPlusAccountsToDrop;
    
    /**
     * The scheduled orderEvent feeding task.
     */ 
    private OrderEventFeederTask orderEventFeederTask;
    
    @GigaSpaceContext(name = "gigaSpace")
    private GigaSpace gigaSpace;
    
    /**
     * @param defaultDelay - Sets default delay between feeding tasks.
     */
    public void setDefaultDelay(long defaultDelay) {
        this.defaultDelay = defaultDelay;
    }
    
    /**
     * @param numberOfAccounts - Sets number of accounts preloaded by the loader.
     */
    public void setNumberOfAccounts(Integer numberOfAccounts) {
		this.numberOfAccounts = numberOfAccounts;
	}
    
	/**
	 * The first method run upon bean Initialization when implementing InitializingBean.
	 * Starts a scheduled orderEvent feeding task. 
	 */
	public void afterPropertiesSet() throws Exception {
        numberOfAccountsPlusAccountsToDrop = (100*numberOfAccounts)/(100-dropOffPercent);
    	System.out.println("---[Starting feeder with cycle <" + defaultDelay + "> ]---");
        //	Create a thread pool containing 1 thread capable of performing scheduled tasks
        executorService = Executors.newScheduledThreadPool(1);
        orderEventFeederTask = new OrderEventFeederTask();
        //	Schedule the thread to execute the task at fixed rate with the default delay defined 
        sf = executorService.scheduleAtFixedRate(orderEventFeederTask, /* initialDelay */defaultDelay, defaultDelay,
                TimeUnit.MILLISECONDS);
    }

    public void destroy() throws Exception {
    	//	Shuting down the thread pool upon bean disposal
    	sf.cancel(true);
        sf = null;
        executorService.shutdown();
    }

    public class OrderEventFeederTask implements Runnable {

        private int counter;
                 
        public void run() {
            try {       
            	//	Create a new orderEvent with randomized userName , price and 
            	//	operation divided between buy and sell values.
            	OrderEvent orderEvent = new OrderEvent
                	("USER" +randomGen.nextInt(numberOfAccountsPlusAccountsToDrop+1), 100/*price*/, 
                	 OrderEvent.OPERATIONS[counter++ % OrderEvent.OPERATIONS.length]);
                gigaSpace.write(orderEvent);
                System.out.println("---[Wrote orderEvent: "+orderEvent+" ]---");
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
}
