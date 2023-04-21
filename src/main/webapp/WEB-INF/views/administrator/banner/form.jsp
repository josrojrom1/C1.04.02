<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form>
	<acme:input-textbox code="administrator.banner.form.label.moment" path="moment"/>
	<acme:input-textbox code="administrator.banner.form.label.startDisplayPeriod" path="startDisplayPeriod"/>
	<acme:input-textbox code="administrator.banner.form.label.endDisplayPeriod" path="endDisplayPeriod"/>
	<acme:input-textbox code="administrator.banner.form.label.pictureLink" path="pictureLink"/>
	<acme:input-textbox code="administrator.banner.form.label.webLink" path="webLink"/>
	<acme:input-textbox code="administrator.banner.form.label.slogan" path="slogan"/>
	
	
	
	
	<jstl:choose>
		<jstl:when test="${_command == 'create'}">
			<acme:submit code="administrator.banner.form.button.create" action="/administrator/banner/create"/>
		</jstl:when>
		<jstl:when test="${_command == 'show'}">
			<acme:submit code="administrator.banner.form.button.update" action="/administrator/banner/update"/>
			<acme:submit code="administrator.banner.form.button.delete" action="/administrator/banner/delete"/>
		</jstl:when>
	</jstl:choose>
</acme:form>