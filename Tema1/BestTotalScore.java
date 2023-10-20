package Tema1;

import java.util.ArrayList;

public class BestTotalScore implements StrategyStudent{
    @Override
    public Student getbestGrade(Course course) {
        ArrayList<Grade> grades = course.getGrades();
        Double maxim = -1.0;
        Student student = null;
        for(int i = 0; i < grades.size(); i++ ) {

            if (maxim < grades.get(i).getTotal()) {
                maxim = grades.get(i).getTotal();
                student = grades.get(i).getStudent();
            }
        }
        return student;
    }
}
