<%@ page import="java.util.Queue" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="db.ConnectionDB" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <META HTTP-EQUIV="Refresh" CONTENT="10">
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Room</title>


    </head>
<body>
<% String youuuu = (String) request.getSession().getAttribute("userName2"); %>
<center><p><b>You are  <% out.println(youuuu); %></b></p>
<form action="success" method="post">
    <input type="submit" name="backToMeniu" value="Next turn">
</form> </center>
<form action="enterInRoom" method="post">

    <% String autor = (String) request.getSession().getAttribute("autorCamera"+(String) request.getSession().getAttribute("nrcamera")); %>
    You are in room of  <% out.println(autor); %>
    <table id="players" border="1"  width="100%">
        <tr>
            <td><b> Nume Jucatori</b></td>
        </tr>

        <%Iterator itr;%>
        <%  ArrayList<String> users;
            List players=new ArrayList();
            ConnectionDB connectionDB =new ConnectionDB();
            users = connectionDB.getPlyaersFromRoom(Integer.parseInt((String) request.getSession().getAttribute("nrcamera")));
            if(!users.isEmpty())
            {
                for (String s:users
                ) {
                    if(!s.equals((String)request.getSession().getAttribute("userName2")))
                    {
                        if(s.equals(request.getServletContext().getAttribute("tura"+ request.getSession().getAttribute("nrcamera")))) {
                            players.add("66ff99");
                            players.add(s);
                        }
                        else{
                            players.add("ffffff");
                            players.add(s);
                        }
                    }
                }
                request.getSession().setAttribute("playersList",players);
            }
            List data= (List)request.getSession().getAttribute("playersList");
            for (itr=data.iterator(); itr.hasNext(); )
            {
        %>
        <tr>
            <td width="119"  bgcolor="#<%=itr.next()%>"><%=itr.next()%></td>
        </tr>
        <%}%>
    </table>
    <% String turn = (String) request.getServletContext().getAttribute("tura"+request.getSession().getAttribute("nrcamera")); %>
    <p>It's <% out.println(turn); %> turn
    <% String start = (String) request.getServletContext().getAttribute("start"+request.getSession().getAttribute("nrcamera")); %>
    <% if(turn.equals(youuuu) && start!=null)
    {%>
        <input type="submit" name="nextTurn" value="Next turn">
     <%}%>

    <% if(turn.equals(youuuu) && start==null) {%>
        <input type="submit" name="Start" value="Start">  <%}%>
</form></p>

</body>
</html>
