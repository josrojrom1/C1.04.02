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

<%@page language="java" import="acme.framework.helpers.PrincipalHelper,acme.roles.Provider,acme.roles.Consumer"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:menu-bar code="master.menu.home">
	<acme:menu-left>
			
		<!-- ANY LIST COURSES -->
		<acme:menu-option code="master.menu.courses">
			<acme:menu-suboption code="master.menu.course.list" action="/any/course/list"/>
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

			<acme:menu-suboption code="master.menu.auditor.audit.list" action="/auditor/audit/list"/>
		</acme:menu-option>
		
					
		
		
		<!-- AUTHENTICATED -->
		<acme:menu-option code="master.menu.authenticated" access="isAuthenticated()">
			<acme:menu-suboption code="master.menu.authenticated.bulletin.list" action="/authenticated/bulletin/list"/>
			<acme:menu-suboption code="master.menu.authenticated.audit.list" action="/authenticated/audit/list"/>
		</acme:menu-option>

		
		
		<!-- ANONYMOUS -->
		<acme:menu-option code="master.menu.anonymous" access="isAnonymous()">
			<acme:menu-suboption code="47546590: Reina Munoz, Jose Antonio" action="https://www.youtube.com/"/>
			<acme:menu-suboption code="77938490: Parra Mendez, Pablo" action="https://sevillafc.es/"/>
			<acme:menu-suboption code="20605668: Rojas Romero, Jose Joaquin" action="https://www.artistapirata.com/"/>
			<acme:menu-suboption code="45388453: Ybarra Manrique, Miguel" action="https://open.spotify.com/?"/>
			<acme:menu-suboption code="29580039: Albalat Ortiz, Samuel" action="https://cityofmist.co/"/>
		</acme:menu-option>

		
		<acme:menu-option code="master.menu.authenticated" access="isAuthenticated()">
			<acme:menu-suboption code="master.menu.authenticated.note" action="/authenticated/note/list"/>
			
		</acme:menu-option>
		
		<!-- ADMINISTRATOR -->
		<acme:menu-option code="master.menu.administrator" access="hasRole('Administrator')">
			<acme:menu-suboption code="master.menu.administrator.bulletin.list" action="/administrator/bulletin/list"/>
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
		
	</acme:menu-left>

	<acme:menu-right>
		<acme:menu-option code="master.menu.sign-up" action="/anonymous/user-account/create" access="isAnonymous()"/>
		<acme:menu-option code="master.menu.sign-in" action="/master/sign-in" access="isAnonymous()"/>

		<acme:menu-option code="master.menu.user-account" access="isAuthenticated()">
			<!-- UPDATE user account -->
			<acme:menu-suboption code="master.menu.user-account.general-data" action="/authenticated/user-account/update"/>

			
			<!-- BECOME LECTURER -->
			<acme:menu-suboption code="master.menu.user-account.become-lecturer" action="/authenticated/lecturer/create" access="!hasRole('Lecturer')"/>
			
			<acme:menu-suboption code="master.menu.user-account.lecturer" action="/authenticated/lecturer/update" access="hasRole('Lecturer')"/>

			<acme:menu-suboption code="master.menu.user-account.become-provider" action="/authenticated/provider/create" access="!hasRole('Provider')"/>
			<acme:menu-suboption code="master.menu.user-account.provider" action="/authenticated/provider/update" access="hasRole('Provider')"/>
			<acme:menu-suboption code="master.menu.user-account.become-consumer" action="/authenticated/consumer/create" access="!hasRole('Consumer')"/>
			<acme:menu-suboption code="master.menu.user-account.consumer" action="/authenticated/consumer/update" access="hasRole('Consumer')"/>
			<acme:menu-suboption code="master.menu.user-account.become-auditor" action="/authenticated/auditor/create" access="!hasRole('Auditor')"/>
			<acme:menu-suboption code="master.menu.user-account.auditor" action="/authenticated/auditor/update" access="hasRole('Auditor')"/>

		</acme:menu-option>
		

		<acme:menu-option code="master.menu.sign-out" action="/master/sign-out" access="isAuthenticated()"/>
	</acme:menu-right>
</acme:menu-bar>

