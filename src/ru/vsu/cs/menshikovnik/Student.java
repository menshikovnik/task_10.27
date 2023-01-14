package ru.vsu.cs.menshikovnik;

public class Student implements Comparable{
    String name;
    String sex;
    int course;
    int averageScore;

    Student(String name, String sex, int course, int averageScore){
        this.name = name;
        this.sex = sex;
        this.course = course;
        this.averageScore = averageScore;
    }
    public int compareTo(Object o){
        Student f = (Student) o;
        return this.course - f.course;
    }
}
