/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author c0642607
 */
@ApplicationScoped
@ManagedBean
public class UserController {
    private static List<User> users;
    
    public UserController() {
        users = new ArrayList<>();
        updateUsersFromDatabase();
    }
    
    public static List<User> getUsers() {
        return users;
    }
    
    public String getUsernameById(int id) {
        String ret = null;
        for(User u : users) {
            if(u.getId() == id)
                ret = u.getUsername();
        }
        return ret;
    }
        
    
    private void updateUsersFromDatabase(){
        try {
            users.clear();
            //Make a connection
            Connection conn = utils.getConnection();
            //Build a query
            String sql = "SELECT * FROM users";
            
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            
            //Parse the Results
            while(rs.next()) {
                User u = new User(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("passhash")
                );
                users.add(u);
            }         
        } catch (SQLException ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
        
}
