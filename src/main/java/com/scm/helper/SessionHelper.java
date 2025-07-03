package com.scm.helper;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpSession;

// This class is used for to remove the Message after reload the page
@Component
public class SessionHelper {

	public static void removeMessage() {
		try {
			System.out.println("Removing Message from session....");
			HttpSession session = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest().getSession();
			session.removeAttribute("message");
			
		} catch (Exception e) {
			System.out.println("Error in SessionHelper : "+e);
			e.printStackTrace();
		}
	}
}
