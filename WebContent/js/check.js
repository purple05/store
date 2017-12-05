$(function() {
	$("#username").blur(function(){
		$.get(
			"checkreg.user", 
			{username : $("#username").val()}, 
			function(data) {
				if(1 == data){
					$("#username").parent().next().remove();
					$("#username").parent().after("<span style='color:green'>用户名可以使用</span>");
					//alert("可以使用");
				}else{
					$("#username").parent().next().remove();
					$("#username").parent().after("<span style='color:red'>用户名已经被注册了</span>");
				}
		});
		
	});
});