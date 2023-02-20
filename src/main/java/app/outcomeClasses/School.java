package app.outcomeClasses;

// The classes for the different categories of the database 
public class School {
   // Row LGA code
   private int LGAcode;

   // Row LGA name
   private String LGAname;

   // Row IndiStatus
   private String indiStatus;

   // Row Sex
   private String sex;

   // Row Employed
   private double noSchool;

   // Row Unemployed
   private double year8;

   // Row Government Industry
   private double year10;

   // Row privateIndustry
   private double year12;

   // // Setup a population object to call methods for various populations
   // private static Population population = new Population();

   // // Indigenous population Aus
   // private static int indigenousPopulationAus = population.getIndiPopAus();

   // // Non Indigenous population Aus
   // private static int notIndigenousPopulationAus =
   // population.getNotIndiPopAus();

   // // Non Indigenous population Aus
   // private static int indiPopNsw = population.getIndiPopNsw();

   // Constructor

   public School(int LGAcode, String LGAname, String indiStatus, String sex, double noSchool, double year8,
         double year10, double year12) {
      this.LGAcode = LGAcode;
      this.LGAname = LGAname;
      this.indiStatus = indiStatus;
      this.sex = sex;
      this.noSchool = noSchool;
      this.year8 = year8;
      this.year10 = year10;
      this.year12 = year12;
   }

   public int getLGAcode() {
      return this.LGAcode;
   }

   public void setLGAcode(int LGAcode) {
      this.LGAcode = LGAcode;
   }

   public String getLGAname() {
      return this.LGAname;
   }

   public void setLGAname(String LGAname) {
      this.LGAname = LGAname;
   }

   public String getIndiStatus() {
      return this.indiStatus;
   }

   public void setIndiStatus(String indiStatus) {
      this.indiStatus = indiStatus;
   }

   public String getSex() {
      return this.sex;
   }

   public void setSex(String sex) {
      this.sex = sex;
   }

   public double getNoSchool() {
      return this.noSchool;
   }

   public void setNoSchool(double noSchool) {
      this.noSchool = noSchool;
   }

   public double getYear8() {
      return this.year8;
   }

   public void setYear8(double year8) {
      this.year8 = year8;
   }

   public double getYear10() {
      return this.year10;
   }

   public void setYear10(double year10) {
      this.year10 = year10;
   }

   public double getYear12() {
      return this.year12;
   }

   public void setYear12(double year12) {
      this.year12 = year12;
   }

}
