package app.outcomeClasses;

// The classes for the different categories of the database 
public class Category {
   // Row LGA code
   private int LGAcode;

   // Row LGA name
   private String LGAname;

   // Row Sex
   private String sex;

   // Row IndiStatus
   private String indiStatus;

   // Row Category
   private String category;

   // Row NumPeople
   private int numPeople;

   // Constructors for the classes
   public Category(int LGAcode, String LGAname, String indiStatus, String sex, String category, int numPeople) {
      this.LGAcode = LGAcode;
      this.LGAname = LGAname;
      this.indiStatus = indiStatus;
      this.sex = sex;
      this.category = category;
      this.numPeople = numPeople;
   }

   // Getters and Setters
   public int getCode() {
      return LGAcode;
   }

   public String getLGAName() {
      return LGAname;
   }

   public String getIndiStatus() {
      return indiStatus;
   }

   public String getSex() {
      return sex;
   }

   public String getcategory() {
      return this.category;
   }

   public int getnumPeople() {
      return this.numPeople;
   }

   public void setLGAcode(int LGAcode) {
      this.LGAcode = LGAcode;
   }

   public void setLGAname(String LGAname) {
      this.LGAname = LGAname;
   }

   public void setIndiStatus(String indiStatus) {
      this.indiStatus = indiStatus;
   }

   public void setSex(String sex) {
      this.sex = sex;
   }

   public void setCategory(String category) {
      this.category = category;
   }

   public void setNumPeople(int numPeople) {
      this.numPeople = numPeople;
   }
}
