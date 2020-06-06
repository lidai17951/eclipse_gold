/*当前访问量*/
(function () {//当前在线人数
    var myChart = echarts.init(document.querySelector(".content .nowline"));
    var option = {
        color: ['#ed3f35','green'],// 修改折线颜色
        tooltip: {
            trigger: 'axis'
        },
        legend: {
            textStyle:{
                fontSize:20,
            }
        },
        grid: {
            top: '20%',
            left: '3%',
            right: '4%',
            bottom: '6%',
            containLabel: true
        },
        xAxis: {
            type: 'category',
            data: ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun'],
            axisTick: {
                show: false
                // 去除刻度
            },
            axisLabel: {
                show: false
            }
        },
        yAxis: {
            type: 'value',
            splitLine: {
                lineStyle: {
                    color: '#012f4a'
                }
            },
            axisLabel: {
                fontSize: 16
            }
        },
        series: [
            {
                name:"注册用户",
                data: [4, 5, 0, 0, 1, 0, 0],
                type: 'line'
            },
            {
                name:"非注册用户",
                data: [4, 5, 1, 1, 6, 1, 1],
                type: 'line'
            },
        ]
    };
    myChart.setOption(option);
})();
/*当前访问量*/

/*日访问量*/
(function () {//当前在线人数
    var myChart = echarts.init(document.querySelector(".content .dayline"));
    var option = {
        color: ['#ed3f35','green'],// 修改折线颜色
        tooltip: {
            trigger: 'axis'
        },
        legend: {
            textStyle:{
                fontSize:20,
            }
        },
        grid: {
            top: '20%',
            left: '3%',
            right: '4%',
            bottom: '6%',
            containLabel: true
        },
        xAxis: {
            type: 'category',
            data: ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun'],
            axisTick: {
                show: false
                // 去除刻度
            },
            axisLabel: {
                show: false
            }
        },
        yAxis: {
            type: 'value',
            splitLine: {
                lineStyle: {
                    color: '#012f4a'
                }
            },
            axisLabel: {
                fontSize: 16
            }
        },
        series: [
            {
                name:"注册用户",
                data: [4, 5, 0, 0, 1, 0, 0],
                type: 'line'
            },
            {
                name:"非注册用户",
                data: [4, 5, 1, 1, 6, 1, 1],
                type: 'line'
            },
        ]
    };
    myChart.setOption(option);
})();
/*日访问量*/

/*月访问量*/
(function () {//当前在线人数
    var myChart = echarts.init(document.querySelector(".content .monline"));
    var option = {
        color: ['#ed3f35','green'],// 修改折线颜色
        tooltip: {
            trigger: 'axis'
        },
        legend: {
            textStyle:{
                fontSize:20,
            }
        },
        grid: {
            top: '20%',
            left: '3%',
            right: '4%',
            bottom: '6%',
            containLabel: true
        },
        xAxis: {
            type: 'category',
            data: ['1月', '2月', '3月', '4月', '5月', '6月', '7月', '8月', '9月', '10月', '11月', '12月'],
            axisTick: {
                show: false
                // 去除刻度
            },
            axisLabel: {
                show: false
            }
        },
        yAxis: {
            type: 'value',
            splitLine: {
                lineStyle: {
                    color: '#012f4a'
                }
            },
            axisLabel: {
                fontSize: 16
            }
        },
        series: [
            {
            name:"注册用户",
            data: [4, 5, 0, 0, 60, 0, 0,0,0,0,0,0],
            type: 'line'
        },
            {
            name:"非注册用户",
            data: [4, 5, 1, 1, 44, 1, 1,0,0,0,0,0],
            type: 'line'
        },
        ]
    };
    myChart.setOption(option);
})();
/*月访问量*/

