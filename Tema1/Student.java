package Tema1;

import java.util.Comparator;

public class Student extends User {
    private Parent mother,father;
    public Student(String firstName, String lastName) {
        super(firstName, lastName);
    }
    public void setMother(Parent mother) {
        this.mother = mother;
    }
    public void setFather(Parent father) {
        this.father = father;
    }

}
