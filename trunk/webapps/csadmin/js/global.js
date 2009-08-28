
$(document).ready(function() {
	
	initElements();
	initAccordion();

});


/**
 *  common methods need by web appllcation. 
 */
 
submitForm = function(obj, methodName) {
    obj.method.value = methodName
    obj.submit();
};

confirmSubmit = function(obj, methodName, mes){
    if(window.confirm(mes)){
        obj.method.value = methodName;
        obj.submit();
    }
}
 
 
initElements = function() {
	
	/*-- init nav --*/
	var beforeWidth = 0;
	var navLen = $('.navitem').length;
	
	for (var i = 0; i < navLen; i ++) {
		var navItem = $('.navitem').eq(i);
		$('.subnav').eq(i).css('left', beforeWidth);
		beforeWidth = beforeWidth + navItem.width();
	}
	
	/*-- init button --*/
	$(':input.bttn, :input.sbttn, :input.mbttn,:input.navbttn, a.button').each(function() {
		
		$(this).onfocus = function() { this.blur() };
    	
    	if($(this).attr('class') == 'sbttn' || $(this).attr('class') == 'mbttn') {
    		$(this).attr('disabled', 'disabled');
    	}		
		
		$(this).addClass('ui-state-default ui-corner-all').hover(
      		function () { $(this).addClass('ui-state-hover'); }, 
      		function () { $(this).removeClass('ui-state-hover'); }
    	);
    	
	});		
	
};

initAccordion = function() {
	
	var userAgent = navigator.userAgent.toLowerCase();
	jQuery.browser.msie7 = jQuery.browser.msie && /msie 7\.0/i.test(userAgent) && !/msie 8\.0/i.test(userAgent);
	
	if (jQuery.browser.msie7) {

		$('#side-content').css('padding-top', '10px');
			
		$('.navitem').each(function() {
			
			$(this).hover(
				function () {
					$('#side-accordion > .ui-accordion-header').css('z-index', '-1'); 
					$('#side-accordion > .ui-accordion-content').css('z-index', '-1');
				},
		    	function () { 
					$('#side-accordion > .ui-accordion-header').css('z-index', 'auto'); 
					$('#side-accordion > .ui-accordion-content').css('z-index', 'auto'); 
			    }	
			);
		});
	}	
	
}

initDataTable = function(method, text) {
	
	// initiation detail dialog
	if ($('#dialog').length > 0) {
		
		var buttons = {};
		buttons[text] = function() { $(this).dialog("close");};
		
		$('#dialog').dialog({
			autoOpen: false, resizable: false,
			bgiframe: true,	modal: true,
			width: 804,	height: 480,
			buttons: buttons
		});
	}
	
	// initiation data table height
	
	// initiation data table
	if ($('TABLE.data').length > 0) {
		
		var url = $('#listForm').attr('action');
		var rows = $('TABLE.data tbody > tr:has(td)');
		var checkboxes = $('TABLE.data :checkbox');
		
		$('#check-all').click(function() {
			
			if ($(this).attr('checked')) {
				checkboxes.attr('checked', true);
				rows.each(function() { $(this).addClass('fix'); });
			} else {
				checkboxes.attr('checked', false);
				rows.each(function() { $(this).removeClass('fix'); });
			}
			
			update_button_status();				
			
		});
		
		rows.each(function() {
			
			var row = $(this);
			var checkbox = row.find('input[type="checkbox"]');
			var para = $(this).find('input[type="checkbox"]').val();
			
			checkbox.click(function() { fix_checked_style(row, checkbox); });
			
			row.find('td:first').click(function() { fix_checked_style(row, checkbox); });
			
			row.dblclick(function() {
				$('#dialog').dialog('open');					
				$("#dialog").load(url, 'method=' + method + '&chk_' + para + '=' + para);
			});		
			
			row.hover (
			    function () { $(this).addClass('hover'); }, 
	      		function () { $(this).removeClass('hover'); }	
			);
			
		});
		
	}
	
};

fix_checked_style = function(row, checkbox) {
	
	if (!checkbox.attr('checked')) {
		checkbox.attr('checked', true);
		row.addClass('fix');					
	} else {
		checkbox.attr('checked', false);
		$('#check-all').attr('checked', false);
		row.removeClass('fix');
	}
	
	update_button_status();	
	
};

update_button_status = function() {
	
	var i = 0;
	
	$('TABLE.data :checkbox').each(function() {
		if($(this).attr('checked')) i++;
	});
	
	$(':input.sbttn, :input.mbttn').each(function() {
		$(this).attr('disabled', 'disabled');
	});
	
	if(i > 0) {	
		$(':input.mbttn').each(function() {
			$(this).removeAttr('disabled');
		});
	}
	
	if(i == 1) {	
		$(':input.sbttn').each(function() {
			$(this).removeAttr('disabled');
		});
	}	

};

toggleSearch = function(id) {
	
	$('#' + id).toggleClass('ui-state-active');
	
	if($.browser.safari) {
		$('#searchbar').slideToggleHorizontal();
	} else {
		$('#searchbar').animate({width: 'toggle'}, 'fast'); 
	}
}


/**
 *  Self defined jQuery (effect, method, event, .etc)
 */
jQuery.fn.extend({
	  
	slideRight: function() {    
		this.each(function() {
			$(this).animate({width: 'show'}, 'fast');    
		});  
	},  
	
	slideLeft: function() {
		this.each(function() {
			$(this).hide(75);  // for chorme don't support animate hide perfertly.
//			$(this).animate({width: 'hide'}, 'fast');  
		});  
	},  
	
	slideToggleHorizontal: function() { 
		this.each(function() {      
			var el = $(this);      
			if (el.css('display') == 'none') {       
				 el.slideRight();      
			} else {        
				el.slideLeft();
			}    
		});  
	}

});


