<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form>
	<acme:input-textbox code="assistant.tutorialSession.show.label.title" path="title"/>
	<acme:input-textbox code="assistant.tutorialSession.show.label.abst" path="abst"/>
	<acme:input-select code="assistant.tutorialSession.show.label.sessionType" path="sessionType" choices="${lessonChoices}"/>
	<acme:input-moment code="assistant.tutorialSession.show.label.periodStart" path="periodStart"/>
	<acme:input-moment code="assistant.tutorialSession.show.label.periodFinish" path="periodFinish"/>
	<acme:input-url code="assistant.tutorialSession.show.label.link" path="link"/>
	<acme:input-select code="assistant.tutorialSession.show.label.tutorial" path="tutorial" choices="${choices}"/>
	
	<jstl:if test="${acme:anyOf(_command, 'show|delete|update|publish') && draftMode}">
		<acme:submit code="assistant.tutorialSession.form.button.update" action="/assistant/tutorialSession/update"/>
		<acme:submit code="assistant.tutorialSession.form.button.delete" action="/assistant/tutorialSession/delete"/>
	</jstl:if>
	<jstl:if test="${acme:anyOf(_command, 'create')}">
		<acme:submit code="assistant.tutorialSession.form.button.create" action="/assistant/tutorial/create"/>
	</jstl:if>
</acme:form>