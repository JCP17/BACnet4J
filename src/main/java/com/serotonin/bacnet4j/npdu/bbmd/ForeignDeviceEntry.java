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

import com.serotonin.bacnet4j.type.primitive.OctetString;

public class ForeignDeviceEntry {

	private volatile long entryTime;
	private int ttl;
	private OctetString link;
	
	
	public ForeignDeviceEntry(long entryTime, int ttl, OctetString link) {
		this.entryTime=entryTime;
		this.ttl=ttl;
		this.link=link;
	}
	
	public long getEntryTime() {
		return entryTime;
	}
	public void setEntryTime(long entryTime) {
		this.entryTime = entryTime;
	}
	public int getTtl() {
		return ttl;
	}
	public void setTtl(int ttl) {
		this.ttl = ttl;
	}
	public OctetString getLink() {
		return link;
	}
	public void setLink(OctetString link) {
		this.link = link;
	}
	
	public String toString() {
		return "device:"+link+" entry:"+entryTime+" ttl:"+ ttl;
	}
	
}
