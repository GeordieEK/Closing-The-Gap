package app.outcomeClasses;

public class User_Attributes {
    //Persona id 
    public int Persona_ID;

    //Persona Age 
    public int Age;

    //Persona ethnicity
    public String Ethnicity;

    //Persona Occupation
    public String Occupation;

    //Persona Description
    public String Description;

    //Persona Goals
    public String Goals;

    //Persona Pain points
    public String Pain_Points;

    //Persona Needs
    public String Needs;

    //Persona Skills and experience
    public String Experiences;

    //Fields have default empty value 
    public User_Attributes(){
    }

    //Creating a persona attribute by setting all of the fields

    public User_Attributes(int Persona_ID, int Age, String Ethnicity, String Occupation, String Description, String Goals, String Pain_Points, String Needs, String Experiences) {
        this.Persona_ID = Persona_ID;
        this.Age = Age;
        this.Ethnicity = Ethnicity;
        this.Occupation = Occupation;
        this.Description = Description;
        this.Goals = Goals;
        this.Pain_Points = Pain_Points;
        this.Needs = Needs;
        this.Experiences = Experiences;
    }

}
