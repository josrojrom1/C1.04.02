<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>


<acme:list>
	<acme:list-column code="authenticated.bulletin.list.label.moment" path="moment" width="20%"/>
	<acme:list-column code="authenticated.bulletin.list.label.title" path="title" width="20%"/>
	<acme:list-column code="authenticated.bulletin.list.label.message" path="message" width="20%"/>
	<acme:list-column code="authenticated.bulletin.list.label.flag" path="flag" width="20%"/>
	<acme:list-column code="authenticated.bulletin.list.label.link" path="link" width="20%"/>
</acme:list>


<acme:button code="administrator.bulletin.list.button.create" action="/administrator/bulletin/create"/>
