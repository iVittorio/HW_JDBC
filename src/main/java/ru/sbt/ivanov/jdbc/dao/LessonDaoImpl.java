package ru.sbt.ivanov.jdbc.dao;

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
    public int countOfVisits(String name) {
        return studentVisits(name).size();
    }
}
