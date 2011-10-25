<%@page import="java.util.*"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html"/>
<title>Welcome to GigaSpaces XAP</title>
</head>
<body>
<h3>Welcome, current date and time are <%= new Date() %></h3>
<% 	String hostName = request.getServerName(); 
    String sessionId = session.getId();
%>
<br/>
<table>	
	<tr>
		<td>Servlet container host name:</td>
		<td><%= hostName %></td>
	</tr>
	<tr>
		<td>Session id:</td>
		<td><%= sessionId %></td>
	</tr>
</table>
</form>
</body>
</html>