<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" buffer="64kb" %>
<%@ include file="/inc/lib.inc.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="${f:l2s(userLocale)}">
<head>
	<title>TOUKI</title>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
	<meta http-equiv="imagetoolbar" content="no" />
	<meta http-equiv="pragma" content="no-cache" /> 
	<meta http-equiv="cache-control" content="no-cache, must-revalidate" />   
	<meta http-equiv="expires" content="0" />
	<link rel="stylesheet" type="text/css" href="./css/global-${f:l2s(userLocale)}.css" media="all" />	
	<script type="text/javascript" src="./js/jquery/jquery-1.3.2.min.js"></script>
	<script type="text/javascript" src="./js/jquery/jquery-ui-1.7.1.custom.min.js"></script>
	<script type="text/javascript" src="./js/jquery/jquery-layout.min.js"></script>	
	<script type="text/javascript" src="./js/global.js"></script>
    <script type="text/javascript">
		$(document).ready( function() {

			var defaultLayout, bodyNorth, bodySouth, bodyWest, bodyCenter, bodyEast, siderNorth, siderCenter;

			if ($('#container > center-div').length > 0 && $('#container > loginForm').length > 0) {
				defaultLayout = {
						fxName: 'slide',
						fxSpeed: 'slow',
						spacing_open: 10,
						spacing_closed: 10,
						closable: false,
						resizable: false
					};
			} else {
				defaultLayout = {
						fxName: 'slide',
						fxSpeed: 'slow',
						spacing_open: 0,
						spacing_closed: 0,
						closable: false,
						resizable: false
					};
			}


			bodyNorth = {
				paneSelector: '#header',
				size: 108
			};

			if ($('#footer').length > 0) {	
				bodySouth = {
					paneSelector: '#footer',
					size: 35
				};
			} else {
				bodySouth = false;
			}

			if ($('sider').length > 0) {
				bodyWest = {
					paneSelector: '#sider',
					resizable: true,
					size: 250			
				}; 
			} else {
				bodyWest = false;
			}

			bodyEast = false;

			bodyCenter = {
				paneSelector: '#container'
			};

			if ($('#addressbar').length > 0) {
				siderNorth = {
					paneSelector: '#addressbar',
					size: 38
				};
			} else {
				siderNorth = false;
			}

			if ($('#left-center').length > 0) {
				siderCenter = {
					paneSelector: '#left-center'
				};
			} else {
				siderCenter = false;
			}		

			var bodyOptions = {
				defaults: defaultLayout,
				north: bodyNorth,
				east: bodyEast,  
				south: bodySouth,
				west: bodyWest, 
				center: bodyCenter
			};

			var siderOptions = {
				north: siderNorth,
				center: siderCenter
			};
			
			$("body").layout(bodyOptions);

			if ($('sider').length > 0) {
				$('sider').layout(siderOptions);
			}
			
			if ($('#side-accordion').length > 0) {
				
				$('#side-accordion').accordion({
					fillSpace: true
				});	
			}
		});
			    
    </script>	
</head>

<body>

<div id="header">

<div id="navmenu">
	<ul id="nav"></ul>
</div>

</div>