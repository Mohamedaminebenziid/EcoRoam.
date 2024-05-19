package tn.esprit.models;

public class ActivityStatistic {
    private String state;
    private int count;

    public ActivityStatistic(String state, int count) {
        this.state = state;
        this.count = count;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}

