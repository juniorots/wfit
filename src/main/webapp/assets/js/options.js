$(function(){
	Options.init();
	Scroll.init();
});

/* 
	Options
*/
Options = {
	init: function() {
		$('.options .more').click(function() {
			var more = $(this);
			var options = more.parent();

			if (more.hasClass('closed')) {
				// Destroy fullpage
				FullpageSwiper.destroy();

				more.find('.fa').removeClass('fa-cog');
				more.find('.fa').addClass('fa-times');
				more.removeClass('closed');
				$('#menu').hide();
				setTimeout(function() {
					Scroll.scroll('introPageContent');
				}, 300);
			} else {
				more.find('.fa').removeClass('fa-times');
				more.find('.fa').addClass('fa-cog');
				more.addClass('closed');
				$('#menu').show();

				FullpageSwiper.init();
			}

			$('.introPage.pages').slideToggle(300);
		});

		// Dim & pattern switcher
		$('#dim-switcher').change(function() {
			$('.options .more').click();
			$('.bgWrap').toggleClass('dimBg');
		});
		
		$('#pattern-switcher').change(function() {
			$('.options .more').click();
			$('.bgWrap').toggleClass('patternBg');
		});

		// Init fullpage swiper on options scroll
		$(window).scroll(function (event) {
			if (!$('.options .more').hasClass('closed') && $('.js-fullpage-swiper').length) {
				var scroll = $(window).scrollTop();
				if ($(window).scrollTop() > $('#intro').position().top) {
					$('html, body').css({
						'overflow': 'hidden',
						'height': '100%'
					});
					
					$('.introPage.pages').hide();
					$('.options .more').find('.fa').removeClass('fa-times');
					$('.options .more').find('.fa').addClass('fa-cog');
					$('.options .more').addClass('closed');
					setTimeout(function() {
						FullpageSwiper.init();
					}, 300);
				}
			}
		});
	}
}

Scroll = {
	init: function() {
		$('.js-scroll').click(function(evt) {
			evt.preventDefault();
			var arr = $(this).attr('href').split('#');
			if (arr[1]) {
				var section = arr[1];
				Scroll.scroll(section);
			}
		});
	},
	scroll: function(section) {
		$('html, body').animate({
			scrollTop: $("#" + section).offset().top - 80
		}, 500);
	}
}