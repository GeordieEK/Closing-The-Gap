package app;

public class Population {

    // Setup a jdbc object to query database for various populations
    private static JDBCConnection jdbc = new JDBCConnection();

    // This class is redundant at this point (populations are calculated within
    // queries in SQL now) but left here just in case.

    // Indigenous population Aus
    private int indiPopAus = jdbc.getPopulation("Australia", "Indigenous");

    // Non Indigenous population Aus
    private int notIndiPopAus = jdbc.getPopulation("Australia", "Not Indigenous");

    // Indigenous population NSW
    private int indiPopNsw = jdbc.getPopulation("NSW", "Indigenous");

    public int getIndiPopAus() {
        return indiPopAus;
    }

    public int getNotIndiPopAus() {
        return notIndiPopAus;
    }

    public int getIndiPopNsw() {
        return indiPopNsw;
    }

}
