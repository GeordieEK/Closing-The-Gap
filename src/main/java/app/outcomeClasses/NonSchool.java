package app.outcomeClasses;

// The classes for the different categories of the database 
public class NonSchool {
   // Row LGA code
   private int LGAcode;

   // Row LGA name
   private String LGAname;

   // Row IndiStatus
   private String indiStatus;

   // Row Sex
   private String sex;

   // Row Certificate II
   private double certii;

   // Row Advanced Diploma
   private double advdip;

   // Row Graduate Diploma/Certificate
   private double gradcert;

   // Row Bachelor Degree
   private double bachelor;

   // Row Postgraduate Degree
   private double postgrad;

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

   public NonSchool(int LGAcode, String LGAname, String indiStatus, String sex, double certii, double advdip,
         double gradcert, double bachelor, double postgrad) {
      this.LGAcode = LGAcode;
      this.LGAname = LGAname;
      this.indiStatus = indiStatus;
      this.sex = sex;
      this.certii = certii;
      this.advdip = advdip;
      this.gradcert = gradcert;
      this.bachelor = bachelor;
      this.postgrad = postgrad;
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

   public double getCertii() {
      return this.certii;
   }

   public void setCertii(double certii) {
      this.certii = certii;
   }

   public double getAdvdip() {
      return this.advdip;
   }

   public void setAdvdip(double advdip) {
      this.advdip = advdip;
   }

   public double getGradcert() {
      return this.gradcert;
   }

   public void setGradcert(double gradcert) {
      this.gradcert = gradcert;
   }

   public double getBachelor() {
      return this.bachelor;
   }

   public void setBachelor(double bachelor) {
      this.bachelor = bachelor;
   }

   public double getPostgrad() {
      return this.postgrad;
   }

   public void setPostgrad(double postgrad) {
      this.postgrad = postgrad;
   }

}
