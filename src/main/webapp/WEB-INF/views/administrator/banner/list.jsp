<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>


<acme:list>
	<acme:list-column code="administrator.banner.list.label.moment" path="moment" width="50%"/>
	<acme:list-column code="administrator.banner.list.label.webLink" path="webLink" width="50%"/>
	<acme:list-column code="administrator.banner.list.label.slogan" path="slogan" width="50%"/>
</acme:list>
<acme:button code="administrator.banner.list.button.create" action="/administrator/banner/create"/>