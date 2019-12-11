package models;

import java.io.Serializable;

public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer idUser;

    private String password;

    private String userType;

    private String username;

    public User() {
    }

    public Integer getIdUser() {
        return this.idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserType() {
        return this.userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public User(String username, String password, String userType) {
        super();
        this.password = password;
        this.userType = userType;
        this.username = username;
    }

    public User(String username, String password) {
        super();
        this.password = password;
        this.username = username;
    }

    public User(int i, String string, String string2, String string3) {
        super();
        this.idUser =i;
        this.username = string;
        this.password = string2;
        this.userType = string3;
    }

    @Override
    public String toString() {
        return "Utilizatori [idutilizator=" + idUser + ", password=" + password + ", tiputilizator="
                + userType + ", username=" + username + "]";
    }
}