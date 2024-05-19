package tn.esprit.services;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.SQLException;

import javafx.geometry.Pos;
import tn.esprit.models.Post;
import tn.esprit.models.PostAudience;
import tn.esprit.util.MyConnection;
import tn.esprit.models.Account;
import java.sql.ResultSet;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


public class ServicePost {
      Connection cnx;

      public boolean ajouterPost(Post p) {

            Connection cnx = MyConnection.getInstance().getCnx();


            boolean a = false;
            try {
                  String req = MessageFormat.format("INSERT INTO post(id,client_id, description_p, hashtag_p, visibilite, image_p) VALUES ({0},{1}, ''{2}'', ''{3}'', ''{4}'', ''{5}'')",p.getIdPost(), p.getIdc(), p.getDescriptionP(), p.getHashtagP(), p.getVisibilite(), p.getImageP());

                  Statement st = cnx.createStatement();
                  st.executeUpdate(req);
                  System.out.println("Post ajoutée");
                  a = true;
            } catch (SQLException ex) {
                  System.out.println(ex.getMessage());
            }
            return a;
      }
      public boolean updatePost(Post post) {
            Connection cnx = MyConnection.getInstance().getCnx();
            boolean isSuccess = false;
            try {
                  String req = MessageFormat.format(
                          "UPDATE post SET description_p=''{0}'', hashtag_p=''{1}'', visibilite=''{2}'', image_p=''{3}'' WHERE id={4}",
                          post.getDescriptionP(), post.getHashtagP(), post.getVisibilite(), post.getImageP(), post.getIdPost()
                  );

                  Statement st = cnx.createStatement();
                  int result = st.executeUpdate(req);

                  if (result > 0) {
                        System.out.println("Post updated successfully");
                        isSuccess = true;
                  } else {
                        System.out.println("No post was updated. Please check the provided ID.");
                  }
            } catch (SQLException ex) {
                  System.out.println("Failed to update the post: " + ex.getMessage());
            }
            return isSuccess;
      }

      public boolean supprimer(int id){
            Connection cnx = MyConnection.getInstance().getCnx();


            boolean a = false;
            try {
                  String req="DELETE from post where id="+id;
                  Statement st = cnx.createStatement();
                  st.executeUpdate(req);
                  System.out.println("Post supprimer");
                  a = true;
            } catch (SQLException ex) {
                  System.out.println(ex.getMessage());
            }
            return a;
      }

      public boolean UserPost(int id ,int idc) {
            List<Post> posts = new ArrayList<>();
            try {
                  String req = "select * from post where client_id = " + idc +" and id = "+id;
                  Statement st = cnx.createStatement();
                  ResultSet rs = st.executeQuery(req);

                  while (rs.next()) {
                        Post p = new Post();
                        p.setIdPost(rs.getInt("id"));
                        p.setDescriptionP(rs.getString("description_p"));
                        p.setHashtagP(rs.getString("hashtag_p"));
                        p.setVisibilite(rs.getString("audience"));
                        p.setDateP(rs.getString("date_p"));
                        p.setImageP(rs.getString("image_p"));
                        p.setIdc(rs.getInt("client_id"));
                        if(p.getVisibilite().equals("Public")){
                              p.setAudience(PostAudience.PUBLIC);
                        }else{
                              p.setAudience(PostAudience.FRIENDS);
                        }
                        posts.add(p);
                  }
                  System.out.print(posts);

            } catch (SQLException ex) {
                  System.out.println(ex.getMessage());
            }
            return !posts.isEmpty();
      }
      public List<Post> afficher() {
            Connection cnx = MyConnection.getInstance().getCnx();
            List<Post> posts = new ArrayList<>();
            try {
                  String req = "select * from post";
                  Statement st = cnx.createStatement();
                  ResultSet rs = st.executeQuery(req);

                  while (rs.next()) {
                        Post p = new Post();
                        p.setIdPost(rs.getInt("id"));
                        p.setDescriptionP(rs.getString("description_p"));
                        p.setHashtagP(rs.getString("hashtag_p"));
                        //p.setDateP(rs.getString("date_p"));
                        p.setImageP(rs.getString("image_p"));
                        p.setIdc(rs.getInt("client_id"));
                        p.setVisibilite(rs.getString("visibilite"));
                        if(p.getVisibilite().equals("Public")){
                              p.setAudience(PostAudience.PUBLIC);
                        }else{
                              p.setAudience(PostAudience.FRIENDS);
                        }
                        posts.add(p);
                  }
                  System.out.print(posts);

            } catch (SQLException ex) {
                  System.out.println(ex.getMessage());
            }
            return posts;
      }
      public Post getPostById(int id){
            return afficher()
                    .stream()
                    .filter(p->p.getIdPost()==id)
                    .findFirst().orElse(null);
      }
      public List<Post> triPostParCritere(String critere){
            switch (critere){
                  case "Description":
                        return afficher().stream()
                                .sorted(Comparator.comparing(Post::getDescriptionP))
                                .collect(Collectors.toList());
                  case "Hashtag":
                        return afficher().stream()
                                .sorted(Comparator.comparing(Post::getHashtagP))
                                .collect(Collectors.toList());
                  case "Visibilite":
                        return afficher().stream()
                                .sorted(Comparator.comparing(Post::getVisibilite))
                                .collect(Collectors.toList());
                  default:
                        return afficher();
            }
      }

}









