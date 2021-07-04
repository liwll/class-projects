import java.util.Iterator;
import java.util.LinkedList;

/**
 * This class inherits LinkedList to represent a queue of vehicles.
 * It has one field, a LinkedList called vehicles.
 * This field is used to accomplish all the operations of the queue.
 *
 * @author
 * liwll
 */
public class VehicleQueue extends LinkedList{
    private LinkedList<Vehicle> vehicles;

    /**
     * The default constructor for a VehicleQueue
     */
    public VehicleQueue() {
        vehicles = new LinkedList<>();
    }

    /**
     * The enqueue method for the queue
     * @param v
     *  The Vehicle to be enqueued
     */
    public void enqueue(Vehicle v) {
        vehicles.addLast(v);
    }

    /**
     * The dequeue method for the queue
     * @return
     *  The Vehicle that was dequeued
     */
    public Vehicle dequeue() {
        return vehicles.removeFirst();
    }

    /**
     * The size method for the queue
     * @return
     *  The number of Vehicles in the queue.
     */
    public int size() {
        return vehicles.size();
    }

    /**
     * Method to determine if the VehicleQueue is empty
     * @return
     *  True if the queue is empty, false if not
     */
    public boolean isEmpty() {
        return vehicles.isEmpty();
    }


    /**
     * Method which returns the Vehicle at the front of the Queue
     * @return
     *  The Vehicle at the front of the Queue
     */
    public Vehicle peek() {
        Vehicle vehicle = vehicles.removeFirst();
        vehicles.addFirst(vehicle);
        return vehicle;
    }

    /**
     * Method which clones the VehicleQueue
     * @return
     *  The clone of the VehicleQueue
     */
    public Object clone() {
        VehicleQueue clone = new VehicleQueue();
        Iterator<Vehicle> it = this.vehicles.iterator();
        while (it.hasNext())
            clone.enqueue(it.next());
        return clone;
    }
}
