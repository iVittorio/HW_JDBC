package ru.sbt.ivanov.jdbc.dao;

import ru.sbt.ivanov.jdbc.domain.Student;

import java.util.Date;
import java.util.List;

/**
 * Created by i.viktor on 13/10/2016.
 */
public interface StudentDao {

    Student findById(long id);

    List<Student> findByFirstName(String name);

    List<Student> findBySecondName(String surname);

    List<Student> studentsByDate(Date date);

    List<Student> studentsByLesson(String lesson);

}
