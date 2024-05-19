package tn.esprit.models;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Events {

    private int id;
    private String name, state, description, img;
    private float price;
    private Date date;
    private List<Activities> activities;

    public Events(String name, String state, String description, String img, float price, Date date) {
        this.name = name;
        this.state = state;
        this.description = description;
        this.img = img;
        this.price = price;
        this.date = date;
    }

    public Events(int id, String name, String state, String description, String img, float price, Date date) {
        this.id = id;
        this.name = name;
        this.state = state;
        this.description = description;
        this.img = img;
        this.price = price;
        this.date = date;
    }

    public Events() {
    }

    public Events(String name, String state, String description, String img, float price) {

        this.name = name;
        this.state = state;
        this.description = description;
        this.img = img;
        this.price = price;
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

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<Activities> getActivities() {
        return activities;
    }

    public void setActivities(List<Activities> activities) {
        this.activities = activities;
    }

    public List<String> getActivityNames() {
        List<String> activityNames = new ArrayList<>();
        if (activities != null) {
            for (Activities activity : activities) {
                activityNames.add(activity.getName());
            }
        }
        return activityNames;
    }



    public void addActivity(Activities activity) {
        activities.add(activity);
    }

    @Override
    public String toString() {
        return "Events{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", state='" + state + '\'' +
                ", description='" + description + '\'' +
                ", img='" + img + '\'' +
                ", price=" + price +
                ", date=" + date +
                '}';
    }

    // Format date as per Symfony's DateInterval format
    private String formatDate(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        return formatter.format(date);
    }
}
