package app;

/**
 *
 * This code is adapted from vananth22 on github and uses the
 * Haversine Distance algorithm to find the distance between two places.
 *
 * R = earth’s radius (mean radius = 6,371km)
 * Δlat = lat2− lat1
 * Δlong = long2− long1
 * a = sin²(Δlat/2) + cos(lat1).cos(lat2).sin²(Δlong/2)
 * c = 2.atan2(√a, √(1−a))
 * d = R.c
 *
 */

public class Haversine {

    /**
     * @param args
     *             arg 1- latitude 1
     *             arg 2 — latitude 2
     *             arg 3 — longitude 1
     *             arg 4 — longitude 2
     */
    public Double Distance(double latitude1, double longitude1, double latitude2, double longitude2) {
        final int R = 6371; // Radius of the earth
        Double lat1 = latitude1;
        Double lon1 = longitude1;
        Double lat2 = latitude2;
        Double lon2 = longitude2;
        Double latDistance = toRad(lat2 - lat1);
        Double lonDistance = toRad(lon2 - lon1);
        Double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2) +
                Math.cos(toRad(lat1)) * Math.cos(toRad(lat2)) *
                        Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        Double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        Double distance = R * c;

        // System.out.println("The distance between two lat and long is:" + distance);
        return distance;
    };

    private static Double toRad(Double value) {
        return value * Math.PI / 180;
    }
}