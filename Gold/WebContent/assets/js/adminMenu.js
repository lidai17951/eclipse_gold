window.onload=function () {
    var leftBar =document.querySelector(".left-nav");
    var content = document.querySelector(".content");
    var closeBtn = document.getElementById("collapse-nav");
    var imgBtn =document.querySelector(".nav-img-open");
    closeBtn.onclick =function () {
        toggleMenu(leftBar,"left-nav-close","offsetWidth","width");
        toggleMenu(imgBtn,"nav-img-close","offsetLeft","left")
        toggleMenu(content,"content-close","offsetLeft","left")
    };

    function toggleMenu(obj,cn,offsetXXX,attr) {
        var begin=obj[offsetXXX];//切换之前的高度
        toggleClass(obj,cn);
        var end=obj[offsetXXX];//切换之后的高度
        obj.style[attr]=begin+"px";//改为内联样式
        move(obj,attr,end,10,function () {
            obj.style[attr]=""; //去除内联样式
        });
    }
};
