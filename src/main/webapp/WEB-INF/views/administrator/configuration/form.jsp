<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form readonly="${readonly }">
	<acme:input-textbox code="administrator.configuration.form.label.systemCurrency" path="systemCurrency"/>
	<acme:input-textbox code="administrator.configuration.form.label.acceptedCurrencies" path="acceptedCurrencies"/>
	<jstl:choose>
		<jstl:when test="${_command == 'show' }">
			<acme:button code="administrator.configuration.form.button.update" action="/administrator/configuration/update"/>
		</jstl:when>
		<jstl:when test="${_command == 'update' }">
			<acme:submit code="administrator.configuration.form.button.update" action="/administrator/configuration/update"/>
		</jstl:when>
	</jstl:choose>
	
</acme:form>