package Tema1;

import java.util.*;

public abstract class Course {
    private String name;
    private String strategy = null;
   private Teacher teacher;
    //o multime de asistenti (colectie fără duplicate)
    private HashSet<Assistant> assistants;
    //o colectie ordonată
    private ArrayList<Grade> grades;
    // un dictionar ce contine grupele (cheia este de ID-ul grupei, iar valoarea este grupa)
    private HashMap<String,Group> groupMap;

    //numarul de puncte de credit
    private int no_credit_points;
    protected Course(CourseBuilder builder)
    {
        this.name = builder.name;
        this.strategy = builder.strategy;
        this.teacher = builder.teacher;
        this.assistants = builder.assistants;
        this.groupMap = builder.groupMap;
        this.no_credit_points = builder.no_credit_points;
        grades = new ArrayList<>();
    }
    //setter pentru strategy
    public void setStrategy(String strategy)
    {
        this.strategy = strategy;
    }
    //getter pentru strategy
    public String getStrategy(){return strategy;}
    //getter pentru name
    public String getName() {
        return name;
    }
    //setter pentru name
    public void setName(String name) {
        this.name = name;
    }
    //getter pentru teacher
    public Teacher getTeacher() {
        return teacher;
    }
    //setter pentru teacher
    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }
    //getter pentru assistants
    public HashSet<Assistant> getAssistants() {
        return assistants;
    }
    //setter pentru assistants
    public void setAssistants(HashSet<Assistant> assistants) {
        this.assistants = assistants;
    }
    //getter pentru grades
    public ArrayList<Grade> getGrades() {
        return grades;
    }
    //setter pentru grades
    public void setGrades(ArrayList<Grade> grades) {
        this.grades = grades;
    }
    //getter pentru dictionar groupMap
    public Map<String, Group> getGroupMap() {
        return groupMap;
    }
    //setter pentru dictionar groupMap
    public void setGroupMap(HashMap<String, Group> groupMap) {
        this.groupMap = groupMap;
    }
    //getter pentru nr de puncte de credit
    public int getNo_credit_points() {
        return no_credit_points;
    }
    //setter pentru nr de puncte de credit
    public void setNo_credit_points(int no_credit_points) {
        this.no_credit_points = no_credit_points;
    }
    public static abstract class CourseBuilder{
        private String name;
        private String strategy = null;
        private Teacher teacher;
        private HashSet<Assistant> assistants = new HashSet<>();
        private ArrayList<Grade> grades = new ArrayList<>();
        private HashMap<String,Group> groupMap = new HashMap<String,Group>();
        private int no_credit_points;
        public CourseBuilder(String name, Teacher teacher, String strategy )
        {
            this.name = name;
            this.strategy = strategy;
            this.teacher = teacher;
        }

        public CourseBuilder assistants(HashSet<Assistant> assistants)
        {
            this.assistants = assistants;
            return this;
        }
        public CourseBuilder grades()
        {
            grades = new ArrayList<>();
            return this;
        }
        public CourseBuilder groupMap(HashMap<String,Group> groupMap)
        {
            this.groupMap = groupMap;
            return this;
        }
        public abstract Course build();

    }


    // Seteaza asistentul în grupa cu ID-ul indicat
    // Daca nu exista deja, adauga asistentul si în multimea asistentilor
    public void addAssistant(String ID, Assistant assistant)
    {

        getAssistants().add(assistant);
        //in aux se afla grupa cu id-ul ID
        Group aux = getGroupMap().get(ID);
        //i-am setat asistentul
        aux.setAssistant(assistant);
        //l-am bagat la id-ul ID, grupa cu asistentul assistant
        getGroupMap().put(ID,aux);
    }
    // Adauga studentul în grupa cu ID-ul indicat
    public void addStudent(String ID, Student student)
    {
        //in aux se afla grupa cu id-ul ID
        Group aux = getGroupMap().get(ID);
        //adauga studentul in grupa aux
        aux.add(student);
        //l-am bagat la id-ul ID, grupa cu studentul student
        getGroupMap().put(ID,aux);
    }
    // Adauga grupa
    public void addGroup(Group group)
    {
        //am luat id-ul grupei
        String id = group.getId();
        //bagam in dictionar groupMap grupa group cu id-ul id
        getGroupMap().put(id,group);
    }
    // Instantiaza o grupa si o adauga
    public void addGroup(String ID, Assistant assistant)
    {
        //am instantiat grupa
        Group aux = new Group(ID,assistant);
        //am luat id-ul grupei
        String id = aux.getId();
        //bagam in dictionar groupMap grupa group cu id-ul id
        getGroupMap().put(id,aux);
    }
    // Instantiaza o grupa si o adauga
    public void addGroup(String ID, Assistant assist, Comparator<Student> comp)
    {
        //am instantiat grupa
        Group aux = new Group(ID,assist,comp);
        //am luat id-ul grupei
        String id = aux.getId();
        //bagam in dictionar groupMap grupa group cu id-ul id
        getGroupMap().put(id,aux);
    }
    // Returneaza nota unui student sau null
    public Grade getGrade(Student student)
    {
        for (Grade grade : grades) {
            if (grade.getStudent().equals(student))
                return grade;
        }
        return null;
    }
    // Adauga o nota
    public void addGrade(Grade grade)
    {
        grades.add(grade);
    }
    // Returneaza o lista cu toti studentii
    public ArrayList<Student> getAllStudents()
    {
        ArrayList<Student> students= new ArrayList<>();
        //itereaza prin hashmap
        for(Map.Entry<String,Group>entry : groupMap.entrySet())
        {
            //adauga in arraylistul studenti toti studentii
            students.addAll(entry.getValue());
        }

        return students;
    }
    // Returneaza un dictionar cu situatia studentilor
    public HashMap<Student, Grade> gettAllStudentGrades()
    {
        HashMap<Student,Grade> studentGradeMap = new HashMap<>();
        ArrayList<Student> students= new ArrayList<>();
        //se adauga toti studentii intr-un arraylist
        students = getAllStudents();

            //adauga in hashmap studentii si grade-ul fiecaruia
                for(int j = 0; j < students.size(); j++)
                    studentGradeMap.put(students.get(j),getGrade(students.get(j)));

        return studentGradeMap;
    }
    // Metoda ce o sa fie implementata pentru a determina studentii promovati
    public abstract ArrayList<Student> getGraduatedStudents();

    //implemantarea strategy
    public Student getBestStudent()
    {
        if (strategy == null)
            return null;
        if(strategy.equals("BestExamScore"))
        {
            BestExamScore obj = new BestExamScore();
            return obj.getbestGrade(this);
        }
        else if(strategy.equals("BestPartialScore"))
        {
            BestPartialScore obj = new BestPartialScore();
            return obj.getbestGrade(this);
        }
        else
        {
            BestTotalScore obj = new BestTotalScore();
            return obj.getbestGrade(this);
        }
    }
    //implementare memento
    private Snapshot snapshot = new Snapshot();
    public void makeBackup() {
        //snapshot.gradeArrayList = grades;
    }
    public void undo() {
        grades = snapshot.gradeArrayList;
    }

    public class Snapshot{
        ArrayList<Grade> gradeArrayList ;


        public void setGradeArrayList(ArrayList<Grade> gradeArrayList) {
            this.gradeArrayList = gradeArrayList;
        }

        public Snapshot()
        {
            gradeArrayList = new ArrayList<>();
            makeBackup();
        }
    }
}
