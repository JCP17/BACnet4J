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
	private static Logger LOG = Logger.getLogger(ForeignDeviceTable.class);

	private HashMap<OctetString, ForeignDeviceEntry> foreignDeviceMap= new HashMap<OctetString, ForeignDeviceEntry>();
	private DeviceExpiryThread expiryThread;
	
	//TODO limit max size of table and reject DeviceRegistrations
	public ForeignDeviceTable() {
		expiryThread= new DeviceExpiryThread();
		expiryThread.start();
	}
	
	
	public void shutdown() {
		expiryThread.running.set(false);
	}
	
	public void registerDevice(OctetString link, int ttl) {
		
		ForeignDeviceEntry entry = new ForeignDeviceEntry(new Date().getTime(), ttl, link);
		if(!foreignDeviceMap.keySet().contains(link)){
			LOG.debug("adding device to table");
			foreignDeviceMap.put(link,entry);
		} else {
			LOG.debug("device already registered, only updating time");
			foreignDeviceMap.get(link).setEntryTime(new Date().getTime());
		}
	}
	
	public Set<OctetString> getForeignDeviceBroadcastList() {
		
		return foreignDeviceMap.keySet();
		
	}
	
	class DeviceExpiryThread extends Thread {

		AtomicBoolean running = new AtomicBoolean(true);
		
		public DeviceExpiryThread() {
			this.setName("FDT Expiry Thread");
		}
		
		@Override
		public void run() {
			
			while(running.get()) {
				LOG.debug("doing foreign device expiry");
				List<OctetString> expiredDevices = new ArrayList<OctetString>();
				for(ForeignDeviceEntry e : foreignDeviceMap.values()) {
					long currentTime = new Date().getTime();
					long timeSpent = (currentTime-e.getEntryTime())/1000;
					if(timeSpent > e.getTtl())  expiredDevices.add(e.getLink());
					LOG.debug(e.toString()+ " -> time spent: "+timeSpent+" secs");
				}
				for(OctetString s : expiredDevices) {
					LOG.debug("remove expired device "+s.toIpPortString());
					foreignDeviceMap.remove(s);
				}
				
				try {
					Thread.sleep(10000);
				} catch (InterruptedException e1) {
					LOG.error("exc",e1);
				}
			}
		
		}
		
		
		
	}
	
}
