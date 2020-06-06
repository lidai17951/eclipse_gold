function hasClass(obj,cn) {//有返回true
    var reg = new RegExp("\\b"+cn+"\\b");
    return reg.test(obj.className);
}

function addClass(obj,cn) {//追加样式
    if (!hasClass(obj,cn)){
        obj.className +=" "+cn;
    }
}

function removeClass(obj,cn) {
    var reg = new RegExp("\\b"+cn+"\\b");
    obj.className = obj.className.replace(reg,"");
}

function toggleClass(obj,cn) {
    if (hasClass(obj,cn)){
        removeClass(obj,cn);
    }else {
        addClass(obj,cn);
    }
}