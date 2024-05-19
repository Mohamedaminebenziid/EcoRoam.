package tn.esprit.models;


public class Categories {
    private String intitule;

    private int id;
        //contructeurs
    public Categories() {
    }

    public Categories(String intitule, int id) {
        this.intitule = intitule;
        this.id = id;
    }

    public Categories(String intitule) {
        this.intitule = intitule;
    }
//getter and setter

    public String getIntitule() {
        return intitule;
    }

    public void setIntitule(String intitule) {
        this.intitule = intitule;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    //Display
    @Override
    public String toString() {
        return intitule ;
    }
}
