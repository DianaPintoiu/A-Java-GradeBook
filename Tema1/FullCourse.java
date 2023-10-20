package Tema1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FullCourse extends Course{
    public FullCourse(FullCourseBuilder builder)
    {
        super(builder);
    }

    @Override
    public ArrayList<Student> getGraduatedStudents() {
        ArrayList<Student> graduatedStudents = new ArrayList<>();
        HashMap<Student,Grade> studentGradeMap = gettAllStudentGrades();
        for(Map.Entry<Student,Grade>entry : studentGradeMap.entrySet())
        {
            if(entry.getValue().getPartialScore() >= 3 && entry.getValue().getExamScore() >=2)
                graduatedStudents.add(entry.getKey());
        }
        return graduatedStudents;
    }
    public static class  FullCourseBuilder extends CourseBuilder{

        public FullCourseBuilder(String name,Teacher teacher,String strategyStudent) {
            super(name,teacher,strategyStudent);
        }

        @Override
        public Course build() {
            return new FullCourse(this);
        }

    }
}
