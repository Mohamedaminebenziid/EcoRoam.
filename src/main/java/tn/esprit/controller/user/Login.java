package tn.esprit.controller.user;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import tn.esprit.services.UserCrud;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tn.esprit.util.MyConnection;

import java.io.IOException;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Login {

    @FXML
    private Button login;
    @FXML
    private CheckBox showpassword;

    @FXML
    private Button inscrit;

    @FXML
    private TextField usereamiltextfiled;

    @FXML
    private TextField userpasswordtextfiled;

    private Connection connection;
    private final UserCrud usercrud = new UserCrud(connection);

    @FXML
    void login(ActionEvent event) throws IOException {
        String email = usereamiltextfiled.getText();
        String password = userpasswordtextfiled.getText();

        // Validation des informations de connexion
        if (usercrud.isValidLogin(email, password)) {
            try {
                String username = "";
                String tel_number = "";
                String role = "";
                try (PreparedStatement statement = MyConnection.getInstance().getCnx().prepareStatement("SELECT * FROM user WHERE email = ?")) {
                    statement.setString(1, email);

                    try (ResultSet resultSet = statement.executeQuery()) {
                        if (resultSet.next()) {
                            username = resultSet.getString("username");
                            tel_number = resultSet.getString("tel_number");
                            role = resultSet.getString("roles");
                        } else {
                            System.out.println("Aucun utilisateur trouvé avec cet email.");
                            // Print a debug message if no user is found with the provided email
                            return;
                        }
                    }
                }

                if ("admin".equals(username)) {
                    // Redirect to admin menu

                    try {
                        // Load the FXML file for the update destination page
                        Parent root = FXMLLoader.load(getClass().getResource("/backoffice/destinationtable.fxml"));

                        // Get the current stage from the ActionEvent's source
                        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

                        // Create a new scene with the loaded FXML root and set it on the stage
                        Scene scene = new Scene(root);
                        stage.setScene(scene);
                        stage.show();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    // Redirect to user info page

                    try {
                        // Load the FXML file for the update destination page
                        Parent root = FXMLLoader.load(getClass().getResource("/frontoffice/destinationdesplay.fxml"));

                        // Get the current stage from the ActionEvent's source
                        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

                        // Create a new scene with the loaded FXML root and set it on the stage
                        Scene scene = new Scene(root);
                        stage.setScene(scene);
                        stage.show();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                    Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                    successAlert.setTitle("Login Successful");
                    successAlert.setHeaderText(null);
                    successAlert.setContentText("Login successful! Welcome, " + email + "!");
                    successAlert.showAndWait();
                }
            } catch ( SQLException e) {
                e.printStackTrace();
                // Print the stack trace if an IOException or SQLException occurs
            }
        } else {
            // Invalid login, show error message
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Login Error");
            alert.setHeaderText("Invalid username or password");
            alert.setContentText("Please check your username and password and try again.");
            alert.showAndWait();
        }
    }

    @FXML
    void inscrit(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/user/signin.fxml"));
            Stage stage = (Stage) inscrit.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


      @FXML
      public void qccode(ActionEvent actionEvent) {
          String data = "http://127.0.0.1:8000/sign_up";
          String path = "C:\\Users\\pc\\ecoroam\\ecoroam\\QR2.jpg";
          BitMatrix matrix = null;
          try {
              matrix = new MultiFormatWriter().encode(data, BarcodeFormat.QR_CODE, 500, 500);
          } catch (WriterException ex) {
              throw new RuntimeException(ex);
          }
          try {
              MatrixToImageWriter.writeToPath(matrix, "jpg", Paths.get(path));
          } catch (IOException ex) {
              throw new RuntimeException(ex);
          }
          System.out.println("QR Code généré avec succès.");

          // Afficher une alerte de succès
          Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
          successAlert.setTitle("QR Code généré");
          successAlert.setHeaderText(null);
          successAlert.setContentText("Le QR Code a été généré avec succès !");
          successAlert.showAndWait();
      }

    public void userpasswordtextfiled(ActionEvent actionEvent) {
    }


        @FXML
        public void forgetpassword(ActionEvent actionEvent) {

            try {
                Parent root = FXMLLoader.load(getClass().getResource("/user/CodeVerification.fxml"));
                Stage stage = (Stage) inscrit.getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }


        @FXML
        public void showpassword(ActionEvent actionEvent) {

            if (showpassword.isSelected()) {
                userpasswordtextfiled.setText(userpasswordtextfiled.getText());
            } else {
                userpasswordtextfiled.setText("");
            }
        }
    }








