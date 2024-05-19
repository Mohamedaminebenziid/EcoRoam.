package tn.esprit.controller.forum;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import tn.esprit.models.Comment;
import tn.esprit.models.Post;
import tn.esprit.models.PostAudience;
import tn.esprit.services.Listener;
import tn.esprit.services.ServicePost;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;

public class EcoroamPost implements Listener {

    @FXML
    private ImageView imgProfile;

    @FXML
    private Label username;

    @FXML
    private ImageView gotopost;

    @FXML
    private ImageView imgeProfile;

    @FXML
    private TextField tfimage;

    @FXML
    private TextField tfpost;

    @FXML
    private TextField tfhashtag;

    @FXML
    private ComboBox<String> cbvisibilite;

    @FXML
    private ImageView addpost;

    @FXML
    private GridPane grid;
    ServicePost servicePost=new ServicePost();
    @FXML
    private ComboBox<String> cbtri;

    @FXML
    private TextField tfrecherche;

    @FXML
    private ImageView playMusic;



    @FXML
    void tri(ActionEvent event) {
        grid.getChildren().clear();
        int row=1;
        List<Post> posts=servicePost.triPostParCritere(cbtri.getValue());
        for(Post p: posts){
            FXMLLoader fxmlUrl =new FXMLLoader( getClass().getResource("/templatePost.fxml"));
            if (fxmlUrl == null) {
                System.err.println("Erreur : Fichier templatePost.fxml non trouvé !");
            } else {
                try {
                    VBox anchorPane = fxmlUrl.load();
                    TemplatePost controller=fxmlUrl.getController();
                    controller.setData(p);
                    grid.add(anchorPane,0,row++);

                } catch (Exception e) {
                    System.err.println("Erreur lors du chargement du fichier FXML : " + e.getMessage());
                }
            }
        }
    }
    @FXML
    void initialize(){
        cbvisibilite.getItems().setAll("Public","Friends");
        cbtri.getItems().setAll("Description","Hashtag","Visibilite");
        afficherPost();
    }
    @FXML
    void AddPoste(MouseEvent event) {
        Post post=new Post();
        post.setHashtagP(tfhashtag.getText());
        post.setDescriptionP(tfpost.getText());
        post.setImageP(tfimage.getText());
        post.setVisibilite(cbvisibilite.getValue());
        post.setIdc(1);
        post.setNbComments(0);
        post.setNbShares(0);
        post.setTotalReactions(0);
        if(idModifier!=0){
            post.setIdPost(idModifier);
            servicePost.updatePost(post);
            afficherPost();
            Notifications
                    .create()
                    .title("Updated post")
                    .text("Post updated successfuly")
                    .hideAfter(Duration.seconds(5))
                    .showConfirm();
            idModifier=0;
        }
        else{
            servicePost.ajouterPost(post);
            afficherPost();
            Notifications
                    .create()
                    .title("Add post")
                    .text("Post added successfuly")
                    .hideAfter(Duration.seconds(5))
                    .showConfirm();
        }


    }
    public void afficherPost(){
        grid.getChildren().clear();
        int row=1;
        List<Post> posts=servicePost.afficher();
        for(Post p: posts){
            FXMLLoader fxmlUrl =new FXMLLoader( getClass().getResource("/forum/templatePost.fxml"));
            if (fxmlUrl == null) {
                System.err.println("Erreur : Fichier templatePost.fxml non trouvé !");
            } else {
                try {
                    VBox anchorPane = fxmlUrl.load();
                    TemplatePost controller=fxmlUrl.getController();
                    controller.setData(p);
                    controller.setListener(this);
                    grid.add(anchorPane,0,row++);

                } catch (Exception e) {
                    System.err.println("Erreur lors du chargement du fichier FXML : " + e.getMessage());
                }
            }
        }
    }

    @FXML
    void uploadimage(MouseEvent event) {
        FileChooser fileChooser=new FileChooser();
        File file=fileChooser.showOpenDialog(tfhashtag.getScene().getWindow());
        if(file!=null){
            tfimage.setText(file.getName());
        }

    }
    @FXML
    void setPlayMusic(MouseEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/MusicPlayer.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            System.err.println("Erreur lors du chargement du fichier FXML : " + e.getMessage());
        }

    }


    @Override
    public void onSupprimerClicked() {

    }

    @Override
    public void onModifierClicked(Comment comment) {

    }

    @Override
    public void onSupprimerPostClicked() {
        afficherPost();
    }
    int idModifier=0;
    @Override
    public void onModifierPostClicked(Post post) {
        idModifier=post.getIdPost();
        tfpost.setText(post.getDescriptionP());
        tfhashtag.setText(post.getHashtagP());
        tfimage.setText(post.getImageP());
        cbvisibilite.setValue(post.getVisibilite());
    }
}