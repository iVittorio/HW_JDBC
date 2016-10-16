package ru.sbt.ivanov.jdbc.dao;

import ru.sbt.ivanov.jdbc.domain.Student;

import java.util.Date;
import java.util.List;

/**
 * Created by i.viktor on 13/10/2016.
 */
public interface LessonDao {

    List<Date> studentVisits(String name);

    int countOfVisits(String name);

}
