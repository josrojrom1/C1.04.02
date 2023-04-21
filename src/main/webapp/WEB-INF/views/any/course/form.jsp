<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>


<acme:form>
	<acme:input-textbox code="any.course.form.label.code" path="code"/>
	<acme:input-textbox code="any.course.form.label.title" path="title"/>
	<acme:input-textarea code="any.course.form.label.abst" path="abst"/>
	<acme:input-money code="any.course.form.label.retailPrice" path="retailPrice"/>
	<acme:input-money code="any.course.form.label.moneyExchange" path="moneyExchange" readonly="true"/>
	<acme:input-textbox code="any.course.form.label.link" path="link"/>
</acme:form>
<jstl:choose>
	<jstl:when test="${principal}">
		<acme:button code="master.menu.authenticated.audit.list" action="/authenticated/audit/list?masterId=${id}"/>
		<acme:button code="master.menu.authenticated.practicum.list" action="/authenticated/practicum/list?masterId=${id}"/>
	</jstl:when>
</jstl:choose>