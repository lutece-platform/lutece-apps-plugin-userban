<jsp:include page="../../../AdminHeader.jsp" />

<%@page import="fr.paris.lutece.plugins.userban.web.user.UserJspBean"%>
<jsp:useBean id="user" scope="session" class="fr.paris.lutece.plugins.userban.web.user.UserJspBean" />
<% 
user.init( request, UserJspBean.RIGHT_MANAGE_USER);
%>
<%= user.getSaveUser( request ) %>

<%@ include file="../../../AdminFooter.jsp" %>