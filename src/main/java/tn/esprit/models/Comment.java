package tn.esprit.models;

public class Comment {
    private int id;
    private int idPost;
    private int idClient;
    private String dateC;
    private String commentaire;

    public Comment() {
    }

    public Comment(int idPost, int idClient, String dateC, String commentaire) {
        this.idPost = idPost;
        this.idClient = idClient;
        this.dateC = dateC;
        this.commentaire = commentaire;
    }

    public Comment(int id, int idPost, int idClient, String dateC, String commentaire) {
        this.id = id;
        this.idPost = idPost;
        this.idClient = idClient;
        this.dateC = dateC;
        this.commentaire = commentaire;
    }
  //getters et setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdPost() {
        return idPost;
    }

    public void setIdPost(int idPost) {
        this.idPost = idPost;
    }

    public int getIdClient() {
        return idClient;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }

    public String getDateC() {
        return dateC;
    }

    public void setDateC(String dateC) {
        this.dateC = dateC;
    }


    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

   
}

