package com.shove.util;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

public class JSUtils {

	public static void print(HttpServletResponse response,String str){
		PrintWriter pr = null;
		try {			  
			response.setContentType("text/html;charset=utf-8");			
			pr = response.getWriter();	 
			pr.print(str);
		} catch (IOException e) {
			e.printStackTrace();
		}	   			
	}
 
	public static void alert(HttpServletResponse response,String msg){		
	    StringBuffer str = new StringBuffer();	 	
		str.append("<script type=\"text/javascript\">");			
		str.append("  alert('"+msg+"');   ");
		str.append("</script>");
		print(response,str.toString());		
	}
	
	public static void call(HttpServletResponse response,String call,String target){		
	    StringBuffer str = new StringBuffer();	 	
		str.append("<script type=\"text/javascript\">");	
		if(target != null){
		   str.append("window.").append(target).append(".");
		}
		str.append(call).append(";");
		str.append("</script>");
		print(response,str.toString());		
	}
	
	
	public static void location(HttpServletResponse response,String url,String msg,String target){
	    StringBuffer str = new StringBuffer();	 	
		str.append("<script type=\"text/javascript\">");
		if(msg != null){
		  str.append("  alert('"+msg+"');   ");
		}
		if(target != null){
		   str.append("window."+target+".location.href='").append(url).append("';"); 		 
		}
		 
		str.append("</script>");
		print(response,str.toString());			   	
	}
	
	public static void location(HttpServletResponse response,String url){		
		location(response,url,null,"self");
	}
	
	public static void location(HttpServletResponse response,String url,String msg){		
		location(response,url,msg,"self");
	}
	
 
	
}
