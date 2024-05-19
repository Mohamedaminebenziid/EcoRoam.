package tn.esprit.models;

import java.util.Objects;

public class user {

    private int id;
    private String username;

    private String email;
    private String adresse;

    private String password;
    private String roles;
    private String tel_number;

    private boolean is_banned;
    private String reset_token;


    // Constructeur avec tous les attributs

    public user() {
    }

    public user(String username, String email, String adresse, String password, String roles, String tel_number, boolean is_banned) {
        this.username = username;
        this.email = email;
        this.adresse = adresse;
        this.password = password;
        this.roles = roles;
        this.tel_number = tel_number;
        this.is_banned = is_banned;
    }

    public user(int id, String username, String email, String adresse, String password, String roles, String tel_number, boolean is_banned) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.adresse = adresse;
        this.password = password;
        this.roles = roles;
        this.tel_number = tel_number;
        this.is_banned = is_banned;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getReset_token() {
        return reset_token;
    }

    public void setReset_token(String reset_token) {
        this.reset_token = reset_token;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public boolean isIs_banned() {
        return is_banned;
    }

    public void setIs_banned(boolean is_banned) {
        this.is_banned = is_banned;
    }

    public String getTel_number() {
        return tel_number;
    }

    public void setTel_number(String tel_number) {
        this.tel_number = tel_number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        user user = (user) o;
        return id == user.id && is_banned == user.is_banned && Objects.equals(adresse, user.adresse) && Objects.equals(email, user.email) && Objects.equals(username, user.username) && Objects.equals(password, user.password) && Objects.equals(reset_token, user.reset_token) && Objects.equals(roles, user.roles) && Objects.equals(tel_number, user.tel_number);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, adresse, email, username, password, reset_token, roles, is_banned, tel_number);
    }

    @Override
    public String toString() {
        return "user{" +
                "id=" + id +
                ", adresse='" + adresse + '\'' +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", reset_token='" + reset_token + '\'' +
                ", roles='" + roles + '\'' +
                ", is_banned=" + is_banned +
                ", tel_number='" + tel_number + '\'' +
                '}';
    }
}


