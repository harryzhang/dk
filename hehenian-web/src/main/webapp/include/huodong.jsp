<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.text.DateFormat" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.sql.*" %>
<%@ page import="com.sp2p.entity.*" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.*" %>
<%@ page import="com.hehenian.biz.common.account.dataobject.AccountUserDo" %>
<%
	String huodong="huikui";
    AccountUserDo sessionUser=(AccountUserDo)session.getAttribute("user");
	String driver_ = "com.mysql.jdbc.Driver";
	 String url_ = "jdbc:mysql://10.111.0.200:3306/sp2p";
	String user_ = "root";
	String password_ = "12345@mfhyn"; 
	/* String url_ = "jdbc:mysql://10.111.0.200:3306/sp2p";
	String user_ = "root";
	String password_ = ""; */
	Class.forName(driver_);
	if(sessionUser!=null){
	System.out.println(sessionUser);
	    Object userId=sessionUser.getId();
	    System.out.println("session:"+sessionUser.getId());
	    Connection conn = DriverManager.getConnection(url_, user_, password_);
	    Statement statement = conn.createStatement();
	    String sql = "select * from t_huodong_yuyue where userId="+userId+" and huodongId='"+huodong+"'";
	    ResultSet rs = statement.executeQuery(sql);
	    boolean hasYuyue=false;
	    System.out.println("-----------");
	    if (rs!=null&&rs.next()){
	        hasYuyue=true;
	        System.out.println("已预约");
	    }else{
	        System.out.println("未预约");
	    }
	
	    if ("yy".equals(request.getParameter("cmd"))&&!hasYuyue){
	        sql="insert into t_huodong_yuyue(huodongId,userId,createTime) values('"+huodong+"',"+userId+",now())";
	        statement.execute(sql);
	        hasYuyue=true;
	    }else{
	    }
	    conn.close();
	    request.setAttribute("hasYuyue",hasYuyue);
	}else{
	    //  response.sendRedirect("/login.jsp");
	//    request.getRequestDispatcher("/login.do").forward(request,response);
	    if ("yy".equals(request.getParameter("cmd"))){
	        //这是要预约啊
	        System.out.println("xxxxxxxxxxxxxxxxxxxxxx");
	        response.sendRedirect("/login.jsp");
	    }else{
	    	System.out.println("yyyyyyyyyyyyyyyy");
	    }
	}
	
	
	
	DateFormat dateFormat=new SimpleDateFormat("yyyyMMdd");
	request.setAttribute("nowDay",dateFormat.format(new Date()));
    %>