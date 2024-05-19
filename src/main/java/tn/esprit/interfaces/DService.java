package tn.esprit.interfaces;

import tn.esprit.models.Course;

import java.util.List;

public interface DService<T> {
    //CRUD
    //1
    void add(T t);
    void update(T t);
    //3
    void delete(T t);
    //4
    List<T> getAll();
    //5
    T getOne(int id);

    List<Course> getAllc();

    Course getOnec(int id);
}
