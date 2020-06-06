(function () {
    var select =document.getElementById("goldVariety");

    select.onchange=function () {//金价种类的select
        if (typeof (select.value)!=null){
            alert(select.value);
        }
    };
})();