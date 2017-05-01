// 创建新页面，调整page高度
function createNewPage(){
    $(".page").css({
        "height" : $(window).height()-39+"px"
    });
}

$(document).ready(function(){
    getUrldata();
});

// 获取url中参数
function getUrlParam(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return unescape(r[2]); return null;
}
// 获取URL中的数据，获取课程数据
function getUrldata(){
    var openid = getUrlParam("openid");
    var classid = getUrlParam("claid");
    if((openid == null) || (openid == "") || (classid == null) || (classid == "")) goErrorPage();
    else{
        $.ajax({
            url: "./content/assets/json/test_class.json",
                type: "get",
                data: openid,
                dataType: "JSON",
                success: function(data) {
                                loadClassPage(data);
                            },
                error: function() {alert("error");}
        });
        loadClassPage();
    }
}
// 加载ClassPage
function loadClassPage(data){
    var html = document.getElementById("classPage").innerHTML;
    var source = html.replace(reg, function (node, key) { return {"className":data.class_name,"classInfo":data.class_info,"teacher_id":data.teacher_id,"teacherName":data.teacher_name,"count":data.count,"time":data.time}[key]; });
    $("#loading").remove();
    $(document.body).append(source);
    createNewPage();
    initPage();
}
// 初始化Page
function initPage(){
    $("#teacher").attr("href","./teacher.html?openid="+$("#teacherID").val());
}
// 跳转至error页
function goErrorPage(){
    window.location.href="./state.html?state=1&info=1";
}