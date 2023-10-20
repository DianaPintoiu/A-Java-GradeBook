package Tema1;

import java.util.ArrayList;
import java.util.Comparator;

public class Group extends ArrayList<Student> {
    private Assistant assistant;
    private String id;

    private Comparator<Student> comp;

    public Group(String ID, Assistant assistant, Comparator<Student> comp) {
        super();
        this.assistant = assistant;
        id = ID;
        comp = new Comparator<Student>() {
            @Override
            public int compare(Student a, Student b)
            {
                if(a.getLastName().compareTo(b.getLastName()) != 0)
                    return a.getLastName().compareTo(b.getLastName());
                else
                    return a.getFirstName().compareTo(b.getFirstName());
            }
        };
    }
    public Group(String ID, Assistant assistant) {
        super();
        this.assistant = assistant;
        id = ID;
    }


    //getter pentru assistant
    public Assistant getAssistant()
    {
        return assistant;
    }
    //setter pentru assistant
    public void setAssistant(Assistant assistant)
    {
        this.assistant = assistant;
    }
    //getter pentru id
    public String getId()
    {
        return id;
    }
    //setter pentru id
    public void setId(String id)
    {
        this.id = id;
    }

}
