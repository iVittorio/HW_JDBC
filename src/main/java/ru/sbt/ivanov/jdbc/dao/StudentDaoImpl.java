package ru.sbt.ivanov.jdbc.dao;

import ru.sbt.ivanov.jdbc.domain.Student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * Created by i.viktor on 13/10/2016.
 */
public class StudentDaoImpl implements StudentDao {
    private final Connection connection;

    public StudentDaoImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Student findById(long id) {
        return getFirst(execute("select id, firstName, secondName from Students where id  = ?", s -> s.setLong(1, id)));
    }

    @Override
    public List<Student> findByFirstName(String name) {
        return execute("select id, firstName, secondName from Students where firstName  = ?", s -> s.setString(1, name));
    }

    @Override
    public List<Student> findBySecondName(String surname) {
        return execute("select id, firstName, secondName from Students where secondName  = ?", s -> s.setString(1, surname));
    }

    @Override
    public List<Student> studentsByDate(Date date) {
        return execute("SELECT id, firstname, secondname\n" +
                "FROM STUDENTS\n" +
                "WHERE ID IN (\n" +
                "  SELECT STUDENT_ID\n" +
                "  FROM STUDENTS_VISITS\n" +
                "  WHERE LESSON_ID IN (SELECT id\n" +
                "                       FROM LESSONS\n" +
                "                       WHERE DATE = ?)\n" +
                ")", s -> s.setDate(1, new java.sql.Date(date.getTime())));
    }

    @Override
    public List<Student> studentsByLesson(String lesson) {
        return execute("SELECT DISTINCT id, FIRSTNAME, SECONDNAME\n" +
                "FROM STUDENTS, STUDENTS_VISITS\n" +
                "WHERE STUDENTS_VISITS.LESSON_ID IN (SELECT id\n" +
                "FROM LESSONS\n" +
                "WHERE LESSON_NAME = ?)", s -> s.setString(1, lesson));
    }

    private Student getFirst(List<Student> list) {
        return list.isEmpty() ? null : list.get(0);
    }

    private List<Student> execute(String sql, Consumer<PreparedStatement> consumer) {
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            consumer.accept(statement);
            ResultSet resultSet = statement.executeQuery();

            List<Student> studentList = new ArrayList<>();
            while (resultSet.next()) {
                long id = resultSet.getLong("id");
                String name = resultSet.getString("firstName");
                String surname = resultSet.getString("secondName");
                studentList.add(new Student(id, name, surname));
            }

            return studentList;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private interface Consumer<T> {
        void accept(T t) throws Exception;
    }
}
