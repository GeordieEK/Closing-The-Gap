package app.outcomeClasses;

public class Team_Members {

    public int student_number;

    public String first_name;

    public String last_name;

    public String email_ID;

    public Team_Members(){
    }

    public Team_Members (int student_number, String first_name, String last_name, String email_ID){
        this.student_number = student_number;
        this.first_name = first_name;
        this.last_name = last_name;
        this.email_ID = email_ID;
    }
    
}
