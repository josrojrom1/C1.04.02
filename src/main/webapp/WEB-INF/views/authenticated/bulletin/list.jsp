<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>


<acme:list>
	<acme:list-column code="authenticated.bulletin.list.label.title" path="title" width="20%"/>
	<acme:list-column code="authenticated.bulletin.list.label.flag" path="flag" width="20%"/>
</acme:list>