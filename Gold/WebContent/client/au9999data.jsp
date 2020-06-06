<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="../assets/js/jquery-3.4.1.min.js"></script>
<script type="text/javascript">
		$(document).ready(function(){ //JQ加载函数
			
			var data = getVolume(5);
			console.log(data[0]);
			console.log(data[1]);
		})
		
		
			function getVolume(howRow){
				var xData =new Array();
				var seriesData = new Array();
				var data = new Array();
				$.getJSON(
					"/Gold/GetVolumeServlet",
					{"howRow":howRow},
					function(result){
						var jObj = eval(result) ;//eval防止后台传来的json对象格式不对
					
						$.each(result,function(index,element){
							//遍历
							//document.write(jObj[index].change_margin);
							//document.write(element["volume"]+"可耻的分隔符");
							//document.write(element["goldTime"].hhmmss+"</br>");
							xData.push(element["goldTime"].hhmmss);
							seriesData.push(element["volume"]);
						});
					}
				);
				data.push(xData);
				data.push(seriesData);
				return data;
			}
</script>
</head>
<body>

</body>
</html>