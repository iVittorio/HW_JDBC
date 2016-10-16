package ru.sbt.ivanov.jdbc.domain;

/**
 * Created by i.viktor on 13/10/2016.
 */
public class Student_visit {
    private final long studentId;
    private final long lesson_id;

    public Student_visit(long lesson_id, long studentId) {
        this.lesson_id = lesson_id;
        this.studentId = studentId;
    }

    public long getLesson_id() {
        return lesson_id;
    }

    public long getStudentId() {
        return studentId;
    }

    @Override
    public String toString() {
        return "Student_visit{" +
                "lesson_id=" + lesson_id +
                ", studentId=" + studentId +
                '}';
    }
}
