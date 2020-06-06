(function () {
    var liArr = document.querySelectorAll(".content-left ul li");

    liArr[0].onclick =function () { //首页li
        window.location.href='../index.html';
    };
    liArr[1].onclick =function () { //我的信息li
        window.location.href='individual.html';
    };
})();



/*-------更新头像-------*/
(function () {
    var input_upload = document.getElementById("file_input");
    const maxSize =2097152;  //最大2M
    input_upload.onchange= function (event) {
        var imgFile = input_upload.files[0];
        var fileSize = imgFile.size; //获取文件大小string
        var imgName = imgFile.name; //获取文件名number
        var suffixIndex =imgName.lastIndexOf(".");//获取最后一个点后面的内，即文件后缀
        var suffix =imgName.substr(suffixIndex+1,imgName.length).toUpperCase(); //后缀名 并转为大写

        if(suffix !='PNG' && suffix !='JPG' && suffix !='JPEG' ){
            alert('文件类型错误,请上传图片类型');
        }else if(parseInt(fileSize)>=parseInt(maxSize)){
            alert('图片不能超过2M');
        }else {
            /*上传代码*/
            var url =window.URL.createObjectURL(imgFile);
            var preview = document.querySelector('.user-head-photo img');
            preview.src = url;

            var updateBtn = document.getElementById('update-btn');
            removeClass(updateBtn,'disabled');
            updateBtn.onclick =function () {//更新图像按钮
                var formData = new FormData();
                formData.append('imgFile',imgFile);
                $.ajax({
                    type:'post',
                    url:'HeadPhotoServlet',
                    data:formData,
                    contentType:false,//前端到后端的类型
                    processData:false,
                    cache:false,
                    dataType:'text',//服务器回传的类型
                    success:function(result){
                    	alert(result);	
                        },
                    error:function(xhr,errorMessage,e){
                        alert(result)
                          }
                });
            };
        }

    };
})();
/*-------更新头像-------*/


/*-------初始化当前头像-------*/
(function(){
	//当前头像框
	var nowHead =document.querySelector(".img-preview .user-head-photo img");

	  $.post(//检查是否有session
		        "ClientStatusServlet",
		        {},
		        function (result) {
		            ressult=eval(result);
		            nowHead.src=result['headurl'];
		            
		        },
		        "json"
		    );

})();
/*-------初始化当前头像-------*/