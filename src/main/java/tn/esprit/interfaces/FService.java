package tn.esprit.interfaces;
import tn.esprit.models.Comment;



import java.util.List;

public interface FService <T> {

    public void Ajouter(T t);
    public void modifier(T t);
    public void SupprimerComment(int ID);
    public List<Comment> recuperer(int idp);
    public boolean UserPost(int id ,int idc);




}
