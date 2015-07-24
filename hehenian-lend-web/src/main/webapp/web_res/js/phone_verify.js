/**
 * Created by liuwtmf on 2014/12/31.
 */
function getTelephoneCode_(mobile) {
            $("#ida").attr("disable", "yes");
            $("#ida").attr("class","sendBtn1");
            $("#ida").text("60秒后可重新发送");

            $.post("/common/send-phone-virifycode.do", {mobile:mobile}, function(data) {
                var ret= data.ret;
                /*if (ret == "0") {
                    $("#s_telephone").attr("class", "formtips");
                    $("#s_telephone").html("");
                    telephoneTimer_();
                }else {
                    $("#s_telephone").attr("class", "formtips onError");
                    $("#s_telephone").html("验证码获取失败");
                    $("#ida").attr("disable", "no").attr("class","sendBtn").text("获取验证码");
                }*/
            });
            $("#s_telephone").attr("class", "formtips");
            $("#s_telephone").html("");
            telephoneTimer_();

}
//倒计时

function telephoneTimer_() {
    var count = 60;

    var timer=setInterval(function(){
        if(count <= 0){
            clearInterval(timer);
            $("#ida").attr("disable", "no").attr("class","sendBtn").text("获取验证码");
        }else{
            count--;
            $("#ida").text(count+"秒后可重新发送");
        }
    },1000);

}

function verifyPhone_(telephone){
    if (telephone == "") {
        return "请输入手机号";
    } else if (!/^1[3|4|5|8|7][0-9]\d{4,8}$/.test(telephone)) {
        return "输入手机号的格式有误";
    }else if(telephone.length!=11){
        return "输入手机号的格式有误";
    }else{
        return "";
    }
}