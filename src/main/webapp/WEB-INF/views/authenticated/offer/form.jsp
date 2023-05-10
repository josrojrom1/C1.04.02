<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form>

	<acme:input-moment code="authenticated.offer.form.label.moment" path="moment"/>
	<acme:input-textbox code="authenticated.offer.form.label.heading" path="heading"/>
	<acme:input-textbox code="authenticated.offer.form.label.summary" path="summary"/>
	<acme:input-moment code="authenticated.offer.form.label.timePeriodStart" path="timePeriodStart"/>
	<acme:input-moment code="authenticated.offer.form.label.timePeriodEnd" path="timePeriodEnd"/>
	<acme:input-money code="authenticated.offer.form.label.retailPrice" path="retailPrice"/>
	<jstl:if test="${showExchange}">
	<acme:input-money code="authenticated.offer.form.label.moneyExchange" path="moneyExchange" readonly="true"/>
	</jstl:if>
	<acme:input-url code="authenticated.offer.form.label.link" path="link"/>
	
</acme:form>

