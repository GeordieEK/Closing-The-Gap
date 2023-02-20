package app.outcomeClasses;

import app.Population;

// The classes for the different categories of the database 
public class LabourForce {
   // Row LGA code
   private int LGAcode;

   // Row LGA name
   private String LGAname;

   // Row IndiStatus
   private String indiStatus;

   // Row Sex
   private String sex;

   // Row Employed
   private double employed;

   // Row Unemployed
   private double unemployed;

   // Row Government Industry
   private double governmentIndustry;

   // Row privateIndustry
   private double privateIndustry;

   // Row notInLabourForce
   private double notInLabourForce;

   // Setup a population object to call methods for various populations
   private static Population population = new Population();

   // // Indigenous population Aus
   // private static int indigenousPopulationAus = population.getIndiPopAus();

   // // Non Indigenous population Aus
   // private static int notIndigenousPopulationAus =
   // population.getNotIndiPopAus();

   // // Non Indigenous population Aus
   // private static int indiPopNsw = population.getIndiPopNsw();

   // Constructor

   public LabourForce(int LGAcode, String LGAname, String indiStatus, String sex, double employed, double unemployed,
         double governmentIndustry, double privateIndustry, double notInLabourForce) {
      this.LGAcode = LGAcode;
      this.LGAname = LGAname;
      this.indiStatus = indiStatus;
      this.sex = sex;
      this.employed = employed;
      this.unemployed = unemployed;
      this.governmentIndustry = governmentIndustry;
      this.privateIndustry = privateIndustry;
      this.notInLabourForce = notInLabourForce;
   }

   // // Get Percentage of employed people
   // public double getEmployedPercentage() {
   // double percentage = 1;

   // // If Aus value is checked in dropdown.

   // if (indiStatus.equalsIgnoreCase("Indigenous")) {
   // // System.out.println("indigenous check passed");
   // percentage = ((employed * 1.00) / indigenousPopulationAus) * 100.00;
   // } else if (indiStatus.equalsIgnoreCase("Not Indigenous")) {
   // percentage = ((employed * 1.00) / notIndigenousPopulationAus) * 100.00;
   // }

   // // If State value is checked in dropdown.

   // // Divide by State values
   // if (LGAcode < 30000 && indiStatus.equalsIgnoreCase("Indigenous")) {
   // // System.out.println("indigenous check passed");
   // percentage = ((employed * 1.00) / indiPopNsw) * 100.00;
   // }
   // ;
   // return percentage;
   // }

   // // Get Percentage of unemployed people
   // public double getUnemployedPercentage() {
   // double percentage = 1;
   // if (indiStatus.equalsIgnoreCase("Indigenous")) {
   // // System.out.println("indigenous check passed");
   // percentage = ((unemployed * 1.00) / indigenousPopulationAus) * 100.00;
   // } else if (indiStatus.equalsIgnoreCase("Not Indigenous")) {
   // percentage = ((unemployed * 1.00) / notIndigenousPopulationAus) * 100.00;
   // }
   // ;
   // return percentage;
   // }

   // // Get Percentage of government industry people
   // public double getGovernmentPercentage() {
   // double percentage = 1;
   // if (indiStatus.equalsIgnoreCase("Indigenous")) {
   // // System.out.println("indigenous check passed");
   // percentage = ((governmentIndustry * 1.00) / indigenousPopulationAus) *
   // 100.00;
   // } else if (indiStatus.equalsIgnoreCase("Not Indigenous")) {
   // percentage = ((governmentIndustry * 1.00) / notIndigenousPopulationAus) *
   // 100.00;
   // }
   // ;
   // return percentage;
   // }

   // // Get Percentage of private industry people
   // public double getPrivatePercentage() {
   // double percentage = 1;
   // if (indiStatus.equalsIgnoreCase("Indigenous")) {
   // // System.out.println("indigenous check passed");
   // percentage = ((privateIndustry * 1.00) / indigenousPopulationAus) * 100.00;
   // } else if (indiStatus.equalsIgnoreCase("Not Indigenous")) {
   // percentage = ((privateIndustry * 1.00) / notIndigenousPopulationAus) *
   // 100.00;
   // }
   // ;
   // return percentage;
   // }

   // // Get Percentage of people not in Labour Force
   // public double getNotInLFPercentage() {
   // double percentage = 1;
   // if (indiStatus.equalsIgnoreCase("Indigenous")) {
   // // System.out.println("indigenous check passed");
   // percentage = ((unemployed * 1.00) / indigenousPopulationAus) * 100.00;
   // } else if (indiStatus.equalsIgnoreCase("Not Indigenous")) {
   // percentage = ((unemployed * 1.00) / notIndigenousPopulationAus) * 100.00;
   // }
   // ;
   // return percentage;
   // }

   // Getters and Setters

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

   public double getEmployed() {
      return this.employed;
   }

   public void setEmployed(double employed) {
      this.employed = employed;
   }

   public double getUnemployed() {
      return this.unemployed;
   }

   public void setUnemployed(double unemployed) {
      this.unemployed = unemployed;
   }

   public double getGovernmentIndustry() {
      return this.governmentIndustry;
   }

   public void setGovernmentIndustry(double governmentIndustry) {
      this.governmentIndustry = governmentIndustry;
   }

   public double getPrivateIndustry() {
      return this.privateIndustry;
   }

   public void setPrivateIndustry(double privateIndustry) {
      this.privateIndustry = privateIndustry;
   }

   public double getNotInLabourForce() {
      return this.notInLabourForce;
   }

   public void setNotInLabourForce(double notInLabourForce) {
      this.notInLabourForce = notInLabourForce;
   }

}
