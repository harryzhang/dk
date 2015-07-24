/*banner*/
$(function() {
    var timer = 5000;
    var showtime = 800;
    var showbox = $("#banner_show");
    var inbox = $(".bannger_inbox");
    var movelist = $("#yq_banner_list");
    var s;
    var b = 0;
    var size = inbox.size();
    var play = 1;
    var colorArray = ['#06c1ae','#ff571e','#4ed5eb'];
    function move() {
        b++;
        if (b > size - 1) {
            b = 0;
        }
        console.log(b);
        inbox.each(function(e) {
            inbox.eq(e).hide(0);
            $("#banner_magbox" + e).hide();
            movelist.find("a").eq(e).removeClass("hover");
            console.log(b);
            if (e == b) {
                inbox.eq(b).fadeIn(showtime);
                $("#banner_magbox" + b).show();
                movelist.find("a").eq(b).addClass("hover");
            }
        });
        showbox.css('background', colorArray[b]);
    }
    s = setInterval(move, timer);
    function stopp(obj) {
        $(obj).hover(function() {
            if (play) {
                clearInterval(s);
                play = 0;
            }
        }, function() {
            if (!play) {
                s = setInterval(move, timer);
                play = 1;
            }
        });
    }
    stopp(".banner_show");
    $(".banner_btn_right").click(function() {
        move();
    });
    $(".banner_btn_left").click(function() {
        b--;
        if (b < 0) {
            b = size - 1
        }
        inbox.each(function(e) {
            inbox.eq(e).hide(0);
            movelist.find("a").eq(e).removeClass("hover");
            if (e == b) {
                inbox.eq(b).fadeIn(showtime);
                movelist.find("a").eq(b).addClass("hover");
            }
        });
    });
    movelist.find("a").click(function() {
        // var rel = $(this).attr("rel");
        // inbox.each(function(e) {
        //     inbox.eq(e).hide(0);
        //     movelist.find("a").eq(e).removeClass("hover");
        //     $("#banner_magbox" + e).hide(0);
        //         showbox.css('background', colorArray[e]);
        //     if (e == rel) {
        //         inbox.eq(rel).fadeIn(showtime);
        //         movelist.find("a").eq(rel).addClass("hover");
        //         $("#banner_magbox" + rel).show(0);
        //     }
        // });
        var index =$(this).index();
        for(var i=0;i<3;i++){
            inbox.eq(i).hide();
        }
        inbox.eq(index).show()
        $(this).addClass("hover").siblings().removeClass('hover');
        showbox.css('background', colorArray[index]);
    });
    $(".bannger_inbox").each(function(e) {
        var inboxsize = $(".bannger_inbox").size();
        inboxwimg = $(this).find("img").width();
        $(".bannger_inbox").eq(e).css({
            "margin-left" : (-1) * inboxwimg / 2 + "px",
            "z-index" : inboxsize - e
        });
    });
    /*
     * $(".banner").hover( function(){ $(".banner_pre_next").fadeIn();
     * },function(){ $(".banner_pre_next").fadeOut(); })
     */
});

/* menu */
$(function() {
    var tt1;
    $(".content_bottom_tig").bind({
        mouseenter : function() {
            $(this).find(".h_bg").addClass("hover");
            that = $(this)
            tt1 = setTimeout(function() {
                that.animate({
                    height : 130
                });
            }, 200);
        },
        click : function() {
        },
        mouseleave : function() {
            $(this).find(".h_bg").removeClass("hover");
            clearTimeout(tt1);
            that.animate({
                height : 25
            });
        }
    });
})
// $(function() {

//  $('#carousel ul').carouFredSel({
//      prev : '#prev',
//      next : '#next',
//      scroll : 1000
//  });

// });

function isIdCardNo(code){         //身份证
    var trimCode = trim(code);
    var errorText = checkIDcard(trimCode);
    if(typeof errorText == 'object' && errorText.check == true){
        return true;
    }else{
        return false;
    }
}
function checkIDcard(idcard) {
    var Errors = ["", "请输入正确的身份证号码", "请输入正确的身份证号码", "请输入正确的身份证号码", "请输入正确的身份证号码"];
    var area = {
        11 : "北京",
        12 : "天津",
        13 : "河北",
        14 : "山西",
        15 : "内蒙古",
        21 : "辽宁",
        22 : "吉林",
        23 : "黑龙江",
        31 : "上海",
        32 : "江苏",
        33 : "浙江",
        34 : "安徽",
        35 : "福建",
        36 : "江西",
        37 : "山东",
        41 : "河南",
        42 : "湖北",
        43 : "湖南",
        44 : "广东",
        45 : "广西",
        46 : "海南",
        50 : "重庆",
        51 : "四川",
        52 : "贵州",
        53 : "云南",
        54 : "西藏",
        61 : "陕西",
        62 : "甘肃",
        63 : "青海",
        64 : "宁夏",
        65 : "新疆",
        71 : "台湾",
        81 : "香港",
        82 : "澳门",
        91 : "国外"
    };

    var idcard, Y, JYM,data={};
    var S, M;
    var idcard_array = new Array();
    idcard_array = idcard.split("");
    //地区检验
    if (area[parseInt(idcard.substr(0, 2))] == null) {
        return Errors[4];
    }
    data["area"] = area[idcard.slice(0,2)];
    /*
     15位的旧身份证，最后一个数是单数的为男，双数的为女；。
     18位的看倒数第二位，单数的为男，双数的为女
     */
    //获取性别;
    data["sex"] = idcard .slice(14,17)%2?  "男" : "女";

    //身份号码位数及格式检验
    switch (idcard.length) {
        case 15:

            if ((parseInt(idcard.substr(6, 2)) + 1900) % 4 == 0 || ((parseInt(idcard.substr(6, 2)) + 1900) % 100 == 0 && (parseInt(idcard.substr(6, 2)) + 1900) % 4 == 0)) {
                ereg = /^[1-9][0-9]{5}[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|[1-2][0-9]))[0-9]{3}$/; //测试出生日期的合法性
            } else {
                ereg = /^[1-9][0-9]{5}[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|1[0-9]|2[0-8]))[0-9]{3}$/; //测试出生日期的合法性
            }
            if (ereg.test(idcard)) {
                data["check"] = true;
                data["birthday"] = (new Date(idcard.slice(6,10) , idcard.slice(10,12)-1 , idcard.slice(12,14))).toLocaleDateString();
                return data;
            } else {
                return Errors[2];
            }
            break;
        case 18:

            //18位身份号码检测
            //出生日期的合法性检查
            //闰年月日:((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|[1-2][0-9]))
            //平年月日:((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|1[0-9]|2[0-8]))
            if (parseInt(idcard.substr(6, 4)) % 4 == 0 || (parseInt(idcard.substr(6, 4)) % 100 == 0 && parseInt(idcard.substr(6, 4)) % 4 == 0)) {
                ereg = /^[1-9][0-9]{5}(19|20)[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|[1-2][0-9]))[0-9]{3}[0-9Xx]$/; //闰年出生日期的合法性正则表达式
            } else {
                ereg = /^[1-9][0-9]{5}(19|20)[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|1[0-9]|2[0-8]))[0-9]{3}[0-9Xx]$/; //平年出生日期的合法性正则表达式
            }
            if (ereg.test(idcard)) { //测试出生日期的合法性
                //计算校验位
                S = (parseInt(idcard_array[0]) + parseInt(idcard_array[10])) * 7 + (parseInt(idcard_array[1]) + parseInt(idcard_array[11])) * 9 + (parseInt(idcard_array[2]) + parseInt(idcard_array[12])) * 10 + (parseInt(idcard_array[3]) + parseInt(idcard_array[13])) * 5 + (parseInt(idcard_array[4]) + parseInt(idcard_array[14])) * 8 + (parseInt(idcard_array[5]) + parseInt(idcard_array[15])) * 4 + (parseInt(idcard_array[6]) + parseInt(idcard_array[16])) * 2 + parseInt(idcard_array[7]) * 1 + parseInt(idcard_array[8]) * 6 + parseInt(idcard_array[9]) * 3;
                Y = S % 11;
                M = "F";
                JYM = "10X98765432";
                M = JYM.substr(Y, 1); //判断校验位
                if (M == idcard_array[17]) {
                    data["check"] = true;
                    data["birthday"] = (new Date(idcard.slice(6,10) , idcard.slice(10,12)-1 , idcard.slice(12,14))).toLocaleDateString();
                    return data; //检测ID的校验位
                } else {
                    return Errors[3];
                }
            } else {
                return Errors[2];
            }
            break;
        default:
            return Errors[1];
            break;
    }
}

//去掉字符串头尾空格
function trim(str) {
    return str.replace(/(^\s*)|(\s*$)/g, "");
}