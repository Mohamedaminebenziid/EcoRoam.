package tn.esprit.models;
import java.util.Arrays;
import java.util.Objects;

public class Destination {

    private int id;
    private String name;
    private String img; // Changed from byte[] to String
    private String state;
    private String address;
    private float price; // Changed from prix to price
    private String description;

    public Destination() {
    }

    public Destination(String name, String img, String state, String address, float price, String description) { // Changed img to String
        this.name = name;
        this.img = img;
        this.state = state;
        this.address = address;
        this.price = price; // Changed prix to price
        this.description = description;
    }

    public Destination(int id, String name, String img, String state, String address, float price, String description) { // Changed img to String
        this.id = id;
        this.name = name;
        this.img = img;
        this.state = state;
        this.address = address;
        this.price = price; // Changed prix to price
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImg() { // Changed return type to String
        return img;
    }

    public String getState() {
        return state;
    }

    public String getAddress() {
        return address;
    }

    public float getPrice() { // Changed getPrix to getPrice
        return price;
    }

    public String getDescription() {
        return description;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImg(String img) { // Changed parameter type to String
        this.img = img;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPrice(float price) { // Changed setPrix to setPrice
        this.price = price;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Destination{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", img='" + img + '\'' +
                ", state='" + state + '\'' +
                ", address='" + address + '\'' +
                ", price=" + price +
                ", description='" + description + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Destination that = (Destination) obj;
        return id == that.id &&
                Float.compare(that.price, price) == 0 &&
                name.equals(that.name) &&
                Objects.equals(img, that.img) && // Changed from img.equals(that.img)
                state.equals(that.state) &&
                address.equals(that.address) &&
                description.equals(that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, img, state, address, price, description);
    }
}
