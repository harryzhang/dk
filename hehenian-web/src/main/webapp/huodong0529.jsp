<%@ page language="java" import="java.util.*"  pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<jsp:include page="/include/head.jsp"></jsp:include>
<%@ page import="java.text.DateFormat" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.sql.*" %>
<%@ page import="com.sp2p.entity.*" %>
    <%@ page import="com.hehenian.biz.common.account.dataobject.AccountUserDo" %>
    <%
	String huodong="huikui";
    AccountUserDo sessionUser=(AccountUserDo)session.getAttribute("user");
	String driver_ = "com.mysql.jdbc.Driver";
	String url_ = "jdbc:mysql://10.111.0.200:3306/sp2p";
	String user_ = "root";
	String password_ = "12345@mfhyn";
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
	        response.sendRedirect("/login.jsp");
	    }else{
	
	    }
	}
	
	Connection conn = DriverManager.getConnection(url_, user_, password_);
	// statement用来执行SQL语句
	Statement statement = conn.createStatement();
	String sql = "SELECT borrowAmount,number,investNum,id,schedules from v_t_borrow_list where borrowStatus in(2,3,4) ORDER BY borrowStatus,publishTime desc limit 5";
	// 结果集
	ResultSet rs = statement.executeQuery(sql);
	List<Map<String ,Object>> lists=new ArrayList<Map<String, Object>>();
	while (rs.next()){
	    String borrowAmount=rs.getString("borrowAmount").replace("万元","").replace("元","");
	    String number=rs.getString("number");
	    String investNum=rs.getString("investNum").replace("元", "");
	    long id=rs.getLong("id");
	    double schedules=rs.getDouble("schedules");
	    Map<String,Object> map=new HashMap<String, Object>();
	    map.put("borrowAmount",borrowAmount);
	    map.put("number",number);
	    map.put("investNum",investNum);
	    map.put("id",id);
	    map.put("schedules",schedules);
	    lists.add(map);
	}
	 request.setAttribute("lists",lists);
	conn.close();
	
	
	
	DateFormat dateFormat=new SimpleDateFormat("yyyyMMdd");
	request.setAttribute("nowDay",dateFormat.format(new Date()));
    %>
</head>
<body>
	<jsp:include page="/include/top.jsp"></jsp:include>
<div style=" background:#063510">
    <div style=" margin:0px auto;text-align:center"><img src="images/huodong/20140529/top.jpg" /></div>
</div>
	<div style=" width:100%; background:#f9f9f9; border-bottom:1px solid #e5e5e5; overflow:hidden; margin-bottom:20px">
    	<div style=" width:1000px;  margin:0px auto;;">
          <div  style=" font-size:32px; font-weight:bold; color:#F00; line-height:45px; width:100%; text-align:center;"></div>
        	<div style=" width:1000px; float:left;text-align:center; height:60px; line-height:30px; padding:10px 0px; font-family:'微软雅黑'">
-------+++
                端午节12+1%活动已圆满结束，感谢大家对合和年在线的大力支持，下次活动我们会做得更好。<br />

对于没有抢到标的亲们，还有12%的标的供大家来投放，投资额达2万以上的亲们将有机会获得我们精心准备的抱枕一个！以表谢意。谢谢大家！
      
            </div>
             

 
</div>
         </div>
         
         
<div style=" width:1000px; margin:0px auto; ">
        	
         
<img src="images/huodong/thx.gif" />


        </div>
        
        
       <div style=" width:1000px; margin:20px auto 0px auto;">
       			 
        

           <s:if test="#request.nowDay=='20140529'">
    <s:iterator value="#request.lists" var="finance">
    <div style=" width:1000px; background:url(images/huodong/20140529/bbj.gif) left center no-repeat; height:170px; overflow:hidden; margin-bottom:20px;">
      <div style=" width:78px; float:left; height:170px;"><img src="images/hot.png"/></div>
      <div class="shouyebiao">
        <ul  style=" float:left">
          <li style=" font-size:16px;"><a href="financeDetail.do?id=${finance.id}" target="_blank">${finance.borrowTitle}</a><br />
            <font size="-2" color="#999999">编号：${finance.number }</font></li>
          <li style=" height:26px;"><img src="images/new/1.gif"  style=" float:left" />借款金额：
            <s:property value="#finance.borrowAmount" default="0" />
            元</li>
          <li style=" height:26px;"><img src="images/new/2.gif" style=" float:left" />借款期限：
            <s:property value="#finance.deadline" default="0" />
            个月
          </li>
          <li style=" height:26px; "><img src="images/new/3.gif"  style=" float:left" />年利率： <font size="3" color="#FF6600">
            12
            %+1&nbsp;

            </font></li>
        </ul>
         <ul style=" float:left">
          <li style=" line-height:52px;">&nbsp;</li>
          <li style=" height:26px; "><span>还款方式： <!-- modify by houli 如果是天标，则还款方式为  到期还本付息 -->
           按月分期还款
            </span></li>
          <li style=" height:26px; "><span>已投人数：
              <s:property value="#finance.investNum1" default="0" />
            人</span></li>
          <li style=" height:26px;  "></li>
        </ul>
      </div>
      <div style=" float:right;  width:275px;padding-right: 40px; padding-top:30px;">
        <div style=" padding-right:20px; text-align:right;" class="nei_button" >
          <s:if test="%{#finance.borrowStatus == 1}">
            <input type="button" value="初审中" style="background: #ccc;cursor:default;" />
          </s:if>
          <s:elseif test="%{#finance.borrowStatus == 2}">
            <div class="bottm"> <input type="button" onclick="window.location.href='financeDetail.do?id=${finance.id}'" value="立即投标" 
														
              <s:if test="%{#finance.schedules==100}"> disabled="disabled" style="background:#ccc;cursor:default;"</s:if>
              /> </div>
          </s:elseif>
          <s:elseif test="%{#finance.borrowStatus == 3}">
            <input type="button" value="满标" style="background: url(../images/pic_all.png) -174px -356px no-repeat ;cursor:default;" />
          </s:elseif>
          <s:elseif test="%{#finance.borrowStatus == 4}">
            <s:if test="%{#finance.borrowShow == 2}">
              <input type="button" value="回购中" style="background: #ccc;cursor:default;" />
            </s:if>
            <s:else>
              <input type="button" onclick="window.location.href='financeDetail.do?id=${finance.id}'" value="还款中" style="background: url(../images/pic_all.png) -174px -300px no-repeat" />
            </s:else>
          </s:elseif>
          <s:elseif test="%{#finance.borrowStatus == 5}">
            <input type="button" value="已还完" style="background: #ccc;cursor:default;" />
          </s:elseif>
          <s:elseif test="%{#finance.borrowStatus == 7}">
            <input type="button" value="复审中" style="background: #ccc;cursor:default;" />
          </s:elseif>
          <s:else>
            <input type="button" value="流标" style="background: #ccc;cursor:default;" />
          </s:else>
        </div>
        <div>
         <div style=" float:left; width:70px;line-height:28px;">借款进度：</div>	<div class="index_jd">	<div style=" font-size:0px; line-height:0px;width: <s:property value="#finance.schedules" default="0"/>%;height:12px; background:url(images/pic_all.png) 0 -67px no-repeat; "></div>
        </div>
      </div>
      <div  style=" padding-right:20px; line-height:32px; text-align:right;" >已成功借款：
        <font color="#FF6600"><s:property value="#finance.hasInvestAmount" default="---" /></font>元
      </div>
      <div  style=" padding-right:20px; text-align:right;" >剩余借款金额：<font color="#006600">
        <s:property value="#finance.investNum" default="---" /></font>元
      </div>
    </div>
  </div>
  <div class="cle"></div>
  </s:iterator>
    </s:if>
    <s:else>
        <img src="images/huodong/20140529/biao.gif" />
    </s:else>

    <div class="cle"></div>
  <s:if test="pageBean.page!=null || pageBean.page.size>0">
    <div class="mjd_fy_all" >
      <shove:page url="finance.do" curPage="${pageBean.pageNum}" showPageCount="7" pageSize="${pageBean.pageSize }" theme="number" totalCount="${pageBean.totalNum}">
        <s:param name="m">${paramMap.m}</s:param>
        <s:param name="title">${paramMap.title}</s:param>
        <s:param name="paymentMode">${paramMap.paymentMode}</s:param>
        <s:param name="purpose">${paramMap.purpose}</s:param>
        <s:param name="raiseTerm">${paramMap.raiseTerm}</s:param>
        <s:param name="reward">${paramMap.reward}</s:param>
        <s:param name="arStart">${paramMap.arStart}</s:param>
        <s:param name="arEnd">${paramMap.arEnd}</s:param>
        <s:param name="type">${paramMap.type}</s:param>
        <s:param name="province">${paramMap.province}</s:param>
        <s:param name="regCity">${paramMap.regCity}</s:param>
      </shove:page>
      <div class="cle"></div>
    </div>
  </s:if>
</div>
        
        
        
        
        
        </div>

	<div class="cle"></div>
	<!-- 引用底部公共部分-->
	<!-- 引用底部公共部分 -->
	<jsp:include page="/include/footer.jsp"></jsp:include>

</body>
</html>
