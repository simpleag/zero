var teacher = {
    openid: null,
    name: null,
    info: null,
    classes: [],
    mobile: null,
    email: null
};

$(document).ready(function(){
    initPage();
});

function getTeacherInfo(){
    teacher.openid = "123456";
    teacher.name = "杨枨";
    teacher.info = "曾担任浙江大学计算机学院本科教学指导委员会委员、软件工程专业课程组组长、校内实习及毕业设计基地负责人；浙江大学计算机学院工会副主席。2012年9月调入城市学院计算分院，现任计算机科学与工程学系副主任。";
    teacher.classes = ["软件工程导论","软件质量保证","软件需求分析与设计"];
    teacher.mobile = 18368893105;
    teacher.email = "lalalaleo.hu@hotmail.com";
}

function initPage(){
    getTeacherInfo();
    var html = document.getElementById("classCell").innerHTML;
    $("#teacher .page__hd .page__title").text(teacher.name);
    $("#teacher .page__hd .page__desc").text(teacher.info);
    if(teacher.classes.length>0) $(".page .page__bd #noClass").css("display","none");
    for(var i in teacher.classes){
        var source = html.replace(reg, function (node, key) { return { 'className': teacher.classes[i]}[key]; });
		$(".page .page__bd #classesList").append(source);
    }
    if(teacher.mobile!=null){
        $("#teacher .page__bd #contact div:first-child .weui-cell__bd").text(teacher.mobile)
    };
    if(teacher.mobile!=null){
        $("#teacher .page__bd #contact div:nth-child(2) .weui-cell__bd").text(teacher.email);
    }
}