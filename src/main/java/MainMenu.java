import db.ConnectionDB;
import models.Room;
import models.User;

import javax.servlet.AsyncContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

@WebServlet(name = "success",
        urlPatterns = {"/success"})
public class MainMenu extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       doGet(request, response);
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getParameter("backToMeniu") != null ) {
            // response.setIntHeader("Refresh", 3);
            ConnectionDB connectionDB = new ConnectionDB();

            try {
                if(request.getSession().getAttribute("nrcamera")!=null) {
                    String nrr= (String) request.getSession().getAttribute("nrcamera");
                    System.out.println(nrr);
                    connectionDB.deletePlayerFromRoom((String) request.getSession().getAttribute("userName2"),Integer.parseInt(nrr));
                }
                connectionDB.deleteRoom((String) request.getSession().getAttribute("userName2"));
                Queue queue = (Queue) request.getServletContext().getAttribute("coadaTura"+ request.getSession().getAttribute("nrcamera"));

                String username=(String)request.getSession().getAttribute("userName2");
                // queue.add(username);
                String turn= (String) queue.poll();
                Queue queue2 = new LinkedList();
                while (turn!=null)
                {
                    if(!turn.equals(username))
                        queue2.add(turn);
                    turn= (String) queue.poll();
                }
                request.getServletContext().setAttribute("coadaTura"+ request.getSession().getAttribute("nrcamera"),queue);
               // queue.add(turn);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            ArrayList<User> users = connectionDB.getAccounts();
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
            response.sendRedirect("success.jsp");
            // response.sendRedirect("room.jsp");
            //System.out.println(request.getAttribute("autorCamera"));
        }

    }
}
