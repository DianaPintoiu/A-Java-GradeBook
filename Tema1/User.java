package Tema1;

public abstract class User {
    private String firstName, lastName;
    public User(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }
    public String toString() {
        return firstName + " " + lastName;
    }
    //getter pentru firstName
    public String getFirstName() {
        return firstName;
    }
    //getter pentru lastName
    public String getLastName() {
        return lastName;
    }
}
