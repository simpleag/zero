var teacher = {
    openid: null,
    name: null,
    info: null,
    classes: [],
    mobile: null,
    email: null
};
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
                url: "teacherTest.do",
                type: "POST",
                dataType: "json",
				data: {
					"a": "abc",
					"b": 2,
					"c": 3
				},
                success: function(data) {
                                if((openid in data) == false) goErrorPage();
                                loadTeacherPage(data,openid);
                            },
                error: function() {alert("error");}
            });
    }
}
// 数据传递至全局变量teacher
function getTeacherInfo(JSONObject){
    teacher.openid = JSONObject.openid;
    teacher.name = JSONObject.name;
    teacher.info = JSONObject.info;
    teacher.classes = JSONObject.classes;
    teacher.mobile = JSONObject.mobile;
    teacher.email = JSONObject.email;
}
// 加载教师信息页面
function loadTeacherPage(data,openid){
    getTeacherInfo(data[openid]);
    if(teacher.openid==null || teacher.name==null) goErrorPage();
    else {
        var html = document.getElementById("teacherPage").innerHTML;
        var source = html.replace(reg, function (node, key) { return {}[key]; });
        $("#loading").remove();
        $(document.body).append(source);
        createNewPage();
        initPage()
    };
}
// 教师信息页面初始化
function initPage(){
    var html = document.getElementById("classCell").innerHTML;
    // 教师姓名
    $("#teacher .page__hd .page__title").text(teacher.name);
    // 教师介绍
    if(teacher.info!=null){
        // $("#teacher .page__hd .page__desc").text(teacher.info);
        $("#teacher .page__hd .page__desc").text(teacher.info.slice(0,102)+"...");
    }
    // 教授课程
    if(teacher.classes.length>0) $(".page .page__bd #noClass").css("display","none");
    for(var i in teacher.classes){
        var source = html.replace(reg, function (node, key) { return { 'className': teacher.classes[i]}[key]; });
		$("#teacher .page__bd #classesList").append(source);
    }
    // 手机号
    if(teacher.mobile!=null){
        $("#teacher .page__bd #contact div:first-child .weui-cell__bd").text(teacher.mobile)
    };
    // 邮件
    if(teacher.email!=null){
        $("#teacher .page__bd #contact div:nth-child(2) .weui-cell__bd").text(teacher.email);
    }
}
// 跳转至error页
function goErrorPage(){
    window.location.href="./state.html?state=1&info=1";
}
// 切换显示全部教师介绍
function switchAllTeacherInfo(){
    if(teacher.info.length>100){
        if($("#teacher .page__hd .page__desc").text()!=teacher.info){
            $("#teacher .page__hd .page__desc").text(teacher.info);
        }
        else{
            $("#teacher .page__hd .page__desc").text(teacher.info.slice(0,102)+"...");
        }
    }
}

