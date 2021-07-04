/**
 * This class represents the Intersection in our simulation.
 * It is comprised of an array of roads, the lightIndex, and a timer.
 * The lightIndex specifies which road is active,
 * and the timer tracks the remaining time steps left for the road.
 *
 * @author
 * liwll
 */
public class Intersection {
    private TwoWayRoad[] roads;
    private int lightIndex;
    private int countdownTimer;
    private final int MAX_ROADS = 2;

    /**
     * The default constructor for an Intersection
     * @param initRoads
     *  The roads that form the Intersection
     * @throws IllegalArgumentException
     *  If the roads are null or there are too many roads
     */
    public Intersection(TwoWayRoad[] initRoads)
            throws IllegalArgumentException{
        if (initRoads == null || initRoads.length > MAX_ROADS)
            throw new IllegalArgumentException();
        for (int i = 0; i < initRoads.length; i++) {
            if (initRoads[i] == null)
                throw new IllegalArgumentException();
        }

        roads = initRoads;
        lightIndex = 0;
        countdownTimer = roads[lightIndex].getGreenTime();
    }

    /**
     * This method executes the proceeding of time and decrements the timer
     * @return
     *  An array of vehicles that has been dequeued
     *  from the road during the time step
     */
    public Vehicle[] timeStep() {
        Vehicle[] vehicles = roads[lightIndex].proceed(countdownTimer);
        countdownTimer--;
        return vehicles;
    }

    /**
     * Enqueues a Vehicle into the Intersection
     * @param roadIndex
     *  The desired road to enqueue the Vehicle
     * @param wayIndex
     *  The desired way on the road to enqueue the Vehicle
     * @param laneIndex
     *  The desired lane to enqueue the Vehicle
     * @param vehicle
     *  The vehicle to be enqueued
     * @throws IllegalArgumentException
     *  When the road, way, or lane are invalid, or if the Vehicle is null
     */
    public void enqueueVehicle(int roadIndex, int wayIndex,
                               int laneIndex, Vehicle vehicle)
            throws IllegalArgumentException{
        if (vehicle == null || roadIndex < 0 || roadIndex >= roads.length
                || wayIndex < 0 || wayIndex >= TwoWayRoad.NUM_WAYS
                || laneIndex < 0 || laneIndex >= TwoWayRoad.NUM_LANES)
            throw new IllegalArgumentException();

        roads[roadIndex].enqueueVehicle(wayIndex, laneIndex, vehicle);
    }

    /**
     * Method which prints the intersection to the terminal
     * Displays the road, Vehicles, and traffic lights in the Intersection.
     */
    public void display() {
        for (TwoWayRoad road: roads) {
            String header = String.format("%s:\n%30s%23s\n%s%45s\n",
                    road.getName(), "FORWARD",
                    "BACKWARD", "==============================",
                    "==============================");
            String roadDisplay = "";
            for (int i = 0; i < TwoWayRoad.NUM_LANES; i++) {
                for (int j = 0; j < 6 - road.getLanes()[0][i].size(); j++) {
                    roadDisplay += "     ";
                }

                VehicleQueue cloneQueue =
                        (VehicleQueue) road.getLanes()[0][i].clone();

                String cars = "";
                int serialID;
                String formattedID;
                for (int j = 0; j < road.getLanes()[0][i].size(); j++) {
                    serialID = cloneQueue.dequeue().getSerialID();
                    formattedID = String.format("%03d", serialID);
                    cars = "[" + formattedID + "]" + cars;
                }

                String trafficLights = "";

                switch (i) {
                    case 0:
                        trafficLights += " [L] ";
                        break;
                    case 1:
                        trafficLights += " [M] ";
                        break;
                    case 2:
                        trafficLights += " [R] ";
                        break;
                }

                switch (road.getLightValue()) {
                    case GREEN:
                        if (i == 0)
                            trafficLights += "x    ";
                        if (i == 1)
                            trafficLights += "     ";
                        if (i == 2)
                            trafficLights += "    x";
                        break;
                    case LEFT_SIGNAL:
                        if (i == 0)
                            trafficLights += "    x";
                        if (i == 1)
                            trafficLights += "x   x";
                        if (i == 2)
                            trafficLights += "x    ";
                        break;
                    case RED:
                        trafficLights += "x   x";
                        break;
                }

                switch (i) {
                    case 0:
                        trafficLights += " [R] ";
                        break;
                    case 1:
                        trafficLights += " [M] ";
                        break;
                    case 2:
                        trafficLights += " [L] ";
                        break;
                    default:
                        trafficLights += "     ";
                        break;
                }

                String cars2 = "";
                cloneQueue = (VehicleQueue) road.getLanes()
                        [1][TwoWayRoad.NUM_LANES - i - 1].clone();

                for (int j = 0; j < road.getLanes()
                        [1][TwoWayRoad.NUM_LANES - i - 1].size(); j++) {
                    serialID = cloneQueue.dequeue().getSerialID();
                    formattedID = String.format("%03d", serialID);
                    cars2 += "[" + formattedID + "]";
                }

                roadDisplay += cars + trafficLights + cars2 + "\n";

                if (i != TwoWayRoad.NUM_LANES - 1)
                    roadDisplay += String.format("%s%45s\n",
                            "------------------------------",
                            "------------------------------");
            }

            String footer = String.format("%s%45s\n\n",
                    "==============================",
                    "==============================");

            String display = header + roadDisplay + footer;
            System.out.println(display);
        }
    }


    /**
     * Getter method for the number of roads in the Intersection
     * @return
     *  The number of roads in the Intersection
     */
    public int getNumRoads() {
        return roads.length;
    }

    /**
     * Getter method for the index of the active light
     * @return
     *  The lightIndex
     */
    public int getLightIndex() {
        return lightIndex;
    }

    /**
     * Getter method for the value of the countdownTimer
     * @return
     *  The current value of the countdownTimer
     */
    public int getCountdownTimer() {
        return countdownTimer;
    }

    /**
     * Getter method for the current value of the light
     * @return
     *  The lightValue, or value of the currently active light
     */
    public LightValue getCurrentLightValue() {
        return roads[lightIndex].getLightValue();
    }

    /**
     * Getter method for the roads in the Intersection
     * @return
     *  An array of the roads that form the Intersection
     */
    public TwoWayRoad[] getRoads() {
        return roads;
    }

    /**
     * Setter method for the lightIndex of the Intersection
     * @param index
     *  The index to set the lightIndex to
     */
    public void setLightIndex(int index) {
        lightIndex = index;
    }

    /**
     * Setter method for the countdownTimer
     * @param timer
     *  The int value to set the timer to
     */
    public void setCountdownTimer(int timer) {
        countdownTimer = timer;
    }

    /**
     * Method which determines if the Intersection is empty
     * @return
     *  True if the intersection is completely empty, false if not
     */
    public boolean isEmpty() {
        for (int n = 0; n < getNumRoads(); n++) {
            if (!roads[n].isEmpty())
                return false;
        }
        return true;
    }
}
