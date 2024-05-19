  package tn.esprit.models;

public class Course {
    private int id ;
    private String title ;
    private String description ;
    private String duration ;
    private String difficulty ;
    private String category ;
    private String image ;

    public Course() {
    }

    public Course(String title, String description, String duration, String difficulty, String category, String image) {
        this.title = title;
        this.description = description;
        this.duration = duration;
        this.difficulty = difficulty;
        this.category = category;
        this.image = image;
    }

    public Course(int id, String title, String description, String duration, String difficulty, String category, String image) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.duration = duration;
        this.difficulty = difficulty;
        this.category = category;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", duration='" + duration + '\'' +
                ", difficulty='" + difficulty + '\'' +
                ", category='" + category + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
}
