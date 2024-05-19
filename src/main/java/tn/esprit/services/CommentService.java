package tn.esprit.services;

import tn.esprit.interfaces.FService;
import tn.esprit.interfaces.IService;
import tn.esprit.models.Comment;
import tn.esprit.util.MyConnection;
import tn.esprit.util.FilterBadWord;
import tn.esprit.util.MyConnection;

import java.sql.Statement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;

public class CommentService implements FService<Comment> {


    Connection cnx = MyConnection.getInstance().getCnx();




    @Override
    public void Ajouter(Comment t) {
        try {
            String req = "INSERT INTO `comment`(`idPost`, `idClient`, `dateC`, `commentaire`) VALUES ('" + t.getIdPost() + "','" + t.getIdClient() + "','" + t.getDateC() + "','" + FilterBadWord.filter(t.getCommentaire()) + "')";
            Statement st = cnx.createStatement();
            st.executeUpdate(req);
            System.out.println("Comment ajoutée");


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public void modifier(Comment t) {
        try {
            String req = "UPDATE comment SET commentaire=? WHERE id=?";
            PreparedStatement ps = cnx.prepareStatement(req);

            ps.setString(1, FilterBadWord.filter(t.getCommentaire()));
            ps.setInt(2, t.getId());
            ps.executeUpdate();
            System.out.println("Comment modifié avec succès");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }


    public void SupprimerComment(int id) {
        try
        {
            Statement st = cnx.createStatement();
            String req = "DELETE FROM comment WHERE id = "+id+"";
            st.executeUpdate(req);
            System.out.println("Comment supprimer avec succès...");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public List<Comment> recuperer(int idp) {
        List<Comment> comments = new ArrayList<>();
        try {
            String req = "select * from comment where idPost= "+idp;
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);

            while (rs.next()) {
                Comment p = new Comment();
                p.setId(rs.getInt("id"));
                p.setCommentaire(rs.getString("commentaire"));
                p.setDateC(rs.getString("dateC"));
                p.setIdClient(rs.getInt("idClient"));
                p.setIdPost(idp);
                comments.add(p);
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return comments;
    }

    //Le commentaire appartient-il à l'utilisateur//idc et i
    public boolean UserPost(int id ,int idc) {
        List<Comment> posts = new ArrayList<>();
        try {
            String req = "select * from comment where idClient = " + idc +" and id = "+id;
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);

            while (rs.next()) {
                Comment p = new Comment();
                p.setId(rs.getInt("id"));

                posts.add(p);
            }
            // System.out.print(!posts.isEmpty());

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        if (posts.size()==0)
            return false ;
        else
            return true;
    }



}



