package Tema1;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * JList basic tutorial and example
 *
 * @author wwww.codejava.net
 */
public class StudentPage1 extends JFrame {

    private JList<String> courseJList;
    JTextField studentName;
    JButton button;
    ArrayList<Course> courseArrayList = new ArrayList<>();

    public StudentPage1(String studentName) {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 600);
        setMinimumSize(new Dimension(600, 600));

        //pt textfield
        //studentName = new JTextField();
        //JPanel panel = new JPanel();
        //panel.setLayout(new GridLayout(1,1));
        //panel.add(studentName);
        JTextField textFieldStudent = new JTextField("Nume student: " + studentName);

        textFieldStudent.setSize(300,300);
        textFieldStudent.setBackground(new Color(66, 245, 197));
        textFieldStudent.setEditable(false);
        textFieldStudent.setFont(new Font("Calibri",Font.BOLD,24));

        JTextArea area = new JTextArea();
        area.setSize(300,300);
        area.setEditable(false);
        area.setFont(new Font("Calibri",Font.BOLD,14));
        area.setBackground(new Color(171, 245, 235));

        String[] nume = studentName.split(" ");
        DefaultListModel<String> listCourses = new DefaultListModel<>();
        int courseLength = 0;
        for(Course course : Catalog.getInstance().courses)
        {
            //Student student = new Student(nume[0],nume[1]);
            for(int i = 0; i < course.getGrades().size(); i++)
                if(course.getGrades().get(i).getStudent().getFirstName().equals(nume[0]) &&
                        course.getGrades().get(i).getStudent().getLastName().equals(nume[1])
                && !courseArrayList.contains(course))
            {
                //am adaugat in ArrayListul de courses cursul studentului
                courseArrayList.add(course);
                //am adaugat in arrayListul de Stringuri numele cursului studentului
                listCourses.addElement(course.getName());
                courseLength++;
            }
        }

        //create the list
        courseJList = new JList<>(listCourses);
        courseJList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {

                    final List<String> selectedValuesList = courseJList.getSelectedValuesList();
                    Student student = new Student(nume[0],nume[1]);
                    for(Course course : Catalog.getInstance().courses)
                    {
                        if(course.getName().equals(selectedValuesList.get(0)))
                        {
                            area.setText("Profesor titular: " + course.getTeacher());
                            area.append("\n");
                            area.append("Asistentii aferenti cursului: " + course.getAssistants());
                            area.append("\n");
                            HashMap<String,Group>  groupMap = (HashMap<String, Group>) course.getGroupMap();
                            for (Map.Entry<String,Group> entry : groupMap.entrySet())
                            {
                                for(int j =0 ; j < entry.getValue().size();j++)
                                    if(entry.getValue().get(j).getFirstName().equals(nume[0])
                                && entry.getValue().get(j).getLastName().equals(nume[1]) )
                                    {
                                        area.append("Asistentul studentului pentru curs: " + entry.getValue().getAssistant());
                                        area.append("\n");
                                    }
                            }
                            int ok = 0;
                            for(int j =0; j <  course.getGrades().size();j++ )
                            {
                                if(course.getGrades().get(j).getStudent().getFirstName().equals(nume[0]) &&
                                        course.getGrades().get(j).getStudent().getLastName().equals(nume[1]) && ok == 0)
                                {
                                    ok = 1;

                                    area.append("Exam score: " + course.getGrades().get(j).getExamScore());
                                    area.append("\n");
                                    //j = course.getGrades().size();
                                }
                                else if(course.getGrades().get(j).getStudent().getFirstName().equals(nume[0]) &&
                                        course.getGrades().get(j).getStudent().getLastName().equals(nume[1]) && ok == 1)
                                {
                                    ok = 2;
                                    area.append("Partial score: " + course.getGrades().get(j).getPartialScore());

                                }
                            }

                        }
                    }
                }

        });

        add(new JScrollPane(courseJList));

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //add(panel,BorderLayout.NORTH);
        add(textFieldStudent,BorderLayout.NORTH);
        add(area,BorderLayout.AFTER_LINE_ENDS);

        this.setTitle("StudentPage");
        this.setSize(600, 600);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
}
