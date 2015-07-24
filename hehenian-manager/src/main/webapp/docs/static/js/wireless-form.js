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
			alert("������ظ����ݣ�");
			return false;
		} else if (content.length > 100) {
			alert("���ݲ��ܳ���100��");
			$("#reply-content-" + id).focus();
			return false;
		}
		$.post("/wireless/reply.do", {
			userId : userId,
			otherId : otherId,
			messageId : messageId,
			content : content,
			op : encodeURIComponent("�����ظ�")
		}, function(data) {
			// $("#reply-content-" + id).val(data.msg);
				alert("��Ϣ���ͳɹ���");
			});
	});
	// �ҿ���Ta
	$('.visitor').click(function() {
		var op = $(this).val();
		var userId = $(this).attr("userId");
		var otherId = $(this).attr("otherId");
		$(this).val('�������Ѿ��ݷù�Ta').attr('disabled', 'true');
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
	// �ظ���Ϣ
	$('.reply-message').click(function() {
		var op = $(this).val();
		var userId = $(this).attr("userId");
		var otherId = $(this).attr("otherId");
		var content = $("#content").val();
		if (content == '' || content.length == 0) {
			alert("������ظ����ݣ�");
			$("#content").focus();
			return false;
		} else if (content.length > 100) {
			alert("���ݲ��ܳ���100��");
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
				alert("��Ϣ���ͳɹ���");
				location.reload();
				return false;
			}
		});
		return false;
	});

	// ���к�
	$('.reply-hi').click(function() {
		var op = $(this).val();
		var userId = $(this).attr("userId");
		var otherId = $(this).attr("otherId");
		$(this).val('�����Ѵ��к�').attr('disabled', 'true');
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

	// �Ӻ���
	$('.reply-requests').click(function() {
		var op = $(this).val();
		var userId = $(this).attr("userId");
		var otherId = $(this).attr("otherId");
		$(this).val('�����ѷ���').attr('disabled', 'true');
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
	// ��Ϊ�Ѷ�
	$('.mark-as-read').click(function() {
		// removeAttr("disabled");
			var op = $(this).val();
			var userId = $(this).attr("userId");
			var messageId = $(this).attr("messageId");
			$(this).val('�Ѷ�').attr('disabled', 'true');
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