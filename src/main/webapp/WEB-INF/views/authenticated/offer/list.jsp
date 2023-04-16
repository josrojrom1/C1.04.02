<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:list>
	<acme:list-column code="authenticated.offer.list.label.moment" path="moment" width="14%"/>
	<acme:list-column code="authenticated.offer.list.label.heading" path="heading" width="16%"/>
	<acme:list-column code="authenticated.offer.list.label.summary" path="summary" width="14%"/>
	<acme:list-column code="authenticated.offer.list.label.timePeriodStart" path="timePeriodStart" width="14%"/>
	<acme:list-column code="authenticated.offer.list.label.timePeriodEnd" path="timePeriodEnd" width="14%"/>
	<acme:list-column code="authenticated.offer.list.label.retailPrice" path="retailPrice" width="14%"/>
	<acme:list-column code="authenticated.offer.list.label.link" path="link" width="14%"/>
	
	
</acme:list>

<acme:button code = "authenticated.offer.list.button.create" action = "/authenticated/offer/create"/> 