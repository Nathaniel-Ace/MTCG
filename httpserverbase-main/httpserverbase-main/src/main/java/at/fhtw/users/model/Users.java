package at.fhtw.users.model;

import com.fasterxml.jackson.annotation.JsonAlias;

public class Users {

    @JsonAlias({"username"})
    private String username;
    @JsonAlias({"password"})
    private String password;

    public Users() {}

    public Users(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @JsonAlias({"username"})
    public String getUsername() {return username;}

    @JsonAlias({"username"})
    public void setUsername(String username) {this.username = username;}

    @JsonAlias({"password"})
    public String getPassword() {return password;}

    @JsonAlias({"password"})
    public void setPassword(String password) {this.password = password;}
}
