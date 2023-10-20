package Tema1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ScoreVisitor implements Visitor{
    private static ScoreVisitor scoreVisitor = null;
    public static ScoreVisitor getInstance()
    {
        if(scoreVisitor == null)
            scoreVisitor = new ScoreVisitor();
        return scoreVisitor;
    }
    HashMap<Teacher, ArrayList<Tuple<Student,String,Double>>> examScores;
    HashMap<Assistant,ArrayList<Tuple<Student,String,Double>>> partialScores;

    public ScoreVisitor()
    {
        examScores = new HashMap<>();
        partialScores = new HashMap<>();
    }
    ArrayList<Tuple<Student,String,Double>> examtuple ;
    public void addExamScore(Teacher teacher, Student student,String course, Double examScore)
    {
        if(!examScores.containsKey(teacher))
        {
            examScores.put(teacher, new ArrayList<>());
            examtuple = new ArrayList<>();
        }

        examtuple.add(new Tuple<>(student,course,examScore));
        examScores.put(teacher,examtuple);
    }
    public void addPartialScore(Assistant assistant, Student student,String course, Double examScore)
    {
        if(!partialScores.containsKey(assistant))
        {
            partialScores.put(assistant, new ArrayList<>());
            examtuple = new ArrayList<>();
        }

        examtuple.add(new Tuple<>(student,course,examScore));
        partialScores.put(assistant,examtuple);

    }
    @Override
    public void visit(Assistant assistant) {
        Tuple<Student,String,Double> aux;
        Catalog catalog = Catalog.getInstance();
        Grade grade;
        for(Map.Entry<Assistant,ArrayList<Tuple<Student,String,Double>>>entry : partialScores.entrySet())
        {
            for(int j = 0; j< entry.getValue().size(); j++) {
                aux = entry.getValue().get(j);
                for (int i = 0; i < catalog.courses.size(); i++) {
                    if (catalog.courses.get(i).getName().equals(aux.course)) {

                        if(catalog.courses.get(i).getGrade(aux.student) == null)
                        {

                            grade = new Grade(aux.nota, 0.0);
                            grade.setStudent(aux.student);
                            grade.setCourse(aux.course);
                            catalog.courses.get(i).addGrade(grade);

                        }
                        else
                        {
                            catalog.courses.get(i).getGrade(aux.student).setPartialScore(aux.nota);
                            catalog.courses.get(i).getGrade(aux.student).setStudent(aux.student);
                            catalog.courses.get(i).getGrade(aux.student).setCourse(aux.course);

                        }
                        catalog.notifyObservers(catalog.courses.get(i).getGrade(aux.student));
                    }
                }
            }
        }
    }


    @Override
    public void visit(Teacher teacher) {
        Tuple<Student,String,Double> aux;
        Catalog catalog = Catalog.getInstance();
        Grade grade;
        for(Map.Entry<Teacher,ArrayList<Tuple<Student,String,Double>>>entry : examScores.entrySet())
        {
            for(int j = 0; j< entry.getValue().size(); j++) {
                aux = entry.getValue().get(j);
                for (int i = 0; i < catalog.courses.size(); i++) {
                    if (catalog.courses.get(i).getName().equals(aux.course)) {

                        if(catalog.courses.get(i).getGrade(aux.student) == null)
                        {
                            grade = new Grade(0.0,aux.nota);
                            grade.setStudent(aux.student);
                            grade.setCourse(aux.course);
                            catalog.courses.get(i).addGrade(grade);
                        }
                        else
                        {
                            catalog.courses.get(i).getGrade(aux.student).setPartialScore(aux.nota);
                            catalog.courses.get(i).getGrade(aux.student).setStudent(aux.student);
                            catalog.courses.get(i).getGrade(aux.student).setCourse(aux.course);
                        }

                        catalog.notifyObservers(catalog.courses.get(i).getGrade(aux.student));
                    }
                }
            }
        }
    }
    private class Tuple<K,V,N>{
        private K student;
        private V course;
        private N nota;

        public Tuple(K student, V course, N nota) {
            this.student = student;
            this.course = course;
            this.nota = nota;
        }
        //getter student
        public K getStudent() {
            return student;
        }
        //setter student
        public void setStudent(K student) {
            this.student = student;
        }
        //getter course
        public V getCourse() {
            return course;
        }
        //setter course
        public void setCourse(V course) {
            this.course = course;
        }
        //getter nota
        public N getNota() {
            return nota;
        }
        //setter nota
        public void setNota(N nota) {
            this.nota = nota;
        }

        @Override
        public String toString() {
            return   student + " , " + course + " , " + nota + "\n";
        }
    }
}
