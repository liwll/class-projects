/**
 * This class represents a Vehicle in the simulation.
 * It has fields for the serialCounter, serialID, and time arrived.
 * The serialID is used to identify the vehicle.
 *
 * @author
 * liwll
 */
public final class Vehicle {
    private static int serialCounter = 0;
    private int serialID;
    private int timeArrived;


    /**
     * Default constructor for the Vehicle
     * @param initTimeArrived
     *  The time that the vehicle arrived
     * @throws IllegalArgumentException
     *  When the time arrived is negative or 0.
     */
    public Vehicle(int initTimeArrived) throws IllegalArgumentException{
        if (initTimeArrived <= 0)
            throw new IllegalArgumentException();
        serialID = serialCounter + 1;
        timeArrived = initTimeArrived;
        serialCounter++;
    }

    /**
     * Getter method for the serialID
     * @return
     *  The Vehicle's serialID
     */
    public int getSerialID() {
        return serialID;
    }

    /**
     * Getter method for the time the Vehicle arrived
     * @return
     *  The timeArrived of the Vehicle
     */
    public int getTimeArrived() {
        return timeArrived;
    }
}
