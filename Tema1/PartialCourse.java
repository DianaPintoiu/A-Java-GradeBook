package Tema1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PartialCourse extends Course{
    public PartialCourse(PartialCourseBuilder builder){
        super(builder);
    }
    @Override
    public ArrayList<Student> getGraduatedStudents() {
        ArrayList<Student> graduatedStudents = new ArrayList<>();
        HashMap<Student,Grade> studentGradeMap = gettAllStudentGrades();
        for(Map.Entry<Student,Grade>entry : studentGradeMap.entrySet())
        {
            if(entry.getValue()!= null)
            if(entry.getValue().getTotal() >=5 )
                graduatedStudents.add(entry.getKey());
        }
        return graduatedStudents;
    }

    public static class PartialCourseBuilder extends CourseBuilder{

        public PartialCourseBuilder(String name,Teacher teacher,String strategyStudent) {
            super(name,teacher,strategyStudent);
        }

        @Override
        public Course build() {
            return new PartialCourse(this);
        }

    }
}
