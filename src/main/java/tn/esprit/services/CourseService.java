package tn.esprit.services;
import tn.esprit.interfaces.DService;
import tn.esprit.models.Course;
import tn.esprit.util.MyConnection;
/*import javax.validation.constraints.NotNull;*/
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CourseService implements DService<Course> {
    //att
    Connection cnx = MyConnection.getInstance().getCnx();
    //actions



  public void addc( Course course) {
      // Validation de la saisie

        try {
            String req = "INSERT INTO `course` (`title`, `descrition`, `duration`, `difficulty`, `category`, `image`) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setString(1, course.getTitle());
            ps.setString(2, course.getDescription());
            ps.setString(3, course.getDuration());
            ps.setString(4, course.getDifficulty());
            ps.setString(5, course.getCategory());
            ps.setString(6, course.getImage());
            ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void updatec(Course course){
        try {
            String req = "UPDATE course SET title=?, descrition=?, duration=?, difficulty=?, category=?, image=? WHERE id=?";
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setString(1, course.getTitle());
            ps.setString(2, course.getDescription());
            ps.setString(3, course.getDuration());
            ps.setString(4, course.getDifficulty());
            ps.setString(5, course.getCategory());
            ps.setString(6, course.getImage());
            ps.setInt(7, course.getId());
            ps.executeUpdate();
            System.out.println("Course Updated Successfully!");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }


    public void deletec(Course course){
        try {
            String req = "DELETE FROM course WHERE id=?";
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, course.getId());
            ps.executeUpdate();
            System.out.println("Course Deleted Successfully!");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    public void deleteCourse(int id) {
        try {
            String req = "DELETE FROM course WHERE id=?";
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, id);
            ps.executeUpdate();
            System.out.println("Course with ID " + id + " Deleted Successfully!");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }


    private boolean validateCourse(Course course) {
        // Vérification de la présence des champs obligatoires et de leur longueur
        if (course.getTitle() == null || course.getTitle().isEmpty() || course.getTitle().length() > 255) {
            return false;
        }
        if (course.getDescription() == null || course.getDescription().isEmpty() || course.getDescription().length() > 1000) {
            return false;
        }
        if (course.getDuration() == null || course.getDuration().isEmpty() || course.getDuration().length() > 50) {
            return false;
        }
        if (course.getDifficulty() == null || course.getDifficulty().isEmpty() || course.getDifficulty().length() > 50) {
            return false;
        }
        if (course.getCategory() == null || course.getCategory().isEmpty() || course.getCategory().length() > 50) {
            return false;
        }
        if (course.getImage() == null || course.getImage().isEmpty() || course.getImage().length() > 255) {
            return false;
        }
        // Ajoutez ici d'autres validations si nécessaire
        return true;
    }

    @Override
    public void add(Course course) {

    }

    @Override
    public void update(Course course) {

    }

    @Override
    public void delete(Course course) {

    }

    @Override
    public List<Course> getAll() {
        return null;
    }

    @Override
    public Course getOne(int id) {
        return null;
    }

    @Override
    public List<Course> getAllc() {
        List<Course> courses = new ArrayList<>();
        String req = "SELECT * FROM course";
        try {
            Statement st = cnx.createStatement();
            ResultSet res = st.executeQuery(req);
            while (res.next()) {
                Course course = new Course();
                course.setId(res.getInt("id"));
                course.setTitle(res.getString("title"));
                course.setDescription(res.getString("descrition"));
                course.setDuration(res.getString("duration"));
                course.setDifficulty(res.getString("difficulty"));
                course.setCategory(res.getString("category"));
                course.setImage(res.getString("image"));
                courses.add(course);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return courses;
    }

    @Override
    public Course getOnec(int id) {
        try {
            // Préparer la requête SQL pour récupérer le cours par ID
            String req = "SELECT * FROM Course WHERE id=?";
            PreparedStatement pst = cnx.prepareStatement(req);
            pst.setInt(1, id);

            // Exécuter la requête
            ResultSet rs = pst.executeQuery();

            // Vérifier si un cours a été trouvé
            if (rs.next()) {
                // Créer un objet Course à partir des données de la base de données
                Course course = new Course();
                course.setId(rs.getInt("id"));
                course.setTitle(rs.getString("title"));
                course.setDescription(rs.getString("descrition"));
                course.setDuration(rs.getString("duration"));
                course.setDifficulty(rs.getString("difficulty"));
                course.setCategory(rs.getString("category"));
                course.setImage(rs.getString("image"));
                return course;
            } else {
                // Aucun cours trouvé avec l'ID spécifié
                return null;
            }
        } catch (SQLException e) {
            throw new IllegalArgumentException("Error while getting course: " + e.getMessage());
        }
    }

}
