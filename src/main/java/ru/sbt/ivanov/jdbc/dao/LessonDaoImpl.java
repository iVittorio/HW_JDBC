package ru.sbt.ivanov.jdbc.dao;

import ru.sbt.ivanov.jdbc.domain.Student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by i.viktor on 16/10/2016.
 */
public class LessonDaoImpl implements LessonDao {
    private final Connection connection;

    public LessonDaoImpl(Connection connection) {
        this.connection = connection;
    }


    @Override
    public List<Student> studentsByLesson(String lesson) {

        try {
            PreparedStatement statement = connection.prepareStatement("SELECT DISTINCT id, FIRSTNAME, SECONDNAME\n" +
                    "FROM STUDENTS, STUDENTS_VISITS\n" +
                    "WHERE STUDENTS_VISITS.LESSON_ID IN (SELECT id\n" +
                    "FROM LESSONS\n" +
                    "WHERE LESSON_NAME = ?)");
            statement.setString(1, lesson);
            ResultSet resultSet = statement.executeQuery();

            List<Student> studentList = new ArrayList<>();
            while (resultSet.next()) {
                long id = resultSet.getLong("id");
                java.lang.String name = resultSet.getString("firstName");
                java.lang.String surname = resultSet.getString("secondName");
                studentList.add(new Student(id, name, surname));
            }

            return studentList;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public List<Date> studentVisits(String name) {

        try {
            PreparedStatement statement = connection.prepareStatement("SELECT DATE\n" +
                    "FROM LESSONS\n" +
                    "WHERE ID IN (\n" +
                    "  SELECT LESSON_ID\n" +
                    "  FROM STUDENTS_VISITS\n" +
                    "  WHERE STUDENT_ID IN (SELECT id\n" +
                    "                       FROM STUDENTS\n" +
                    "                       WHERE FIRSTNAME = ?))");
            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();

            List<Date> dateList = new ArrayList<>();

            while (resultSet.next()) {
                dateList.add(resultSet.getDate("DATE"));
            }
            return dateList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Student> studentsByDate(Date date) {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT id, firstname, secondname\n" +
                    "FROM STUDENTS\n" +
                    "WHERE ID IN (\n" +
                    "  SELECT STUDENT_ID\n" +
                    "  FROM STUDENTS_VISITS\n" +
                    "  WHERE LESSON_ID IN (SELECT id\n" +
                    "                       FROM LESSONS\n" +
                    "                       WHERE DATE = ?)\n" +
                    ")");
            statement.setDate(1, new java.sql.Date(date.getTime()));
            ResultSet resultSet = statement.executeQuery();

            List<Student> studentList = new ArrayList<>();

            while (resultSet.next()) {
                long id = resultSet.getLong("id");
                String name = resultSet.getString("firstName");
                String surname = resultSet.getString("secondName");
                studentList.add(new Student(id, name, surname));
            }
            return studentList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int countOfVisits(String name) {
        return studentVisits(name).size();
    }
}
