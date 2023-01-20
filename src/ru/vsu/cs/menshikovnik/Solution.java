package ru.vsu.cs.menshikovnik;

import java.util.*;

public class Solution {

    public static List<Student> searchTheBestStudent(ArrayList<Student> list) {
        int maxMen = 0;
        int maxWoman = 0;
        List<Student> best = new ArrayList<>();
        List<Student> woman = new ArrayList<>();
        List<Student> men = new ArrayList<>();
        List<Student> bestMenRepeat = new ArrayList<>();
        List<Student> bestWomanRepeat = new ArrayList<>();
        for (Student student : list) {
            if (student.sex.equals("Женский")) {
                woman.add(student);
            }
            if (student.sex.equals("Мужской")) {
                men.add(student);
            }
        }
        Random rnd = new Random();
        for (int numCourse = 1; numCourse <= 4; numCourse++) {
            for (Student student : men) {
                if (student.averageScore > maxMen && student.course == numCourse) {
                    maxMen = student.averageScore;
                }
            }
            for (Student student : men) {
                if (student.averageScore == maxMen && student.course == numCourse) {
                    for (Student student1 : men) {
                        if (student.averageScore == student1.averageScore && student1.course == numCourse) {
                            bestMenRepeat.add(student1);
                        }
                    }
                    Student randomElement = bestMenRepeat.get(rnd.nextInt(bestMenRepeat.size()));
                    best.add(randomElement);
                    bestMenRepeat.clear();
                    maxMen = 0;
                    break;
                }
            }
            for (Student student : woman) {
                if (student.averageScore > maxWoman && student.course == numCourse) {
                    maxWoman = student.averageScore;
                }
            }
            for (Student student : woman) {
                if (student.averageScore == maxWoman && student.course == numCourse) {
                    for (Student student1 : woman) {
                        if (student.averageScore == student1.averageScore && student1.course == numCourse) {
                            bestWomanRepeat.add(student1);
                        }
                    }
                    Student randomElement1 = bestWomanRepeat.get(rnd.nextInt(bestWomanRepeat.size()));
                    best.add(randomElement1);
                    bestWomanRepeat.clear();
                    maxWoman = 0;
                    break;
                }
            }
        }
        for (int i = 0; i < best.size() - 1; ) {
            Student student = best.get(i);
            Student student1 = best.get(i + 1);
            if (student.course == student1.course) {
                if (i == best.size() - 2) {
                    break;
                }
                i += 2;
                student = best.get(i);
                if (i != best.size() - 1) student1 = best.get(i + 1);
            }
            if (i != best.size() - 1) {
                if (student.course != student1.course) {
                    best.remove(student);
                    student = best.get(i);
                }
            }
            if (i == best.size() - 1) best.remove(student);
        }
        return best;
    }
}