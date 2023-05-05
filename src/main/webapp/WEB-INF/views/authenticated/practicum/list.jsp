<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>


<acme:list>
	<acme:list-column code="authenticated.practicum.list.label.title" path="title" width="50%"/>
	<acme:list-column code="authenticated.practicum.list.label.company" path="company" width="50%"/>
</acme:list>