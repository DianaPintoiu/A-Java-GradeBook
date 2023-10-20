package Tema1;
import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;

public class Test {
    public static void main(String[] args) throws IOException, ParseException {
        JSONParser jsonParser = new JSONParser();
        FileReader fileReader = new FileReader("catalog.json");
        Object parsare = jsonParser.parse(fileReader);
        JSONObject jsonObject = (JSONObject) parsare;
        JSONArray jsonCourses = (JSONArray) jsonObject.get("courses");
        Catalog catalog = Catalog.getInstance();
        ArrayList<Course> courses = new ArrayList<>();

        for (int i = 0; i < jsonCourses.size(); i++ ) {
            //am luat type-ul sa vedem ce fel de builder avem
            String jsonType = (String) ((JSONObject)jsonCourses.get(i)).get("type");
            //ce strategie are profu
            String strategyStudent =  (String) ((JSONObject)jsonCourses.get(i)).get("strategy");
            //numele cursului
            String name = (String) ((JSONObject)jsonCourses.get(i)).get("name");
            //am luat teacher-ul cu firstname si lastname
            JSONObject teacher = (JSONObject) ((JSONObject)jsonCourses.get(i)).get("teacher");
            Teacher teacher1 = (Teacher) UserFactory.getUser("Teacher",(String) teacher.get("firstName"),(String) teacher.get("lastName"));
            //toti asistentii din json
            JSONArray jsonAssistants = (JSONArray) ((JSONObject) jsonCourses.get(i)).get("assistants");
            HashSet<Assistant> assistants = new HashSet<>();
            for (Object jsonAssistant : jsonAssistants) {
                Object firstName = ((JSONObject) jsonAssistant).get("firstName");
                Object lastName = ((JSONObject) jsonAssistant).get("lastName");
                Assistant assistant = (Assistant) UserFactory.getUser("Assistant", (String) firstName, (String) lastName);
                assistants.add(assistant);
            }
            //incepem pt groups
            JSONArray jsonGroups = (JSONArray) (((JSONObject) jsonCourses.get(i)).get("groups"));
            HashMap<String,Group> groupMaps = new HashMap<>();
            for (Object jsonGroup : jsonGroups) {
                //ID-UL
                String id = (String) ((JSONObject) jsonGroup).get("ID");
                JSONObject assistant = (JSONObject) ((JSONObject) jsonGroup).get("assistant");
                Object firstName = assistant.get("firstName");
                Object lastName = assistant.get("lastName");
                //Asistentul
                Assistant assistant1 = (Assistant) UserFactory.getUser("Assistant", (String) firstName, (String) lastName);
                //STUDENTII
                JSONArray jsonStudents = (JSONArray) (((JSONObject) jsonGroup).get("students"));
                Group students = new Group(id, assistant1);
                for (Object jsonStudent : jsonStudents) {
                    Object firstNamestud = ((JSONObject) jsonStudent).get("firstName");
                    Object lastNamestud = ((JSONObject) jsonStudent).get("lastName");
                    Student student = (Student) UserFactory.getUser("Student", (String) firstNamestud, (String) lastNamestud);
                    //vedem daca exista mother
                    if (((JSONObject) jsonStudent).get("mother") != null) {
                        Object firstNameMother = ((JSONObject) ((JSONObject) jsonStudent).get("mother")).get("firstName");
                        Object lastNameMother = ((JSONObject) ((JSONObject) jsonStudent).get("mother")).get("lastName");
                        Parent mother = (Parent) UserFactory.getUser("Parent", (String) firstNameMother, (String) lastNameMother);
                        student.setMother(mother);
                    }
                    //daca exista father
                    if (((JSONObject) jsonStudent).get("father") != null) {
                        Object firstNameFather = ((JSONObject) ((JSONObject) jsonStudent).get("father")).get("firstName");
                        Object lastNameFather = ((JSONObject) ((JSONObject) jsonStudent).get("father")).get("lastName");
                        Parent father = (Parent) UserFactory.getUser("Parent", (String) firstNameFather, (String) lastNameFather);
                        student.setFather(father);
                    }
                    students.add(student);
                }
                groupMaps.put(id, students);
            }

            //pt FullCourse
            if(jsonType.equals("FullCourse"))
            {
                FullCourse.FullCourseBuilder fullCourseBuilder = new FullCourse.FullCourseBuilder(name,teacher1,strategyStudent);
                Course fullCourse = fullCourseBuilder.assistants(assistants).groupMap(groupMaps).grades().build();
                catalog.addCourse(fullCourse);
                System.out.println("Curs de tip FULLCOURSE: ");
                System.out.println("Profesor: " + catalog.courses.get(i).getTeacher());
                System.out.println("Nume curs: " + catalog.courses.get(i).getName());
                System.out.println("Strategia profesorului: " + catalog.courses.get(i).getStrategy());
                System.out.println("Asistentii: : " + catalog.courses.get(i).getAssistants());
                System.out.println("Elevii din grupele" + catalog.courses.get(i).getGroupMap());
                System.out.println();
            }
            else
            //pt PartialCourse
            {
                PartialCourse.PartialCourseBuilder partialCourseBuilder = new PartialCourse.PartialCourseBuilder(name,teacher1,strategyStudent);
                Course partialCourse = partialCourseBuilder.assistants(assistants).groupMap(groupMaps).grades().build();
                catalog.addCourse(partialCourse);
                System.out.println("Curs de tip PARTIALCOURSE:");
                System.out.println("Profesor: " + catalog.courses.get(i).getTeacher());
                System.out.println("Nume curs: " +catalog.courses.get(i).getName());
                System.out.println("Strategia profesorului: " + catalog.courses.get(i).getStrategy());
                System.out.println("Asistentii: " +catalog.courses.get(i).getAssistants());
                System.out.println("Elevii din grupele: " + catalog.courses.get(i).getGroupMap());
                System.out.println();
            }

        }

        //pt grades EXAM_SCORES
        JSONArray jsonExamScores = (JSONArray) jsonObject.get("examScores");
        ScoreVisitor scoreVisitor = ScoreVisitor.getInstance();
        for(int i = 0; i < jsonExamScores.size(); i++ )
        {
            //am aflat teacher-ul
            JSONObject jsonTeacher =(JSONObject) ((JSONObject)jsonExamScores.get(i)).get("teacher");
            Teacher teacher =(Teacher) UserFactory.getUser("Teacher",(String) jsonTeacher.get("firstName"),(String) jsonTeacher.get("lastName"));
            //Am aflat studentul
            JSONObject jsonStudent = (JSONObject) ((JSONObject)jsonExamScores.get(i)).get("student");
            Student student =(Student) UserFactory.getUser("Student",(String) jsonStudent.get("firstName"),(String) jsonStudent.get("lastName"));
            //Am aflat course-ul
            String course = (String) ((JSONObject)jsonExamScores.get(i)).get("course");
            //grade-ul
            Object grade = ((JSONObject)jsonExamScores.get(i)).get("grade");
            Double grade2 = 0.000;
            if(grade instanceof Long)
                grade2 = ((Long)grade).doubleValue();
            else
                grade2 = ((Double) grade);
            scoreVisitor.addExamScore(teacher,student,course,  grade2);
            scoreVisitor.visit(teacher);
        }


        //System.out.println("Ce profesor pune nota la ce student la o anumita materie:");
        //System.out.println(scoreVisitor.examScores.toString());



        //PT PARTIALSCORES

        JSONArray jsonPartialScores = (JSONArray) jsonObject.get("partialScores");
        for(int i = 0; i < jsonPartialScores.size(); i++ )
        {
            //am aflat asisttentul-ul
            JSONObject jsonAssistent =(JSONObject) ((JSONObject)jsonPartialScores.get(i)).get("assistant");
            Assistant assistant =(Assistant) UserFactory.getUser("Assistant",(String) jsonAssistent.get("firstName"),(String) jsonAssistent.get("lastName"));
            //Am aflat studentul
            JSONObject jsonStudent = (JSONObject) ((JSONObject)jsonPartialScores.get(i)).get("student");
            Student student =(Student) UserFactory.getUser("Student",(String) jsonStudent.get("firstName"),(String) jsonStudent.get("lastName"));
            //Am aflat course-ul
            String course = (String) ((JSONObject)jsonPartialScores.get(i)).get("course");
            //grade-ul
            Object grade = ((JSONObject)jsonPartialScores.get(i)).get("grade");
            Double grade2 = 0.000;
            if(grade instanceof Long)
                grade2 = ((Long)grade).doubleValue();
            else
                grade2 = ((Double) grade);
            scoreVisitor.addPartialScore(assistant,student,course,  grade2);
            scoreVisitor.visit(assistant);
        }

       /* for(int i = 0 ; i<catalog.courses.size() ;i++)
        {
          for(int j =0 ; j < catalog.courses.get(i).getGrades().size() ; j++)
          {
              System.out.println(catalog.courses.get(i).getGrades().get(j).getStudent() + " ->"+
                      catalog.courses.get(i).getGrades().get(j).getExamScore()
                      + " " + catalog.courses.get(i).getGrades().get(j).getPartialScore());
          }

        }*/
        System.out.println();
        // TESTARE CATALOG
        //In acelasi timp am testat si clasele USER, USERFACTORY, PARENT,ASSISTENT,TEACHER,STUDENT
        //Deoarece ele trebuiau initializate cu UserFactory
        Teacher teacher1 =(Teacher) UserFactory.getUser("Teacher","Brad","Pitt");
        PartialCourse.PartialCourseBuilder partialCourseBuilder = new PartialCourse.PartialCourseBuilder("Olarit",teacher1,"BestPartialScore");
        HashSet<Assistant> assistants = new HashSet<>();

        Assistant assistant = (Assistant) UserFactory.getUser("Assistant", "Asistentul", "Bun");
        Assistant assistant2 = (Assistant) UserFactory.getUser("Assistant", "Asistentul2", "Bun");
        assistants.add(assistant);

        HashMap<String,Group> groupMaps = new HashMap<>();
        Group students = new Group("324CC", assistant);
        Student student = (Student) UserFactory.getUser("Student","Angelina","Jolie");
        Parent father = (Parent) UserFactory.getUser("Parent","Tata","1");
        student.setFather(father);
        Student student1 = (Student) UserFactory.getUser("Student","Andrei","Anghel");
        Parent mother = (Parent) UserFactory.getUser("Parent","Mama","1");
        student1.setMother(mother);
        students.add(student);
        students.add(student1);
        groupMaps.put("324CC",students);
        Course partialCourse = partialCourseBuilder.assistants(assistants).groupMap(groupMaps).grades().build();
        catalog.addCourse(partialCourse);
        System.out.println("Am adaugat in courses inca un course cu profesorul Brad Pitt");
        for(int i = 0; i < catalog.courses.size(); i++)
        {
            System.out.println();
            System.out.println("Profesor: " + catalog.courses.get(i).getTeacher());
            System.out.println("Nume curs: " +catalog.courses.get(i).getName());
            System.out.println("Strategia profesorului: " + catalog.courses.get(i).getStrategy());
            System.out.println("Asistentii: " +catalog.courses.get(i).getAssistants());
            System.out.println("Elevii din grupele: " + catalog.courses.get(i).getGroupMap());
            System.out.println();
        }
        System.out.println("Vreau sa mai adaug cateva chestii in ultimul curs");
        System.out.println("Adaug assistant");
        catalog.courses.get(catalog.courses.size() - 1).addAssistant("324CC",assistant2);
        System.out.println("Si acum adaug inca un student");
        Student student12 = (Student) UserFactory.getUser("Student","Diana","Pintoiu");
        catalog.courses.get(catalog.courses.size() - 1).addStudent("324CC",student12);
        System.out.println("Acum adaug grup");
        Group grupa = new Group("331CC", assistant);
        Student student3 = (Student) UserFactory.getUser("Student","Maria","Nu");
        Parent father1 = (Parent) UserFactory.getUser("Parent","Tata","1");
        student3.setFather(father1);
        grupa.add(student3);
        catalog.courses.get(catalog.courses.size() - 1).addGroup(grupa);

        System.out.println("Adauga o grupa altfel");
        catalog.courses.get(catalog.courses.size() - 1).addGroup("322CB",assistant2);
        System.out.println("Adauga o grupa altfel");
        Comparator<Student> comparator = new Comparator<Student>() {
            @Override
            public int compare(Student a, Student b)
            {
                if(a.getLastName().compareTo(b.getLastName()) != 0)
                    return a.getLastName().compareTo(b.getLastName());
                else
                    return a.getFirstName().compareTo(b.getFirstName());
            }
        };
        catalog.courses.get(catalog.courses.size() - 1).addGroup("325AA",assistant,comparator);

            System.out.println();
            System.out.println("Profesor: " + catalog.courses.get(catalog.courses.size() - 1).getTeacher());
            System.out.println("Nume curs: " +catalog.courses.get(catalog.courses.size() - 1).getName());
            System.out.println("Strategia profesorului: " + catalog.courses.get(catalog.courses.size() - 1).getStrategy());
            System.out.println("Asistentii: " +catalog.courses.get(catalog.courses.size() - 1).getAssistants());
            System.out.println("Elevii din grupele: " + catalog.courses.get(catalog.courses.size() - 1).getGroupMap());
            System.out.println();
        System.out.println("In continuarea vom adauga un grade");
        Grade grade = new Grade(2.3,3.0);
        grade.setStudent(student3);
        Grade grade1 = new Grade(3.6,3.3);
        grade1.setStudent(student);
        Grade grade2 = new Grade(0.1,0.0);
        grade2.setStudent(student1);
        Grade grade3 = new Grade(4.0,6.0);
        grade3.setStudent(student12);
        catalog.courses.get(catalog.courses.size() - 1).addGrade(grade);
        catalog.courses.get(catalog.courses.size() - 1).addGrade(grade1);
        catalog.courses.get(catalog.courses.size() - 1).addGrade(grade2);
        catalog.courses.get(catalog.courses.size() - 1).addGrade(grade3);
        System.out.println("In continuarea vom afisa un grade a unui student sau null daca nu exista");
        System.out.println(catalog.courses.get(catalog.courses.size() - 1).getGrade(student3).getStudent() + " " +
                catalog.courses.get(catalog.courses.size() - 1).getGrade(student3).getPartialScore() + " "+
                        catalog.courses.get(catalog.courses.size() - 1).getGrade(student3).getExamScore());
        System.out.println("Vreau sa mi returneze toti studentii");
        System.out.println(catalog.courses.get(catalog.courses.size() - 1).getAllStudents());
        System.out.println("Dictionar cu situatia studentiilor");
        System.out.println(catalog.courses.get(catalog.courses.size() - 1).gettAllStudentGrades());
        System.out.println("Acum vreau sa iau doar studentii care ar lua cursul");
        System.out.println(catalog.courses.get(catalog.courses.size() - 1).getGraduatedStudents());
        System.out.println("intr-un final vedem strategia implementata");
        System.out.println("Best student pt strategia: " +catalog.courses.get(catalog.courses.size() - 1).getStrategy()
                + " : " + catalog.courses.get(catalog.courses.size() - 1).getBestStudent());
        catalog.removeCourse(partialCourse);

        //catalog.courses.get(catalog.courses.size() - 1).makeBackup();

        System.out.println("Acum am sters cursul cu Brad Pitt de olarit");
        for(int i = 0; i < catalog.courses.size(); i++)
        {
            System.out.println();
            System.out.println("Profesor: " + catalog.courses.get(i).getTeacher());
            System.out.println("Nume curs: " +catalog.courses.get(i).getName());
            System.out.println("Strategia profesorului: " + catalog.courses.get(i).getStrategy());
            System.out.println("Asistentii: " +catalog.courses.get(i).getAssistants());
            System.out.println("Elevii din grupele: " + catalog.courses.get(i).getGroupMap());
            System.out.println();
        }

        StudentPage1 page = new StudentPage1("Cristian Manole");
    }
}
