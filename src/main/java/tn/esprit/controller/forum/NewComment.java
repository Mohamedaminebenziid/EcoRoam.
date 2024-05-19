package tn.esprit.controller.forum;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;

import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import tn.esprit.models.Comment;
import tn.esprit.models.Post;
import tn.esprit.services.CommentService;
import tn.esprit.services.Listener;


import java.util.Date;
import java.util.List;


public class NewComment implements Listener {


    @FXML
    private TextArea textcomment;

    @FXML
    private ImageView imgProfile;

    @FXML
    private ImageView imgVerified;

    @FXML
    private ImageView send;
    @FXML
    private GridPane grid;
    @FXML
    private TextField tfrecherche;
    @FXML
    private Label username;
    private int id;

    public void setId(int id) {
        this.id = id;
    }
    @FXML
            void initialize(){
    }
    CommentService commentaireService = new CommentService();
    void afficherComments(int id){
        grid.getChildren().clear();
        int row=1;

        List<Comment> list=commentaireService.recuperer(id);
        System.out.println(list);
        for(Comment c:list){
            FXMLLoader fxmlUrl =new FXMLLoader( getClass().getResource("/comment.fxml"));
            if (fxmlUrl == null) {
                System.err.println("Erreur : Fichier templatePost.fxml non trouvé !");
            } else {
                try {
                    VBox anchorPane = fxmlUrl.load();
                    AffichComment controller=fxmlUrl.getController();
                    controller.setData(c);
                    controller.setListener(this);
                    grid.add(anchorPane,0,row++);

                } catch (Exception e) {
                    System.err.println("Erreur lors du chargement du fichier FXML : " + e.getMessage());
                }
            }
        }
        recherche_avance();
    }
    @FXML
    void onSendClicked(MouseEvent event) {


        String contenu = textcomment.getText();
        if (!contenu.trim().isEmpty()) {
            Comment comment = new Comment();
            comment.setIdClient(1);
            comment.setIdPost(id);//idPost
            comment.setCommentaire(contenu);
            Date date=new Date();
            comment.setDateC(date.toString());
            if(idModifier==0){
                commentaireService.Ajouter(comment); // Correction de l'appel de méthode
                System.out.println("Comment added successfully");
            }
            else{
                comment.setId(idModifier);
                commentaireService.modifier(comment);
                idModifier=0;
            }

            afficherComments(id);

        } else {
            // Display an alert box indicating that the post content cannot be empty
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Empty Post Content");
            alert.setHeaderText(null);
            alert.setContentText("Comment content cannot be empty. Please enter some text.");
            alert.showAndWait();
        }

    }
    void recherche_avance(){
        ObservableList<Comment> data= FXCollections.observableArrayList(commentaireService.recuperer(id));
        FilteredList<Comment> filteredList=new FilteredList<>(data,c->true);
        tfrecherche.textProperty().addListener((observable,oldValue,newValue)->{
            filteredList.setPredicate(c->{
                if(newValue.isEmpty() || newValue==null){
                    return true;
                }
                if(c.getCommentaire().toLowerCase().contains(newValue.toLowerCase())){
                    return true;
                }
                else if(c.getDateC().toString().contains(newValue)){
                    return true;
                }
                else{
                    return false;
                }
            });
            int row=1;
            grid.getChildren().clear();

            for(Comment c:filteredList){
                FXMLLoader fxmlUrl =new FXMLLoader( getClass().getResource("/comment.fxml"));
                if (fxmlUrl == null) {
                    System.err.println("Erreur : Fichier templatePost.fxml non trouvé !");
                } else {
                    try {
                        VBox anchorPane = fxmlUrl.load();
                        AffichComment controller=fxmlUrl.getController();
                        controller.setData(c);
                        controller.setListener(this);
                        grid.add(anchorPane,0,row++);

                    } catch (Exception e) {
                        System.err.println("Erreur lors du chargement du fichier FXML : " + e.getMessage());
                    }
                }
            }
        });

    }


    public int getId() {
        return id;
    }

    public ImageView getImgProfile() {
        return imgProfile;
    }



    public TextArea getTextcomment() {
        return textcomment;
    }

    public ImageView getSend() {
        return send;
    }

    public Label getUsername() {
        return username;
    }

    public void setUsername(Label username) {
        this.username = username;
    }

    public void setImgProfile(ImageView imgProfile) {
        this.imgProfile = imgProfile;
    }

    public void setTextcomment(TextArea textcomment) {
        this.textcomment = textcomment;
    }

    public void setSend(ImageView send) {
        this.send = send;
    }

    @Override
    public void onSupprimerClicked() {
        afficherComments(id);
    }
    int idModifier=0;
    @Override
    public void onModifierClicked(Comment comment) {
        idModifier=comment.getId();
        textcomment.setText(comment.getCommentaire());
    }

    @Override
    public void onSupprimerPostClicked() {

    }

    @Override
    public void onModifierPostClicked(Post post) {

    }
}







