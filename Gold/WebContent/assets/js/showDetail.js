// 获取首页传过来的黄金代码并赋给全局变量
var goldInfo ={"variety":(function() {
        var url = location.search;
        if ((url) != "" && url != null) {
            if (url.indexOf("?") != -1) {
                return  url.substr(1);
            }else {
                window.location.href="../unlogin.html"
            }
        } else {
            window.location.href="../unlogin.html"
        }
    })()};
// alert(typeof (goldInfo["variety"])=="undefined");



/*初始化5个模块的标题*/
//即顶部,左上，左中，右中,当前报价(标题)
(function() {
    //首字母转为大写
    var variety =goldInfo["variety"].charAt(0).toUpperCase()+goldInfo["variety"].slice(1);

    //网页标题
    var headerh1 =document.querySelector("header h1");

    //左上柱状图标题
    var barh2 =document.querySelector(".bar h2");

    //左中折线图标题
    var lineh2 = document.querySelector(".line h2");

    //右中折线图标题
    var line2h2 =document.querySelector(".line2 h2");

    //当前报价的小标题
    var nobd = document.querySelectorAll(".column .no .no-bd li")[0];

    headerh1.innerHTML=variety;
    barh2.innerHTML=variety+"近期成交量";
    lineh2.innerHTML=variety+"买卖价(元/克)"
    line2h2.innerHTML=variety+ "价格走势";
    nobd.innerHTML = variety+"当前价格(元/克)";
})();
/*初始化5个模块的标题*/




/*左侧左上柱状图 动态*/
(function(goldVariety) {
    var myChart = echarts.init(document.querySelector(".bar .chart"));
    var howRow = 5;// 数字代表从数据库取出几行数据

    function getVolume(howRow,goldVariety) {
        $.getJSON(
            "GetVolumeServlet",
            {"howRow" : howRow ,"goldVariety":goldVariety},
            function(result) {
                var jObj = eval(result);// eval防止后台传来的json对象格式不对
                myChart.showLoading();
                var xData = [];
                var seriesData = [];
                $.each(result, function(index, element) {
                    // 遍历
                    xData[index] = element["goldTime"].uptime;
                    seriesData[index] = element["volume"];
                });

                xData.reverse();
                seriesData.reverse();// 反转顺序
                var option = {
                    color : [ '#2f89cf' ],
                    tooltip : {
                        trigger : 'axis',
                        axisPointer : { // 坐标轴指示器，坐标轴触发有效
                            type : 'shadow' // 默认为直线，可选为：'line' | 'shadow'
                        }
                    },
                    grid : {
                        left : "0%",
                        top : "10px",
                        right : "0%",
                        bottom : "4%",
                        containLabel : true
                    },
                    xAxis : [ {
                        type : 'category',
                        data : xData,
                        axisTick : {
                            alignWithLabel : true
                        },
                        axisLabel : {// x轴刻度标签
                            show:false,
                            color : "rgba(255,255,255,0.9)", // 文字颜色
                            fontSize : "12"
                        },
                        axisLine : {// x轴
                            show : false
                            // 不显示x轴
                            // 如果要设置单独的线条样式
                            // lineStyle:{
                            // color:"red",
                            // width:"1",
                            // type:"solid"
                            // }
                        }
                    } ],
                    yAxis : [ {
                        type : 'value',
                        axisLabel : {// y轴刻度标签
                            color : "rgba(255,255,255,0.9)", // 文字颜色
                            fontSize : "12"
                        },
                        axisLine : {
                            lineStyle : {
                                color : "rgba(255,255,255,0.9)"
                                // ,width:1,
                                // type:"solid"
                            }
                        },
                        splitLine : {// y轴分隔线样式
                            lineStyle : {
                                color : "rgba(255,255,255,0.9)",
                                type : "dashed"
                            }
                        }
                    } ],
                    series : [ {
                        name : '交易量',
                        type : 'bar',
                        barWidth : '35%', // 柱子的宽度
                        data : seriesData,
                        itemStyle : {
                            // 修改柱子为圆角
                            barBorderRadius : 5
                        }
                    } ]
                };

                myChart.setOption(option);
                myChart.hideLoading();
            });
    }

    getVolume(howRow,goldVariety);// 第一次打开页面时加载一次数据

    setInterval(function() {
        getVolume(howRow,goldVariety);
    }, 1000 * 60 * 5); // 每5分钟刷新一次

    // 让图表跟随屏幕自适应
    window.addEventListener('resize', function() {
        myChart.resize();
    });

})(goldInfo["variety"]);
/*左侧左上柱状图 动态*/



/* 左侧中间模块 动态*/
(function(goldVariety) {
    var myChart = echarts.init(document.querySelector(".line .chart"));
    var howRow =7; //查询几条数据

    function getDeal(howRow,goldVariety) {//获取 买/卖价 7条
        $.getJSON(
            "GetDealServlet",
            {"howRow" : howRow ,"goldVariety":goldVariety},
            function (result) {
                var jObj = eval(result);
                myChart.showLoading();
                var xData = [];
                var buyData = [];  //买入价
                var sellData = []; //卖出价
                $.each(result,function (index,element) {
                    xData[index]= element["goldTime"].uptime;
                    buyData[index] = element["buy_price"];
                    sellData[index] = element["sell_price"];
                });
                xData.reverse();
                buyData.reverse();
                sellData.reverse();// 反转顺序

                var option = {
                    color : [ '#00f2f1', '#ed3f35' ],// 修改折线颜色
                    tooltip : {
                        trigger : 'axis'
                    },
                    legend : {
                        // 如果series里有name，则legend不用data
                        textStyle : {// 图例文字样式
                            color : '#fff',
                            fontSize : 16,
                        },
                        right : '10%'
                    },
                    grid : {
                        top : '20%',
                        left : '3%',
                        right : '4%',
                        bottom : '3%',
                        show : true,// 显示边框
                        borderColor : '#012f4a',
                        containLabel : true
                    },

                    xAxis : {
                        type : 'category',
                        boundaryGap : true,
                        data : xData,
                        axisTick : {
                            show : false
                            // 去除刻度
                        },
                        axisLabel : {
                            show:false,
                            color : '#fff'
                        },
                        axisLine : {
                            show : false
                            // 去除轴线
                        }
                    },
                    yAxis : {
                        type : 'value',
                        min : function(value) {// 把最小值作为0点(否则太微小的变化显示不出)
                            return value.min;
                        },
                        axisTick : {
                            show : false
                            // 去除刻度
                        },
                        axisLabel : {
                            color : '#fff'
                        },
                        axisLine : {
                            show : false
                            // 去除轴线
                        },
                        splitLine : {
                            lineStyle : {
                                color : '#012f4a'
                            }
                        }
                    },
                    series : [ {
                        name : '买入价',
                        type : 'line',
                        data : buyData,
                        smooth : true
                        // 折线变圆滑
                    }, {
                        name : '卖出价',
                        type : 'line',
                        data : sellData,
                        smooth : true
                        // 折线变圆滑
                    }

                    ]
                };

                myChart.setOption(option);
                myChart.hideLoading();
            }
        );

    }

    getDeal(howRow,goldVariety);// 第一次打开页面时加载一次数据

    setInterval(function() {
        getDeal(howRow,goldVariety);
    }, 1000 * 60 * 2); // 每2分钟刷新一次

    // 让图表跟随屏幕自适应
    window.addEventListener('resize', function() {
        myChart.resize();
    });
})(goldInfo["variety"]);
/* 左侧中间模块 动态*/




/*左侧第三模块*/
(function () {
    var myChart = echarts.init(document.querySelector(".dash .chart"));

    var option = {
        color:['rgb(16, 137, 231)','rgb(245, 116, 116)','rgb(86, 208, 227)','rgb(248, 180, 72)','rgb(139, 120, 246)'],
        tooltip: {
            trigger: 'item',
        },
        series: [

            {
                name: '面积模式',
                type: 'pie',
                radius: [10, 80],
                center: ['50%', '50%'],
                roseType: 'area',
                data: [
                    {value: 10, name: '周一'},
                    {value: 5, name: '周二'},
                    {value: 15, name: '周三'},
                    {value: 25, name: '周四'},
                    {value: 20, name: '周五'},

                ]
            }
        ]
    };



    myChart.showLoading();
    myChart.setOption(option);
    myChart.hideLoading();

    //让图表跟随屏幕自适应
    window.addEventListener('resize',function () {
        myChart.resize();
    });

})();
/*左侧第三模块*/


/* 右侧右上的横向柱状图 */
(function() {
    var myChart = echarts.init(document.querySelector(".bar2 .chart"));
    // 用于在series中设置每根柱子的颜色都不一样
    var colorArr = [ '#1089E7', '#F57474', '#56D0E3', '#F8B448', '#8B78F6' ];
    var option = {
        grid : {
            left : '18%',
            top : '10%',
            right : '16%',
            bottom : '10%',
            containLabel : false
            // grid 区域是否包含坐标轴的刻度标签
        },
        xAxis : {
            show : false
            // 不显示x轴
        },
        yAxis : [ {// 坐标系 左侧文字
            type : 'category',
            data : [ 'Au99.99', 'Au99.95', 'Au100g', 'Au(T+D)', 'PT99.95' ],
            inverse : true,
            axisLine : {
                show : false
                // 不显示Y轴线条
            },
            axisTick : {
                show : false
                // 不显示刻度
            },
            axisLabel : {
                color : '#fff' // Y轴颜色为白色
                ,
                fontSize : 12
            }
        }, {// 坐标系 右侧文字
            show : true,
            data : [ 340, 76, 310, 355, 290 ],
            inverse : true,
            axisLine : {
                show : false
                // 不显示Y轴线条
            },
            axisTick : {
                show : false
                // 不显示刻度
            },
            axisLabel : {
                textStyle : {
                    color : '#fff', // Y轴颜色为白色
                    fontSize : 12,
                },
            }
        } ],
        /*
         * 做成进度条的形式：思路是第一组柱子是实心的,第二组柱子是空心的 而后第一组柱子压住第二组柱子
         */
        series : [

            { // 第一组：实心柱子
                name : '条',
                type : 'bar',
                data : [ 70, 34, 60, 78, 69 ],
                barCategoryGap : 50,// 柱子之间的距离
                barWidth : 10, // 柱子的宽度
                itemStyle : {// 每条柱子样式
                    barBorderRadius : 20,// 把柱子设为圆角
                    // color:"red" //color把所有柱子都设为一个颜色
                    /* 设置每条柱子的颜色都不一样 */
                    color : function(params) {// params系统自动传入当前渲染的柱子对象
                        // params.dataIndex当前第几根柱子
                        // 0%5=5 1%5=1 2%5=2.....5%5=0 6%5=1...10%5=0
                        return colorArr[params.dataIndex % colorArr.length];
                    },
                },
                label : { // label:可以在柱子上显示文本
                    normal : {// 加不加normal都可
                        show : true,
                        color : '#fff',
                        fontWeight : 400,
                        position : 'inside', // 在柱子内显示文字
                        formatter : '{c}%' // 文字的样式(会把上面的data加上%号)
                        /*
                         * {a}：系列名。显示name的值 {b}：数据名。显示yAxis中的data值 {c}：数据值。显示自己的data值
                         */
                    }
                },
                yAxisIndex : 0,// 通过此属性实现第二组压住第一组(达到重叠效果)

            }, {// 第二组柱子,空心 框状
                name : '空心框',
                type : 'bar',
                barCategoryGap : 50,// 柱子之间的距离
                barWidth : 15, // 柱子的宽度
                itemStyle : {
                    color : 'none',// color为none加上borderColor组合成空心
                    borderColor : '#00c1de',
                    borderWidth : 3,
                    barBorderRadius : 15
                },
                yAxisIndex : 1,// 通过此属性实现第二组压住第一组(达到重叠效果)
                data : [ 100, 100, 100, 100, 100 ]

            } ]
    };
    myChart.showLoading();
    myChart.setOption(option);
    myChart.hideLoading();

    // 让图表跟随屏幕自适应
    window.addEventListener('resize', function() {
        myChart.resize();
    });
})();
/* 右侧右上的横向柱状图 */



/*右侧中间模块和中间顶部数字  动态*/
(function (goldVariety) {
    var myChart = echarts.init(document.querySelector(".line2 .chart"));
    var howRow =7;
    //中间顶部最新价的li
    var lastPriceLi = document.querySelectorAll(".column .no .no-hd li")[0];
    //中间顶部涨跌幅的li
    var changeMarginLi = document.querySelectorAll(".column .no .no-hd li")[1];
    var reg = new RegExp("-{1}\w*\.\w*");// 检查字符串中是否含有负号
    function getLastPrice(howRow,goldVariety) {
        $.getJSON(
            "GetLastArrServlet",
            {"howRow":howRow,"goldVariety":goldVariety},
            function (result) {
                result = eval(result);
                myChart.showLoading();
                var xData=[];  //x坐标轴
                var seriesData=[];
                var change_margin=[]; //涨跌幅
                $.each(result,function (index,element) {
                    xData[index]= element["goldTime"].uptime;
                    seriesData[index] = element["last_price"];
                    change_margin[index]= element["change_margin"];
                });
                xData.reverse();
                seriesData.reverse();
                change_margin.reverse();
                var option = {
                    color:['#ed3f35'],//修改折线颜色
                    tooltip: {
                        trigger: 'axis',
                        axisPointer: {
                            type: 'cross',
                            label: {
                                backgroundColor: '#6a7985'
                            }
                        }
                    },

                    grid: {
                        top:'20%',
                        left: '3%',
                        right: '4%',
                        bottom: '3%',
                        show:true,//显示边框
                        borderColor:'#012f4a',
                        containLabel: true
                    },

                    xAxis: {
                        type: 'category',
                        boundaryGap: true,
                        data: xData,
                        axisTick:{
                            show:false //去除刻度
                        },
                        axisLabel:{
                            show:false,
                            color:'#fff'
                        },
                        axisLine:{
                            show:false //去除轴线
                        }
                    },
                    yAxis: {
                        type: 'value',
                        min:function (value) {//把最小值作为0点(否则太微小的变化显示不出)
                            return value.min;
                        },
                        axisTick:{
                            show:false //去除刻度
                        },
                        axisLabel:{
                            color:'#fff'
                        },
                        axisLine:{
                            show:false //去除轴线
                        },
                        splitLine:{
                            lineStyle:{
                                color:'#012f4a'
                            }
                        }
                    },
                    series: [
                        {
                            name: '价格走势',
                            type: 'line',
                            data: seriesData,
                            //smooth:true  //折线变圆滑
                        }

                    ]
                };
                myChart.setOption(option);
                myChart.hideLoading();
                if (reg.test(change_margin[change_margin.length-1])){
                    lastPriceLi.style.color="limegreen";
                    changeMarginLi.style.color="limegreen";
                }else {
                    lastPriceLi.style.color="red";
                    changeMarginLi.style.color="red";
                }
                lastPriceLi.innerHTML = seriesData[seriesData.length-1];
                changeMarginLi.innerHTML= change_margin[change_margin.length-1];
            }
        );
    }

    getLastPrice(howRow,goldVariety);

    setInterval(function() {
        getLastPrice(howRow,goldVariety);
    }, 1000 * 60 * 2); // 每2分钟刷新一次

    //让图表跟随屏幕自适应
    window.addEventListener('resize',function () {
        myChart.resize();
    });
})(goldInfo["variety"]);
/*右侧中间模块和中间顶部数字  动态*/




/*右侧第三模块*/
(function () {
    var myChart = echarts.init(document.querySelector(".radar .chart"));
    var color = ['#e9df3d', '#f79c19', '#21fcd6', '#dd0000', '#df4131'];
    var data = [{
        "name": "建议入手",
        "value": 50
    },
        {
            "name": "买卖价",
            "value": 30
        },
        {
            "name": "购买人数",
            "value": 22
        },
        {
            "name": "成交量",
            "value": 50
        },
        {
            "name": "升值概率",
            "value": 52
        }
    ];

    var max = data[0].value;
    data.forEach(function(d) {
        max = d.value > max ? d.value : max;
    });

    var renderData = [{
        value: [],
        symbol: 'none',
        lineStyle: {
            normal: {
                color: '#ecc03e',
                width: 2
            }
        },
        areaStyle: {
            normal: {
                color: new echarts.graphic.LinearGradient(0, 0, 1, 0,
                    [{
                        offset: 0,
                        color: 'rgba(203, 158, 24, 0.8)'
                    }, {
                        offset: 1,
                        color: 'rgba(190, 96, 20, 0.8)'
                    }],
                    false)
            }
        }
    }];


    data.forEach(function(d, i) {
        var value = ['', '', '', '', ''];
        value[i] = max,
            renderData[0].value[i] = d.value;
        renderData.push({
            value: value,
            symbol: 'circle',
            symbolSize: 12,
            lineStyle: {
                normal: {
                    color: 'transparent'
                }
            },
            itemStyle: {
                normal: {
                    color: color[i],
                }
            }
        })
    })
    var indicator = [];

    data.forEach(function(d) {
        indicator.push({
            name: d.name,
            max: max,
            color: '#fff'
        })
    })


    var option = {
        //backgroundColor: '#01193d',
        tooltip: {
            show: false,
            trigger: "item"
        },
        radar: {
            center: ["50%", "50%"],
            radius: "70%",
            startAngle: 90, // 起始角度
            splitNumber: 4,
            shape: "circle",
            splitArea: {
                areaStyle: {
                    color: 'transparent'
                }
            },
            axisLabel: {
                show: false,
                fontSize: 20,
                color: "#000",
                fontStyle: "normal",
                fontWeight: "normal"
            },
            axisLine: {
                show: true,
                lineStyle: {
                    color: "rgba(255, 255, 255, 0.5)"
                }
            },
            splitLine: {
                show: true,
                lineStyle: {
                    color: "rgba(255, 255, 255, 0.5)"
                }
            },
            indicator: indicator
        },
        series: [{
            type: "radar",
            data: renderData
        }]
    };
    myChart.showLoading();
    myChart.setOption(option);
    myChart.hideLoading();

    //让图表跟随屏幕自适应
    window.addEventListener('resize',function () {
        myChart.resize();
    });
})();
/*右侧第三模块*/





/*-------------在线聊天-------------*/

(function(){
    var ws = new WebSocket("ws://localhost:8888/Gold/ChartWebSocket");
    /*
     *监听三种状态的变化 。js会回调
     */
    ws.onopen = function(message) {

    };
    ws.onclose = function(message) {

    };
    ws.onmessage = function(message) {
        showMessage(message.data);
    };
    //监听窗口关闭事件，当窗口关闭时，主动去关闭websocket连接，防止连接还没断开就关闭窗口，server端会抛异常。
    window.onbeforeunload = function() {
        ws.close();
    };
    //关闭连接
    function closeWebSocket() {
        ws.close();
    }
    //发送消息
    function send() {
        var textArea = document.getElementById("sendMsg");
        var text = textArea.value;
        if(text==""){
            alert("请输入文字");
        }else if(text.length>200){
            alert("不能超过200字");
        }else{
            ws.send(text);
            textArea.value = "";
        }

    }
    function showMessage(message) {
        var text = document.createTextNode(message);
        var br = document.createElement("br");
        var br2 = document.createElement("br");
        var div = document.getElementById("showTalk");
        div.appendChild(text);
        div.appendChild(br);
        div.appendChild(br2);
    }
    var sendMsgBtn = document.getElementById("sendMsgBtn");
    sendMsgBtn.onclick = function(){
        send();
    };
})();

/*-------------在线聊天-------------*/



/*买外币、卖外币、中间价 的按钮切换*/

(function () {
    var h3Arr =document.querySelectorAll(".main-box-content h3 span");
    var formArr= document.querySelectorAll(".main-box-content form");
    var index =0; //记录当前点的是谁
    for (i=0;i<h3Arr.length;i++){
        h3Arr[i].onclick=function (event) {
             inint();
            removeClass(event.target,"unchecked");
            switch (event.target.innerText) {
                case "买外币": formArr[0].style.display=""; break;
                case "卖外币": formArr[1].style.display=""; break;
                case "中间价": formArr[2].style.display=""; break;
            }

        } ;
    }
    function inint() {//重置所有span和form
        for (i=0;i<h3Arr.length;i++){
            addClass(h3Arr[i],"unchecked");
            formArr[i].style.display="none";
        }
    }
})();

/*买外币、卖外币、中间价 的按钮切换*/


/*汇率转换*/
(function () {
   var torFormArr = document.querySelectorAll(".top-nav .main-box-content form");
   var submitArr = document.querySelectorAll(".top-nav .main-box-content form .form_submit");
    var changeObj={
        "CNY":{"USD":0.1409,"GBP":0.1155,"EUR":0.1304,"CNY":1},//人民币汇率
        "USD":{"CNY":7.0954,"GBP":0.8198,"EUR":0.9254,"USD":1},//美元汇率
        "GBP":{"CNY":8.655,"USD":1.2198,"EUR":1.1288,"GBP":1},//英镑汇率
        "EUR":{"CNY":7.6673,"USD":1.0806,"GBP":0.8859,"EUR":1} //欧元汇率
    };
    for (i=0;i<submitArr.length;i++){
        submitArr[i].onclick=function (event) {
          var father= event.target.parentNode.parentNode;
             var formArr= father.querySelectorAll(".form-column")[0];//目前只做第一行
             var iWant = formArr.querySelectorAll("input")[0];//我想换input
             var iWantSelect =  father.getElementsByTagName("select")[0];//我想换select
            var iWantOption =iWantSelect.options[iWantSelect.selectedIndex].value;//我想换option值
            var needTo = formArr.querySelectorAll("input")[1];//需要用input
             var needToSelect =  father.getElementsByTagName("select")[1];//需要用select
            var needToOption =needToSelect.options[needToSelect.selectedIndex].value;//我想换option值
            var nowForm= father.querySelectorAll(".form-column")[2];//当前汇率form
            var nowChange = nowForm.getElementsByTagName("input")[0];//当前汇率input
            var result = exchange(iWantOption,iWant.value,needToOption);
            nowChange.value= result["exchange"];
            needTo.value=result["destNum"];
        };
    }
    /* src 我想换的钱(字符串)
       srcNum 数量(Number)
       dest 要换的钱(Number)
     */
    function exchange(src,srcNum,dest) {
        var srcChange = changeObj[src];
        var destNum = srcChange[dest]*srcNum;
        return {"exchange":srcChange[dest],"destNum":destNum};//exchange:基本汇率 destNum:转换后可以换多少钱
    }

})();
/*汇率转换*/
