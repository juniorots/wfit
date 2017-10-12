
$("document").ready(function() {


	/*============================================
	NAVIGATION and SIZING
	==============================================*/

	$('body').scrollspy({ target: '.navigation' });

	// Remove min-height on iOS after slideshow initialization
	var iOS = navigator.userAgent.match(/(iPad|iPhone|iPod)/g) ? true : false;
	if(iOS){
    	function iosVhHeightBug() {
        	var height = $(window).height();
        	$(".slidepage").css('min-height', height);
        	$('.vertical-center').css('margin-top', height/2);
    	}
    	iosVhHeightBug();
    	$(window).bind('resize', iosVhHeightBug);
	}

	$(document).on('click', '.scroll', function(evt) {
		evt.preventDefault();
		var arr = $(this).attr('href').split('#');
		if (arr[1]) {
			var section = arr[1];
			$('html, body').animate({
	        	scrollTop: $("#" + section).offset().top
	        }, 1000);
		}

		// Close navigation menu on item click for mobile devices
		if ($('.visible-xs').is(':visible') && $('.navigation').is(':visible')) {
			$('#menuswitcher').click();
		}

		if ($(this).find('.fa-arrow-up').length) {
			$.fn.fullpage.moveTo(1,0);
		} else {
			if ($.fn.fullpage) {
				$.fn.fullpage.moveSectionDown();
			}
		}

		if ($(this).parents('#asideBullets').length) {
			$(this).dequeue(function() {
				$(this).addClass('active');
				$(this).parent().siblings().find('a').removeClass('active');
			});
		}
	});

	/*============================================
	MENU
	==============================================*/
	$('#menuswitcher').click(function(el) {
		el.preventDefault();
		var navigation = $('.navigation');
		var buttonspan = $('#menuswitcher span');
		var menu = $('#menu');
		if (navigation.is(":visible")) {
			// navigation.hide({duration: 500, easign: 'easeOutExpo'});
			navigation.animate({
				height:"toggle"
			}, {duration: 300});
			buttonspan.removeClass('fa-times');
			buttonspan.addClass('fa-bars');
			menu.removeClass('solid-background');
		} else {
			buttonspan.removeClass('fa-bars');
			buttonspan.addClass('fa-times');
			menu.addClass('solid-background');
			// navigation.show({duration: 500, easign: 'easeOutExpo', direction: "down"})
			navigation.animate({
				height:"toggle"
			}, {duration: 300});


		}
	});


	/*============================================
	Gallery
	==============================================*/

	$('#gallery-container').css({visibility:'visible'});

	// Initialize shuffle plugin
	var $grid = $('.gallery-items');

	$grid.shuffle({
		itemSelector: '.gallery-item'
	});

	// Reshuffle when user clicks a filter item
	$('#filter-gallery a').click(function (e) {
		e.preventDefault();

		// Set active class
		$('#filter-gallery li').removeClass('active');
		$(this).parent().addClass('active');

		// Get group name from clicked item
		var groupName = $(this).attr('data-group');

		// Reshuffle grid
		$grid.shuffle('shuffle', groupName );
	});

	$('.gallery-item').click(function(e) {
		e.preventDefault();

		var imageUrl = $(this).attr('data-image-url');

		var img = $("<img />").attr('src', imageUrl).attr('class', 'img-responsive').load(function() {
			if (this.complete) {
				$('#modalImage .modal-body').html(img);
			}
		}).error(function() {
			$('#modalImage .modal-body').html('<div class="alert alert-danger">Error loading image.</div>');
		});
	});

	/*============================================
	WOW animated content
	==============================================*/
	var wow = new WOW({
		mobile: false,
		live: true
	});
	wow.init();

	/*============================================
	OWL Carousel
	==============================================*/
	$("#owl-example").owlCarousel({
		items: 3,
		navigation: true,
		navigationText: ['',''],
		rewindNav: false,
		slideSpeed : 300,
		paginationSpeed: 300
	});

	/*============================================
	Contact form submit
	==============================================*/
	$('.js-contact-form').on('submit', function(e) {
		e.preventDefault();
		var url = $(this).attr('action');
		var name = $(this).find('input.name').val();
		var email = $(this).find('input.email').val();
		var message = $(this).find('textarea.message').val();
		var firstName = name;

		// Check for white space in name for Success/Fail message
		if (firstName.indexOf(' ') >= 0) {
			firstName = name.split(' ').slice(0, -1).join(' ');
		}

		$.ajax({
			url: url,
			type: 'POST',
			data: {
				name: name,
				email: email,
				message: message
			},
			cache: false,
			success: function() {
				// Success message
				$('#msgInfo').html("<div class='alert alert-success'>");
				$('#msgInfo > .alert-success').html("<button type='button' class='close' data-dismiss='alert' aria-hidden='true'>&times;")
				.append("</button>");
				$('#msgInfo > .alert-success')
				.append("<strong>Your message has been sent. </strong>");
				$('#msgInfo > .alert-success')
				.append('</div>');
			},
			error: function() {
				// Fail message
				$('#msgInfo').html("<div class='alert alert-danger'>");
				$('#msgInfo > .alert-danger').html("<button type='button' class='close' data-dismiss='alert' aria-hidden='true'>&times;")
				.append("</button>");
				$('#msgInfo > .alert-danger').append("<strong>Sorry " + firstName + ", it seems that my mail server is not responding. Please try again later!");
				$('#msgInfo > .alert-danger').append('</div>');
			},
			complete: function() {
				// Clear fields
				$('.js-contact-form').trigger("reset");
			}
		});
	});

	/* Clear message info box */
	$('.js-contact-form').find('input, textarea').focus(function() {
		$('#msgInfo').html('');
	});

	/*============================================
	Fullpage swiper
	==============================================*/
	FullpageSwiper.init();
	SideBullets.init();
});

FullpageSwiper = {
	init: function() {
		if ($('.js-fullpage-swiper').length && $('.visible-xs').is(':hidden')) {
			$('.js-fullpage-swiper').fullpage({
				navigation: true,
				navigationPosition: 'right',
				responsiveWidth: 768,
				afterLoad : function (anchorLink, index){
					$(this).find('.wow').removeAttr('style').addClass('animated');
				}
			});
		}
	},

	destroy: function() {
		if ($.fn.fullpage) {
			$.fn.fullpage.destroy('all');
		}
	}
}

SideBullets = {
	init: function() {
		if ($('.slidepage').length && $('.visible-xs').is(':hidden') && !$('.js-fullpage-swiper').length) {
			$('body').append('<div id="asideBullets" class="right"><ul></ul></div>');
			
			$('.slidepage').each(function() {
				var section = $(this).attr('id');
				$('#asideBullets ul').append('<li data-section="' + section +'"><a href="#' + section + '" class="scroll"><span></span></a></li>');
			});

			bulletsHeight = parseInt($('#asideBullets ul').height() / 2);
			$('#asideBullets ul').css('margin-top', -bulletsHeight);

			$('.slidepage').waypoint(function(direction) {
				if (direction === 'down') {
					var section = $(this.element).attr('id');
					SideBullets.work(section);
				}
			}, { offset: 0 });

			$('.slidepage').waypoint(function(direction) {
				if (direction === 'up') {
					var section = $(this.element).attr('id');
					SideBullets.work(section);
				}
			}, {
				offset: -1
			});
		}
	},
	work: function(section) {
		$('#asideBullets li a').removeClass('active');
		$('#asideBullets li[data-section="' + section + '"] a').addClass('active');
	}
}