jQuery(document).ready(
		function() {

			// CSS tweaks
			jQuery('#header #nav li:last').addClass('nobg');
			jQuery('.block_head ul').each(function() {
				jQuery('li:first', this).addClass('nobg');
			});

			// Tabs
			jQuery(".tab_content").hide();
			jQuery("ul.tabs li:first-child").addClass("active").show();
			jQuery(".block").find(".tab_content:first").show();

			jQuery("ul.tabs li").click(
					function() {
						closeAllDialogs();
						jQuery(this).parent().find('li').removeClass("active");
						jQuery(this).addClass("active");
						jQuery(this).parents('.block').find(".tab_content")
								.hide();

						var activeTab = jQuery(this).find("a").attr("href");
						jQuery(activeTab).show();

						// refresh visualize for IE
						jQuery(activeTab).find('.visualize').trigger(
								'visualizeRefresh');

						return false;
					});

			// Sidebar Tabs
			jQuery(".sidebar_content").hide();

			if (window.location.hash && window.location.hash.match('sb')) {

				jQuery("ul.sidemenu li a[href=" + window.location.hash + "]")
						.parent().addClass("active").show();
				jQuery(".block .sidebar_content#" + window.location.hash)
						.show();
			} else {

				jQuery("ul.sidemenu li:first-child").addClass("active").show();
				jQuery(".block .sidebar_content:first").show();
			}

			jQuery("ul.sidemenu li").click(
					function() {
						closeAllDialogs();
						var activeTab = jQuery(this).find("a").attr("href");
						window.location.hash = activeTab;
						if( activeTab  == "#end")
							jQuery('#saveButton').hide();
						else
							jQuery('#saveButton').show();

						jQuery(this).parent().find('li').removeClass("active");
						jQuery(this).addClass("active");
						jQuery(this).parents('.block').find(".sidebar_content")
								.hide();
						jQuery(activeTab).show();
						return false;
					});

			// Block search
			jQuery('.block .block_head form .text').bind('click', function() {
				jQuery(this).attr('value', '');
			});

			// Navigation dropdown fix for IE6
			if (jQuery.browser.version.substr(0, 1) < 7) {
				jQuery('#header #nav li').hover(

				function() {
					jQuery(this).addClass('iehover');
				}, function() {
					jQuery(this).removeClass('iehover');
				});
			}

		});