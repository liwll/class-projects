import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Class which runs the simulation involving the Intersection.
 * It has fields to keep track of statistics such as the totalWaitTime,
 * avgWaitTime, longestWaitTime, and totalCarsPassed.
 * It uses these fields to print the statistics after each time step.
 *
 * @author
 * liwll
 */
public class IntersectionSimulator {
    private static int totalWaitTime;
    private static double avgWaitTime;
    private static int longestWaitTime;
    private static int totalCarsPassed;

    /**
     * The main method of the simulator which prompts the user for
     * parameters, either through command line arguments or by input
     * @param args
     *  The command line arguments that may be parsed for parameters
     */
    public static void main(String[] args) {
        System.out.println("Welcome to IntersectionSimulator 2021\n");

        try {
            if (args.length > 1) {
                int simTime = Integer.parseInt(args[0]);
                double prob = Double.parseDouble(args[1]);
                int numStreets = Integer.parseInt(args[2]);

                if (numStreets > 2 || numStreets <= 0)
                    throw new IllegalArgumentException();

                String[] streetNames = new String[numStreets];
                int[] greenTimes = new int[numStreets];

                for (int i = 0; i < numStreets; i++) {
                    streetNames[i] = args[3 + i];
                    greenTimes[i] = Integer.parseInt(args[3 + numStreets + i]);
                }

                if (streetNames[0].equals(streetNames[1])) {
                    System.out.println("Duplicate detected, try again.");
                    throw new IllegalArgumentException();
                }

                System.out.println("\nStarting Simulation...\n");
                simulate(simTime, prob, streetNames, greenTimes);
            } else {
                Scanner userIn = new Scanner(System.in);
                System.out.print("Input the simulation time: ");
                int simTime = userIn.nextInt();

                System.out.print("Input the arrival probability: ");
                double prob = userIn.nextDouble();

                System.out.print("Input number of Streets: ");
                int numStreets = userIn.nextInt();
                userIn.nextLine();


                if (numStreets > 2 || numStreets <= 0)
                    throw new IllegalArgumentException();

                int i;
                String[] streetNames = new String[numStreets];
                for (i = 0; i < numStreets; i++) {
                    System.out.print("Input Street " + (i + 1) + " name: ");
                    streetNames[i] = userIn.nextLine();
                }

                while (streetNames[0].equals(streetNames[1])) {
                    System.out.println("Duplicate detected.");
                    System.out.print("Input Street " + (i) + " name: ");
                    streetNames[1] = userIn.nextLine();
                }

                int[] greenTimes = new int[numStreets];
                for (i = 0; i < numStreets; i++) {
                    System.out.print("Input max green time for "
                            + streetNames[i] + ": ");
                    greenTimes[i] = userIn.nextInt();
                }

                System.out.println("\nStarting Simulation...\n");
                simulate(simTime, prob, streetNames, greenTimes);
            }
        }
        catch (NumberFormatException e) {
            System.out.println("Argument format invalid, please try again.");
        }
        catch (InputMismatchException e) {
            System.out.println("Input mismatch, please try again.");
        }
        catch (IllegalArgumentException e) {
            System.out.println("Invalid input, please try again.");
        }

    }

    /**
     * The method which runs the actual simulation
     * @param simulationTime
     *  The total number of time steps for the simulation
     * @param arrivalProbability
     *  The probability of a vehicle arriving at any particular lane
     * @param roadNames
     *  An array of names for each of the roads
     * @param maxGreenTimes
     *  An array of the greenTimes for each of the roads
     */
    public static void simulate(int simulationTime, double arrivalProbability,
                                String[] roadNames, int[] maxGreenTimes) {
        try {
            if (simulationTime <= 0)
                throw new IllegalArgumentException();
            int numStreets = roadNames.length;
            TwoWayRoad[] roads = new TwoWayRoad[numStreets];
            for (int i = 0; i < numStreets; i++) {
                roads[i] = new TwoWayRoad(roadNames[i], maxGreenTimes[i]);
            }
            Intersection intersection = new Intersection(roads);
            BooleanSource bool = new BooleanSource(arrivalProbability);
            int time = 1;
            totalCarsPassed = 0;
            totalWaitTime = 0;
            longestWaitTime = 0;
            Vehicle[] vehicles;

            System.out.print("#########################################"
                    + "#######################################\n");

            while (time <= simulationTime) {
                int index = intersection.getLightIndex();

                if (intersection.getCountdownTimer() == 0 || (intersection.
                        getRoads()[index].isEmpty() && time != 1)) {
                    intersection.getRoads()[index].
                            setLightValue(LightValue.RED);
                    intersection.setLightIndex((index + 1) % roads.length);
                    index = intersection.getLightIndex();
                    intersection.
                            setCountdownTimer(roads[index].getGreenTime());
                }


                int countdownTimer = intersection.getCountdownTimer();

                intersection.getRoads()[index].setLightValue(LightValue.GREEN);
                if (intersection.getCountdownTimer() <= intersection.
                        getRoads()[index].getLeftSignalGreenTime()) {
                    intersection.getRoads()[index].
                            setLightValue(LightValue.LEFT_SIGNAL);
                }

                printTimeInfo(intersection, time, countdownTimer);

                String carsArrived =
                        simulateCarArrivals(intersection, bool, time);

                int carsWaiting = 0;
                for (int n = 0; n < intersection.getNumRoads(); n++) {
                    for (int i = 0; i < TwoWayRoad.NUM_WAYS; i++) {
                        for (int j = 0; j < TwoWayRoad.NUM_LANES; j++) {
                            carsWaiting +=
                                    intersection.getRoads()[n].
                                            getLanes()[i][j].size();
                        }
                    }
                }

                vehicles = intersection.timeStep();
                totalCarsPassed = printDisplay(vehicles, carsArrived, time,
                        carsWaiting, intersection);

                time++;
            }
            while (!intersection.isEmpty()) {
                int index = intersection.getLightIndex();

                if (intersection.getCountdownTimer() == 0
                        || intersection.getRoads()[index].isEmpty()) {
                    intersection.getRoads()[index].
                            setLightValue(LightValue.RED);
                    intersection.setLightIndex
                            ((index + 1) % roads.length);
                    index = intersection.getLightIndex();
                    intersection.
                            setCountdownTimer(roads[index].getGreenTime());
                }

                int countdownTimer = intersection.getCountdownTimer();

                intersection.getRoads()[index].setLightValue(LightValue.GREEN);
                if (intersection.getCountdownTimer() <= intersection.
                        getRoads()[index].getLeftSignalGreenTime() ||
                        intersection.getRoads()[index].isRightMiddleEmpty()) {
                    intersection.getRoads()[index].
                            setLightValue(LightValue.LEFT_SIGNAL);
                }

                printTimeInfo(intersection, time, countdownTimer);

                String carsArrived = "ARRIVING CARS:\n";

                int carsWaiting = 0;
                for (int n = 0; n < intersection.getNumRoads(); n++) {
                    for (int i = 0; i < TwoWayRoad.NUM_WAYS; i++) {
                        for (int j = 0; j < TwoWayRoad.NUM_LANES; j++) {
                            carsWaiting +=
                                    intersection.getRoads()[n].
                                            getLanes()[i][j].size();
                        }
                    }
                }

                vehicles = intersection.timeStep();
                System.out.println("Cars no longer arriving.\n");
                totalCarsPassed = printDisplay(vehicles, carsArrived, time,
                        carsWaiting, intersection);

                time++;

            }


            String summary = String.format("%s\n\n%-22s %d steps"
                            + "\n%-22s %d vehicles\n%-22s %d turns"
                            + "\n%-22s %d turns\n%-22s %.2f turns",
                    "SIMULATION SUMMARY:", "Total Time:", (time - 1),
                    "Total vehicles:", totalCarsPassed, "Longest wait time:",
                    longestWaitTime, "Total wait time:", totalWaitTime,
                    "Average wait time:", avgWaitTime);
            System.out.println("#####################################"
                    + "###########################################\n"
                    + "#############################################"
                    + "###################################\n");
            System.out.print(summary + "\n\nEnd simulation.");
        }
        catch(IllegalArgumentException e) {
            System.out.println("Invalid arguments, please try again.");
        }

    }

    /**
     * Method to simulate the arrival of Vehicles (within the simulation)
     * @param intersection
     *  The specified intersection where Vehicles will arrive
     * @param bool
     *  The specified BooleanSource which uses the occurs method
     * @param time
     *  The current time, this is used to calculate statistics
     * @return
     *  A string of the Vehicles that were enqueued to the Intersection
     */
    public static String simulateCarArrivals(Intersection intersection,
                                             BooleanSource bool, int time) {
        String arrivingCars = "ARRIVING CARS:\n";
        for (int n = 0; n < intersection.getNumRoads(); n++) {
            for (int i = 0; i < TwoWayRoad.NUM_WAYS; i++) {
                for (int j = 0; j < TwoWayRoad.NUM_LANES; j++) {
                    if (bool.occurs()) {
                        Vehicle v = new Vehicle(time);
                        intersection.enqueueVehicle(n, i, j, v);
                        String name =
                                intersection.getRoads()[n].getName();
                        String way, lane;
                        if (i == 0) {
                            way = "FORWARD";
                            switch (j) {
                                case 0:
                                    lane = "LEFT";
                                    break;
                                case 1:
                                    lane = "MIDDLE";
                                    break;
                                case 2:
                                    lane = "RIGHT";
                                    break;
                                default:
                                    lane = "easter egg";
                                    break;
                            }
                        }
                        else {
                            way = "BACKWARD";
                            switch (j) {
                                case 0:
                                    lane = "LEFT";
                                    break;
                                case 1:
                                    lane = "MIDDLE";
                                    break;
                                case 2:
                                    lane = "RIGHT";
                                    break;
                                default:
                                    lane = "easter egg";
                                    break;
                            }
                        }
                        arrivingCars +=
                                String.format("    Car[%03d] entered %s, "
                                                + "going %s in %s lane.\n",
                                        v.getSerialID(),
                                        name , way, lane);
                    }
                }
            }
        }
        return arrivingCars;
    }

    /**
     * Method which prints the current timeStep and countdownTimer
     * @param inter
     *  The specified Intersection which contains the time fields
     * @param time
     *  The current time step of the simulation
     * @param timer
     *  The countdownTimer of the Intersection in the simulation
     */
    public static void printTimeInfo(Intersection inter, int time, int timer) {
        LightValue light = inter.getCurrentLightValue();

        String lightValue;
        switch (light) {
            case GREEN:
                lightValue = "Green Light";
                break;
            case LEFT_SIGNAL:
                lightValue = "Left Signal";
                break;
            default:
                lightValue = "Purple";
        }
        String roadName = inter.
                getRoads()[inter.getLightIndex()].getName();

        System.out.printf("\nTime Step: %d\n\n" +
                        "%s for %s.\nTimer = %d\n\n",
                time, lightValue, roadName, timer);
    }

    /**
     * Method which displays the statistics and Intersection of the time step,
     * such as the Vehicles that arrived, the Vehicles that passed through,
     * the Intersection itself, and the other statistics
     * @param vehicles
     *  The Vehicles that were dequeued (passed through) the Intersection
     * @param arrivingCars
     *  A String of cars that arrived onto the Intersection
     * @param time
     *  The current time step of the simulation
     * @param carsWaiting
     *  The number of cars currently waiting in the Intersection
     * @param inter
     *  The specified Intersection to retrieve the fields from
     * @return
     *  An int value of the total number of cars that have
     *  passed through the Intersection
     */
    public static int printDisplay(Vehicle[] vehicles, String arrivingCars,
                                    int time, int carsWaiting,
                                   Intersection inter) {
        String passingCars = "PASSING CARS:\n";
        int carsPassed = 0;
        int waitTime;
        Vehicle v;
        for (int i = 0; i < vehicles.length; i++) {
            if (vehicles[i] != null) {
                v = vehicles[i];
                waitTime = time - v.getTimeArrived();
                passingCars += String.format("    Car[%03d] passes through. "
                        + "Wait time of %d.\n", v.getSerialID(), waitTime);
                totalWaitTime += waitTime;
                if (waitTime > longestWaitTime)
                    longestWaitTime = waitTime;
                carsPassed++;
            }
        }

        totalCarsPassed += carsPassed;
        carsWaiting = carsWaiting - carsPassed;
        if (totalCarsPassed > 0)
            avgWaitTime = (double) totalWaitTime / totalCarsPassed;
        else
            avgWaitTime = 0.0;

        String statistics = String.format("%s\n%-25s%d cars"
                        + "\n%-25s%d cars\n%-25s%d turns\n%-25s%.2f turns\n",
                "STATISTICS:", "Cars currently waiting:",
                carsWaiting, "Total cars passed:", totalCarsPassed,
                "Total wait time:", totalWaitTime,
                "Average wait time:", avgWaitTime);


        System.out.print(arrivingCars + "\n" + passingCars + "\n");
        inter.display();
        System.out.print(statistics + "\n"
                + "########################################"
                + "########################################\n");

        return totalCarsPassed;
    }
}
