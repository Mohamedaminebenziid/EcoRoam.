package tn.esprit.controller.forum;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import tn.esprit.models.Account;
import tn.esprit.models.Post;
import tn.esprit.models.PostAudience;
import tn.esprit.models.Reactions;
import javafx.scene.input.MouseEvent;
import tn.esprit.services.Listener;
import tn.esprit.services.ServicePost;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class TemplatePost {

    @FXML
    private ImageView imgProfile;

    @FXML
    private Label username;

    @FXML
    private ImageView imgVerified;

    @FXML
    private Label date;

    @FXML
    private ImageView audience;

    @FXML
    private Label caption;

    @FXML
    private ImageView imgPost;

    @FXML
    private Label nbReactions;

    @FXML
    private Label nbComments;

    @FXML
    private Label nbShares;

    @FXML
    private HBox reactionsContainer;

    @FXML
    private ImageView imgLike;

    @FXML
    private ImageView imgLove;

    @FXML
    private ImageView imgCare;

    @FXML
    private ImageView imgHaha;

    @FXML
    private ImageView imgWow;

    @FXML
    private ImageView imgSad;

    @FXML
    private ImageView imgAngry;

    @FXML
    private HBox likeContainer;

    @FXML
    private ImageView imgReaction;

    @FXML
    private Label reactionName;

    private long startTime = 0;
    private Reactions currentReaction;
    private Post post;
    public static int idShare=0;
    private Listener listener;

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    @FXML
    public void onLikeContainerPressed(MouseEvent me){
        startTime = System.currentTimeMillis();
    }
    @FXML
    void gotoComments(MouseEvent event) {
        FXMLLoader fxmlLoader=new FXMLLoader(getClass().getResource("/newcomment.fxml"));
        Parent root;
        try {

            root=fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        NewComment controller =fxmlLoader.getController();

        controller.setId(post.getIdPost());
        controller.afficherComments(post.getIdPost());
        Stage stage=new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }
    @FXML
    public void onLikeContainerMouseReleased(MouseEvent me){
        if(System.currentTimeMillis() - startTime > 500){
            reactionsContainer.setVisible(true);
        }else {
            if(reactionsContainer.isVisible()){
                reactionsContainer.setVisible(false);
            }
            if(currentReaction == Reactions.NON){
                setReaction(Reactions.LIKE);
            }else{
                setReaction(Reactions.NON);
            }
        }
    }

    @FXML
    public void onReactionImgPressed(MouseEvent me){
        switch (((ImageView) me.getSource()).getId()){
            case "imgLove":
                setReaction(Reactions.LOVE);
                break;
            case "imgCare":
                setReaction(Reactions.CARE);
                break;
            case "imgHaha":
                setReaction(Reactions.HAHA);
                break;
            case "imgWow":
                setReaction(Reactions.WOW);
                break;
            case "imgSad":
                setReaction(Reactions.SAD);
                break;
            case "imgAngry":
                setReaction(Reactions.ANGRY);
                break;
            default:
                setReaction(Reactions.LIKE);
                break;
        }
        reactionsContainer.setVisible(false);
    }

    public void setReaction(Reactions reaction){
        Image image = new Image(getClass().getResourceAsStream(reaction.getImgSrc()));
        imgReaction.setImage(image);
        reactionName.setText(reaction.getName());
        reactionName.setTextFill(Color.web(reaction.getColor()));

        if(currentReaction == Reactions.NON){
            post.setTotalReactions(post.getTotalReactions() + 1);
        }

        currentReaction = reaction;

        if(currentReaction == Reactions.NON){
            post.setTotalReactions(post.getTotalReactions() - 1);
        }

        nbReactions.setText(String.valueOf(post.getTotalReactions()));
    }

    public void setData(Post post){
        this.post = post;
        Image img;
        /*img = new Image(getClass().getResourceAsStream(post.getAccount().getProfileImg()));
        imgProfile.setImage(img);
        username.setText(post.getAccount().getName());
        if(post.getAccount().isVerified()){
            imgVerified.setVisible(true);
        }else{
            imgVerified.setVisible(false);
        }*/

        //date.setText(post.getDateP());
        if(post.getAudience() .equals( PostAudience.PUBLIC)){
            img = new Image(getClass().getResourceAsStream(PostAudience.PUBLIC.getImgSrc()));
        }else{
            img = new Image(getClass().getResourceAsStream(PostAudience.FRIENDS.getImgSrc()));
        }
        audience.setImage(img);

        if(post.getDescriptionP() != null && !post.getDescriptionP().isEmpty()){

            if(post.getHashtagP() != null && !post.getHashtagP().isEmpty()){
                caption.setText(post.getDescriptionP()+"\n"+post.getHashtagP());
            }
            else{
                caption.setText(post.getDescriptionP());
            }
        }else{
            caption.setManaged(false);
        }


        if(post.getImageP() != null && !post.getImageP().isEmpty()){
            img = new Image(getClass().getResourceAsStream("/forum/img"+post.getImageP()));
            imgPost.setImage(img);
        }else{
            imgPost.setVisible(false);
            imgPost.setManaged(false);
        }

        nbReactions.setText(String.valueOf(post.getTotalReactions()));
        nbComments.setText(post.getNbComments() + " comments");
        nbShares.setText(post.getNbShares()+" shares");

        currentReaction = Reactions.NON;
    }
    @FXML
    void gotosharepost(MouseEvent event) {
        idShare=post.getIdPost();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/share.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            System.err.println("Erreur lors du chargement du fichier FXML : " + e.getMessage());
        }
    }

    private Post getPost(){
        Post post = new Post();
        Account account = new Account();
        account.setName("Islem cherni");
        account.setProfileImg("/img/user.png");
        account.setVerified(true);
        post.setAccount(account);
        post.setDateP("Feb 18, 2021 at 12:00 PM");
        post.setAudience(PostAudience.PUBLIC);
        post.setDescriptionP("amazing travel experience in Ecoroam!");
        post.setImageP("/img/bg .jpg");

        post.setTotalReactions(20);
        post.setNbComments(2);
        post.setNbShares(3);

        return post;
    }
@FXML

    public void initialize() {
        //setData(getPost());
    }
    ServicePost servicePost=new ServicePost();
    @FXML
    void supprimerClicked(MouseEvent event) {
        if(listener!=null){
            servicePost.supprimer(post.getIdPost());
            listener.onSupprimerPostClicked();
        }
    }
    @FXML
    void modifierClicked(MouseEvent event) {
        if(listener!=null){
            listener.onModifierPostClicked(post);
        }
    }
}
