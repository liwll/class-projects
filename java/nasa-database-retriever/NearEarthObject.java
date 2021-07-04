import java.util.Date;

/**
 * This class represents an asteroid that NASA has data on.
 * It has fields such as the ID, name, diameter, and other information.
 *
 * liwll
 */
public class NearEarthObject {
    private int referenceID;
    private String name;
    private double absoluteMagnitude;
    private double averageDiameter;
    private boolean isDangerous;
    private Date closestApproachDate;
    private double missDistance;
    private String orbitingBody;

    /**
     * Default constructor for this object
     * @param referenceID
     *  The unique ID of the asteroid
     * @param name
     *  The unique name of the asteroid
     * @param absoluteMagnitude
     *  The absolute brightness of the asteroid
     * @param minDiameter
     *  The estimated minimum diameter of the asteroid (km)
     * @param maxDiameter
     *  The estimated maximum diameter of the asteroid (km)
     * @param isDangerous
     *  Indicates whether the asteroid is dangerous
     * @param closestDateTimestamp
     *  Date when the closest approach occurred
     * @param missDistance
     *  Miss distance when the closest approach occurred
     * @param orbitingBody
     *  The orbital body the asteroid orbits
     */
    public NearEarthObject(int referenceID, String name,
                           double absoluteMagnitude, double minDiameter,
                           double maxDiameter, boolean isDangerous,
                           long closestDateTimestamp, double missDistance,
                           String orbitingBody) {
        this.referenceID = referenceID;
        this.name = name;
        this.absoluteMagnitude = absoluteMagnitude;
        this.averageDiameter = (minDiameter + maxDiameter) / 2;
        this.isDangerous = isDangerous;
        this.closestApproachDate = new Date(closestDateTimestamp);
        this.missDistance = missDistance;
        this.orbitingBody = orbitingBody;
    }

    /**
     * Getter method for the unique ID
     * @return
     *  The unique ID of the asteroid
     */
    public int getReferenceID() {
        return referenceID;
    }

    /**
     * Setter method for the unique ID
     * @param referenceID
     *  The unique ID of the asteroid
     */
    public void setReferenceID(int referenceID) {
        this.referenceID = referenceID;
    }

    /**
     * Getter method for the name of the asteroid
     * @return
     *  The unique name of the asteroid
     */
    public String getName() {
        return name;
    }

    /**
     * Setter method for the name of the asteroid
     * @param name
     *  The unique name of the asteroid
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter method for the absolute brightness of the asteroid
     * @return
     *  The absolute brightness of the asteroid
     */
    public double getAbsoluteMagnitude() {
        return absoluteMagnitude;
    }

    /**
     * Setter method for the absolute brightness of the asteroid
     * @param absoluteMagnitude
     *  The absolute brightness of the asteroid
     */
    public void setAbsoluteMagnitude(double absoluteMagnitude) {
        this.absoluteMagnitude = absoluteMagnitude;
    }

    /**
     * Getter method for the average diameter of the asteroid
     * @return
     *  The average diameter of the asteroid
     */
    public double getAverageDiameter() {
        return averageDiameter;
    }

    /**
     * Setter method for the average diameter of the asteroid
     * @param averageDiameter
     *  The average diameter of the asteroid
     */
    public void setAverageDiameter(double averageDiameter) {
        this.averageDiameter = averageDiameter;
    }

    /**
     * Getter method for the field isDangerous
     * @return
     *  True if the asteroid is dangerous, false if not
     */
    public boolean isDangerous() {
        return isDangerous;
    }

    /**
     * Setter method for the field isDangerous
     * @param dangerous
     *  True if the asteroid is dangerous, false if not
     */
    public void setDangerous(boolean dangerous) {
        isDangerous = dangerous;
    }

    /**
     * Getter method for the date of the asteroid's closest approach
     * @return
     *  The date of the asteroid's closest approach
     */
    public Date getClosestApproachDate() {
        return closestApproachDate;
    }

    /**
     * Setter method for the date of the asteroid's closts approach
     * @param closestApproachDate
     *  The date of the asteroid's closest approach
     */
    public void setClosestApproachDate(Date closestApproachDate) {
        this.closestApproachDate = closestApproachDate;
    }

    /**
     * Getter method for the miss distance of the closest approach
     * @return
     *  The miss distance of the closest approach
     */
    public double getMissDistance() {
        return missDistance;
    }

    /**
     * Setter method for the miss distance of the closest approach
     * @param missDistance
     *  The miss distance of the closest approach
     */
    public void setMissDistance(double missDistance) {
        this.missDistance = missDistance;
    }

    /**
     * Getter method for the orbital body the asteroid orbits
     * @return
     *  The orbital body which the asteroid orbits
     */
    public String getOrbitingBody() {
        return orbitingBody;
    }

    /**
     * Setter method for the orbital body the asteroid orbits
     * @param orbitingBody
     *  The orbital body which the asteroid orbits
     */
    public void setOrbitingBody(String orbitingBody) {
        this.orbitingBody = orbitingBody;
    }
}
