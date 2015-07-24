<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<link rel="stylesheet" type="text/css" href="css/hhncss.css">
	<link href="css/user.css" rel="stylesheet" type="text/css" />
	<script src="script/jquery-1.2.6.pack.js" type="text/javascript"></script>
	<script src="script/add_all.js" type="text/javascript"></script>
	<script src="script/MSClass.js" type="text/javascript"></script>
	<link rel="stylesheet" href="css/jbox/Gray/jbox.css" type="text/css">
	</link>
	<script type="text/javascript" src="css/popom.js"></script>
	<script type="text/javascript" src="script/jquery.shove-1.0.js"></script>
	<script type="text/javascript" src="script/jbox/jquery.jBox-2.3.min.js"></script>
	<script language="javascript" type="text/javascript" src="My97DatePicker/WdatePicker.js"></script>
	<script type="text/javascript">
		$(function() {
			$(".header_two_right_ul li").hover(function() {
				$(this).find("ul").show();
			}, function() {
				$(this).find("ul").hide();
			})

			$(".wdzh_top_ul li").eq(1).addClass("wdzhxxk");
			$(".wdzh_top_ul li").click(function() {
				var ppain = $(".wdzh_top_ul li").index(this);
				$(".wdzh_top_ul li").removeClass("wdzhxxk");
				$(this).addClass("wdzhxxk");
	<%--		$(".wdzh_next").hide();--%>
		
	<%--		$(".wdzh_next").eq(ppain).show();--%>
		})
			$(".tzjl_fwxy").click(function() {
				$(".tzjl_fwxyh").show();
			})
			$(".tzjl_fwxy1").click(function() {
				$(".tzjl_fwxy1h").show();
			})
		})
	</script>
	<jsp:include page="/include/head.jsp"></jsp:include>
	</head>
	<body>
<jsp:include page="/include/top.jsp"></jsp:include>
<div class="user-all">
      <div class="user-center">
    <div style=" width:1170px; margin:0px auto; padding-bottom:24px;"><img src="/images/v1/detail_logo_bg.jpg" width="1170" height="6"  alt=""/></div>
    <div style=" overflow:hidden">
          <div class="u-left">
        <jsp:include page="/include/menu_userManage.jsp"></jsp:include>
      </div>
          <div class="u-right">
        <div class="user-right">
              <h2>理财统计</h2>
              <div class="xxtj_one" style=" margin-top:20px;" >
            <div class="xxtj_one_a" style=" width:420px; margin-right:40px;">
                  <h3>投标概览</h3>
                  <table  border="0"  width="400">
                <tr height="30">
                      <td width="80">投标中</td>
                      <td align="right" class="total">￥${map.investingTotal }</td>
                      <td align="right" class="num">${map.investingN }</td>
                    </tr>
                <tr height="30">
                      <td width="80">回款中</td>
                      <td align="right" class="total">￥${map.repayingTotal }</td>
                      <td align="right" class="num">${map.repayingN }</td>
                    </tr>
                <tr height="30">
                      <td width="80">回款完毕</td>
                      <td align="right" class="total">￥${map.repayedTotal }</td>
                      <td align="right" class="num">${map.repayedN }</td>
                    </tr>
                <tr height="30">
                      <td width="80">逾期30天以内</td>
                      <td align="right" class="total">￥${map.outedTotal }</td>
                      <td align="right" class="num">${map.outedN }</td>
                    </tr>
             <!--   <tr height="30">
                      <td width="80">已垫付</td>
                      <td align="right" class="total">￥${map.payforTotal }</td>
                      <td align="right" class="num">${map.payforN }</td>
                    </tr>-->
                <tr>
                      <td style="border-bottom:1px dashed #ccc;">&nbsp;</td>
                      <td style="border-bottom:1px dashed #ccc;">&nbsp;</td>
                      <td style="border-bottom:1px dashed #ccc;">&nbsp;</td>
                    </tr>
                <tr height="30">
                      <td width="80"><strong>全部</strong></td>
                      <td align="right" class="total"><strong> ￥${map.investingTotal+map.repayingTotal+map.repayedTotal+map.outedTotal+map.payforTotal } </strong></td>
                      <td align="right" class="num"><strong> ${map.investingN+map.repayingN+map.repayedN+map.outedN+map.payforN } </strong></td>
                    </tr>
                <tr>
                      <td>&nbsp;</td>
                      <td align="right" colspan="2"><span>呈现方式：</span><a href="#" onclick="moneyHide();">金额</a><span>|</span><a href="#" onclick="numHide();">笔数</a></td>
                    </tr>
              </table>
                </div>
            <div class="xxtj_one_a"  style=" width:420px; margin-right:0px;" >
                  <h3>投标统计列表</h3>
                  <table  border="0" width="390">
                <%--<tr height="30">
									<td width="80">加权收益率</td>
									<td align="right">0.00%</td>
								</tr>--%>
                <tr height="30">
                      <td width="80">待收利息</td>
                      <td align="right">￥${map.forPayInterest }</td>
                    </tr>
                <tr height="30">
                      <td width="80">总回款额</td>
                      <td align="right">￥${map.hasPaySum }</td>
                    </tr>
                <tr height="30">
                      <td width="80">总回收本金</td>
                      <td align="right">￥${map.hasPayPrincipal }</td>
                    </tr>
                <tr height="30">
                      <td width="80">总回收利息</td>
                      <td align="right">￥${map.hasPayInterest }</td>
                    </tr>
                <tr height="30">
                      <td width="80">总回收罚息</td>
                      <td align="right">￥${map.hasFITotal }</td>
                    </tr>
             <!--   <tr height="30">
                      <td width="80">总垫付金额</td>
                      <td align="right">￥${map.payforTotal }</td>
                    </tr>-->
                <tr height="5"></tr>
              </table>
                </div>
          </div>
            </div>
      </div>
        </div>
    <div style=" width:1170px; margin:0px auto">&nbsp;</div>
  </div>
    </div>
<div class="cle"></div>
<jsp:include page="/include/footer.jsp"></jsp:include>
<script type="text/javascript">
		$(function() {
			$("#search").click(function() {
				if ($("#publishTimeStart").val() > $("#publishTimeEnd").val()) {
					alert("开始日期不能大于结束日期");
					return;
				}
				/* if (isNaN($("#id").val())) {
					alert("编号输入错误");
					return;
				} */
				$("#pageNum").val(1);
				$("#searchForm").submit();
			});
		});
		//隐藏
		$(".num").hide();
		function moneyHide() {
			$(".num").hide();
			$(".total").show();
		}
		function numHide() {
			$(".total").hide();
			$(".num").show();
		}
	</script>
</body>
</html>
