package app.outcomeClasses;

/**
 * Class represeting a LGA from the Studio Project database
 *
 * @author Timothy Wiley, 2022. email: timothy.wiley@rmit.edu.au
 */
public class LGA {
   // LGA Code
   private int code;

   // LGA Name
   private String name;

   // LGA Type
   private String type;

   // LGA Area
   private int area;

   // LGA Latitude
   private double latitude;

   // LGA Longitude
   private double longitude;

   // LGA Distance (from another LGA)
   private Double distance;

   // LGA Total population
   private int population;

   // LGA Age Score
   private double ageScore;

   // LGA Labour Force Score
   private double lfScore;

   // LGA Non School Education Score
   private double nsScore;

   // LGA School Completion Score
   private double scScore;

   // LGA Score
   private double totalScore;

   // Default constructor
   public LGA() {
   }

   /**
    * Create an LGA and set the fields if no population given
    */
   public LGA(int code, String name, String type, int area, double latitude, double longitude) {
      this.code = code;
      this.name = name;
      this.type = type;
      this.area = area;
      this.latitude = latitude;
      this.longitude = longitude;
   }

   /**
    * Create an LGA and set the fields if you are given a distance from another lga
    */
   public LGA(int code, String name, String type, int area, double latitude, double longitude, double distance) {
      this.code = code;
      this.name = name;
      this.type = type;
      this.area = area;
      this.latitude = latitude;
      this.longitude = longitude;
      this.distance = distance;
   }

   /**
    * Create an LGA and set the fields if population and various scores are given
    */
   public LGA(int code, String name, String type, int area, double latitude, double longitude, int population,
         double ageScore, double lfScore, double nsScore, double scScore, double totalScore) {
      this.code = code;
      this.name = name;
      this.type = type;
      this.area = area;
      this.latitude = latitude;
      this.longitude = longitude;
      this.population = population;
      this.ageScore = ageScore;
      this.lfScore = lfScore;
      this.nsScore = nsScore;
      this.scScore = scScore;
      this.totalScore = totalScore;
   }

   // Getters and Setters

   public void setCode(int code) {
      this.code = code;
   }

   public void setName(String name) {
      this.name = name;
   }

   public void setType(String type) {
      this.type = type;
   }

   public void setArea(int area) {
      this.area = area;
   }

   public void setLatitude(double latitude) {
      this.latitude = latitude;
   }

   public void setLongitude(double longitude) {
      this.longitude = longitude;
   }

   public int getCode() {
      return code;
   }

   public String getName() {
      return name;
   }

   public String getType() {
      return type;
   }

   public int getArea() {
      return this.area;
   }

   public double getLatitude() {
      return this.latitude;
   }

   public double getLongitude() {
      return this.longitude;
   }

   public int getPopulation() {
      return this.population;
   }

   public double getTotalScore() {
      return this.totalScore;
   }

   public double getAgeScore() {
      return this.ageScore;
   }

   public void setAgeScore(double ageScore) {
      this.ageScore = ageScore;
   }

   public double getLfScore() {
      return this.lfScore;
   }

   public void setLfScore(double lfScore) {
      this.lfScore = lfScore;
   }

   public double getNsScore() {
      return this.nsScore;
   }

   public void setNsScore(double nsScore) {
      this.nsScore = nsScore;
   }

   public double getScScore() {
      return this.scScore;
   }

   public void setScScore(double scScore) {
      this.scScore = scScore;
   }

   public void setPopulation(int population) {
      this.population = population;
   }

   public Double getDistance() {
      return this.distance;
   }

   public void setDistance(Double distance) {
      this.distance = distance;
   }

   public void setTotalScore(double totalScore) {
      this.totalScore = totalScore;
   }

   public String getState() {
      int LGACode = getCode();

      while (LGACode > 9) {
         LGACode /= 10;
      }

      int stateCode = LGACode;
      String state;
      switch (stateCode) {
         case 1:
            state = "NSW";
            break;
         case 2:
            state = "Victoria";
            break;
         case 3:
            state = "QLD";
            break;
         case 4:
            state = "South Australia";
            break;
         case 5:
            state = "Western Australia";
            break;
         case 6:
            state = "Tasmania";
            break;
         case 7:
            state = "Northern Territory";
            break;
         case 8:
            state = "ACT";
            break;
         case 9:
            state = "Other Territories";
            break;
         default:
            state = "Unspecified";
            break;
      }
      return state;
   }

}
