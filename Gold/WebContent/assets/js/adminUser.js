(function () {
    var select = document.getElementById("howRow");
    var tbody = document.querySelector(".user-tab table tbody");
    var pageSize =parseInt(select.value); //每页显示的行数
    var pageInfo=[];//放页码信息
    var userInfo=[];//放获取到的信息
    var currentPage =1; //当前页数
    var totalPage=1 ; //总页数
    var ul =document.querySelector(".page-size ul");

    getUserArr();
    select.onchange=function () {
        pageSize =select.value;
        getUserArr();
    };
    


    ul.onclick =function (event) {
        event = event || window.event;
        if(event.target.href=="javascript:;"){//如果href==xxx。则触发
            switch (event.target.innerHTML) {
                case "首页": currentPage=1; break;
                case "最后一页": currentPage=totalPage; break;
                case "上一页": currentPage--; break;
                case "下一页": currentPage++; break;
                default:currentPage=parseInt(event.target.innerHTML);
            }
            if (currentPage<1){
                currentPage=1;
            }else if(currentPage>totalPage){
                currentPage= totalPage;
            }
        }
        getUserArr();
    };
    
    
    
    
    
    function getUserArr() {
        $.getJSON(
            "GetAllUserServlet",
            {"pageSize":pageSize,"currentPage":currentPage},
            function (result) {
                result=eval(result);
                // result[0] 页码信息,如：{"totalPage":1,"pageSize":10,"currentPage":1,"totalCount":2}
                // result[1] list注意是数组,如：[{"uname":"admin","createTime":"2020-04-12:15:57:28:04","upower":"supreme","userId":"332617778@qq.com"},{"uname":"美特斯邦邦硬","createTime":"2020-04-17:01:41:35:04","upower":"client","userId":"13553464390"}]
                pageInfo =result[0];
                userInfo = result[1];
                totalPage= pageInfo["totalPage"];
                currentPage =pageInfo["currentPage"];
                tbody.innerHTML="";//开始先清空table
                $.each(userInfo,function (index,element) {
                    tbody.innerHTML+='<tr><td><input class="checkbox" type="checkbox"></td><td>'+element["rownum"]+'</td><td><img class="userHead" src='+element["headurl"]+'></td><td>'+element["userId"]+'</td><td>'+element["uname"]+'</td><td>'+element["upower"]+'</td><td>'+element["createTime"]+'</td><td>null</td></tr>\n';
                });
                setBtn();//每次改变重置页码
            }
        );
    }

            function setBtn() {
             ul.innerHTML='<li><a href="javascript:;">上一页</a></li>\n' +
                 '                <li><a href="javascript:;">首页</a></li>\n' +
                 '                <li id="end-page"><a href="javascript:;">最后一页</a></li>\n' +
                 '                <li><a href="javascript:;">下一页</a></li>';
                var pageNavLi = document.getElementById("end-page");
                for (i=0;i<5;i++){
                        if((currentPage+i)<=totalPage){
                            var li = document.createElement("li");
                            li.innerHTML +='<a href="javascript:;">'+(currentPage+i)+'</a>';
                            pageNavLi.parentNode.insertBefore(li,pageNavLi);
                        }else {
                            break;
                        }
                    }
            }

})();