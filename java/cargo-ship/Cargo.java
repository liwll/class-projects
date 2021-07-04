/**
 * A class which represents cargo to be store in a stack.
 * The Cargo the attributes name, weight, and strength.
 *
 * @author
 * liwll
 */
public class Cargo {
    private String name;
    private double weight;
    private CargoStrength strength;

    /**
     * Default constructor for Cargo
     * @param initName
     *  The initial name of the Cargo
     * @param initWeight
     *  The initial weight of the Cargo
     * @param initStrength
     *  The initial strength of the Cargo
     * @throws IllegalArgumentException
     *  When the given name is null or the given weight is negative
     */
    public Cargo(String initName, double initWeight,
                 CargoStrength initStrength) throws IllegalArgumentException{
        if (initName == null || initWeight <= 0) {
            throw new IllegalArgumentException();
        }
        else {
            name = initName;
            weight = initWeight;
            strength = initStrength;
        }
    }

    /**
     * Getter method for the name of the Cargo
     * @return
     *  The String name of the Cargo
     */
    public String getName() {
        return name;
    }

    /**
     * Getter method for the weight of the Cargo
     * @return
     *  A double value that is the weight of the Cargo
     */
    public double getWeight() {
        return weight;
    }

    /**
     * Getter method for the strength of the Cargo
     * @return
     *  The strength of the Cargo: either FRAGILE, MODERATE, OR STURDY
     */
    public CargoStrength getStrength() {
        return strength;
    }
}
