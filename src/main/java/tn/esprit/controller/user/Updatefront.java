package tn.esprit.controller.user;

import tn.esprit.models.user;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import tn.esprit.services.UserCrud;

import java.sql.SQLException;
public class Updatefront {

        @FXML
        private Button back;

        @FXML
        private TextField iduser;

        @FXML
        private Button ok;


        @FXML
        private TextField usereamiltextfiled;


        @FXML
        private TextField usernametextfiled;

        @FXML
        private TextField userpasswordtextfiled;

        @FXML
        private TextField userphonetextfiled;

        @FXML
        private TextField userrolesextfiled;


        @FXML
        void back(ActionEvent event) {

        }

        @FXML
        void modifieruser(ActionEvent event) {
        }

    }

