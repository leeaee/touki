
$(document).ready(function() {
	
	initAccordion();
	initElements();	
	initLayout();
	
});


/**
 *  common methods need by web appllcation. 
 */
 
submitForm = function(obj, methodName) {
    obj.method.value = methodName;
    obj.submit();
};

confirmSubmit = function(obj, methodName, mes){
    if(window.confirm(mes)){
        obj.method.value = methodName;
        obj.submit();
    }
};
 

/**
 *  Initi methods for web appllcation. 
 */

initLayout = function() {
	
	var bodyDefault, bodyNorth, bodySouth, bodyWest, bodyCenter;
	var containerDefault, containerNorth, containerSouth, containerEast, containerCenter;
	var upperDefault, upperWest, upperCenter;
	
	/*-- init body options --*/
	if ($('#container > #center-div').length > 0) {
		bodyDefault = {
			spacing_open: 0,
			spacing_closed: 0,
			closable: false,
			resizable: false
		};
	} else {
		bodyDefault = {
			spacing_open: 10,
			spacing_closed: 10,
			closable: false,
			resizable: false
		};
	}

	bodyNorth = {
		paneSelector: '#header',
		size: 108
	};

	bodySouth = {
		paneSelector: '#footer',
		size: 40
	};

	if ($('#sider').length > 0 && $('#accordion').length > 0) {
		bodyWest = {
			paneSelector: '#sider',
			onresize: function() { $('#accordion').accordion('resize'); },
			onopen:	function () { $("#accordion").accordion('resize'); },
			size: 200			
		}; 
	} else {
		bodyWest = false;
	}

	bodyCenter = {
		paneSelector: '#container'
	};
	
	var bodyOptions = {
		defaults: bodyDefault,
		north: bodyNorth,
		south: bodySouth,
		east: false,  		
		west: bodyWest, 
		center: bodyCenter
	};	

	$('body').layout(bodyOptions);	
	
	/*-- init container options --*/
	if ($('#container').length > 0) {
		
		containerDefault = {
			fxName: 'slide',
			fxSpeed: 'slow',		
			spacing_open: 5,
			spacing_closed: 5,
			closable: false,
			resizable: false
		};
			
		if ($('#upper').length > 0) {
			containerNorth = {
				paneSelector: '#upper',
				size: 38
			};
		} else {
			containerNorth = false;
		}
		
		if ($('#downer').length > 0) {
			containerSouth = {
				paneSelector: '#downer',
				size: 38
			};
		} else {
			containerSouth = false;
		}
		
		if ($('#search').length > 0) {
			containerEast = {
				paneSelector: '#search',
				closable: true,
				spacing_open: 5,
				spacing_close: 5,
				togglerLength_open: 150,
				togglerLength_closed: 150,
				size: 173
			};
		} else {
			containerEast = false;
		}
		
		if ($('#mainer').length > 0) {
			containerCenter = {
				paneSelector: '#mainer'
			};
		} else {
			containerCenter = false;
		}	
	
		var containerOptions = {
			defaults: containerDefault,
			north: containerNorth,
			south: containerSouth,
			east: containerEast,  		
			west: false, 
			center: containerCenter
		};
		
		if (containerCenter != false) {
			$('#container').layout(containerOptions);
		}
	}
	
	/*-- init upper div options --*/
	if ($('#upper').length > 0) {
		
		upperDefault = {
			spacing_open: 5,
			spacing_closed: 5,
			closable: false,
			resizable: false	
		};
		
		if ($('#upper-left').length > 0) {
			upperWest = {
				paneSelector: '#upper-left',
				size: 220
			};
		} else {
			upperWest = false;
		}
		
		if ($('#upper-main').length > 0) {
			upperCenter = {
				paneSelector: '#upper-main'
			};
		} else {
			upperCenter = false;
		}	
		
		var upperOptions = {
				defaults: upperDefault,
				north: false,
				south: false,
				east: false,  		
				west: upperWest, 
				center: upperCenter
			};
		
		if (upperCenter != false) {
			$('#upper').layout(upperOptions);
		}
	}			

	/*-- resized accordion --*/
	if ($('#accordion').length > 0) {
		$('#accordion').accordion('resize');
	}		
	
};	

initElements = function() {
	
	/*-- init nav --*/
	var beforeWidth = 0;
	var navLen = $('.navitem').length;
	
	for (var i = 0; i < navLen; i ++) {
		var navItem = $('.navitem').eq(i);
		$('.subnav').eq(i).css('left', beforeWidth);
		beforeWidth = beforeWidth + navItem.width();
	}
	
	$('.navitem').each(function() {
		
		$(this).hover(
		
			function () {
				$('#sider').css('z-index', '-1'); 
				$('#container').css('z-index', '-1');
			},
			
	    	function () { 
				$('#sider').css('z-index', 'auto'); 
				$('#container').css('z-index', 'auto'); 
		    }	
		);
		
	});	
	
	/*-- init button --*/
	$(':input.bttn, :input.sbttn, :input.mbttn,:input.navbttn, a.button').each(function() {
		
		$(this).onfocus = function() { this.blur(); };
    	
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
	
	if ($('#accordion').length > 0) {
		
		$('#accordion').accordion({
			fillSpace: true
		});	
	}
};

initDataTable = function(method, text) {
	
	// initiation detail dialog
	if ($('#dialog').length > 0) {
		
		var buttons = {};
		buttons[text] = function() { $(this).dialog('close'); };
		
		$('#dialog').dialog({
			autoOpen: false, resizable: false,
			bgiframe: true,	modal: true,
			width: 804,	height: 480,
			buttons: buttons
		});
	}
	
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
				
				$('#dialog').text('');
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


