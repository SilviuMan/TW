<%@ page import="java.util.Iterator" %>
<%@ page import="java.util.List" %>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%--   <META HTTP-EQUIV="Refresh" CONTENT="10">--%>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Main menu</title>
</head>
<body style="background: linear-gradient(to bottom, #33ccff 0%, #ff99cc 100%)fixed;" >

<%

    if((String)request.getSession().getAttribute("userName2")==null) {
        response.sendRedirect("login.jsp");

    }

%>

<% String autor = (String) request.getSession().getAttribute("userName2"); %>
<CENTER><b>WELCOME <% out.println(autor); %></b></CENTER>
<form action="enterInRoom" method="post">
    <table align="center">
        <tr>
            <td>Select a Room :</td>
            <td><select name="nrOfRoom" style="width:180px">
            <%Iterator itr2;%>
            <% List data2= (List)request.getServletContext().getAttribute("slist");
                for (itr2=data2.iterator(); itr2.hasNext(); )
                {
            %>
                <%int nr= (int) itr2.next();%>
                <option value="<%=nr%>"> <%=nr%></option>
            <%}%>
            </select></td>
        </tr>
    </table>


<table align="center">
        <tr>
            <td><input type="submit" name="enter" value="Enter"></td>
            <td></td>
        </tr>
        <tr>
            <td><input type="submit" name="createRoom" value="Create a room"></td>
            <td></td>
        </tr>
        <tr>
            <td><input type="submit" name="RefreshRooms" value="Refresh rooms"></td>
            <td></td>
        </tr>
    </table>


<table id="rooms" border="1"  width="100%">
    <tr>
        <td><b> Numar Camera</b></td>
        <td><b>Numar jucatori</b></td>
        <td><b>Creator</b></td>
    </tr>

    <%Iterator itr;%>
    <% List data= (List)request.getServletContext().getAttribute("roomslist");
        for (itr=data.iterator(); itr.hasNext(); )
        {
    %>
    <tr>
        <td width="119"><%=itr.next()%></td>
        <td width="168"><%=itr.next()%></td>
        <td width="168"><%=itr.next()%></td>
    </tr>
    <%}%>
</table>
</form>

<form action="logout">
    <input type="submit" value="Logout">
</form>

</body>
</html>