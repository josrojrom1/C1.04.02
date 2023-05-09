<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form>
	<acme:input-textbox code="authenticated.practicum.show.label.code" path="code"/>
	<acme:input-textbox code="authenticated.practicum.show.label.title" path="title"/>
	<acme:input-textbox code="authenticated.practicum.show.label.abst" path="abst"/>
	<acme:input-textbox code="authenticated.practicum.show.label.goals" path="goals"/>
	<acme:input-double code="authenticated.practicum.show.label.totalTime" path="totalTime"/>
	<acme:input-textbox code="authenticated.practicum.show.label.company" path="company"/>
	<acme:input-textbox code="authenticated.practicum.show.label.course" path="course"/>
</acme:form>