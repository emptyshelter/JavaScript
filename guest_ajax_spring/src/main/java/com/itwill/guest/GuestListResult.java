package com.itwill.guest;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="result")
public class GuestListResult {
	private List<Guest> guestList;

	@XmlElementWrapper(name="guestList")
	@XmlElement(name="guest")
	public List<Guest> getGuestList(){
		return guestList;
	}
	public void setGuestList(List<Guest> guestList) {
		this.guestList = guestList;
	}
	
}
