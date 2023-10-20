package Tema1;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Vector;


public class Catalog implements Subject{
    private static Catalog catalog = null;
    ArrayList<Course> courses;
    private Catalog() {
        courses = new ArrayList<>();
    }
    public static Catalog getInstance()
    {
        if(catalog == null)
            catalog = new Catalog();
        return catalog;
    }
    // Adauga un curs în catalog
    public void addCourse(Course course) {
        courses.add(course);
    }
    // Sterge un curs din catalog
    public void removeCourse(Course course) {
        courses.remove(course);
    }
    private List<Observer> observers = new ArrayList<>();
    @Override
    public void addObserver(Observer observer) {
        if(observer == null) throw new NullPointerException("Null Observer");
        if(!observers.contains(observer))
                observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(Grade grade) {
        for(int i =0; i< observers.size(); i++)
            observers.get(i).update(new Notification(grade));
    }
}