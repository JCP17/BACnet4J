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

import org.apache.log4j.Logger;
import org.free.bacnet4j.util.ByteQueue;

import com.serotonin.bacnet4j.exception.BACnetException;
import com.serotonin.bacnet4j.npdu.MessageValidationAssertionException;
import com.serotonin.bacnet4j.npdu.bbmd.ForeignDeviceTable;
import com.serotonin.bacnet4j.npdu.ip.IpNetwork;
import com.serotonin.bacnet4j.type.primitive.OctetString;
import java.util.Map;


public class BBMDevice {

	private static final Logger LOG = Logger.getLogger(BBMDevice.class);
	private final ForeignDeviceTable foreignDeviceTable;
	private final IpNetwork network;
	
	public BBMDevice(final IpNetwork ipNetwork) {
            
		network = ipNetwork;
		foreignDeviceTable = new ForeignDeviceTable();
                
	}
	
	/**
	 * handle register foreign device call
         * @param queue
         * @param from
         * @return 
	 * @throws MessageValidationAssertionException 
	 */
	public boolean registerForeignDevice(final ByteQueue queue, final OctetString from) throws MessageValidationAssertionException {
		/*
			BVLC Length: 2-octets X'0006' Length, in octets, of the BVLL message
			Time-to-Live 2-octets T Time-to-Live T, in seconds
			The Time-to-Live value is the number of seconds within which a foreign device must re-register with a BBMD or risk having its
			entry purged from the BBMD's FDT. This value will be sent most significant octet first. See J.5.2.2.		
		*/
		
		int length = queue.popU2B();
		if(length != 6) {
                        throw new MessageValidationAssertionException("length field is not 6 for function 0x5");
                }
                
		int ttl = queue.popU2B();
		
		LOG.debug("foreign device request from " + from + " with TTL=" + ttl);
                try {
                        foreignDeviceTable.registerDevice(from, ttl);
                } catch (BACnetException ex) {
                        LOG.error("cannot register device: " + ex.getMessage());
                        return false;
                }
                
		return true;
                
	} 
	
	public boolean deleteForeignDeviceTableEntry(final OctetString device) {

		return foreignDeviceTable.deleteDevice(device);
                
	}
	
	public ByteQueue readForeignDeviceTable() {
		
                return foreignDeviceTable.readForeignDeviceTable();
                
	}
	
	/**
	 * distribute a received broadcast
	 * @param queue
         * @param from
	 * @throws BACnetException 
	 */
	public void handleReceivedBroadcast(final ByteQueue queue, final OctetString from) throws BACnetException {
		
                LOG.debug("processing broadcast for BBMD");
		for(OctetString target : foreignDeviceTable.getForeignDeviceBroadcastList()) {
                    
			LOG.debug("forward to "+target);
                        network.forwardPacket(target, from, queue);
                        
		}
                
	}
	
	public void handleDistributeBroadcastToNetwork(final ByteQueue queue, final OctetString from) throws BACnetException {

                network.forwardPacket(network.getLocalBroadcastAddress().getMacAddress(), from, queue);

        }
}
