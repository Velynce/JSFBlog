/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.security.MessageDigest;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author c0642607
 */
@ManagedBean
@SessionScoped
public class Login {

    private String username;
    private String password;
    private boolean loggedIn;
    private User currentUser;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public boolean isLoggedIn() {
        return loggedIn;
    }
    
    public User getCurrentUser() {
        return currentUser;
    }

    public String doLogin() {
        String ret = null;
        String passhash = utils.hash(password);
        UserController uc = new UserController();
        for (User u : uc.getUsers()) {
            if (username.equals(u.getUsername()) 
                    && passhash.equals(u.getPasshash())) {
                loggedIn = true;
                currentUser = u;
                ret =  "index";
            } else {
                loggedIn = false;
                currentUser = null;
                ret = "login";
            }
        }
        return "index";
    }

}
