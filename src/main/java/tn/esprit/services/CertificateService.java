package tn.esprit.services;

import tn.esprit.interfaces.CService;
import tn.esprit.models.Certificate;
import tn.esprit.models.Course;
import tn.esprit.util.MyConnection;
import tn.esprit.util.MyConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CertificateService implements CService<Certificate> {

    //attribut
    Connection cnx = MyConnection.getInstance().getCnx();

    //actions
    public void add(Certificate certificate) {
        try {
            String req = "INSERT INTO certificate (p_name, c_title, date, signature, course_id) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setString(1, certificate.getP_name());
            ps.setString(2, certificate.getC_title());
            ps.setDate(3, new Date(certificate.getDate().getTime()));
            ps.setString(4, certificate.getSignature());
            ps.setInt(5, certificate.getCourse().getId());
            ps.executeUpdate();
            System.out.println("Certificate added successfully!");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void update(Certificate certificate) {
        try {
            String req = "UPDATE certificate SET p_name=?, c_title=?, date=?, signature=?, course_id=? WHERE id=?";
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setString(1, certificate.getP_name());
            ps.setString(2, certificate.getC_title());
            ps.setDate(3, new Date(certificate.getDate().getTime()));
            ps.setString(4, certificate.getSignature());
            ps.setInt(5, certificate.getCourse().getId());
            ps.setInt(6, certificate.getId());
            ps.executeUpdate();
            System.out.println("Certificate updated successfully!");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

    public void delete(int id) {
        try {
            String req = "DELETE FROM certificate WHERE id=?";
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, id);
            ps.executeUpdate();
            System.out.println("Certificate deleted successfully!");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public List<Certificate> getAllCertificate() {
        List<Certificate> certificates = new ArrayList<>();
        String req = "SELECT * FROM certificate";
        try {
            Statement st = cnx.createStatement();
            ResultSet res = st.executeQuery(req);
            while (res.next()) {
                Certificate certificate = new Certificate();
                certificate.setId(res.getInt("id"));
                certificate.setP_name(res.getString("p_name"));
                certificate.setC_title(res.getString("c_title"));
                certificate.setDate(res.getDate("date"));
                certificate.setSignature(res.getString("signature"));

                // Get the course for this certificate
                int courseId = res.getInt("course_id");
                Course course = getCourseById(courseId);
                certificate.setCourse(course);

                certificates.add(certificate);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return certificates;
    }

    @Override
    public Certificate getOneCertificate(int id) {
        try {
            // Prepare SQL query to retrieve the certificate by ID
            String req = "SELECT * FROM certificate WHERE id=?";
            PreparedStatement pst = cnx.prepareStatement(req);
            pst.setInt(1, id);

            // Execute the query
            ResultSet rs = pst.executeQuery();

            // Check if a certificate was found
            if (rs.next()) {
                // Create a Certificate object from the database data
                Certificate certificate = new Certificate();
                certificate.setId(rs.getInt("id"));
                certificate.setP_name(rs.getString("p_name"));
                certificate.setC_title(rs.getString("c_title"));
                certificate.setDate(rs.getDate("date"));
                certificate.setSignature(rs.getString("signature"));

                // Get the course for this certificate
                int courseId = rs.getInt("course_id");
                Course course = getCourseById(courseId);
                certificate.setCourse(course);

                return certificate;
            } else {
                // No certificate found with the specified ID
                return null;
            }
        } catch (SQLException e) {
            throw new IllegalArgumentException("Error while getting certificate: " + e.getMessage());
        }
    }

    private Course getCourseById(int courseId) {
        try {
            // Prepare SQL query to retrieve the course by ID
            String req = "SELECT * FROM course WHERE id=?";
            PreparedStatement pst = cnx.prepareStatement(req);
            pst.setInt(1, courseId);

            // Execute the query
            ResultSet rs = pst.executeQuery();

            // Check if a course was found
            if (rs.next()) {
                // Create a Course object from the database data
                Course course = new Course();
                course.setId(rs.getInt("id"));
                course.setTitle(rs.getString("title"));
                course.setDescription(rs.getString("description"));
                course.setDuration(rs.getString("duration"));
                course.setDifficulty(rs.getString("difficulty"));
                course.setCategory(rs.getString("category"));
                course.setImage(rs.getString("image"));
                return course;
            } else {
                // No course found with the specified ID
                return null;
            }
        } catch (SQLException e) {
            throw new IllegalArgumentException("Error while getting course: " + e.getMessage());
        }
    }
}
