<%--
- menu.jsp
-
- Copyright (C) 2012-2023 Rafael Corchuelo.
-
- In keeping with the traditional purpose of furthering education and research, it is
- the policy of the copyright owner to permit non-commercial use and redistribution of
- this software. It has been tested carefully, but it is not guaranteed for any particular
- purposes.  The copyright owner does not offer any warranties or representations, nor do
- they accept any liabilities with respect to them.
--%>

<%@page language="java" import="acme.framework.helpers.PrincipalHelper"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:menu-bar code="master.menu.home">
	<acme:menu-left>
			
		<!-- ANY LIST COURSES -->
		<acme:menu-option code="master.menu.courses">
			<acme:menu-suboption code="master.menu.course.list" action="/any/course/list"/>
		</acme:menu-option>
		
		<!-- AUTHENTICATED -->
		<acme:menu-option code="master.menu.authenticated" access="isAuthenticated()">
			<acme:menu-suboption code="master.menu.authenticated.bulletin.list" action="/authenticated/bulletin/list"/>
			<acme:menu-suboption code="master.menu.authenticated.tutorial" action="/authenticated/tutorial/list"/>
			<acme:menu-suboption code="master.menu.authenticated.practicum" action="/authenticated/practicum/list"/>
			<acme:menu-suboption code="master.menu.authenticated.note" action="/authenticated/note/list"/>
      		<acme:menu-suboption code="master.menu.authenticated.offer" action="/authenticated/offer/list"/>
      		<acme:menu-suboption code="master.menu.authenticated.audit.list" action="/authenticated/audit/list"/>
      		
		</acme:menu-option>
		
		<!-- LECTURER -->
		<acme:menu-option code="master.menu.lecturer" access="hasRole('Lecturer')">
			<acme:menu-suboption code="master.menu.lecturer.dashboard" action="/lecturer/lecturer-dashboard/show"/>
			<acme:menu-separator/>
			<acme:menu-suboption code="master.menu.lecturer.lecture.list" action="/lecturer/lecture/list"/>
			<acme:menu-suboption code="master.menu.lecturer.course.list" action="/lecturer/course/list"/>
		</acme:menu-option>

		<!-- AUDITOR -->
		<acme:menu-option code="master.menu.auditor" access="hasRole('Auditor')">
			<acme:menu-suboption code="master.menu.auditor.dashboard" action="/auditor/auditor-dashboard/show"/>
			<acme:menu-separator/>

			<acme:menu-suboption code="master.menu.auditor.audit.list" action="/auditor/audit/list"/>
		</acme:menu-option>
		
		<!-- ANONYMOUS -->
		<acme:menu-option code="master.menu.anonymous" access="isAnonymous()">
			<acme:menu-suboption code="47546590: Reina Munoz, Jose Antonio" action="https://www.youtube.com/"/>
			<acme:menu-suboption code="77938490: Parra Mendez, Pablo" action="https://sevillafc.es/"/>
			<acme:menu-suboption code="20605668: Rojas Romero, Jose Joaquin" action="https://www.artistapirata.com/"/>
			<acme:menu-suboption code="45388453: Ybarra Manrique, Miguel" action="https://open.spotify.com/?"/>
			<acme:menu-suboption code="29580039: Albalat Ortiz, Samuel" action="https://cityofmist.co/"/>
		</acme:menu-option>

		<!-- ANY LIST PEEPS -->
        <acme:menu-option code="master.menu.peep" >
        	<acme:menu-suboption code="master.menu.peep.list" action="/any/peep/list"/>
        </acme:menu-option>
        
		<!-- ADMINISTRATOR -->
		<acme:menu-option code="master.menu.administrator" access="hasRole('Administrator')">
			<acme:menu-suboption code="master.menu.administrator.bulletin.list" action="/administrator/bulletin/list"/>
			<acme:menu-suboption code="master.menu.administrator.offer.list" action="/administrator/offer/list"/>
			<acme:menu-separator/>
			<acme:menu-suboption code="master.menu.administrator.user-accounts" action="/administrator/user-account/list"/>
			<acme:menu-suboption code="master.menu.administrator.configuration" action="/administrator/configuration/list"/>
			<acme:menu-suboption code="master.menu.administrator.banner" action="/administrator/banner/list"/>
			<acme:menu-separator/>
			<acme:menu-suboption code="master.menu.administrator.populate-initial" action="/administrator/populate-initial"/>
			<acme:menu-suboption code="master.menu.administrator.populate-sample" action="/administrator/populate-sample"/>			
			<acme:menu-separator/>
			<acme:menu-suboption code="master.menu.administrator.shut-down" action="/administrator/shut-down"/>
		</acme:menu-option>

		<!-- ASSISTANT -->
		<acme:menu-option code="master.menu.assistant" access="hasRole('Assistant')">
			<acme:menu-suboption code="master.menu.assistant.dashboard" action="/assistant/assistant-dashboard/show"/>
			<acme:menu-suboption code="master.menu.assistant.tutorial" action="/assistant/tutorial/list"/>
      		<acme:menu-suboption code="master.menu.assistant.tutorialSession" action="/assistant/tutorial-session/list-mine"/>
		</acme:menu-option>
		
		<!-- COMPANY -->
		<acme:menu-option code="master.menu.company" access="hasRole('Company')">
			<acme:menu-suboption code="master.menu.company.practicum" action="/company/practicum/list-mine"/>
		</acme:menu-option>

		<!-- STUDENT -->
		<acme:menu-option code="master.menu.student" access="hasRole('Student')">
			<acme:menu-suboption code="master.menu.student.dashboard" action="/student/student-dashboard/show"/>
			<acme:menu-suboption code="master.menu.student.enrolment" action="/student/enrolment/list"/>
			<acme:menu-suboption code="master.menu.student.activity" action="/student/activity/list"/>
			<acme:menu-suboption code="master.menu.student.course" action="/student/course/list"/>
		</acme:menu-option>
	</acme:menu-left>

	<acme:menu-right>
		<acme:menu-option code="master.menu.sign-up" action="/anonymous/user-account/create" access="isAnonymous()"/>
		<acme:menu-option code="master.menu.sign-in" action="/master/sign-in" access="isAnonymous()"/>

		<acme:menu-option code="master.menu.user-account" access="isAuthenticated()">
			<!-- UPDATE user account -->
			<acme:menu-suboption code="master.menu.user-account.general-data" action="/authenticated/user-account/update"/>

			<!-- BECOME COMPANY -->
			<acme:menu-suboption code="master.menu.user-account.become-company" action="/authenticated/company/create" access="!hasRole('Company')"/>
			<acme:menu-suboption code="master.menu.user-account.company" action="/authenticated/company/update" access="hasRole('Company')"/>
			
			<!-- BECOME STUDENT -->
			<acme:menu-suboption code="master.menu.user-account.become-student" action="/authenticated/student/create" access="!hasRole('Student')"/>
			<acme:menu-suboption code="master.menu.user-account.student" action="/authenticated/student/update" access="hasRole('Student')"/>
			<!-- BECOME ASSISTANT -->
			<acme:menu-suboption code="master.menu.user-account.become-assistant" action="/authenticated/assistant/create" access="!hasRole('Assistant')"/>
			<acme:menu-suboption code="master.menu.user-account.assistant" action="/authenticated/assistant/update" access="hasRole('Assistant')"/>

			<!-- BECOME LECTURER -->
			<acme:menu-suboption code="master.menu.user-account.become-lecturer" action="/authenticated/lecturer/create" access="!hasRole('Lecturer')"/>
			<!--<acme:menu-suboption code="master.menu.user-account.lecturer" action="/authenticated/lecturer/update" access="hasRole('Lecturer')"/> -->
			
			<!-- BECOME AUDITOR -->
			<acme:menu-suboption code="master.menu.user-account.become-auditor" action="/authenticated/auditor/create" access="!hasRole('Auditor')"/>
			<acme:menu-suboption code="master.menu.user-account.auditor" action="/authenticated/auditor/update" access="hasRole('Auditor')"/>

		</acme:menu-option>
		
		<acme:menu-option code="master.menu.sign-out" action="/master/sign-out" access="isAuthenticated()"/>
	</acme:menu-right>
</acme:menu-bar>

