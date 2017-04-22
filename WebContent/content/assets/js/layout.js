var reg = new RegExp("\\[([^\\[\\]]*?)\\]", 'igm');
$(document).ready(function(){
    $(".page").css({
        "height" : $(window).height()-39+"px"
    });
});