var teacher = {
    openid: null,
    name: null,
    info: null,
    classes: [],
    mobile: null,
    email: null
};


$(document).ready(function(){
    getJSONObject();
});

function getJSONObject(){
   $.ajax({
        url: "./content/assets/json/test_teacher.json",
        type: "GET",
        dataType: "JSON",
        success: function(data) {
                        getTeacherInfo(data);
                    },
        error: function() {alert("error");}
    });
}

function getTeacherInfo(JSONObject){
    teacher.openid = JSONObject.openid;
    teacher.name = JSONObject.name;
    teacher.info = JSONObject.info;
    teacher.classes = JSONObject.classes;
    teacher.mobile = JSONObject.mobile;
    teacher.email = JSONObject.email;
    if(teacher.openid==null || teacher.name==null) goErrorPage();
    else initPage();
}

function goErrorPage(){
    window.location.href="./state.html?state=1&info=1";
}

function initPage(){
    var html = document.getElementById("classCell").innerHTML;
    // 教师姓名
    $("#teacher .page__hd .page__title").text(teacher.name);
    // 教师介绍
    if(teacher.info!=null){
        $("#teacher .page__hd .page__desc").text(teacher.info);
    }
    // 教授课程
    if(teacher.classes.length>0) $(".page .page__bd #noClass").css("display","none");
    for(var i in teacher.classes){
        var source = html.replace(reg, function (node, key) { return { 'className': teacher.classes[i]}[key]; });
		$(".page .page__bd #classesList").append(source);
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