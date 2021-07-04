import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import java.io.IOException;
import java.net.URL;
import java.util.*;

/**
 * Class that represents a database which contains and manages information
 * on the NearEarthObjects which have been retrieved from NASA's data.
 *
 * liwll
 */
public class NeoDatabase {
    public static final String API_KEY =
            "2hpsVhoFAhAIF2wxFwUFbtNo69xipZCzfvdtBAYl";
    public static final String API_ROOT =
            "https://api.nasa.gov/neo/rest/v1/neo/browse?";
    public LinkedList<NearEarthObject> database;

    /**
     * Default constructor for a NeoDatabase
     */
    public NeoDatabase() {
        this.database = new LinkedList<>();
    }

    /**
     * Method which generates a URL based on the API_KEY
     * @param pageNumber
     *  Page number for the URL you wish to generate
     * @return
     *  The URL of the specified page
     * @throws IllegalArgumentException
     *  When pageNumber is out of bounds
     */
    public String buildQueryURL(int pageNumber)
            throws IllegalArgumentException {
        if (pageNumber < 0 || pageNumber > 715)
            throw new IllegalArgumentException();

        String queryURL = String.format("%spage=%s&api_key=%s",
                API_ROOT, pageNumber, API_KEY);
        return queryURL;
    }

    /**
     * Method which adds the NearEarthObjects to the database from a URL
     * @param queryURL
     *  The URL which contains the dataset
     * @throws IllegalArgumentException
     *  When the URL is null or could nto be resolved
     */
    public void addAll(String queryURL) throws IllegalArgumentException {
        if (queryURL == null)
            throw new IllegalArgumentException();

        try {
            URL getReq = new URL(queryURL);
            JSONTokener tokener = new JSONTokener(getReq.openStream());
            JSONObject root = new JSONObject(tokener);

            JSONArray nearEarthObjects =
                    root.getJSONArray("near_earth_objects");

            JSONObject neoJSON;
            JSONArray closeApproachData;
            int refID;
            String name;
            double absoluteMagnitude;
            double minDiameter;
            double maxDiameter;
            boolean isDangerous;
            long[] closestApproachDates;
            long closestApproachDate;
            double[] missDistances;
            double missDistance;
            String[] orbitingBodies;
            String orbitingBody;
            NearEarthObject neo;
            for (int i = 0; i < nearEarthObjects.length(); i++) {
                neoJSON = (JSONObject) nearEarthObjects.get(i);

                refID = neoJSON.getInt("neo_reference_id");

                name = neoJSON.getString("name");

                absoluteMagnitude =
                        neoJSON.getDouble("absolute_magnitude_h");

                minDiameter =
                        neoJSON.getJSONObject("estimated_diameter").
                                getJSONObject("kilometers").
                                getDouble("estimated_diameter_min");

                maxDiameter = neoJSON.getJSONObject("estimated_diameter").
                        getJSONObject("kilometers").
                        getDouble("estimated_diameter_max");

                isDangerous = neoJSON.
                        getBoolean("is_potentially_hazardous_asteroid");

                closeApproachData =
                        neoJSON.getJSONArray("close_approach_data");
                closestApproachDates = new long[closeApproachData.length()];
                for (int j = 0; j < closeApproachData.length(); j++) {
                    closestApproachDates[j] =
                            closeApproachData.getJSONObject(j).
                                    getLong("epoch_date_close_approach");
                }
                closestApproachDate = closestApproachDates[0];

                missDistances = new double[closeApproachData.length()];
                for (int n = 0; n < closeApproachData.length(); n++) {
                    missDistances[n] = closeApproachData.
                            getJSONObject(n).getJSONObject("miss_distance").
                            getDouble("kilometers");
                }
                missDistance = missDistances[0];

                orbitingBodies = new String[closeApproachData.length()];
                for (int k = 0; k < closeApproachData.length(); k++) {
                    orbitingBodies[k] = closeApproachData.getJSONObject(k).
                            getString("orbiting_body");
                }
                orbitingBody = orbitingBodies[0];

                neo = new NearEarthObject(refID, name, absoluteMagnitude,
                        minDiameter, maxDiameter, isDangerous,
                        closestApproachDate, missDistance, orbitingBody);
                database.add(neo);
            }
        }
        catch (org.json.JSONException e) {
            System.out.println("Error, JSON exception.");
        }
        catch (IOException e) {
            System.out.println("Error, IO exception.");
        }
    }

    /**
     * Method which sorts the database by the specified comparator
     * @param comp
     *  The comparator used to sort the database
     * @throws IllegalArgumentException
     *  If the comparator is null
     */
    public void sort(Comparator<NearEarthObject> comp)
        throws IllegalArgumentException {
        if (comp == null)
            throw new IllegalArgumentException();

        Collections.sort(database, comp);
    }

    /**
     * Method which prints the database in a table, listing all information
     * about the NearEarthObject such as ID, name, missDistance, etc.
     */
    public void printTable() {
        int size = database.size();
        String tableHead = String.format("%-8s|%-34s|%-6s|%-10s|" +
                        "%-8s|%-12s|%-11s|%s\n%S",
                "   ID", "               Name", " Mag.", " Diameter",
                " Danger", " Close Date", " Miss Dist", " Orbits",
                "========================================================="
                        + "===============================================");
        System.out.println(tableHead);

        for (int i = 0; i < size; i++) {
            NearEarthObject cursor = database.get(i);
            Date cursorDate = cursor.getClosestApproachDate();
            String fullDate = cursorDate.toString();
            String month = fullDate.substring(4, 7);
            String day = fullDate.substring(8, 10);
            String year = fullDate.substring(24, 28);
            String monthNum;
            switch (month) {
                case "Jan":
                    monthNum = "01";
                    break;
                case "Feb":
                    monthNum = "02";
                    break;
                case "Mar":
                    monthNum = "03";
                    break;
                case "Apr":
                    monthNum = "04";
                    break;
                case "May":
                    monthNum = "05";
                    break;
                case "Jun":
                    monthNum = "06";
                    break;
                case "Jul":
                    monthNum = "07";
                    break;
                case "Aug":
                    monthNum = "08";
                    break;
                case "Sep":
                    monthNum = "09";
                    break;
                case "Oct":
                    monthNum = "10";
                    break;
                case "Nov":
                    monthNum = "11";
                    break;
                case "Dec":
                    monthNum = "12";
                    break;
                default:
                    monthNum = "69";
            }

            String simpleDate = monthNum + "-" + day + "-" + year;

            System.out.printf("%-10d%-35s%-7.1f%-11.3f%-9b%-13s%-12.0f%s\n",
                    cursor.getReferenceID(), cursor.getName(),
                    cursor.getAbsoluteMagnitude(), cursor.getAverageDiameter(),
                    cursor.isDangerous(), simpleDate,
                    cursor.getMissDistance(), cursor.getOrbitingBody());
        }
    }
}
