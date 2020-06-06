/*------加载tbody------*/
(function () {
    getAllRotateImg();
    
})();


function getAllRotateImg() {
    const tbody=document.querySelector('.img-table tbody');
    $.getJSON(
        "GetAllRotateImgServlet",
        {},
        function (result) {
            result=eval(result);
            $.each(result,function (index,element) {
                tbody.innerHTML+='<tr><td><input class="checkbox" type="checkbox"></td><td>'+element["rownum"]+'</td><td>'+element["newsid"]+'</td><td><img src="'+element["imgurl"]+'"></td><td>'+element["newstitle"]+'</td></tr>';
            });
        }
    );
}
/*------加载tbody------*/

/*最多只能放10张轮播图
1，如果tbody里有10张了则label不显示
2，如果label上传后，tbody+已上传的够10张，则不创建新的label。否则创建新的label
* */
(function () {
    var uploadBox=document.querySelector('.img-table .upload-box');
    var uploadBtn = document.getElementById('uploadBtn');
    var formData = new FormData();
    var index =-1;
    if (index>10){
        index=-1;
    }
    checkImgNum();
    uploadBox.onchange = function (event) {
        event = event||window.event;
        if (event.target.type=="file"){
            ++index;
            var checkMsg =illegalImg(event.target.files[0]);
            if (checkMsg=='ok'){
                var imgFile =event.target.files[0];
                var url =window.URL.createObjectURL(imgFile);//回显图片
                //存疑，把input除掉不知是否还能上传
                event.target.parentNode.innerHTML='<img src="'+url+'">';
                checkImgNum();
                formData.append(index,imgFile);

            }else {
                alert(checkMsg);
            }
        }
    };
    uploadBtn.onclick =function () {
        if (index==-1){
            alert("至少上传一张图片");
        }else {
            $.ajax({
                type:'post',
                url:'RotateImgServlet',
                data:formData,
                contentType:false,//前端到后端的类型
                processData:false,
                cache:false,
                dataType:'text',//服务器回传的类型
                success:function(result){
                    alert(result);
                    if(result=="上传成功"){
                        window.location.href ="adminNewsControl.html";
                    }
                },
                error:function(xhr,errorMessage,e){
                    alert(result)
                }
            });
        }
        
    };

    function checkImgNum() {    //检测tabRow有没有10行(即10张图片)
        var tabRow =document.querySelectorAll('.img-table tbody tr');
        var uploadLabel = document.querySelectorAll('.img-table .upload-box label');
        if (tabRow.length>=10){
            for (i=0;i<uploadLabel.length;i++){
                uploadLabel[i].style.display="none";
            }
        }else if((tabRow.length+uploadLabel.length)<10){
            uploadBox.innerHTML +="<label>\n" +
                "\t\t\t\t\t<input  type=\"file\">\n" +
                "\t\t\t\t\t<i></i><span>选择图片</span>\n" +
                "\t\t\t\t</label>";
        }
    }
    function illegalImg(imgFile) {//检测图片是否合法
        const maxSize =2097152; //图片最大2M
        var fileSize = imgFile.size; //获取文件大小number类型
        var imgName = imgFile.name; //获取文件名string类型
        var suffixIndex =imgName.lastIndexOf(".");//获取最后一个点后面的内，即文件后缀
        var suffix =imgName.substr(suffixIndex+1,imgName.length).toUpperCase(); //后缀名 并转为大写
        if(suffix !='PNG' && suffix !='JPG' && suffix !='JPEG' ){
            return '格式错误'; //格式不合法
        }else if(parseInt(fileSize)>=parseInt(maxSize)){
            return '大小不能超过2M';  //超过2M
        }else {
            return 'ok';
        }
    }
})();



/*------图片弹窗------*/
(function () {
    var divAlert = document.getElementById('imgDetail');//弹窗div
    var imgAlert =divAlert.getElementsByTagName("img")[0];//弹窗div里的img
    var tbody = document.querySelector('.img-table tbody');
    tbody.onclick =function (event) {
        event = event||window.event;
        if (event.target.tagName =='IMG'){//证明你丫点的是img
            imgAlert.src =event.target.src;
            removeClass(divAlert,"disappear")
        }

    } ;
    divAlert.onclick =function () {
        addClass(divAlert,"disappear")
    } ;
})();
/*------图片弹窗------*/



/*全选,重置、删除、导出按钮*/
(function () {
    const resetBtn =document.getElementById('resetBtn');//重置
    const deleteBtn=document.getElementById('deleteBtn');//删除
    const ouputBtn=document.getElementById('outputBtn');//导出
    var checkedAll=document.querySelectorAll('.img-table table thead th')[0];
    resetBtn.onclick = function () {//重置
        window.location.href ="adminNewsControl.html";
    };
    function isAll(objArr) { //检查是否全选的函数
        for (i=0;i<objArr.length;i++){
            if (!objArr[i].checked){
                return false;//没全选
            }
        }
        return true; //已经全选
    }
    checkedAll.onclick =function () {//按一次全选，再按一次全取消
        var checkBox =document.querySelectorAll('.img-table table tbody input');
        if(isAll(checkBox)){//已经全选
            for (i=0;i<checkBox.length;i++){
                checkBox[i].checked=false;
            }
        }else {
            for (i=0;i<checkBox.length;i++){
                checkBox[i].checked=true;
            }
        }
    };
    
    
    function hasCheck(objArr){//判断是否一个都没选
        for (i=0;i<objArr.length;i++){
            if (objArr[i].checked){
                return true;
            }
        }
    }

    deleteBtn.onclick =function () {//删除选中的图片,硬盘和数据库都删

            var checkBox =document.querySelectorAll('.img-table table tbody input');
            var newsIdArr = new FormData();//用来放要删除的图片的新闻id
            for (i=0;i<checkBox.length;i++){//获取选中的checkbox
                if (checkBox[i].checked){//如果选中
                    //获取新闻id的那个<td>
                    var td = checkBox[i].parentNode.parentNode.getElementsByTagName('td')[2];;
                    newsIdArr.append(i,td.innerHTML);
                }
            }
           if (!hasCheck(checkBox)){
               alert('你还没选');
           }else {
               var comfirm1=confirm("是否彻底删除选中的图片?");
               if(comfirm1) {
            	      $.ajax({
                        type:'post',
                        url:'DeleteRotateServlet',
                        data:newsIdArr,
                        contentType:false,//前端到后端的类型
                        processData:false,
                        cache:false,
                        dataType:'text',//服务器回传的类型
                        success:function(result){
                        	alert(result);
                            window.location.href ="adminNewsControl.html";
                        },
                        error:function(xhr,errorMessage,e){
                            alert(result);
                            window.location.href ="adminNewsControl.html";
                        }
                    });
               }
           }
    };
})();
/*全选,重置、删除、导出按钮*/