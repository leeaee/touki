<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" buffer="64kb" %>
<%@ include file="./lib.inc.jsp" %>
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
	<script type="text/javascript" src="./js/jquery/jquery.min.js"></script>
	<script type="text/javascript" src="./js/jquery/jquery-ui.min.js"></script>
	<script type="text/javascript" src="./js/jquery/jquery-layout.min.js"></script>
	<script type="text/javascript" src="./js/global.js"></script>
    <script type="text/javascript">
	    function getbasedir() {
	        return "<%=request.getContextPath()%>";
	    }
    </script>
</head>

<body>

<div id="header">

	<div id="banner">
		${adminName} <span style="color: #333333">&bull;</span> <a href="./j_spring_security_logout"><fmt:message key="term.user.logout" /></a>
	</div>
	
	<ul id="nav"> 
	
		<li class="navitem">
			<a href="./admin"><span><fmt:message key="entity.admin" /></span></a>
			<div class="subnav">
				<ul class="menu">
					<li><a href="./admin?method=adminPreCreate"><fmt:message key="term.admin.create" /></a></li>
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
			<a href="./admin"><span><fmt:message key="entity.admin" /></span></a>
			<div class="subnav">
				<ul class="menu">
					<li><a href="./admin?method=adminPreCreate"><fmt:message key="term.admin.create" /></a></li>
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
			<a href="./admin"><span><fmt:message key="entity.admin" /></span></a>
			<div class="subnav">
				<ul class="menu">
					<li><a href="./admin?method=adminPreCreate"><fmt:message key="term.admin.create" /></a></li>
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
