<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form>
	<acme:input-textbox code="authenticated.tutorial.show.label.code" path="code"/>
	<acme:input-textbox code="authenticated.tutorial.show.label.title" path="title"/>
	<acme:input-textbox code="authenticated.tutorial.show.label.abst" path="abst"/>
	<acme:input-textbox code="authenticated.tutorial.show.label.goals" path="goals"/>
	<acme:input-double code="authenticated.tutorial.show.label.totalTime" path="totalTime"/>
	<acme:input-textbox code="authenticated.tutorial.show.label.assistant" path="assistant"/>
</acme:form>