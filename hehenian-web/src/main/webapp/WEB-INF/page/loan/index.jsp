<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<jsp:include page="/include/head.jsp"></jsp:include>
<script type="text/javascript">
    function DropDown(el) {
        this.dd = el;
        this.placeholder = this.dd.children('span');
        this.opts = this.dd.find('ul.dropdown > li');
        this.val = '';
        this.index = -1;
        this.initEvents();
    }
    DropDown.prototype = {
        initEvents: function() {
            var obj = this;
            obj.dd.on('click',
            function(event) {
                $(this).toggleClass('active');
                return false;
            });
            obj.opts.on('click',
            function() {
                var opt = $(this);
                obj.val = opt.text();
                obj.index = opt.index();
                obj.placeholder.text('Gender: ' + obj.val);
            });
        },
        getValue: function() {
            return this.val;
        },
        getIndex: function() {
            return this.index;
        }
    }
  
    $(function() {
        var dd = new DropDown($('#dd'));
  
        $(document).click(function() {
            // all dropdowns
            $('.wrapper-dropdown-1').removeClass('active');
        });
  
    });
</script>
<script type="text/javascript" src="/images/loan/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="/images/loan/scrollbar.js"></script>
<style>
.tabs {
	width: 100%;
	max-width: 680px;
}
label {
	cursor: pointer;

	color: #666666;
	border-radius: 5px 5px 0 0;
	padding: 1.5% 3%;
	float: left;
	margin-right: 2px;
	font-size: 18px;
	font-weight: bold;
}
label:hover {

}
input:checked + label {
	background: #f5f5f5;
	color: #01A7FD;
}
.tabs input:nth-of-type(1):checked ~ .panels .panel:first-child, .tabs input:nth-of-type(2):checked ~ .panels .panel:nth-child(2), .tabs input:nth-of-type(3):checked ~ .panels .panel:nth-child(3), .tabs input:nth-of-type(4):checked ~ .panels .panel:last-child {
	opacity: 1;
	-webkit-transition: .3s;
}
.panels {
	float: left;
	clear: both;
	position: relative;
	width: 100%;
	background: #fff;
	border-radius: 0 10px 10px 10px;
	min-height: 435px;
	line-height: 24px;
}
.panel {
	width: 100%;
	opacity: 0;
	position: absolute;
	background: #f5f5f5;
	border-radius: 0 10px 10px 10px;
	padding: 4%;
	box-sizing: border-box;
}
.panel h2 {
	margin: 0;
	font-size: 14px;
	font-weight: bold;
	line-height: 32px;
	margin-bottom: 10px;
}
.newslist{ margin:0 auto; text-align:left}
.newslist li{height:32px;line-height:32px;border-bottom:dashed 1px #FFF;padding:0 10px;overflow:hidden;}
/* scr_con style */
.scr_con {position:relative;height:756px;width:100%;}
#dv_scroll{position:absolute;height:736px;overflow:hidden;width:100%;}
#dv_scroll .Scroller-Container{width:100%;}
#dv_scroll_bar {position:absolute;right:0;top:10px;width:15px;height:730px;border-left:1px solid #999;}
#dv_scroll_bar .Scrollbar-Track{position:absolute;left:0;top:20px;width:15px;height:710px;}
#dv_scroll_bar .Scrollbar-Handle{position:absolute;left:-4px;top:0;width:7px;height:29px;overflow:hidden;background:#999;cursor:pointer;}
#dv_scroll_text {position:absolute;}
</style>
</head>
<body>
<!-- 引用头部公共部分 -->
<jsp:include page="/include/top.jsp"></jsp:include>
<div style=" background:#f5f5f5; padding-bottom:30px;">
  <div class="center_banner"> 
    <!--flash begin-->
    <div id="flashBg" style="background-color:#00a8ff; ">
      <div style=" width:1170px; margin:0px auto;">
        <div id="flashLine">
          <div id="flash"> <img src="/images/ad/45.jpg" width="1170" height="349" /> </div>
          <div style=" padding-left:704px; z-index:69; height:30px;  position:absolute;  top:0px;  width:466px; ">
            <div style=" background:url(/images/loan/sqbj.png); overflow:hidden;  padding:0px 20px;">
              <div style=" background:#FFF;">
                <div style="border-right:1px solid #f5f5f5; border-left:1px solid #f5f5f5; border-bottom:1px solid #f5f5f5 ">
                  <div style=" font-size:26px; padding-top:20px; padding-left:20px; color:#1fa735;"><font color="#0679b1">30秒</font>申请 全国<font color="#0679b1">20家</font>分行支持</div>
                  <div style=" overflow:hidden;background:#f2ffee; border:1px solid #0e9d4a; margin:15px 30px;-moz-border-radius: 5px;
 -webkit-border-radius: 5px;border-radius:5px; padding:20px;">
                    <div>
                      <div>
                        <input type="text" class="sq-input" value="姓名" style="background:#fff url(/images/loan/id.png) no-repeat;background-position:290px 8px; "/>
                      </div>
                      <div>
                        <input type="text" class="sq-input" value="身份证" style="background:#fff url(/images/loan/sfz.png) no-repeat;background-position:290px 8px; "/>
                      </div>
                      <div>
                        <input type="text" class="sq-input" value="手机" style="background:#fff url(/images/loan/sj.png) no-repeat;background-position:290px 8px; "/>
                      </div>
                      <div  class="sq-input">
                        <select class="select2"  style="background:#fff url(/images/loan/xl.png) no-repeat;background-position:290px 8px; ">
                          <option>请选择所在城市</option>
                          <option>桂林</option>
                          <option>南宁</option>
                          <option>深圳</option>
                          <option>惠州</option>
                          <option>中山</option>
                          <option>荆州</option>
                          <option>武昌</option>
                          <option>佛山</option>
                          <option>柳州</option>
                          <option>江门</option>
                          <option>成都</option>
                          <option>株洲</option>
                          <option>汉口</option>
                          <option>钦州</option>
                          <option>重庆</option>
                          <option>合肥</option>
                        </select>
                      </div>
                      <div  class="sq-input">
                        <select class="select2"  style="background:#fff url(/images/loan/sr.png) no-repeat;background-position:290px 8px; ">
                          <option>请选择您的月收入</option>
                          <option>5000元以下</option>
                          <option>5000-9999元</option>
                          <option>10000-14999元</option>
                          <option>14999-20000元</option>
                          <option>20000元以上</option>
                        </select>
                      </div>
                      <div>
                        <input type="text" class="sq-input" value="请输入意向贷款金额" style="background:#fff url(/images/loan/je.png) no-repeat;background-position:290px 8px; "/>
                      </div>
                    </div>
                    <div>
                      <input name="checkbox" type="checkbox" id="checkbox" checked="checked" />
                      同意<a href="#" style=" color:#0066cc">隐私政策声明</a></div>
                  </div>
                  <div style=" text-align:center; margin-bottom:15px;">
                    <input type="button"  value="立即申请" style=" width:238px; height:40px; border:0px; background:#01A7FD; font-size:16px;color:#FFF; margin-left:0px;"/>
                    <br />
                    <br />
                    请保证您的手机畅通，我们客户经理会第一时间联系您 </div>
                </div>
                <div><img src="/images/loan/yy.jpg" /></div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
  
  <!---->
  <div class="center" style=" background:#FFF; padding:20px; width:1130px;overflow:hidden">
    <div style="  margin-right:20px; float:left; width:665px;  ;border-top:3px solid #009f43">
      <div style=" background:#f5f5f5; margin-bottom:20px;overflow:hidden ">
        <div style=" width:626px; margin:20px auto 0px auto; font-size:18px; font-weight:bold; color:#01A7FD">简单三步，快速申请</div>
        <div style=" width:544px; margin:10px auto 20px auto;"><img src="/images/loan/lc.png" width="544" height="122"  alt=""/></div>
      </div>
      <div style=" overflow:hidden; margin-bottom:20px;">
        <article class="tabs">
          <input checked id="one" name="tabs" type="radio"  style=" display:none">
          <label for="one">社区业主族专享</label>
          <input id="two" name="tabs" type="radio" value="Two" style=" display:none">
          <label for="two">即购房族专享</label>
          <input id="three" name="tabs" type="radio" style=" display:none">
          <label for="three"  style=" display:none">Tab Three</label>
          <input id="four" name="tabs" type="radio" style=" display:none">
          <label for="four"  style=" display:none">Tab Four</label>
          <div class="panels">
            <div class="panel">
              <h2>申请条件：</h2>
              <ul style=" padding-left:20px;">
                <li>房产：须拥有当地个人物业（含其他物业）</li>
                <li>国籍：须有中华人民共和国国籍（国内居民，不包含港澳台人士）</li>
                <li>年龄：须18～60周岁</li>
                <li>收入：须不低于 2,000元/月</li>
                <li>现住址居住时间：须不少于6个月</li>
                <li>现工作时间：须在不少于6个月</li>
                <li>城市：须在经营城市区域居住或工作</li>
              </ul>
              <h2 style=" padding-top:10px;">申请材料：</h2>
              <ul style=" padding-left:20px;">
                <li>二代身份证</li>
                <li>住址证明</li>
                <li>工作证明</li>
                <li>收入证明</li>
                <li>人行个人信用报告</li>
              </ul>
            </div>
            <div class="panel"> 2 </div>
            <div class="panel"> 3 </div>
            <div class="panel"> 4 </div>
          </div>
        </article>
      </div>
      <div style="  background:#f5f5f5;overflow:hidden ;border-top:3px solid #009f43">
        <div style=" width:626px; margin:20px auto 0px auto; font-size:18px; font-weight:bold; color:#01A7FD">还款计算器</div>
        <div style=" overflow:hidden; text-align:right;">
          <div style=" float:left; width:220px; padding-top:20px; line-height:42px;">
            <div>借款金额：</div>
            <div>分期期数：</div>
            <div>月手续费率：</div>
            <div>其他费用：</div>
          </div>
          <div style=" float:left; padding-top:20px; line-height:42px;">
            <div style=" height:38px; padding-top:4px;">
              <input type="text" class="login_tex1" name="paramMap.userName" id="userName"  style=" background:#fff"/>
            </div>
            <div style=" height:38px; padding-top:4px;">
              <input type="text" class="login_tex1" name="paramMap.userName" id="userName"  style=" background:#fff"/>
            </div>
            <div style=" height:38px; padding-top:4px;">
              <input type="text" class="login_tex1" name="paramMap.userName" id="userName"  style=" background:#fff"/>
            </div>
            <div style=" height:38px; padding-top:4px;">
              <input type="text" class="login_tex1" name="paramMap.userName" id="userName"  style=" background:#fff"/>
            </div>
          </div>
        </div>
        <div style=" text-align:center; padding:20px 0px;">
          <input type="button"  value="计算" style=" width:238px; height:40px; border:0px; background:#01A7FD; font-size:16px;color:#FFF; margin-left:0px;"/>
          <input type="button" value="重置" style=" width:238px; height:40px; border:0px; background:#01A7FD; font-size:16px;color:#FFF; margin-left:0px;"/>
        </div>
        <div style=" text-align:center; padding:20px 0px;border-top:1px solid #009f43"> 答案 </div>
        <div></div>
      </div>
    </div>
    <div style=" background:#f5f5f5; float:left; width:443px; margin-top:190px; border:1px solid #c2c2c4; border-bottom:3px solid #009f43"><img src="/images/loan/map.jpg"/>
      <div style=" padding:0px 20px;">
      
      
      		<div class="scr_con">
	<div id="dv_scroll">
		<div id="dv_scroll_text" class="Scroller-Container">
			<div class="newslist">
				 
        <div style=" font-size:12px; margin-top:20px;">
          <table width="100%" border="0" cellpadding="0" cellspacing="0">
            <tr>
              <td width="68" rowspan="4"><img src="/images/loan/1.jpg"/></td>
              <td><strong>深圳民治分行</strong></td>
            </tr>
            <tr>
              <td>深圳市龙华新区民治大道与民康路交汇处的东浩大厦3楼3C</td>
            </tr>
            <tr>
              <td>电话号码：0755-66632964</td>
            </tr>
            <tr>
              <td>传真号码：0755-23716861 </td>
            </tr>
          </table>
        </div>
        <div style=" font-size:12px; margin-top:20px;">
          <table width="100%" border="0" cellpadding="0" cellspacing="0">
            <tr>
              <td width="68" rowspan="4"><img src="/images/loan/2.jpg"/></td>
              <td><strong>桂林分行</strong></td>
            </tr>
            <tr>
              <td>桂林市七星区高新区4号小区桂林众鼎综合办公孵化楼5层</td>
            </tr>
            <tr>
              <td>电话号码：0773-3117817</td>
            </tr>
            <tr>
              <td>传真号码：0773-3117887</td>
            </tr>
          </table>
        </div>
        <div style=" font-size:12px; margin-top:20px;">
          <table width="100%" border="0" cellpadding="0" cellspacing="0">
            <tr>
              <td width="68" rowspan="4"><img src="/images/loan/3.jpg"/></td>
              <td><strong>惠州分行</strong></td>
            </tr>
            <tr>
              <td>惠州市江北云山西路2号凯宾斯基酒店B座2506号</td>
            </tr>
            <tr>
              <td>电话号码：0752-7776977</td>
            </tr>
            <tr>
              <td>传真号码：0752-7776960 </td>
            </tr>
          </table>
        </div>
        <div style=" font-size:12px; margin-top:20px;">
          <table width="100%" border="0" cellpadding="0" cellspacing="0">
            <tr>
              <td width="68" rowspan="4"><img src="/images/loan/4.jpg"/></td>
              <td><strong>中山分行</strong></td>
            </tr>
            <tr>
              <td>中山市中山三路16号之三国际金融中心15层1509单元 </td>
            </tr>
            <tr>
              <td>电话号码：0760-88210151</td>
            </tr>
            <tr>
              <td>传真号码：0760-88210169 </td>
            </tr>
          </table>
        </div>
        <div style=" font-size:12px; margin-top:20px;">
          <table width="100%" border="0" cellpadding="0" cellspacing="0">
            <tr>
              <td width="68" rowspan="4"><img src="/images/loan/5.jpg"/></td>
              <td><strong>南宁分行</strong></td>
            </tr>
            <tr>
              <td>南宁市青秀区金湖路金源CBD现代城1353号 </td>
            </tr>
            <tr>
              <td>电话号码：0771-5786316</td>
            </tr>
            <tr>
              <td>传真号码：0771-5786315 </td>
            </tr>
          </table>
        </div>
        <div style=" font-size:12px; margin-top:20px;">
          <table width="100%" border="0" cellpadding="0" cellspacing="0">
            <tr>
              <td width="68" rowspan="4"><img src="/images/loan/6.jpg"/></td>
              <td><strong>南宁金湖分行</strong></td>
            </tr>
            <tr>
              <td>南宁市青秀区民族大道115-1号现代国际8层20-22号</td>
            </tr>
            <tr>
              <td>电话号码：0771-5786351</td>
            </tr>
            <tr>
              <td>传真号码：0771-5786332 </td>
            </tr>
          </table>
        </div>
        <div style=" font-size:12px; margin-top:20px;">
          <table width="100%" border="0" cellpadding="0" cellspacing="0">
            <tr>
              <td width="68" rowspan="4"><img src="/images/loan/7.jpg"/></td>
              <td><strong>武昌分行</strong></td>
            </tr>
            <tr>
              <td>武汉市武昌区武珞路456号新时代商务中心主楼6楼08室 </td>
            </tr>
            <tr>
              <td>电话号码：027-51018699</td>
            </tr>
            <tr>
              <td>传真号码：027-51018693 </td>
            </tr>
          </table>
        </div>
        <div style=" font-size:12px; margin-top:20px;">
          <table width="100%" border="0" cellpadding="0" cellspacing="0">
            <tr>
              <td width="68" rowspan="4"><img src="/images/loan/8.jpg"/></td>
              <td><strong>佛山分行</strong></td>
            </tr>
            <tr>
              <td>佛山市禅城区汾江中路121号东建大厦17层JK</td>
            </tr>
            <tr>
              <td>电话号码：0757-66633667</td>
            </tr>
            <tr>
              <td>传真号码：0757-66633669 </td>
            </tr>
          </table>
        </div>
        <div style=" font-size:12px; margin-top:20px;">
          <table width="100%" border="0" cellpadding="0" cellspacing="0">
            <tr>
              <td width="68" rowspan="4"><img src="/images/loan/9.jpg"/></td>
              <td><strong>柳州分行</strong></td>
            </tr>
            <tr>
              <td>柳州市桂中大道南端2号阳光壹佰写字楼1号楼11-5</td>
            </tr>
            <tr>
              <td>电话号码：0772-2052482</td>
            </tr>
            <tr>
              <td>传真号码：0772-2052458 </td>
            </tr>
          </table>
        </div>
        <div style=" font-size:12px; margin-top:20px;">
          <table width="100%" border="0" cellpadding="0" cellspacing="0">
            <tr>
              <td width="68" rowspan="4"><img src="/images/loan/10.jpg"/></td>
              <td><strong>江门分行</strong></td>
            </tr>
            <tr>
              <td>江门市蓬江区迎宾大道中131号中信银行大厦809室 </td>
            </tr>
            <tr>
              <td>电话号码：0750-7173701</td>
            </tr>
            <tr>
              <td>传真号码：0750-7173703 </td>
            </tr>
          </table>
        </div>
        <div style=" font-size:12px; margin-top:20px;">
          <table width="100%" border="0" cellpadding="0" cellspacing="0">
            <tr>
              <td width="68" rowspan="4"><img src="/images/loan/11.jpg"/></td>
              <td><strong>株洲分行</strong></td>
            </tr>
            <tr>
              <td>湖南省株洲市天元区黄河路口银天国际1514 </td>
            </tr>
            <tr>
              <td>电话号码：0731-22035388</td>
            </tr>
            <tr>
              <td>传真号码：0731-22035386 </td>
            </tr>
          </table>
        </div>
        <div style=" font-size:12px; margin-top:20px;">
          <table width="100%" border="0" cellpadding="0" cellspacing="0">
            <tr>
              <td width="68" rowspan="4"><img src="/images/loan/12.jpg"/></td>
              <td><strong>花都分行</strong></td>
            </tr>
            <tr>
              <td>广州市花都区公益路23号钻石商务大厦六楼605室 </td>
            </tr>
            <tr>
              <td>电话号码：020-66314729</td>
            </tr>
            <tr>
              <td>传真号码：020-66356673 </td>
            </tr>
          </table>
        </div>
        <div style=" font-size:12px; margin-top:20px;">
          <table width="100%" border="0" cellpadding="0" cellspacing="0">
            <tr>
              <td width="68" rowspan="4"><img src="/images/loan/13.jpg"/></td>
              <td><strong>成都分行</strong></td>
            </tr>
            <tr>
              <td>四川省成都市锦江区东御街57号人保大厦16楼C区</td>
            </tr>
            <tr>
              <td>电话号码：028-69569920</td>
            </tr>
            <tr>
              <td>传真号码：028-69569914 </td>
            </tr>
          </table>
        </div>
        <div style=" font-size:12px; margin-top:20px;">
          <table width="100%" border="0" cellpadding="0" cellspacing="0">
            <tr>
              <td width="68" rowspan="4"><img src="/images/loan/1.jpg"/></td>
              <td><strong>成都高新区分行</strong></td>
            </tr>
            <tr>
              <td>不详</td>
            </tr>
            <tr>
              <td>电话号码：028-69760816 </td>
            </tr>
            <tr>
              <td>传真号码：028-69760810 </td>
            </tr>
          </table>
        </div>
        <div style=" font-size:12px; margin-top:20px;">
          <table width="100%" border="0" cellpadding="0" cellspacing="0">
            <tr>
              <td width="68" rowspan="4"><img src="/images/loan/15.jpg"/></td>
              <td><strong>汉口分行</strong></td>
            </tr>
            <tr>
              <td>武汉市解放大道世贸大厦写字楼4802室 </td>
            </tr>
            <tr>
              <td>电话号码：027-59712318</td>
            </tr>
            <tr>
              <td>传真号码：027-59712313 </td>
            </tr>
          </table>
        </div>
        <div style=" font-size:12px; margin-top:20px;">
          <table width="100%" border="0" cellpadding="0" cellspacing="0">
            <tr>
              <td width="68" rowspan="4"><img src="/images/loan/16.jpg"/></td>
              <td><strong>钦州分行</strong></td>
            </tr>
            <tr>
              <td>广西钦州市钦南区人民路13号恒基广场1806号 </td>
            </tr>
            <tr>
              <td>电话号码：0777-2562588</td>
            </tr>
            <tr>
              <td>传真号码：0777-2562590 </td>
            </tr>
          </table>
        </div>
        <div style=" font-size:12px; margin-top:20px;">
          <table width="100%" border="0" cellpadding="0" cellspacing="0">
            <tr>
              <td width="68" rowspan="4"><img src="/images/loan/17.jpg"/></td>
              <td><strong>重庆分行</strong></td>
            </tr>
            <tr>
              <td>重庆市渝中区邹容路131号世贸大厦17-11</td>
            </tr>
            <tr>
              <td>电话号码：023-61848368</td>
            </tr>
            <tr>
              <td>&nbsp;</td>
            </tr>
          </table>
        </div>
        <div style=" font-size:12px; margin-top:20px;">
          <table width="100%" border="0" cellpadding="0" cellspacing="0">
            <tr>
              <td width="68" rowspan="4"><img src="/images/loan/18.jpg"/></td>
              <td><strong>合肥分行</strong></td>
            </tr>
            <tr>
              <td>安徽省合肥市包河区芜湖路万达广场7号楼801室 </td>
            </tr>
            <tr>
              <td>电话号码：0551-68113555</td>
            </tr>
            <tr>
              <td>传真号码：0551-68127236 </td>
            </tr>
          </table>
        </div>
			</div><!--about end-->
		</div>
	</div><!--dv_scroll end-->
	<div id="dv_scroll_bar">
		<div id="dv_scroll_track" class="Scrollbar-Track">
			<div class="Scrollbar-Handle"></div>
		</div>
	</div><!--dv_scroll_bar end-->
</div><!--scr_con end-->
      
      
      
      
      
     
      </div>
    </div>
  </div>
</div>
<jsp:include page="/include/footer.jsp"></jsp:include>
</body>
</html>
