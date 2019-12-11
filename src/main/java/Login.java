import db.ConnectionDB;
import models.Room;
import models.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

@WebServlet(name = "login",
        urlPatterns = {"/login"},
        loadOnStartup = 1)
public class Login extends HttpServlet {

    public Login() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ConnectionDB connectionDB = new ConnectionDB();
        ArrayList<User> users = connectionDB.getAccounts();

        //request.getSession().setAttribute("userName", users.get(0));
        Enumeration e = (Enumeration) (request.getSession().getAttributeNames());

        while ( e.hasMoreElements())
        {
            Object tring;
            if((tring = e.nextElement())!=null)
            {
                System.out.println("Element"+ tring.toString());
            }
        }

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if(username.isEmpty() || password.isEmpty() )
        {
            RequestDispatcher req = request.getRequestDispatcher("login.jsp");
            req.include(request, response);
        }
        else
        {

            int ok=1;
            for (User user:users
                 ) {
                if(user.getUsername().equals(username) && user.getPassword().equals(password))
                {
                    ArrayList<User> logedUsers = connectionDB.getLogdAccounts();
                    if(!logedUsers.isEmpty())
                    {
                        int ok2=1;
                        for (User user2:logedUsers
                             ) {
                            if (user2.getUsername().equals(username) && user2.getPassword().equals(password)) {
                                ok2 = 0;
                                RequestDispatcher req = request.getRequestDispatcher("login.jsp");
                                req.forward(request, response);
                            }
                        }
                        if (ok2==1) {
                           // ServletContext sc = request.getServletContext();
                            List<Room> rooms = connectionDB.getRooms();
                            List roos=new ArrayList<>();
                            List idRooms=new ArrayList<>();
                            for (Room room:rooms
                            ) {
                                idRooms.add(room.getId());
                                roos.add(room.getId());
                                roos.add(room.getNrJucatori());
                                roos.add(room.getNumeCreator());
                            }
                            try {
                                connectionDB.insertLogedAccount(user);
                            } catch (SQLException ex) {
                                ex.printStackTrace();
                            }
                            ok = 0;
                           // sc.setAttribute("idlist",idRooms);
                            //sc.setAttribute("roomslist",roos);
                            request.getServletContext().setAttribute("slist", idRooms);
                            request.getServletContext().setAttribute("roomslist", roos);
                            RequestDispatcher req = request.getRequestDispatcher("success.jsp");

                            request.getSession().setAttribute("userName2", username);
                            request.getSession().setAttribute("password2", password);
                            req.forward(request, response);
                            break;
                        }

                    }
                    else{
                        if (logedUsers.isEmpty()) {
                            List<Room> rooms = connectionDB.getRooms();
                            List roos=new ArrayList<>();
                            List idRooms=new ArrayList<>();
                            for (Room room:rooms
                            ) {
                                idRooms.add(room.getId());
                                roos.add(room.getId());
                                roos.add(room.getNrJucatori());
                                roos.add(room.getNumeCreator());
                            }

                            try {
                                connectionDB.insertLogedAccount(user);
                            } catch (SQLException ex) {
                                ex.printStackTrace();
                            }
                            ok = 0;
                            request.getServletContext().setAttribute("slist", idRooms);
                            request.getServletContext().setAttribute("roomslist", roos);
                            RequestDispatcher req = request.getRequestDispatcher("success.jsp");

                            request.getSession().setAttribute("userName2", username);
                            request.getSession().setAttribute("password2", password);
                            req.forward(request, response);
                            break;
                        }
                    }
                }

            }

            if(ok==1){
                RequestDispatcher req = request.getRequestDispatcher("login.jsp");
                req.forward(request, response);
            }

        }

    }
}