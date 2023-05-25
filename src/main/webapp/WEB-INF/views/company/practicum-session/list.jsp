<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>


<acme:list>
	<acme:list-column code="company.practicumSession.list.label.title" path="title" width="50%"/>
	<acme:list-column code="company.practicumSession.list.label.practicum" path="practicum" width="50%"/>
</acme:list>		
		<jstl:if test="${showCreateSession || showCreate}">
	<acme:button code="company.practicumSession.list.button.create" action="/company/practicum-session/create?masterId=${masterId}"/>
		</jstl:if>
		<jstl:if test="${!showCreate && !hasAddendum}">
	<acme:button code="company.practicumSession.list.button.addendum" action="/company/practicum-session/addendum?masterId=${masterId}"/>
		</jstl:if>