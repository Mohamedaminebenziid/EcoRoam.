package tn.esprit.controller.forum;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import tn.esprit.models.Comment;
import tn.esprit.services.CommentService;
import tn.esprit.services.Listener;

import java.io.IOException;
import java.util.Optional;

public class AffichComment {


    @FXML
    private ImageView imgProfile;

    @FXML
    private ImageView imgVerified;

    @FXML
    private Label username;
    private int id;
    @FXML
    private Label username1;

    @FXML
    private Pane lcomment2;

    @FXML
    private Label lcomment;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    CommentService commentService=new CommentService();
    Comment comment;
    private Listener listener;

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    @FXML
    void onDeleteClicked(MouseEvent event) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Confirmation");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to delete this post?");

        ButtonType buttonTypeYes = new ButtonType("Yes");
        ButtonType buttonTypeNo = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == buttonTypeYes) {
            try {

                CommentService commentService = new CommentService();
                Comment Comment = new Comment();
                Comment.setId(id); // Set the appropriate comment id
                commentService.SupprimerComment(id);


                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/Profile.fxml"));
                    Parent root = loader.load();
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();

            }
        }

    }
    void setData(Comment comment){
        this.comment=comment;
        System.out.println(comment);
        lcomment.setText(comment.getCommentaire()+"\n"+comment.getDateC());
    }


    @FXML
    void onModifierClicked(MouseEvent event ){

    }
    @FXML
    void modifiercomment(MouseEvent event) {
        if(listener!=null){
            listener.onModifierClicked(comment);
        }
    }

    @FXML
    void supprimercomment(MouseEvent event) {
        if(listener!=null){
            commentService.SupprimerComment(comment.getId());
            listener.onSupprimerClicked();
        }


    }


}
