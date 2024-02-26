 $(document).ready(function () {
         $(window).scroll(function () {
         
        if ($(window).scrollTop() >= 10) {
                 $('header').addClass('header-sticky animated');
             }
             else {
               $('header').removeClass('header-sticky animated');
             }
           
         });



    $('#mob_menu').on( 'click', function(event) {
    $('.sidemenu').toggleClass('show');
              $('.sidebar_bg').toggle();
         });
     $('.sidebar_bg').on( 'click', function(event) {
    $('.sidemenu').toggleClass('show');
              $('.sidebar_bg').toggle();
         });
$('.faclose').on( 'click', function(event) {
    $('.sidemenu').toggleClass('show');
              $('.sidebar_bg').toggle();
         });
         });



   $(document).ready(function () {
           $(window).scroll(function() {    
      var scroll = $(window).scrollTop();
      if (scroll >= 100) {
         $(".go-top").show();
      } else {
         $(".go-top").hide();
      }
   });
            $(".go-top").on('click', function () {
                $("html, body").animate({ scrollTop: 0 }, 600);
            });
        });


   $('.search-nav-button').on( 'click', function(event) {
             $('#search_div').toggle();
             $('#close_search').toggle();
         });
    $('.search-close').on( 'click', function(event) {
             $('#search_div').toggle();
             $('#close_search').toggle();
         });
    $('#close_search').on( 'click', function(event) {
             $('#search_div').toggle();
             $('#close_search').toggle();
         });

           




