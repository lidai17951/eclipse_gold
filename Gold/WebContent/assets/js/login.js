$(document).ready(function(){
var unameOK = false; //这2个为true才能submit
var upwdOK = false;
$("#uname").blur(function(){
	var regname =/^\w{5,20}$/; //大小写字母，下划线，数字，5到20位
	var $unameVal =$(this).val();
	if (!(regname.test($unameVal)) )  {  
		if ($unameVal=""||$unameVal.length==0) {//什么都还没输的情况，失去焦点不会出现提示
			unameOK = false;
			$("#nameHide").css("display","none");
		}
	  else{ //账号错误
	  	$(this).css("border-color","#FF0000");
	  	$("#nameHide").css("display","inline");
	  }
	  unameOK = false;
	 }
	 else{//账号格式正确，或第一次输错，第二次正确，取消提示且输入框变蓝色
	 	$(this).css("border-color","#2ecc71"); //恢复原来的颜色
	 	$("#nameHide").css("display","none");
	 	unameOK = true;
	 }
});

$("#upwd").blur(function(){
	var regpwd =/^\w{5,20}$/; //大小写字母，下划线，数字，5到20位
	var $upwdVal =$(this).val();
	if (!(regpwd.test($upwdVal))) {
		if ($upwdVal="" || $upwdVal.length==0) {//什么都还没输的情况，失去焦点不会出现提示
			upwdOK = false;
			$("#pwdHide").css("display","none");
		}
	  else{//密码错误
	  	$(this).css("border-color","#FF0000");
	  	$("#pwdHide").css("display","inline");
	  }
	  upwdOK = false;
	 }
	 else{//账号格式正确，或第一次输错，第二次正确，取消提示且输入框变蓝色
	 	$(this).css("border-color","#2ecc71");
	 	$("#pwdHide").css("display","none");
	 	 upwdOK = true;
	 }
});
	$("[type='submit']").keydown(function(event){ 
		if (event.keyCode ==13) { //回车键
			if ($("#uname").focus()) {
				$("#uname").blur();
				$(this).focus();
			}
			if ($("#upwd").focus()) {
				$("#upwd").blur();
				$(this).focus();
			}
		}
	});




  $("#login").submit(function () {
  		$("#uname").blur();
  		$("#upwd").blur();   //这两个键不blur的话unameOK,upwdOK拿不到值
            if (unameOK && upwdOK){
                return true;  //允许submit跳转
            }
            else {
                alert("请正确输入所有信息");
                return false;  //阻止submit跳转
            }
        });


 });
