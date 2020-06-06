//<div id="au9999" class="price_bar">father节点的ID和要设置的黄金种类(都是字符串)
//大小写敏感Au9999  Au9995 PT9995 Au100g AuT+D(即metal表的variety)

/*------------初始化页面------------*/
function initIndex() {
    $.getJSON("GetLastServlet",
        {},
        function(result) {
            result = eval(result);
            $.each(result,function(index,element){
                var goldPrice = result[index];
                var goldObj = {
                    uptime : goldPrice["goldTime"].uptime,
                    last_price : goldPrice["last_price"],
                    change_price : goldPrice["change_price"],// 涨跌额
                    change_margin : goldPrice["change_margin"],// 涨跌幅(百分比)
                    open_price : goldPrice["open_price"],// 今开
                    yesy_price : goldPrice["yesy_price"],// 昨收
                    high_price : goldPrice["high_price"],// 最高
                    low_price : goldPrice["low_price"] ,// 最低
                    variety : goldPrice["variety"]
                };
                var eleId ='';
                switch(goldObj.variety){
                    case 'Au9999':
                        eleId='au9999';
                        break;
                    case 'Au9995':
                        eleId='au9995';
                        break;
                    case 'Au100g':
                        eleId='au100g';
                        break;
                    case 'AuT+D':
                        eleId='autd';
                        break;
                    case 'PT9995':
                        eleId='pt9995';
                        break;
                }
                var father = document.getElementById(eleId);
                var h5 = father.querySelector(".quoteWrap .price h5");// 需要调整颜色
                var lamp = father.querySelector(".quoteWrap .price .updown");// 需要调整颜色(上下那个箭头)
                var changeli = father.querySelector(".quoteWrap .change").children[0];// 需要调整颜色(涨跌额和涨跌幅
                // )
                var setUpTime = father.querySelector(".quoteWrap .change").children[1];// 更新时间
                var ul = father.querySelector(".quote_detail_wrap ul");// 获取ul
                var spanop = ul.children[0].querySelector("span"); // 今开的span标签
                var spanyp = ul.children[1].querySelector("span");// 昨收的span标签
                var spanlp = ul.children[2].querySelector("span"); // 最低价的span标签
                var spanhp = ul.children[3].querySelector("span"); // 最高价的span标签
                // 需要设置的地方有8处(改颜色的3处)
                setUpTime.innerHTML = goldObj.uptime; // 更新时间
                spanop.innerHTML = goldObj.open_price; // 今开
                spanyp.innerHTML = goldObj.yesy_price; // 昨收
                spanlp.innerHTML = goldObj.low_price; // 最低
                spanhp.innerHTML = goldObj.high_price // 最高
                h5.innerHTML = goldObj.last_price; // 最新价
                changeli.innerHTML = goldObj.change_price + " " + "("
                    + goldObj.change_margin + ")";

                var reg = new RegExp("-{1}\w*\.\w*"); // 检查字符串中是否含有负号
                if (reg.test(goldObj.change_price)) {// 有负号
                    h5.className = "h5color_green";
                    lamp.className = "updown greenrow"
                    changeli.className = "li_green";
                } else {
                    h5.className = "h5color_red";
                    lamp.className = "updown redrow"
                    changeli.className = "li_red";
                }
            });

        });
}

/*------------初始化页面------------*/




/*------------轮播图------------*/
(function () {
    initImg();
    function initImg() {//加载轮播图(从数据库获取图片url)
        var ul = document.getElementById("imglist"); // 轮播图的ul
        $.post(
            "InitRotateServlet",
            {},
            function (result) {
                result= eval(result);
                for (i=0;i<result.length;i++){
                    ul.innerHTML+='<li><a href="javascript:;"><img src="'+result[i].imgurl+'"></a></li>\n';
                }
                ul.innerHTML+='<li><a href="javascript:;"><img src="'+result[0].imgurl+'"></a></li>\n';
                var liArr = ul.getElementsByTagName("li"); // 轮播图的li
                ul.style.width = 498 * liArr.length + "px";
            },
            "json"
        );

    };
})();

(function() {
    carousel() ;

    function carousel() {//轮播图
        var ul = document.getElementById("imglist"); // 轮播图的ul
       var liArr = ul.getElementsByTagName("li"); // 轮播图的li
        var showDiv = document.getElementById("slideshow");// 包裹轮播图的div(用来冒泡)
        var index = 0; // 默认显示图片的索引
        var bt_lt = document.querySelector(".bt_lt");
        var bt_rt = document.querySelector(".bt_rt");
        showDiv.onmouseover = function() {
            clearInterval(timer);
        };
        showDiv.onmouseout = function() {
            autoSwitch();
        };
        bt_lt.onclick = function() {
            clearInterval(timer);
            index--;
            if (index < 0) {
                index = liArr.length - 2;
                ul.style.left = index * (-498) + "px";
            } else {
                ul.style.left = index * (-498) + "px";
            }
        };
        bt_rt.onclick = function() {
            clearInterval(timer);
            index++;
            if (index == liArr.length - 1) {
                index = 0;
                ul.style.left = index * (-498) + "px";
            } else {
                ul.style.left = index * (-498) + "px";
            }
        };
        autoSwitch();
        var timer;
        // 自动切换图片的函数
        function autoSwitch() {
            timer = setInterval(function() {
                index++;
                index %= liArr.length;
                move(ul, "left", -498 * index, 30, function() {
                    if (index == liArr.length - 1) {
                        index = 0;
                        ul.style.left = 0;
                    }
                });
            }, 2000);
        }
    }



})();

/*------------轮播图------------*/




/*------------自动登录------------*/
(function(){
    var top_user = document.querySelector(".top_user");
    var login_after = document.querySelector(".login_after");
    var upowerli = document.getElementById("upower");
    var unameli = document.getElementById("uname");
    var headImg = document.querySelector(".login_after #uPhoto img");
    var userInfo = document.querySelector(".login_after .userinfo");

    $.post(//检查是否有session
        "LoginStatus",
        {},
        function (result) {
            ressult=eval(result);
            if (typeof (result["userId"]) != "undefined"){
                top_user.style.visibility = 'hidden';
                login_after.style.visibility = 'visible';
                //upowerli.innerText = result['upower'];
                switch(result['upower']){
                    case 'supreme':
                        upowerli.innerText='超级管理员';
                        userInfo.innerHTML='    <a href="admin/adminIndex.html"  target="_Blank">管理者后台</a>\n' +
                            '                    <a href="client/individual.html"  target="_Blank">个人中心</a>\n' +
                            '                    <a href="javascript:;">修改密码</a>\n' +
                            '                    <a href="UserQuitServlet">退出</a>';
                        break;
                    case 'client':
                        upowerli.innerText='普通用户';
                        userInfo.innerHTML='     <a href="client/individual.html"  target="_Blank">个人中心</a>\n' +
                            '                    <a href="javascript:;">修改密码</a>\n' +
                            '                    <a href="UserQuitServlet">退出</a>';
                        break;
                }
                unameli.innerText = result['uname'];
                headImg.src=result['headurl'];
            }
        },
        "json"
    );
})();

/*------------自动登录------------*/



/*------------登录------------*/
(function() {
    var uid = document.getElementById("uid");
    var upwd = document.getElementById("upwd");
    var submitbtn = document.querySelectorAll(".top_login input")[2];// submit按钮
    var phoneReg = /^1\d{10}$/;
    var mailReg = /^\w+@\w{2,}\.[a-zA-Z]{2,4}(\.[a-zA-Z]{2,4})?$/;
    var top_user = document.querySelector(".top_user");
    var login_after = document.querySelector(".login_after");
    var upowerli = document.getElementById("upower");
    var unameli = document.getElementById("uname");
    var headImg = document.querySelector(".login_after #uPhoto img");
    var userInfo = document.querySelector(".login_after .userinfo");

    submitbtn.onclick = function() { //点击登录按钮
        var uidvalue = uid.value;
        if (!phoneReg.test(uidvalue) && !mailReg.test(uidvalue)) {
            alert("账号或密码格式错误");
            return false;
        } else {
            $.post("LoginServlet", {
                    userid : uid.value,
                    upwd : upwd.value
                }, function(result, testStatus) {
                    result=eval(result);
                    if (typeof (result["userId"]) == "undefined") {
                        alert("账号或密码错误");
                    } else if ((result["userId"]) != "undefined") {

                        top_user.style.visibility = 'hidden';
                        login_after.style.visibility = 'visible';
                        switch(result['upower']){
                            case 'supreme':
                                upowerli.innerText='超级管理员';
                                userInfo.innerHTML='    <a href="admin/adminIndex.html"  target="_Blank">管理员后台</a>\n' +
                                    '                    <a href="client/individual.html">个人中心</a>\n' +
                                    '                    <a href="javascript:;">修改密码</a>\n' +
                                    '                    <a href="UserQuitServlet">退出</a>';
                                break;
                            case 'client':
                                upowerli.innerText='普通用户';
                                userInfo.innerHTML='     <a href="client/individual.html">个人中心</a>\n' +
                                    '                    <a href="javascript:;">修改密码</a>\n' +
                                    '                    <a href="UserQuitServlet">退出</a>';
                                break;
                        }
                        unameli.innerText = result['uname'];

                        headImg.src=result['headurl'];

                    } else {
                        alert("账号或密码错误");
                    }

                }, "json" // 返回值类型
            );
        }
    };

})();
/*------------登录------------*/





/*------------绑定登录按钮------------*/
(function() {
    var loginBtn = document.getElementById("login_a")
    var loginFram = document.querySelector(".top_user .top_login");
    loginBtn.onclick = function() {
        toggleClass(loginFram, "top_login_c");
    };
})();
/*------------绑定登录按钮------------*/



/*------------查看详尽分析按钮------------*/
(function() {
    var detail_A = document.querySelectorAll(".price_bar .quote_detail_wrap a");
    for (i = 0; i < detail_A.length; i++) {
        detail_A[i].onclick = function() {
            var grandpaId = this.parentNode.parentNode.id; // 爷元素的id

            $.post(//检查是否有session
                "LoginStatus",
                {},
                function (result) {
                    ressult=eval(result);
                    if (typeof (result["userId"]) != "undefined"){
                        window.open ("client/showDetail.html?" + grandpaId);
                        // window.location="client/showDetail.html";
                    }else{
                        window.location ="unlogin.html";
                    }
                },
                "json"
            );

        };
    }
})();
/*------------查看详尽分析按钮------------*/



