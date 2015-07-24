<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<jsp:include page="/include/head.jsp"></jsp:include>
<link href="/css/user.css" rel="stylesheet" type="text/css" />
<link href="http://www.hehenian.com/css/hhncss.css?t=17" rel="stylesheet" type="text/css" />
  <link rel="stylesheet"  href="<c:url value='http://www.hehenian.com/hhn_web/css/public.css'/>">
</head>
<body>
	<jsp:include page="/include/top.jsp"></jsp:include>
<div class="user-all">
  <div class="user-center">
    <div style=" width:1170px; margin:0px auto; padding-bottom:24px;"><img src="/images/v1/detail_logo_bg.jpg" width="1170" height="6"  alt=""/></div>

      <div class="u-left">
        <jsp:include page="/include/menu_userManage.jsp"></jsp:include>
      </div>
    <div class="u-right">
        <div class="right-col clearfix">
            <h3 class="col-title col-title-bd date-select">
                奖励记录
                <div class="input">
                    <input type="text"  data-date-format="yyyy-mm" id="datepicker" value="" readonly />
                    <button id="searchBtn">查询</button>
                    <i class="arrow"></i>
                </div>
            </h3>
            <ul class="award-list">
                <li class="sum"><span id="sumRewardAmount">0.00</span>元</li>
                <li class="label">奖励金额</li>
            </ul>
            <ul class="award-list">
                <li class="sum"><span id="sumTradeAmount">0.00</span>元</li>
                <li class="label">好友投资金额</li>
            </ul>
            <ul class="award-list">
                <li class="sum"><span id="sumRegNum">0</span>人</li>
                <li class="label">好友注册</li>
            </ul>
            <ul class="award-list">
                <li class="sum"><span id="sumInvestNum">0</span>人</li>
                <li class="label">好友投资</li>
            </ul>
        </div>

        <div class="right-col mt16 clearfix">
            <div class="user-table" style="margin-top: 16px;">
                <table>
                    <thead>
                        <tr>
                            <th>投资订单</th>

                            <th>投资时间</th>
                            <th>投资金额</th>
                            <th>投资期数</th>
                            <th>好友用户名</th>
                            <th>奖励金额</th>
                        </tr>
                    </thead>
                    <tbody id = "listBox">
                    </tbody>
                </table>
               <!-- 
               <div class="proPage bProPage" id="pageBox" style="display:none">
              <span class="pageStyle" style="visibility:visible"><a href="javascript:void(0)">上一页</a></span>
              <span class="pageStyle"><a href="javascript:void(0)">1</a></span>
              <span class="pageStyle"><a href="javascript:void(0)">2</a></span>
              <span class="pageStyle onPage"><a href="javascript:void(0)">3</a></span>
              <span class="pageStyle"><a href="javascript:void(0)">4</a></span>
              <span class="pageStyle"><a href="javascript:void(0)">5</a></span>
              <span class="pageStyle"><a href="javascript:void(0)">6</a></span>
              <span class="pageStyle"><a href="javascript:void(0)">7</a></span>
              <span class="pageStyle"><a href="javascript:void(0)">...</a></span>
              <span class="pageStyle"><a href="javascript:void(0)">43</a></span>
              <span class="pageStyle" style="visibility:visible"><a href="javascript:void(0)">下一页</a></span>
            </div>
             -->
            </div>
        </div>

        <div class="right-col mt16 clearfix award-right">
            <h3 class="col-title col-title-bd date-select">
                奖励规则
            </h3>
            <ul>
                <li>1．本活动自5月15日开启。</li>
                <li>2．成功邀请好友注册并投资，即可获得好友投资总额的1%返现奖励；好友为渠道用户则可获得好友投资总额的0.5%返现奖励。</li>
                <li>3．奖励计算不计入投资项目和债权转让的投资金额。</li>
                <li>4．奖励按月发放，当月奖励于次月5个工作日内到账。</li>
            </ul>
            <p class="tip">活动最终解释权归合和年在线所有。</p>
        </div>
    </div>
    

    <div style=" width:1170px; margin:0px auto">&nbsp;</div>
  </div>
</div>
<div class="cle"></div>

<!-- 引用底部公共部分 -->
<jsp:include page="/include/footer.jsp"></jsp:include>
<script type="text/javascript" src="<c:url value="/script/jquery-1.7.1.min.js"/>"></script>
<script src="/css/datepicker/jquery.DatePicker.min.js"></script>
<script>
	var g_CurrentPage = 1;
    var g_PageCount = 6;
    
    $(function() {
        var date = new Date(),
            curTime = date.getFullYear() + '-' + (date.getMonth() + 1),
            picker = $('#datepicker'),
        curTime = curTime.replace(/-(\d)$/, function($0, $1) {
            return '-0' + $1;
        });
        picker.val(curTime);
        picker.datePicker({
            followOffset: [0, 36],
            altFormat: 'yyyy-mm',
            defaultDate: curTime,
            minDate: '2015-05-01',
            maxDate: curTime,
            showMode: 1
        });

		getRewardRecords(curTime);
        $('#searchBtn').click(function(){
            //alert(picker.val());
            getRewardRecords(picker.val());
        });
    });
    
    function getRewardRecords(time){
                var stParam = [];
                stParam.push("time="+time);
                //stParam.push("pageNow="+g_CurrentPage);
      			//stParam.push("pageSize="+g_PageCount);
                $.ajax({
                    url:"/recommandRewardAction/queryReferRewardList.do",
                    type:'post',//非必须.默认get
                    data:stParam,
                    dataType:'json',//非必须.默认text
                    async:true,//非必须.默认true
                    cache:false,//非必须.默认false
                    timeout:60000,//非必须.默认30秒
                    success:getRewardRecordsSuccess//非必须
                });
            }
            function getRewardRecordsSuccess(data){
                if(data.returnCode !=0){
                    openPop(data.message);return;
                }
                var st = data.data;//list
                //var iTotal = data.totalCount;//总数
                //var iPage = parseInt(data.pageNow);//当前页
                //g_CurrentPage = iPage;
                //setPageInfo(iTotal);
                var htm ="";
                if(st!=null) {
                    for (var idx = 0; idx < st.length; idx++) {
                        if (st[idx] != '' && st[idx] != 'undefined') {
                            htm += '<tr><td>' + st[idx].trade_id + '</td>' +
                            '<td>' + st[idx].trade_time + '</td>' +
                            '<td>' + st[idx].trade_amount + '</td>' +
                            '<td>' + st[idx].invest_period + '</td>' +
                            '<td>' + st[idx].user_name + '</td>'+
                            '<td class="cOrange">' + st[idx].reward_amount + '</td>';
                        }
                    }
                }else{
                    htm += '<tr><td colspan="6" align="center">查无记录</td></tr>';
                }
                $("#listBox").html(htm);
                $("#sumInvestNum").html(data.sumInvestNum);
				$("#sumRewardAmount").html(data.sumRewardAmount);
				$("#sumTradeAmount").html(data.sumTradeAmount);
				$("#sumRegNum").html(data.sumRegNum);
            }
            
            
    function goPage(page){
      g_CurrentPage = page;
      getRewardRecords($('#datepicker').val());
    }

    function setPageInfo(allCount){
      var pageSum=Math.ceil(allCount/g_PageCount);
      var start=1;
      var end=0;
      var upPageStyle="";
      var downPageStyle="";
      var stHTML=[];
      if(g_CurrentPage>1){
        upPageStyle="visibility:visible";
        $('#upPageButton').show();
      }else {
        upPageStyle="visibility:hidden";
        $('#upPageButton').hide();
      }
      if(pageSum==g_CurrentPage){
        downPageStyle="visibility:hidden";
        $('#downPageButton').hide();
      }else {
        downPageStyle="visibility:visible";
        $('#downPageButton').show();
      }
      if(g_CurrentPage>4)start=g_CurrentPage-4;
      if(start+8<=pageSum)end=start+8;
      else {
        end=pageSum;
        start=end-8;
      }
      if(start<=0)start=1;
      stHTML.push('<span class="pageStyle" style="');
      stHTML.push(upPageStyle);
      stHTML.push('" onclick="goPage(--g_CurrentPage)"><a href="javascript:void(0)">上一页</a></span>');
      var stDetail=[];
      for(var i=start;i<=end;i++){
        var sClick=' onclick="goPage('+i+')" ';
        var sClass='';
        var sNum=i;
        if(i==g_CurrentPage)sClass=' onPage';
        stDetail.push('<span '+sClick+' class="pageStyle'+sClass+'" ><a href="javascript:void(0)">'+sNum+'</a></span>');
      }
      //显示开始和结束页码
      if(stDetail.length==9){
        if(start>1){
          stDetail[0]='<span class="pageStyle" onclick="goPage(1)"><a href="javascript:void(0)">1</a></span>';
          stDetail[1]='<span class="pageStyle"><a href="javascript:void(0)">...</a></span>';
        }
        if(end<pageSum){
          stDetail[8]='<span class="pageStyle" onclick="goPage('+pageSum+')"><a href="javascript:void(0)">'+pageSum+'</a></span>';
          stDetail[7]='<span class="pageStyle"><a href="javascript:void(0)">...</a></span>';
        }
      }
      stHTML.push(stDetail.join(''));
      stHTML.push('<span class="pageStyle" style="');
      stHTML.push(downPageStyle);
      stHTML.push('" onclick="goPage(++g_CurrentPage)"><a href="javascript:void(0)">下一页</a></span>');
      if(pageSum<2)$('#pageBox').hide();
      else $('#pageBox').html(stHTML.join('')).show();
    }
    </script>
</body>

</html>
