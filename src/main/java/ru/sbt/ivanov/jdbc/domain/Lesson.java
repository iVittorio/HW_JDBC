package ru.sbt.ivanov.jdbc.domain;

import java.util.Date;

/**
 * Created by i.viktor on 13/10/2016.
 */
public class Lesson {
    private final long id;
    private final String lessonName;
    private final Date date;

    public Lesson(Date date, long id, String lessonName) {
        this.date = date;
        this.id = id;
        this.lessonName = lessonName;
    }

    public Date getDate() {
        return date;
    }

    public long getId() {
        return id;
    }

    public String getLessonName() {
        return lessonName;
    }

    @Override
    public String toString() {
        return "Lesson{" +
                "date=" + date +
                ", id=" + id +
                ", lessonName='" + lessonName + '\'' +
                '}';
    }
}
