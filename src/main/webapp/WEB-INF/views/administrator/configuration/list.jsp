<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>


<acme:list>
	<acme:list-column code="administrator.configuration.list.label.systemCurrency" path="systemCurrency" width="50%"/>
	<acme:list-column code="administrator.configuration.list.label.acceptedCurrencies" path="acceptedCurrencies" width="50%"/>
</acme:list>