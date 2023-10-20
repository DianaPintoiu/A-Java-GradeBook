package Tema1;

public class Grade implements Comparable,Cloneable{
    private Double partialScore, examScore;
    private Student student;
    private String course; // Numele cursului

    public Grade(Double partialScore,Double examScore)
    {
        this.partialScore = partialScore;
        this.examScore = examScore;

    }
    public Grade(Grade a) {
        this.partialScore = a.partialScore;
        this.examScore = a.examScore;
    }
    //getter pentru PartialScore
    public Double getPartialScore()
    {
        return partialScore;
    }
    //setter pentru PartialScore
    public void setPartialScore(Double partialScore)
    {
        this.partialScore = partialScore;
    }
    //getter pentru examScore
    public Double getExamScore()
    {
        return examScore;
    }
    //setter pentru examScore
    public void setExamScore(Double examScore)
    {
        this.examScore = examScore;
    }
    //getter pentru student
    public Student getStudent()
    {
        return student;
    }
    //setter pentru student
    public void setStudent(Student student)
    {
        this.student = student;
    }
    //getter pentru course
    public String getCourse()
    {
        return course;
    }
    //setter pentru course
    public void setCourse(String course)
    {
        this.course = course;
    }
    public Double getTotal() {
        return getExamScore() + getPartialScore();
    }

    @Override
    public String toString() {
        return partialScore + " " + examScore;
    }

    @Override
    public int compareTo(Object o) {
      if(getTotal() > ((Grade)o).getTotal())
          return 1;
      else
          if(getTotal() < ((Grade)o).getTotal())
              return -1;
          else
              return 0;
    }
    public Object clone() {
        Grade grade = new Grade(partialScore,examScore);
        grade.setStudent(student);
        grade.course = course;
        return grade;
    }
    }
