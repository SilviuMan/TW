import db.ConnectionDB;
import models.Room;
import models.User;

import javax.servlet.AsyncContext;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.*;

@WebServlet(name = "enterInRoom",
        urlPatterns = {"/enterInRoom"},
        asyncSupported=true)
public class EnterInRoom  extends HttpServlet {
    private List<AsyncContext> contexts = new LinkedList<>();
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //response.setIntHeader("Refresh", 3);

        ConnectionDB connectionDB = new ConnectionDB();
//        ArrayList<String> users;
//        List players=new ArrayList();
//        if(request.getSession().getAttribute("nrcamera")!=null) {
//            users = connectionDB.getPlyaersFromRoom(Integer.parseInt((String) request.getSession().getAttribute("nrcamera")));
//            if(!users.isEmpty())
//            {
//                for (String s:users
//                     ) {
//                    if(!s.equals((String)request.getSession().getAttribute("userName2")))
//                    {
//                        if(s.equals(request.getServletContext().getAttribute("tura"+ request.getSession().getAttribute("nrcamera")))) {
//                            players.add("66ff99");
//                            players.add(s);
//                        }
//                        else{
//                            players.add("ffffff");
//                            players.add(s);
//                        }
//                    }
//                }
//                request.getSession().setAttribute("playersList",players);
//            }
//        }


        if (request.getParameter("enter") != null && request.getParameter("nrOfRoom")!=null) {
            String nr=request.getParameter("nrOfRoom");
            // System.out.println(request.getParameter("nrOfRoom"));
            request.getSession().setAttribute("nrcamera",nr);
            Queue queue = (Queue) request.getServletContext().getAttribute("coadaTura"+ request.getSession().getAttribute("nrcamera"));

            try {
                request.getSession().setAttribute("autorCamera"+(String) request.getSession().getAttribute("nrcamera"),connectionDB.getAutorRoom(Integer.parseInt((String) request.getSession().getAttribute("nrcamera"))));
                //System.out.println(connectionDB.getAutorRoom(Integer.parseInt(nr)));
                //System.out.println(request.getSession().getAttribute("autorCamera"+(String) request.getSession().getAttribute("nrcamera")));
                connectionDB.enterRoom((String)request.getSession().getAttribute("userName2"),Integer.parseInt(nr));
            } catch (SQLException e) {
                e.printStackTrace();
            }
            ArrayList<String> users;
            List players=new ArrayList();
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
            queue.add(request.getSession().getAttribute("userName2"));
            request.getServletContext().setAttribute("coadaTura"+ request.getSession().getAttribute("nrcamera"),queue);
            request.getRequestDispatcher("room.jsp").forward(request, response);


            // System.out.println(request.getSession().getAttribute("autorCamera"+(String) request.getSession().getAttribute("nrcamera")));
        }
        if (request.getParameter("createRoom") != null ) {
            Queue queue =new LinkedList<>();

            //ServletContext sc = request.getServletContext();
            // List<AsyncContext> asyncContexts = new ArrayList<>(this.contexts);
            // this.contexts.clear();
            String nr="";
            String username=(String)request.getSession().getAttribute("userName2");
            // System.out.println(username);
            try {

                connectionDB.createRoom(username);
                request.getSession().setAttribute("nrcamera",Integer.toString(connectionDB.getIdRoom(username)));
                nr=(String) request.getSession().getAttribute("nrcamera");
                request.getServletContext().setAttribute("start"+request.getSession().getAttribute("nrcamera"),null);
                request.getSession().setAttribute("autorCamera"+(String) request.getSession().getAttribute("nrcamera"),connectionDB.getAutorRoom(Integer.parseInt((String) request.getSession().getAttribute("nrcamera"))));
            } catch (SQLException e) {
                e.printStackTrace();
            }

            String htmlMessage = "<p><b>" + username + "</b>" + " turn" + "</p>";
            //  sc.setAttribute("messages"+request.getSession().getAttribute("nrcamera"), htmlMessage);
            //System.out.println(sc.getAttribute("messages"+request.getSession().getAttribute("nrcamera")));
            /*for (AsyncContext asyncContext : asyncContexts) {
                try (PrintWriter writer = asyncContext.getResponse().getWriter()) {
                    writer.println(htmlMessage);
                    writer.flush();
                    asyncContext.complete();
                } catch (Exception ex) {
                }
            }*/
            queue.add(username);
            request.getServletContext().setAttribute("coadaTura"+ request.getSession().getAttribute("nrcamera"),queue);
            request.getServletContext().setAttribute("tura"+ request.getSession().getAttribute("nrcamera"),username);
            ArrayList<String> users;
            List players=new ArrayList();
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
            request.getSession().setAttribute("autorCamera",username);

            request.getRequestDispatcher("room.jsp").forward(request, response);
            // response.sendRedirect("room.jsp");
            //System.out.println(request.getAttribute("autorCamera"));
        }
        if (request.getParameter("nextTurn") != null ) {
            // List<AsyncContext> asyncContexts = new ArrayList<>(this.contexts);
            //this.contexts.clear();
            // ServletContext sc = request.getServletContext();
            //System.out.println(request.getParameter("tura" +request.getSession().getAttribute("nrcamera")));
            Queue queue = (Queue) request.getServletContext().getAttribute("coadaTura"+ request.getSession().getAttribute("nrcamera"));

            String username=(String)request.getSession().getAttribute("userName2");
           // queue.add(username);
            String turn= (String) queue.poll();
            queue.add(turn);
           // turn= (String) queue.peek();
            request.getServletContext().setAttribute("coadaTura"+ request.getSession().getAttribute("nrcamera"),queue);
            request.getServletContext().setAttribute("tura" +request.getSession().getAttribute("nrcamera"),turn);
            ArrayList<String> users;
            List players=new ArrayList();
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
            String htmlMessage = "<p><b>" + username + "</b>" + " turn" + "</p>";
            //sc.setAttribute("messages"+request.getSession().getAttribute("nrcamera"), htmlMessage);
            //sc.setAttribute("tura", username);
          //  System.out.println(request.getServletContext().getAttribute("tura" +request.getSession().getAttribute("nrcamera")));

//            for (AsyncContext asyncContext : asyncContexts) {
//                try (PrintWriter writer = asyncContext.getResponse().getWriter()) {
//                    writer.println(htmlMessage);
//                    writer.flush();
//                    asyncContext.complete();
//                } catch (Exception ex) {
//                }
//            }
            request.getRequestDispatcher("room.jsp").forward(request, response);
            //response.sendRedirect("room.jsp");


            //System.out.println(request.getAttribute("autorCamera"));
        }
        if (request.getParameter("RefreshRooms") != null ){
            List<Room> rooms = connectionDB.getRooms();
            List roos = new ArrayList<>();
            List idRooms = new ArrayList<>();
            for (Room room : rooms
            ) {
                idRooms.add(room.getId());
                roos.add(room.getId());
                roos.add(room.getNrJucatori());
                roos.add(room.getNumeCreator());
            }

            request.getServletContext().setAttribute("slist", idRooms);
            request.getServletContext().setAttribute("roomslist", roos);
            request.getRequestDispatcher("success.jsp").forward(request, response);
        }
        if (request.getParameter("Start") != null ){
            // List<AsyncContext> asyncContexts = new ArrayList<>(this.contexts);
            //this.contexts.clear();
            // ServletContext sc = request.getServletContext();
            //System.out.println(request.getParameter("tura" +request.getSession().getAttribute("nrcamera")));
            request.getServletContext().setAttribute("start"+request.getSession().getAttribute("nrcamera"),"asss");
            Queue queue = (Queue) request.getServletContext().getAttribute("coadaTura"+ request.getSession().getAttribute("nrcamera"));

            String username=(String)request.getSession().getAttribute("userName2");
            String turn= (String) queue.poll();
            queue.add(turn);
            request.getServletContext().setAttribute("coadaTura"+ request.getSession().getAttribute("nrcamera"),queue);
            request.getServletContext().setAttribute("tura" +request.getSession().getAttribute("nrcamera"),turn);
            ArrayList<String> users;
            List players=new ArrayList();
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
            String htmlMessage = "<p><b>" + username + "</b>" + " turn" + "</p>";
            //sc.setAttribute("messages"+request.getSession().getAttribute("nrcamera"), htmlMessage);
            //sc.setAttribute("tura", username);
            //  System.out.println(request.getServletContext().getAttribute("tura" +request.getSession().getAttribute("nrcamera")));

//            for (AsyncContext asyncContext : asyncContexts) {
//                try (PrintWriter writer = asyncContext.getResponse().getWriter()) {
//                    writer.println(htmlMessage);
//                    writer.flush();
//                    asyncContext.complete();
//                } catch (Exception ex) {
//                }
//            }
            request.getRequestDispatcher("room.jsp").forward(request, response);
        }
        //response.sendRedirect("room.jsp");
        //request.getRequestDispatcher("room.jsp").include(request, response);
       // doPost(request,response);
        response.sendRedirect("room.jsp");
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            doGet(request,response);

    }
}
