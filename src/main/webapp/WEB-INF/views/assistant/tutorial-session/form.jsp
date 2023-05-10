<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form>
	<acme:input-textbox code="assistant.tutorialSession.form.label.title" path="title"/>
	<acme:input-textbox code="assistant.tutorialSession.form.label.abst" path="abst"/>
	<acme:input-select code="assistant.tutorialSession.form.label.sessionType" path="sessionType" choices="${lessonChoices}"/>
	<acme:input-moment code="assistant.tutorialSession.form.label.periodStart" path="periodStart"/>
	<acme:input-moment code="assistant.tutorialSession.form.label.periodFinish" path="periodFinish"/>
	<acme:input-url code="assistant.tutorialSession.form.label.link" path="link"/>
	<acme:input-select code="assistant.tutorialSession.form.label.tutorial" path="tutorial" choices="${choices}" readonly="${readTutorial}"/>
	
	<jstl:if test="${acme:anyOf(_command, 'show|delete|update|publish') && draftMode}">
		<acme:submit code="assistant.tutorialSession.form.button.update" action="/assistant/tutorial-session/update"/>
		<acme:submit code="assistant.tutorialSession.form.button.delete" action="/assistant/tutorial-session/delete"/>
	</jstl:if>
	<jstl:if test="${acme:anyOf(_command, 'create')}">
		<acme:submit code="assistant.tutorialSession.form.button.create" action="/assistant/tutorial-session/create?masterId=${masterId}"/>
	</jstl:if>
</acme:form>