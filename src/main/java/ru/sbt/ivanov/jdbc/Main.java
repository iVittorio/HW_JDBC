package ru.sbt.ivanov.jdbc;

import ru.sbt.ivanov.jdbc.dao.LessonDao;
import ru.sbt.ivanov.jdbc.dao.LessonDaoImpl;
import ru.sbt.ivanov.jdbc.dao.StudentDao;
import ru.sbt.ivanov.jdbc.dao.StudentDaoImpl;
import ru.sbt.ivanov.jdbc.domain.Student;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

/**
 * Created by i.viktor on 13/10/2016.
 */
public class Main {
    public static void main(String[] args) {

        try(Connection connection = DriverManager.getConnection("jdbc:h2:~/myDB", "admin","")) {
            //createTable(connection);

            StudentDao studentDao = new StudentDaoImpl(connection);
            LessonDao lessonDao = new LessonDaoImpl(connection);

            Student student = studentDao.findById(2L);
            List<Student> studentList1 = studentDao.findByFirstName("Jack");

            List<Student> studentList2 = studentDao.findBySecondName("Alonso");

            List<Student> studentList3 = studentDao.studentsByLesson("java");

            List<Date> dateList = lessonDao.studentVisits("Jack");

            Date date = dateList.get(0);

            List<Student> studentList4 = studentDao.studentsByDate(date);

            System.out.println(student);
            System.out.println(studentList1);
            System.out.println(studentList2);
            System.out.println(studentList3);
            System.out.println(dateList);
            System.out.println(lessonDao.countOfVisits("Dan"));
            System.out.println(studentList4);

        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    private static void createTable(Connection connection) throws SQLException {
        connection.createStatement().executeUpdate("CREATE TABLE Students" +
                "(" +
                "id int primary key," +
                "firstName varchar(255)," +
                "secondName varchar(255)," +
                ");");
    }

}
