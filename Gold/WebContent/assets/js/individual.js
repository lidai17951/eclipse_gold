(function () {
    var liArr = document.querySelectorAll(".content-left ul li");

    liArr[0].onclick =function () { //首页li
        window.location.href='../index.html';
    };
    liArr[2].onclick =function () { //我的头像li
        window.location.href='headphoto.html';
    };
})();


//填充文档信息页面
(function(){
	var userIdSpan =document.getElementById("userId");
	var unameInput =document.getElementById("uname");
	$.getJSON(
			"ClientStatusServlet",
			{},
			function(result){
				result =eval(result);
				userIdSpan.innerHTML=result["userId"];
				unameInput.value=result["uname"];
			}
	);
	
})();