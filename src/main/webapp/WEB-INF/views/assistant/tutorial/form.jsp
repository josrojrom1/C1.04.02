<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form>
	<acme:input-textbox code="assistant.tutorial.show.label.code" path="code"/>
	<acme:input-textbox code="assistant.tutorial.show.label.title" path="title"/>
	<acme:input-textbox code="assistant.tutorial.show.label.abst" path="abst"/>
	<acme:input-textbox code="assistant.tutorial.show.label.goals" path="goals"/>
	<acme:input-double code="assistant.tutorial.show.label.totalTime" path="totalTime"/>
	<acme:input-select code="assistant.tutorial.show.label.course" path="course" choices="${courseChoices}"/>
	
	<jstl:if test="${acme:anyOf(_command, 'show|delete|update|publish') && draftMode}">
		<acme:submit code="assistant.tutorial.form.button.update" action="/assistant/tutorial/update"/>
		<acme:submit code="assistant.tutorial.form.button.delete" action="/assistant/tutorial/delete"/>
		<acme:submit code="assistant.tutorial.form.button.publish" action="/assistant/tutorial/publish"/>
	</jstl:if>
	<jstl:if test="${acme:anyOf(_command, 'create')}">
		<acme:submit code="assistant.tutorial.form.button.create" action="/assistant/tutorial/create"/>
	</jstl:if>
</acme:form>