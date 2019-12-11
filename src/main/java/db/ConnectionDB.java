package db;

import models.Room;
import models.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ConnectionDB {
    private static Connection myConnection = null;
    private Statement myStatement = null;
    private ResultSet myResultSet = null;

    public static Connection createUserConnection() {
        try {
            DatabaseConnection connection = DatabaseConnection.getInstance();
            connection.init("jdbc:mysql://127.0.0.1:3306/macao");
            myConnection = DatabaseConnection.getInstance().getConnection();
            return myConnection;
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("CONNECTION FAILED");
        } catch (Exception e) {
            e.printStackTrace();

        }

        return null;
    }
    public void deleteLogedAccount(User user) throws SQLException {
        createUserConnection();
        try {
            myStatement = myConnection.createStatement();
            myStatement.executeUpdate("DELETE FROM  logdusers WHERE logdusers.username='"+user.getUsername()+"'" +" AND "+" logdusers.password= '"+ user.getPassword()+"'");
            myStatement.close();
            myConnection.close();
        }catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public void insertLogedAccount(User user) throws SQLException {
        createUserConnection();
        try {
            myStatement = myConnection.createStatement();
            myStatement.executeUpdate("INSERT INTO logdusers (logdusers.username,logdusers.password) values ( '"+user.getUsername()+"', '"+user.getPassword()+"')");
            myStatement.close();
            myConnection.close();
        }catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public ArrayList<User> getLogdAccounts() {
        createUserConnection();
        ArrayList<User> users = new ArrayList<User>();
        try {
            myStatement = myConnection.createStatement();
            ResultSet rs = myStatement.executeQuery("SELECT * FROM logdusers");
            while (rs.next()) {
                int id = rs.getInt("idlogdUsers");
                String username = rs.getString("username");
                String password = rs.getString("password");
                User user = new User( username, password);
                users.add(user);
            }
            if (users.isEmpty()) {
                System.out.println("Nu exista niciun user in  baza de date");
            }
            rs.close();
            myStatement.close();
            myConnection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }
    public void enterRoom(String username,int idRoom) throws SQLException {
        createUserConnection();
        try {
            myStatement = myConnection.createStatement();
            myStatement.executeUpdate("INSERT INTO usersroom (usersroom.idusersRoom,usersroom.username) values ('"+Integer.toString(idRoom)+ "'"+", '"+username+"')");
            incresRoomNrOfPlayers(idRoom);
            myStatement.close();
            myConnection.close();
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void deletePlayerFromRoom(String username,int idRoom) throws SQLException {
        createUserConnection();

        try {
            myStatement = myConnection.createStatement();
            myStatement.executeUpdate("DELETE FROM  usersroom WHERE usersroom.idusersRoom='"+Integer.toString(idRoom)+"'"+" AND "+ "usersroom.username= '"+username+"'");
            decresRoomNrOfPlayers(idRoom);
            myStatement.close();
            myConnection.close();
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public int getRoomNrOfPlayers(int idRoom) throws SQLException {
        createUserConnection();
        int nrJucatori=0;
        try {
            myStatement = myConnection.createStatement();
            ResultSet rs = myStatement.executeQuery("SELECT * FROM room where room.idRoom='" + Integer.toString(idRoom) + "'");
            while(rs.next())
                nrJucatori=rs.getInt("nrJucatori");
            rs.close();
            myStatement.close();
            myConnection.close();
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return nrJucatori;
    }
    public void decresRoomNrOfPlayers(int idRoom) throws SQLException {
        createUserConnection();
        //int nrJuc=getRoomNrOfPlayers(idRoom)-1;
        try {
            myStatement = myConnection.createStatement();
            myStatement.executeUpdate("UPDATE room SET room.nrJucatori='"+ Integer.toString(getRoomNrOfPlayers(idRoom)-1)+"' WHERE room.idRoom='"+Integer.toString(idRoom)+"'");
            myStatement.close();
            myConnection.close();
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void incresRoomNrOfPlayers(int idRoom) throws SQLException {
        createUserConnection();
        //int nrJuc=getRoomNrOfPlayers(idRoom)+1;
        try {
            myStatement = myConnection.createStatement();
            myStatement.executeUpdate("UPDATE room SET room.nrJucatori='"+ Integer.toString(getRoomNrOfPlayers(idRoom)+1)+"' WHERE room.idRoom='"+Integer.toString(idRoom)+"'");
            myStatement.close();
            myConnection.close();
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void deleteRoom(String username) throws SQLException {
        createUserConnection();
        try {
            myStatement = myConnection.createStatement();
            myStatement.executeUpdate("DELETE FROM  room WHERE room.numeCreator='"+username+"'");
            myStatement.close();
            myConnection.close();
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public int getIdRoom(String username) throws SQLException {
        int id=0;
        createUserConnection();
        try {
            myStatement = myConnection.createStatement();
            ResultSet rs = myStatement.executeQuery("SELECT * FROM room where room.numeCreator='" + username + "'");
            rs.next();
            id=rs.getInt("idRoom");
            rs.close();
        }catch (SQLException e) {
            e.printStackTrace();
        }
        myStatement.close();
        myConnection.close();
        return  id;
    }
    public String getAutorRoom(int id) throws SQLException {
        String autor="";
        createUserConnection();
        try {
            myStatement = myConnection.createStatement();
            ResultSet rs = myStatement.executeQuery("SELECT * FROM room where room.idRoom='" + Integer.toString(id) + "'");
            rs.next();
            autor=rs.getString("numeCreator");
            rs.close();
        }catch (SQLException e) {
            e.printStackTrace();
        }
        myStatement.close();
        myConnection.close();
        return autor;
    }
    public void createRoom(String username) throws SQLException {
        createUserConnection();
        try {
            myStatement = myConnection.createStatement();
            myStatement.executeUpdate("INSERT INTO room (room.nrJucatori,room.numeCreator) values ('"+Integer.toString(0)+ "'"+", '"+username+"')");
            myStatement.close();
            myConnection.close();
            enterRoom(username,getIdRoom(username));

        }catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public ArrayList<String> getPlyaersFromRoom(int id)
    {  createUserConnection();
        ArrayList<String> player = new ArrayList<>();
        try {
            myStatement = myConnection.createStatement();
            ResultSet rs2 = myStatement.executeQuery("SELECT * FROM usersroom where idusersRoom='"+Integer.toString(id)+"'");
            while (rs2.next())
            {
                int id2 = rs2.getInt("idusersRoom");
                String username2 = rs2.getString("username");
                player.add(username2);
            }
            rs2.close();
            myStatement.close();
            myConnection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return player;
    }
    public ArrayList<Room> getRooms() {
        createUserConnection();
        ArrayList<Room> rooms = new ArrayList<Room>();
        try {
            myStatement = myConnection.createStatement();
            ResultSet rs = myStatement.executeQuery("SELECT * FROM room");
            while (rs.next()) {
                int id = rs.getInt("idRoom");
                int nrJucatori = rs.getInt("nrJucatori");
                String creator = rs.getString("numeCreator");
                Room room = new Room(id, nrJucatori, creator);
                //users.add(user);
                //ArrayList<String> player = new ArrayList<>();
                room.setJucatori(getPlyaersFromRoom(id));
                rooms.add(room);
            }
            if (rooms.isEmpty()) {
                System.out.println("Nu exista nici o camera in  baza de date");
            }
            rs.close();
            myStatement.close();
            myConnection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rooms;
    }
    public ArrayList<User> getAccounts() {
        createUserConnection();
        ArrayList<User> users = new ArrayList<User>();
        try {
            myStatement = myConnection.createStatement();
            ResultSet rs = myStatement.executeQuery("SELECT * FROM users");
            while (rs.next()) {
                int id = rs.getInt("idUsers");
                String username = rs.getString("username");
                String password = rs.getString("password");
                String userType = rs.getString("usertype");
                User user = new User(id, username, password, userType);
                users.add(user);
            }
            if (users.isEmpty()) {
                System.out.println("Nu exista niciun user in  baza de date");
            }
            rs.close();
            myStatement.close();
            myConnection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public Connection getMyConnection() {
        return myConnection;
    }

    public void setMyConnection(Connection myConnection) {
        ConnectionDB.myConnection = myConnection;
    }

    public Statement getMyStatement() {
        return myStatement;
    }

    public void setMyStatement(Statement myStatement) {
        this.myStatement = myStatement;
    }

    public ResultSet getMyResultSet() {
        return myResultSet;
    }

    public void setMyResultSet(ResultSet myResultSet) {
        this.myResultSet = myResultSet;
    }
}