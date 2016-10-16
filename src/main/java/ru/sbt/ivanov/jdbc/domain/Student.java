package ru.sbt.ivanov.jdbc.domain;

/**
 * Created by i.viktor on 13/10/2016.
 */
public class Student {
    private final long id;
    private final String firstName;
    private final String secondName;

    public Student(long id, String firstName, String secondName) {
        this.firstName = firstName;
        this.id = id;
        this.secondName = secondName;
    }

    public long getId() {
        return id;
    }

    public String getSecondName() {
        return secondName;
    }

    public String getFirstName() {
        return firstName;
    }

    @Override
    public String toString() {
        return "Student{" +
                "firstName='" + firstName + '\'' +
                ", id=" + id +
                ", secondName='" + secondName + '\'' +
                '}';
    }
}
