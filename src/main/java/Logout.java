import db.ConnectionDB;
import models.User;
import org.omg.CORBA.UShortSeqHelper;

import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Enumeration;

@WebServlet(name = "logout",
        urlPatterns = {"/logout"})
public class Logout extends HttpServlet{
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ConnectionDB connectionDB = new ConnectionDB();
        try {
            if(request.getSession().getAttribute("nrcamera")!=null) {
                String nrr= (String) request.getSession().getAttribute("nrcamera");
                System.out.println(nrr);
                connectionDB.deletePlayerFromRoom((String) request.getSession().getAttribute("userName2"),Integer.parseInt(nrr));
            }
            connectionDB.deleteRoom((String) request.getSession().getAttribute("userName2"));
            connectionDB.deleteLogedAccount(new User((String) request.getSession().getAttribute("userName2"),(String)request.getSession().getAttribute("password2")));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        request.setAttribute("userName2","null");
        request.getSession().setAttribute("password2",null);
        request.getSession().setAttribute("userName2",null);
        request.getSession().setAttribute("username","null");
        request.getSession().setAttribute("password","null");
        request.getSession().setAttribute("some_token","null");
        request.getRequestDispatcher("login.jsp").include(request,response);
        HttpSession session=request.getSession();
        session.invalidate();
        response.setHeader("Cache-Control","no-cache");
        response.setHeader("Cache-Control","no-store");
        response.setHeader("Pragma","no-cache");
        response.setDateHeader ("Expires", 0);
        response.sendRedirect("login.jsp");
        //req.forward(request, response);
    }

}
