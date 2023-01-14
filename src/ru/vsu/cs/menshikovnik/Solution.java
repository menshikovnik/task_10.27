package ru.vsu.cs.menshikovnik;

import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

public class Solution {

    public static List<Student> searchTheBestStudent(ArrayList<Student> list) {
        List<Student> best = new ArrayList<>();
        List<Student> woman = new ArrayList<>();
        List<Student> men = new ArrayList<>();
        for (Student student : list) {
            if (student.sex.equals("Женский")) {
                woman.add(student);
            }
        }
        for (Student student : list) {
            if (student.sex.equals("Мужской")) {
                men.add(student);
            }
        }
        Collections.sort(men);
        Collections.sort(woman);
        for (int numCourse = 1; numCourse <= 4; numCourse++) {
            for (Student student : men) {
                if (student.course == numCourse) {
                    best.add(student);
                }
            }
            for (Student student : woman) {
                if (student.course == numCourse) {
                    best.add(student);
                }
            }
        }
        for (int i = 0; i < best.size() - 1; i++) {
            Student student = best.get(i);
            Student student1 = best.get(i + 1);
            if (student.course == student1.course) {
                if (student.sex.equals("Мужской") == student1.sex.equals("Мужской") || student.sex.equals("Женский") == student1.sex.equals("Женский")) {
                    if (student.averageScore < student1.averageScore) {
                        best.remove(student);
                        i = -1;
                    } else {
                        best.remove(student1);
                        i = -1;
                    }
                }
            }
        }
        return best;
    }
}