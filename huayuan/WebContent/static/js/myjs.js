$(function(){

    //nav
    $('nav ul li').hover(function(){
        $(this).addClass('current');
        $(this).children('.nav-cont').fadeIn();
    },function(){
        $(this).removeClass('current');
        $(this).children('.nav-cont').fadeOut();
    })


    // 图片放大
    $('.fd_img img').hover(function(){
        $(this).stop().animate({'width':'110%','height':'110%','margin-top':'-5%','margin-left':'-5%'},300);
    },function(){
        $(this).stop().animate({'width':'100%','height':'100%','margin-top':0,'margin-left':0},300);
    })
    
})
