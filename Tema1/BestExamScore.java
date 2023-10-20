package Tema1;

import java.util.ArrayList;

public class BestExamScore implements StrategyStudent{

    @Override
    public Student getbestGrade(Course course) {
        ArrayList<Grade> grades = course.getGrades();
        Double maxim = -1.0;
        Student student = null;
        for(int i = 0; i < grades.size(); i++ )
            if(maxim < grades.get(i).getExamScore() ) {
                maxim = grades.get(i).getExamScore();
                student = grades.get(i).getStudent();
            }
        return student;
    }
}
