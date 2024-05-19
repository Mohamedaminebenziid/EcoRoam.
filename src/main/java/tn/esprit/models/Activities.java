package tn.esprit.models;

import java.util.List;

public class Activities {

    //id,name,img,state,price,description
    private int id;
    private String name, img, state, description;
    private float price;
    private List<Events> events;

    public Activities() {
    }

    public Activities(String name, String img, String state, String description, float price) {
        this.name = name;
        this.img = img;
        this.state = state;
        this.description = description;
        this.price = price;
    }

    public Activities(int id, String name, String img, String state, String description, float price) {
        this.id = id;
        this.name = name;
        this.img = img;
        this.state = state;
        this.description = description;
        this.price = price;
    }

    public Activities(int i, int i1, String name, String image, String california, String testDescription, int i2) {
    }


    public Activities(String activityName) {
        this.name=activityName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public List<Events> getEvents() {
        return events;
    }

    public void setEvents(List<Events> events) {
        this.events = events;
    }

    @Override
    public String toString() {
        return "Activities{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", img='" + img + '\'' +
                ", state='" + state + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                '}';
    }
}
