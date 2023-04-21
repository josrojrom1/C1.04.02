<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>


<acme:form>
	<acme:input-moment code="administrator.offer.form.label.moment" path="moment"/>
	<acme:input-textbox code="administrator.offer.form.label.heading" path="heading"/>	
	<acme:input-textarea code="administrator.offer.form.label.summary" path="summary"/>
	<acme:input-moment code="administrator.offer.form.label.timePeriodStart" path="timePeriodStart"/>
	<acme:input-moment code="administrator.offer.form.label.timePeriodEnd" path="timePeriodEnd"/>
	<acme:input-money code="administrator.offer.form.label.retailPrice" path="retailPrice"/>
	<acme:input-url code="administrator.offer.form.label.link" path="link"/>	
	
	<jstl:if test="${acme:anyOf(_command, 'show|delete|update')}">
		<acme:submit code="administrator.offer.form.button.update" action="/administrator/offer/update"/>
		<acme:submit code="administrator.offer.form.button.delete" action="/administrator/offer/delete"/>
	</jstl:if>
	<jstl:if test="${acme:anyOf(_command, 'create')}">
		<acme:submit code="administrator.offer.form.button.create" action="/administrator/offer/create"/>
	</jstl:if>    

</acme:form>