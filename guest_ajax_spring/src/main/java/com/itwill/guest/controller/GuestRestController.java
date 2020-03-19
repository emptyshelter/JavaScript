package com.itwill.guest.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.itwill.guest.Guest;
import com.itwill.guest.GuestResultList;
import com.itwill.guest.GuestService;

@RestController
public class GuestRestController {
	@Autowired
	private GuestService guestService;
	@RequestMapping(value = "guest/guest_list_html", produces = "text/plain;charset=UTF-8")
	public String guest_list_html() throws Exception {
		StringBuffer sb = new StringBuffer();
		List<Guest> guestList = guestService.selectAll();
		for (int i = 0; i < guestList.size(); i++) {
			Guest guest = guestList.get(i);
			sb.append("<div class=\"guest_item\">");
			sb.append("<h3 class=\"guest_title\"  guest_no=\"" + guest.getGuest_no() + "\"><a href='#'>"
					+ guest.getGuest_title() + "[HTML]</a></h3>");
			sb.append("</div>");
		}
		return sb.toString();
	}
	@RequestMapping(value = "guest/guest_list_json", produces = "application/json;charset=UTF-8")
	public List<Guest> guest_list_json() throws Exception {
		List<Guest> guestList = guestService.selectAll();
		return guestList;
	}
	@RequestMapping(value = "guest/guest_list_xml", produces = "text/xml;charset=UTF-8")
	public GuestResultList guest_list_xml() throws Exception {
		List<Guest> guestList = guestService.selectAll();
		GuestResultList guestResultList = new GuestResultList();
		guestResultList.setGuestList(guestList);
		return guestResultList;
	}
	@RequestMapping(value = "guest/guest_detail_html", produces = "text/plain;charset=UTF-8")
	public String guest_detail_html(@RequestParam int guest_no) throws Exception{
		StringBuffer sb = new StringBuffer();
		Guest guest = guestService.selectByNo(guest_no);
		System.out.println(guest);
		/*
		 * <div class="guest_date">
			날짜:<%=guest.getGuest_date()%>
		</div>
		<div class="guest_name">
			이름:<%=guest.getGuest_name()%>
		</div>
		<div class="guest_email">
			이메일:<%=guest.getGuest_email()%>
		</div>
		<div class="guest_homepage">
			홈페이지:<%=guest.getGuest_homepage()%>
		</div>
		<div class="guest_content">
			내용:<%=guest.getGuest_content()%>
		</div>
		 */
		sb.append("<div class=\"guest_date\">");
		sb.append("<날짜: "+guest.getGuest_date());
		sb.append("</div>");
		sb.append("<div class=\"guest_name\">");
		sb.append("<이름: "+guest.getGuest_name());
		sb.append("</div>");
		sb.append("<div class=\"guest_name\">");
		sb.append("<이메일: "+guest.getGuest_email());
		sb.append("</div>");
		sb.append("<div class=\"guest_name\">");
		sb.append("<홈페이지: "+guest.getGuest_homepage());
		sb.append("</div>");
		sb.append("<div class=\"guest_name\">");
		sb.append("<내용: "+guest.getGuest_content());
		sb.append("</div>");
		return sb.toString();
	}
	@RequestMapping(value = "guest/guest_detail_json", produces = "application/json; charset=UTF-8")
	public List<Guest> guest_detail_json(@RequestParam int guest_no) throws Exception{
		Guest guest = guestService.selectByNo(guest_no);
		List<Guest> guestList = new ArrayList<Guest>();
		guestList.add(guest);
		return guestList;
	}
	@RequestMapping(value = "guest/guest_detail_xml", produces = "text/xml; charset=UTF-8")
	public Guest guest_detail_xml(@RequestParam int guest_no) throws Exception{
		Guest guest = guestService.selectByNo(guest_no);
		return guest;
	}
	@RequestMapping(value = "guest/guest_insert_action",produces = "text/plain;charset=UTF-8" )
	public String guest_insert_action(Guest guest) throws Exception{
		String result= "";
		boolean insertGuest = guestService.insertGuest(guest);
		if (insertGuest) {
			result = "true";
		} else {
			result = "false";
		}
		return result;
	}
	@RequestMapping(value="/guest/guest_login_action", produces="text/plain;charset=UTF-8")
	public String guest_login_action(@RequestParam(value="guest_id", required = true) String guest_id,
									@RequestParam(value="guest_pass", required = true) String guest_pass,
									HttpSession session) {
		String result="fail";
		if(guest_id==null)guest_id="";
		if(guest_pass==null)guest_pass="";
		/*  -----|------
			 id  | pass 
			-----|------	 
			user1|user2
			user1|user2
		*/
		if((guest_id.equals("user1") && guest_pass.equals("user1") )
			||(guest_id.equals("user2") && guest_pass.equals("user2"))){
			session.setAttribute("user_id", guest_id);
			result="success";
		}else{
			result="fail";
		}
		
		return result;
	}
	@RequestMapping(value = "guest/guest_session_check" , produces = "text/plain; charset =UTF-8 ")
	public String guest_session_check(HttpSession session) {
		String isLogin ="";
		String result="";
		String user_id = (String) session.getAttribute("user_id");
		if(user_id != null){
			isLogin="success";
		}
		
		result="{";
		result+="\"status\":\""+isLogin+"\",";
		result+="\"login_id\":\""+user_id+"\"";
		result+="}";
		return result;
	}
	@RequestMapping(value = "guest/guest_logout_action", produces = "text/plain; charset =UTF-8 ")
	public void guest_logout_acution(HttpSession session) {
		session.invalidate();
	}
}
