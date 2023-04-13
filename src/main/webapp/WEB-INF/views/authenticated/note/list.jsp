<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:list>
	<acme:list-column code="authenticated.note.list.label.moment" path="moment" width="20%"/>
	<acme:list-column code="authenticated.note.list.label.title" path="title" width="20%"/>
	<acme:list-column code="authenticated.note.list.label.author" path="author" width="20%"/>
	<acme:list-column code="authenticated.note.list.label.message" path="message" width="20%"/>
	<acme:list-column code="authenticated.note.list.label.email" path="email" width="20%"/>
	<acme:list-column code="authenticated.note.list.label.link" path="link" width="20%"/>
	
	
</acme:list>

<acme:button code = "authenticated.note.list.label.create" action = "/authenticated/note/create"/>