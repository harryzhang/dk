function get_userinfo(userId) {
	window.open("/account/user-info.do?userId=" + userId,"newwindow","height=780,width=800,toolbar=no,menubar=no,scrollbars=yes, resizable=yes, location=no, status=no");
}
$(function() {
	$(".reply-link").click(function() {
		var id = $(this).attr("messageId");
		$("#reply-box-" + id).show();
		$("#send-box-" + id).hide();
	});
	$(".cancel-reply").click(function() {
		var id = $(this).attr("messageId");
		$("#reply-box-" + id).hide();
		$("#send-box-" + id).show();
	});

	$(".reply-submit").click(function() {
		var id = $(this).attr("messageId");
		var content = $("#reply-content-" + id).val();
		var userId = $(this).attr("userId");
		var otherId = $(this).attr("otherId");
		var messageId = $(this).attr("messageId");
		if (!content) {
			$("#reply-content-" + id).focus();
			alert("请输入回复内容！");
			return false;
		} else if (content.length > 100) {
			alert("内容不能超过100字");
			$("#reply-content-" + id).focus();
			return false;
		}
		$.post("/wireless/reply.do", {
			userId : userId,
			otherId : otherId,
			messageId : messageId,
			content : content,
			op : encodeURIComponent("立即回复")
		}, function(data) {
			// $("#reply-content-" + id).val(data.msg);
				alert("消息发送成功！");
			});
	});
	// 我看过Ta
	$('.visitor').click(function() {
		var op = $(this).val();
		var userId = $(this).attr("userId");
		var otherId = $(this).attr("otherId");
		$(this).val('您今天已经拜访过Ta').attr('disabled', 'true');
		$.ajax( {
			url : '/wireless/reply.do',
			data : {
				userId : userId,
				otherId : otherId,
				op : encodeURIComponent(op)
			},
			type : 'post',
			success : function(result) {
				return false;
			}
		});
		return false;
	});
	// 回复消息
	$('.reply-message').click(function() {
		var op = $(this).val();
		var userId = $(this).attr("userId");
		var otherId = $(this).attr("otherId");
		var content = $("#content").val();
		if (content == '' || content.length == 0) {
			alert("请输入回复内容！");
			$("#content").focus();
			return false;
		} else if (content.length > 100) {
			alert("内容不能超过100字");
			$("#content").focus();
			return false;
		}
		$.ajax( {
			url : '/wireless/reply.do',
			data : {
				userId : userId,
				otherId : otherId,
				content : encodeURIComponent(content),
				op : encodeURIComponent(op)
			},
			type : 'post',
			success : function(result) {
				alert("消息发送成功！");
				location.reload();
				return false;
			}
		});
		return false;
	});

	// 打招呼
	$('.reply-hi').click(function() {
		var op = $(this).val();
		var userId = $(this).attr("userId");
		var otherId = $(this).attr("otherId");
		$(this).val('今天已打招呼').attr('disabled', 'true');
		$.ajax( {
			url : '/wireless/reply.do',
			data : {
				userId : userId,
				otherId : otherId,
				op : encodeURIComponent(op)
			},
			type : 'post',
			success : function(result) {
				alert(result.msg);
				return false;
			}
		});
		return false;
	});

	// 加好友
	$('.reply-requests').click(function() {
		var op = $(this).val();
		var userId = $(this).attr("userId");
		var otherId = $(this).attr("otherId");
		$(this).val('请求已发送').attr('disabled', 'true');
		$.ajax( {
			url : '/wireless/reply.do',
			data : {
				userId : userId,
				otherId : otherId,
				op : encodeURIComponent(op)
			},
			type : 'post',
			success : function(result) {
				alert(result.msg);
			}
		});
		return false;
	});
	// 标为已读
	$('.mark-as-read').click(function() {
		// removeAttr("disabled");
			var op = $(this).val();
			var userId = $(this).attr("userId");
			var messageId = $(this).attr("messageId");
			$(this).val('已读').attr('disabled', 'true');
			$.ajax( {
				url : '/wireless/reply.do',
				data : {
					userId : userId,
					messageId : messageId,
					op : encodeURIComponent(op)
				},
				type : 'post',
				success : function(result) {
				}
			});
			return false;
		});
});