<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" buffer="64kb" %>
<%@ include file="/inc/lib.inc.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="${f:l2s(userLocale)}">
<head>
	<title>ULTRALISK</title>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
	<meta http-equiv="imagetoolbar" content="no" />
	<meta http-equiv="pragma" content="no-cache" /> 
	<meta http-equiv="cache-control" content="no-cache, must-revalidate" />   
	<meta http-equiv="expires" content="0" />
	<link rel="stylesheet" type="text/css" href="./css/global-${f:l2s(userLocale)}.css" media="all" />	
	<script type="text/javascript" src="./js/jquery/jquery-1.3.2.min.js"></script>
	<script type="text/javascript" src="./js/jquery/jquery-ui-1.7.1.min.js"></script>
	<script type="text/javascript" src="./js/jquery/jquery-form.js"></script>
	<script type="text/javascript" src="./js/global.js"></script>
    <script type="text/javascript">
	    function getbasedir() {
	        return "<%=request.getContextPath()%>";
	    }
    </script>
</head>

<body>
<div id="wrap">

<div id="header">
	<div id="head-content">
		${curUser.adminId} <span style="color: #333333">&bull;</span> <a href="./login?method=logout"><fmt:message key="term.user.logout" /></a>
	</div>
</div>

<div id="navmenu">

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
		<a href="/agenda/"><span>Press</span></a> 
		<div class="subnav"> 
			<ul class="menu"> 
				<li><a href="/agenda/civil_rights/">Civil</a></li> 
				<li><a href="/agenda/defense/">Defense</a></li> 
				<li><a href="/agenda/disabilities/">Disabilities</a></li> 
				<li><a href="/agenda/economy/">Economy</a></li> 
				<li><a href="/agenda/education/">Education</a></li> 
				<li><a href="/agenda/energy_and_environment/">Energy &amp; Environment</a></li> 
				<li><a href="/agenda/ethics/">Ethics</a></li> 
				<li><a href="/agenda/family/">Family</a></li> 
			</ul> 
			<ul class="menu"> 
				<li><a href="/agenda/fiscal/">Fiscal</a></li> 
				<li><a href="/agenda/foreign_policy/">Foreign Policy</a></li> 
				<li><a href="/agenda/health_care/">Health Care</a></li> 
				<li><a href="/agenda/homeland_security/">Homeland Security</a></li> 
				<li><a href="/agenda/immigration/">Immigration</a></li> 
				<li><a href="/agenda/iraq/">Iraq</a></li> 
				<li><a href="/agenda/poverty/">Poverty</a></li> 
				<li><a href="/agenda/rural/">Rural</a></li> 
			</ul> 
			<ul class="menu"> 
				<li><a href="/agenda/seniors_and_social_security/">Seniors &amp; Social Security</a></li> 
				<li><a href="/agenda/service/">Service</a></li> 
				<li><a href="/agenda/taxes/">Taxes</a></li> 
				<li><a href="/agenda/technology/">Technology</a></li> 
				<li><a href="/agenda/urban_policy/">Urban Policy</a></li> 
				<li><a href="/agenda/veterans/">Veterans</a></li> 
				<li><a href="/agenda/women/">Women</a></li> 
				<li><a href="/agenda/additional/">Additional Issues</a></li> 
			</ul> 
	</div> 
    </li> 

    <li class="navitem"> 
		<a href="/administration/"><span>Privilege</span></a> 
		<div class="subnav"> 
			<ul class="menu"> 
				<li><a href="/administration/president_obama/">President Barack Obama</a></li> 
				<li><a href="/administration/vice_president_biden/">Vice President Joe Biden</a></li> 
				<li><a href="/administration/michelle_obama/">First Lady Michelle Obama</a></li> 
				<li><a href="/administration/jill_biden/">Dr. Jill Biden</a></li> 
			</ul> 
			<ul class="menu"> 
				<li><a href="/administration/cabinet/">The Cabinet</a></li> 
				<li><a href="/administration/staff/">White House Staff</a></li> 
				<li><a href="/administration/eop/">Executive Office of the President</a></li> 
			</ul> 
		</div> 
    </li> 

    <li class="navitem"> 
		<a href="/about/"><span>About us</span></a> 
		<div class="subnav"> 
			<ul class="menu"> 
				<li><a href="/about/history/">History</a></li> 
				<li><a href="/about/presidents/">Presidents</a></li> 
				<li><a href="/about/first_ladies/">First Ladies</a></li> 
				<li><a href="/about/oval_office/">The Oval Office</a></li> 
				<li><a href="/about/vp_residence/">Vice President's Residence &amp; Office</a></li> 
				<li><a href="/about/eeob/">Eisenhower Executive Office Building</a></li> 
			</ul> 
			<ul class="menu"> 
				<li><a href="/about/camp_david/">Camp David</a></li> 
				<li><a href="/about/air_force_one/">Air Force One</a></li> 
				<li><a href="/about/fellows/">White House Fellows</a></li> 
				<li><a href="/about/internships/">White House Internships</a></li> 
				<li><a href="/about/white_house_101/">White House 101</a></li> 
				<li><a href="/about/tours_and_events/">Tours &amp; Events</a></li> 
			</ul> 
		</div> 
    </li> 

    <li class="navitem"> 
		<a href="/our_government/"><span>Governament</span></a> 
		<div class="subnav"> 
			<ul class="menu"> 
				<li><a href="/our_government/executive_branch/">The Executive Branch</a></li> 
				<li><a href="/our_government/legislative_branch/">The Legislative Branch</a></li> 
				<li><a href="/our_government/judicial_branch/">The Judicial Branch</a></li> 
				<li><a href="/our_government/the_constitution/">The Constitution</a></li> 
			</ul> 
			<ul class="menu"> 
				<li><a href="/our_government/federal_agencies_and_commissions/">Federal Agencies &amp; Commissions</a></li> 
				<li><a href="/our_government/elections_and_voting/">Elections &amp; Voting</a></li> 
				<li><a href="/our_government/state_and_local_government/">State &amp; Local Government</a></li> 
				<li><a href="/our_government/resources/">Resources</a></li> 
			</ul> 
		</div> 
    </li>
     
    <li class="navitem"> 
		<a href="/contact/"><span>Contact Us</span></a> 
    </li> 
</ul>

</div>

<div id="container" class="clearfix">
