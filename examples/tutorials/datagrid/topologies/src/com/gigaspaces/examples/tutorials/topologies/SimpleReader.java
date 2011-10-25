/*
 * Copyright 2007 GigaSpaces Technologies Ltd. All rights reserved.
 *
 * THIS SOFTWARE IS PROVIDED "AS IS," WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED INCLUDING BUT NOT LIMITED TO WARRANTIES OF MERCHANTABILITY AND 
 * FITNESS FOR A PARTICULAR PURPOSE OR NON-INFRINGEMENT. GIGASPACES WILL NOT 
 * BE LIABLE FOR ANY DAMAGE OR LOSS IN CONNECTION WITH THE SOFTWARE.
 */
/**
 * Title:        SimpleReader.
 * Description:  Reads POJO account objects from the space.
 * 
 */

package com.gigaspaces.examples.tutorials.topologies;

import org.openspaces.core.GigaSpace;
import org.openspaces.core.GigaSpaceConfigurer;
import org.openspaces.core.space.UrlSpaceConfigurer;

import com.j_spaces.core.IJSpace;

public class SimpleReader {
	
	static IJSpace space = null;
	static GigaSpace gigaSpace;
	
	private Integer numberOfAccountsToRead;
	
	public Integer getNumberOfAccountsToRead() {
		return numberOfAccountsToRead;
	}

	public void setNumberOfAccountsToRead(Integer numberOfAccountsToRead) {
		this.numberOfAccountsToRead = numberOfAccountsToRead;
	}
	
	/**
	 * Configure reader and connect it to the space
	 * @param url
	 */
	public SimpleReader(String url) {
		
		setNumberOfAccountsToRead(100);
		
        IJSpace space = new UrlSpaceConfigurer(url).space(); // Connect to the space using the URL
        
        gigaSpace = new GigaSpaceConfigurer(space).gigaSpace(); // Use gigaSpace simplified API
    }
	
	/**
	 * Read and display accounts
	 * @throws Throwable
	 */
	public void read() throws Throwable {
		
		//	Read accounts from space
		//	------------------------
		Object accounts[] = null;
		Account template = new Account();
		accounts = gigaSpace.readMultiple(template, getNumberOfAccountsToRead());
	
		//	Output accounts read
		//	--------------------
		if ((accounts != null) && (accounts.length != 0)){
			System.out.println("Read "+accounts.length+" objects from space");
			for (int j=0; j<((accounts.length+1) / 2); j++){
				System.out.print("["+accounts[j*2].toString()+"]");
				if (((accounts.length % 2 != 0) && (accounts.length == (j*2)+1)) == false){
					System.out.println("\t["+accounts[(j*2)+1].toString()+"]");
				}
			}
		}
		else {
			System.out.println("No accounts found, use DataLoader application to populate the space with accounts");
		}
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		System.out.println(args[0]);
		if (args.length != 1) {
			System.out.println("Usage: <URL>");
			System.out.println("<protocol>://host:port/containername/spacename");
			System.exit(1);
		}
		
		try {
			System.out.println("\nWelcome to GigaSpaces Data_Grid Topologies Example");        
			System.out.println("This example reads from various caching topologies...");       
	      	
	        Integer counter = 0;
			
	        SimpleReader simpleReader = new SimpleReader(args[0]); // Create reader connected to the space
			
			//	Reading loop
			while(true){
				counter++;
				System.out.println("\n\nRead round number: "+counter);
				
				simpleReader.read(); // Read accounts
				
				Thread.sleep(8000); //	Sleeping to enable console viewing
			}
	
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
}