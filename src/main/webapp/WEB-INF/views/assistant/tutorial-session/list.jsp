<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>


<acme:list>
	<acme:list-column code="assistant.tutorialSession.list.label.title" path="title" width="50%"/>
	<acme:list-column code="assistant.tutorialSession.list.label.tutorial" path="tutorial" width="50%"/>
</acme:list>
	<jstl:if test="${showCreate}">
		<acme:button code="assistant.tutorialSession.list.button.create" action="/assistant/tutorial-session/create?masterId=${masterId}"/>	
	</jstl:if>

