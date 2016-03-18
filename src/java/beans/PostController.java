/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.util.ArrayList;
import java.util.List;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

/**
 *
 * @author c0642607
 */
@ApplicationScoped
@ManagedBean
public class PostController {

    private List<Post> posts;
    private Post currentPost;

    public Post getCurrentPost() {
        return currentPost;
    }

    public PostController() {
        posts = new ArrayList<>();
        updatePostsFromDatabase();
    }

    public List<Post> getPosts() {
        return posts;
    }
    
    public Post getPostById(int id) {
        Post ret = null;
        for(Post p : posts) {
            if(p.getId() == id)
            ret = p;
        }
        return ret;
    }
    
    public Post getPostByTitle(String title) {
        Post ret = null;
        for(Post p : posts) {
            if(p.getTitle().equals(title)) {
                ret = p;
            }
        }
        return ret;
    }

    private void updatePostsFromDatabase() {
        try {
            posts.clear();
            //Make a connection
            Connection conn = utils.getConnection();
            //Build a query
            String sql = "SELECT * FROM posts";

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            //Parse the Results
            while (rs.next()) {
                Post p = new Post(
                        rs.getInt("id"),
                        rs.getInt("user_id"),
                        rs.getString("title"),
                        rs.getTimestamp("created_time"),
                        rs.getString("contents")
                );
                posts.add(p);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PostController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String savePost() throws SQLException {
        try {
            //Make a connection
            Connection conn = utils.getConnection();
            if (true) {
                //Build a query
                String sql = "UPDATE posts WHERE id = ? SET title = ?, contents = ?";

                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.setInt(1, currentPost.getId());
                pstmt.setString(2, currentPost.getTitle());
                pstmt.setString(3, currentPost.getContents());
                pstmt.executeUpdate();
            } else {
                String sql = "INSERT INTO posts(id, user_id, title, created_time, contents) values(?,?,?,?,?)";
                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.setInt(1, currentPost.getId());
                pstmt.setString(2, currentPost.getTitle());
                pstmt.setString(3,currentPost.getContents());
                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            Logger.getLogger(PostController.class.getName()).log(Level.SEVERE, null, e);
        }
         updatePostsFromDatabase();
         currentPost = getPostByTitle(currentPost.getTitle());
        return "viewPost";
    }
    
    public String addPost() {
        return "editPost";
    }
    
    public String deletePost() {
        return "viewPost";
    }

    public String viewPost(Post post) {
        return "viewPost";
    }

}
