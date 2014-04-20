/*
 * ============================================================================
 * GNU General Public License
 * ============================================================================
 *
 * Copyright (C) 2014 J.Seitter
 * @author J.Seitter
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>. *
 */
package com.serotonin.bacnet4j.npdu.bbmd;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.log4j.Logger;

import com.serotonin.bacnet4j.npdu.bbmd.ForeignDeviceEntry;
import com.serotonin.bacnet4j.type.primitive.OctetString;

public class ForeignDeviceTable {
	private static final Logger LOG = Logger.getLogger(ForeignDeviceTable.class);
        
        // TODO check out maximum size of table
        private static final int FOREIGN_DEVICE_TABLE_MAX_ENTRIES = 512;
                
	private final HashMap<OctetString, ForeignDeviceEntry> foreignDeviceMap = new HashMap<>();
	private final DeviceExpiryThread expiryThread;
	
	public ForeignDeviceTable() {
            
		expiryThread = new DeviceExpiryThread();
		expiryThread.start();
                
	}
	
	
	public void shutdown() {
            
                expiryThread.halt();
                
	}
	
        /**
         * register a foreign device entry
         * @param link device
         * @param ttl time to live for entry in seconds
         */
        public void registerDevice(final OctetString link, final int ttl) {
            
                if (ttl <= 0) {
                    // invalid ttl given
                    throw new IllegalArgumentException("Illegal ttl given: " + ttl);
                }
                
                if (foreignDeviceMap.size() >= FOREIGN_DEVICE_TABLE_MAX_ENTRIES) {
                    // TODO find a better exception here
                    throw new RuntimeException("cannot register foreign device " + link.toString() + " because foreign device table has reached its maximum size of " + FOREIGN_DEVICE_TABLE_MAX_ENTRIES + " entries.");
                }
		
		if(!foreignDeviceMap.keySet().contains(link)){
			LOG.debug("adding device to table");
                        ForeignDeviceEntry entry = new ForeignDeviceEntry(new Date().getTime(), ttl, link);
			foreignDeviceMap.put(link, entry);
		} else {
			LOG.debug("device already registered, only updating time");
                        ForeignDeviceEntry entry = foreignDeviceMap.get(link);
                        if (entry == null) {
                            throw new RuntimeException("cannot update ForeignDeviceEntry for link " + link.toString() + " because link was not found");
                        }
                        entry.setEntryTime(new Date().getTime());
		}
	}
	
	public Set<OctetString> getForeignDeviceBroadcastList() {
		
		return foreignDeviceMap.keySet();
		
	}
	
        /**
         * thread for device expiry checks
         * checks on a regulary interval which registered foreign devices have expired
         */
	class DeviceExpiryThread extends Thread {

		private final AtomicBoolean running = new AtomicBoolean(true);
		
		public DeviceExpiryThread() {
                    
			this.setName("FDT Expiry Thread");
                        
		}
                
                /**
                 * halt this thread
                 */
                public void halt() {
                    
                    running.set(false);
                    this.interrupt();
                    
                }
		
		@Override
		public void run() {
			
			while(running.get()) {
                            
				LOG.debug("doing foreign device expiry");
				List<OctetString> expiredDevices = new ArrayList<>();
				for(ForeignDeviceEntry e : foreignDeviceMap.values()) {
                                        // find all devices which are expired
					long currentTime = new Date().getTime();
					long timeSpent = (currentTime - e.getEntryTime()) / 1000;
					if(timeSpent > e.getTtl()) {
                                                expiredDevices.add(e.getLink());
                                        }
					LOG.debug(e.toString() + " -> time spent: " + timeSpent + " secs");
                                        
				}
                                
                                // remove all expired devices
				for(OctetString s : expiredDevices) {
                                    
					LOG.debug("remove expired device " + s.toIpPortString());
					foreignDeviceMap.remove(s);
                                        
				}
				
				try {
					Thread.sleep(10000);
				} catch (InterruptedException e1) {
					// if thread is interrupted (e.g. with halt())
                                        // break the classes main loop to stop instantly
                                        break;
				}
                                
			}
		
		}
		
	}
	
}
