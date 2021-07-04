/**
 * This class represents a road in the simulation.
 * It has constant fields for the ways (2) and number of lanes (3).
 * It also has fields for the name of the road, the lanes themselves,
 * the greenTime, leftSignalGreenTime, and lightValue of the road.
 *
 * @author
 * liwll
 */
public class TwoWayRoad {
    public static final int FORWARD_WAY = 0, BACKWARD_WAY = 1, NUM_WAYS = 2,
            LEFT_LANE = 0, MIDDLE_LANE = 1, RIGHT_LANE = 2, NUM_LANES = 3;
    private String name;
    private int greenTime, leftSignalGreenTime;
    private VehicleQueue[][] lanes;
    private LightValue lightValue;

    /**
     * Default constructor for a TwoWayRoad
     * @param initName
     *  The name that the road is assigned
     * @param initGreenTime
     *  The greenTime for the traffic light of the road
     * @throws IllegalArgumentException
     *  If the initName is null or the initGreenTime is negative
     */
    public TwoWayRoad(String initName, int initGreenTime)
            throws IllegalArgumentException{
        if (initGreenTime <= 0 || initName == null)
            throw new IllegalArgumentException();
        name = initName;
        greenTime = initGreenTime;
        leftSignalGreenTime = (int) ((1.0/NUM_LANES) * (double) greenTime);
        lanes = new VehicleQueue[NUM_WAYS][NUM_LANES];
        for (int i = 0; i < NUM_WAYS; i++) {
            for (int j = 0; j < NUM_LANES; j++) {
                lanes[i][j] = new VehicleQueue();
            }
        }
        lightValue = LightValue.RED;
    }

    /**
     * Method which represents the passage of time in the simulation.
     * It dequeues vehicles according the the lightValue of the road.
     * @param timerVal
     *  The current time in the simulation.
     * @return
     *  An array of Vehicles that have been dequeued.
     * @throws IllegalArgumentException
     *  If the time is negative.
     */
    public Vehicle[] proceed(int timerVal) throws IllegalArgumentException{
        if (timerVal <= 0)
            throw new IllegalArgumentException();

        Vehicle[] deqVehicles = new Vehicle[NUM_WAYS*(NUM_LANES-1)];
        int arrayPlace = 0;

        if (lightValue == LightValue.GREEN) {
            for (int i = 0; i < NUM_WAYS; i++) {
                for (int j = 1; j < NUM_LANES; j++) {
                    if (!lanes[i][j].isEmpty()) {
                        deqVehicles[arrayPlace] = lanes[i][j].dequeue();
                        arrayPlace++;
                    }
                }
            }
        }
        else if (lightValue == LightValue.LEFT_SIGNAL) {
            for (int i = 0; i < NUM_WAYS; i++) {
                if (!lanes[i][0].isEmpty()) {
                    deqVehicles[arrayPlace] = lanes[i][0].dequeue();
                    arrayPlace++;
                }
            }
        }
        return deqVehicles;
    }

    /**
     * Method which enqueues a Vehicle onto the road,
     * at a specified way and lane.
     * @param wayIndex
     *  The index of the specified way
     * @param laneIndex
     *  The index of the specified lane
     * @param vehicle
     *  The Vehicle to be enqueued
     * @throws IllegalArgumentException
     *  When the way or lane are out of bounds, or the vehicle is null
     */
    public void enqueueVehicle(int wayIndex, int laneIndex, Vehicle vehicle)
            throws IllegalArgumentException{
        if (wayIndex > 1 || wayIndex < 0 || laneIndex < 0
                || laneIndex > 2 || vehicle == null)
            throw new IllegalArgumentException();

        lanes[wayIndex][laneIndex].enqueue(vehicle);
    }

    /**
     * Method which determines if a specified lane is empty
     * @param wayIndex
     *  The specified way of the lane
     * @param laneIndex
     *  The specified lane
     * @return
     *  True if the lane is empty, false if not
     * @throws IllegalArgumentException
     *  If the specified way or lane are out of bounds
     */
    public boolean isLaneEmpty(int wayIndex, int laneIndex)
            throws IllegalArgumentException{
        if (wayIndex > 1 || wayIndex < 0 || laneIndex < 0 || laneIndex > 2)
            throw new IllegalArgumentException();

        return lanes[wayIndex][laneIndex].isEmpty();
    }

    /**
     * Method which determines if all the lanes in the road are empty
     * @return
     *  True if there are no cars on the road, false if not
     */
    public boolean isEmpty() {
        for (int i = 0; i < NUM_WAYS; i++) {
            for (int j = 0; j < NUM_LANES; j++) {
                if (!isLaneEmpty(i, j))
                    return false;
            }
        }
        return true;
    }

    /**
     * Method which determines if the right and middle lanes are empty
     * @return
     *  True if all the right and middle lanes are empty, false if not
     */
    public boolean isRightMiddleEmpty() {
        for (int i = 0; i < NUM_WAYS; i++) {
            for (int j = 1; j < NUM_LANES; j++) {
                if (!isLaneEmpty(i, j))
                    return false;
            }
        }
        return true;
    }

    /**
     * Getter method for the greenTime of the road
     * @return
     *  The greenTime of the road
     */
    public int getGreenTime() {
        return greenTime;
    }

    /**
     * Getter method for the lightValue of the road
     * @return
     *  The light value of the road
     */
    public LightValue getLightValue() {
        return lightValue;
    }

    /**
     * Getter method for the lanes of the road
     * @return
     *  The array of VehicleQueues which represent the lanes of the road
     */
    public VehicleQueue[][] getLanes() {
        return lanes;
    }

    /**
     * Getter method for the name of the road
     * @return
     *  The name of the road
     */
    public String getName() {
        return name;
    }

    /**
     * Getter method for the leftSignalGreenTime of the road
     * @return
     *  The leftSignalGreenTime of the road
     */
    public int getLeftSignalGreenTime() {
        return leftSignalGreenTime;
    }

    /**
     * Setter method for the LightValue of the road
     * @param light
     *  The desired LightValue to set the road to
     */
    public void setLightValue(LightValue light) {
        lightValue = light;
    }
}
