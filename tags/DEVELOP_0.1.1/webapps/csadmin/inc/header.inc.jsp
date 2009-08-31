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
	<script type="text/javascript" src="./js/jquery/jquery-ui-1.7.1.min.js"></script>
	<script type="text/javascript" src="./js/jquery/jquery-layout.min.js"></script>
	<script type="text/javascript" src="./js/global.js"></script>
    <script type="text/javascript">
	    function getbasedir() {
	        return "<%=request.getContextPath()%>";
	    }
	    
		$(document).ready( function() {

			var defaultLayout, bodyNorth, bodySouth, bodyWest, bodyCenter, bodyEast, siderNorth, siderCenter;

			defaultLayout = {
				fxName: 'slide',
				fxSpeed: 'slow',
				spacing_open: 10,
				spacing_closed: 10,
				closable: false,
				resizable: false
			};

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

			bodyWest = {
				paneSelector: '#sider',
				resizable: true,
				size: 250			
			}; 

			bodyEast = {
				paneSelector: '#east',
				closable: true,
				size: 200
			};

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

	<div id="banner">
		${curUser.adminId} <span style="color: #333333">&bull;</span> <a href="./login?method=logout"><fmt:message key="term.user.logout" /></a>
	</div>
	
	<ul id="nav"> 
		<li class="navitem">
			<a href="./admin.do"><span><fmt:message key="entity.admin" /></span></a>
			<div class="subnav">
				<ul class="menu">
					<li><a href="./admin.do?method=adminPreCreate"><fmt:message key="term.admin.create" /></a></li>
					<li><a href="/weekly_address/"><fmt:message key="term.admin.create" /></a></li>
					<li><a href="/slideshows/">Slideshows</a></li>
					<li><a href="/briefing_room/PressBriefings/">Press Briefings</a></li>
					<li><a href="/briefing_room/OfficialStatements/">Official Statements</a></li>
				</ul>
				<ul class="menu"><li><a href="/briefing_room/PressReleases/">Presidentia</a></li>
					<li><a href="/briefing_room/PresidentialActions/">Presidential Actions</a></li>
					<li><a href="/briefing_room/nominations_and_appointments/">Nominations &amp; Appointments</a></li>
				</ul>
			</div> 
	    </li> 
	
	    <li class="navitem"> 
			<a href="/contact/"><span>Contact Us</span></a> 
	    </li> 
	</ul>

</div>
