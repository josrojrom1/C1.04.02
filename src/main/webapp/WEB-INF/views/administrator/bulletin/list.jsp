<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>


<acme:list>
	<acme:list-column code="administrator.bulletin.list.label.title" path="title" width="20%"/>
	<acme:list-column code="administrator.bulletin.list.label.flag" path="flag" width="20%"/>
</acme:list>


<acme:button code="administrator.bulletin.list.button.create" action="/administrator/bulletin/create"/>

 