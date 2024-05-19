package tn.esprit.models;

import java.sql.Date;

public class Certificate {
    private int id;
    private String p_name;
    private String c_title;
    private Date date;
    private String signature;
    private Course course;



    public Certificate() {
    }

    public Certificate(String p_name, String c_title, Date date, String signature, Course course) {
        this.p_name = p_name;
        this.c_title = c_title;
        this.date = date;
        this.signature = signature;
        this.course = course;
    }

    public Certificate(int id, String p_name, String c_title, Date date, String signature, Course course) {
        this.id = id;
        this.p_name = p_name;
        this.c_title = c_title;
        this.date = date;
        this.signature = signature;
        this.course = course;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getP_name() {
        return p_name;
    }

    public void setP_name(String p_name) {
        this.p_name = p_name;
    }

    public String getC_title() {
        return c_title;
    }

    public void setC_title(String c_title) {
        this.c_title = c_title;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }


    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }



    @Override
    public String toString() {
        return "Certificate{" +
                "id=" + id +
                ", p_name='" + p_name + '\'' +
                ", c_title='" + c_title + '\'' +
                ", date=" + date +
                ", signature='" + signature +
                '}';
    }
}
