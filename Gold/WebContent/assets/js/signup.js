(function () {
    const phoneReg = /^1\d{10}$/;
    const mailReg = /^\w+@\w{2,}\.[a-zA-Z]{2,4}(\.[a-zA-Z]{2,4})?$/;
    const pwdReg =/^\w{1,20}$/;
    const loginBtn =document.getElementById("loginbtn");//登录按钮

    loginBtn.onclick =function () {//登录按钮
        var loginUserid  =document.getElementById("login-userid");
        var loginUpwd = document.getElementById("login-upwd");
        if (!phoneReg.test(loginUserid.value) && !mailReg.test(loginUserid.value)) {
            alert("只能使用手机或邮箱")
        } else if (!pwdReg.test(loginUpwd.value)) {
            alert("1-20位密码")
        } else {
            $.post(
                "LoginServlet",
                {"userid":loginUserid.value,"upwd":loginUpwd.value},
                function(result, testStatus) {
                    result=eval(result);
                    if (typeof (result["userId"]) == "undefined") {
                        alert("账号或密码错误");
                    } else if ((result["userId"]) != "undefined") {

                        window.location.href = "index.html";
                    } else {
                        alert("账号或密码错误");
                    }

                }, "json" // 返回值类型
            );
        }
    };

    const signupBtn = document.getElementById("signup-btn");
    const unameReg =/^.{1,10}$/; //只能1-10个字符
    signupBtn.onclick = function () {//注册按钮
        var signupUserId = document.getElementById("signup-userId");
        var signupUname =document.getElementById("signup-uname");
        var signupUpwd = document.getElementById("signup-upwd");
        if (!phoneReg.test(signupUserId.value) && !mailReg.test(signupUserId.value)) {
            alert("请使用手机或邮箱")
        } else if (!unameReg.test(signupUname.value)) {
           alert("1-10个字符");
        }else if(!pwdReg.test(signupUpwd.value)){
            alert("1-20位密码");
        }else {
            $.post(
                "SignUpServlet",
                {"signupUserId":signupUserId.value,"signupUname":signupUname.value,
                "signupUpwd":signupUpwd.value},
                function(result, testStatus) {
                	alert(result);
                	if(result=="注册成功"){
                        window.location.href ="index.html";
                	}

                }, "text" // 返回值类型
            );
        }
    };

})();
