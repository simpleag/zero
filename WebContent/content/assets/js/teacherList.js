// 创建新页面，调整page高度
function createNewPage(){
    $(".page").css({
        "height" : $(window).height()-39+"px"
    });
}

$(document).ready(function(){
    getJSONObject();
});
// 获取url中参数
function getUrlParam(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return unescape(r[2]); return null;
}
// 获取数据
function getJSONObject(){
    var openid = getUrlParam("openid");
    if(openid==null || openid=="") goErrorPage();
    else{
        $.ajax({
                url: "./content/assets/json/test_teacher.json",
                type: "get",
                data: openid,
                dataType: "JSON",
                success: function(data) {
                                loadTeacherListPage(data);
                            },
                error: function() {alert("error");}
            });
    }
}
//加载教师列表页面
function loadTeacherListPage(data){
    if(data==null) goErrorPage();
    else{
        var html = document.getElementById("teacherListPage").innerHTML;
        var source = html.replace(reg, function (node, key) { return {}[key]; });
        $("#loading").remove();
        $(document.body).append(source);
        createNewPage();
        initPage(data)
    }
}
// 初始化页面
function initPage(data){
    var html = document.getElementById("teacherListCell").innerHTML;
    for(var key in data){
        $("#noTeacher").remove();
        var userID = data[key].openid;
        var tName = data[key].name;
        var source = html.replace(reg, function (node, key) { return { 'userID': userID,"teacherName":tName }[key]; });
        $(".page .page__bd .weui-cells").append(source);
    }
}
// 列表Cell点击事件
function goTeacherPage(argument) {
    var openid = $(argument).children(".input-data__hide").val();
    window.location.href="./teacher.html?openid="+openid;
}
// 跳转至error页
function goErrorPage(){
    window.location.href="./state.html?state=1&info=1";
}