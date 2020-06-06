
//获取当前样式值
//obj 要获取的元素
//name 要获取的样式名
function getStyle(obj , name) {
    if (window.getComputedStyle){
        //正常浏览器,有getComputedStyle()方法
        return getComputedStyle(obj, null)[name];
    }else {
        //ie8,没有getComputedStyle()方法
        return obj.currentStyle[name];
    }
}