/**
 * This class simulates events occurring with a specified probability.
 * It has a private field probability which defines this behavior.
 *
 * @author
 * liwll
 */
public class BooleanSource {
    private double probability;

    /**
     * Default constructor for a BooleanSource
     * @param initProbability
     *  The probability you want the event to occur with
     * @throws IllegalArgumentException
     *  When the probability <= 0 or 1 < probability
     */
    public BooleanSource(double initProbability)
            throws IllegalArgumentException{
        if (initProbability <= 0 || initProbability > 1)
            throw new IllegalArgumentException();
        probability = initProbability;
    }

    /**
     * This method returns true if the event occurs and false if not.
     * @return
     *  A boolean, true or false, depending if the event occured.
     */
    public boolean occurs() {
        double foo = Math.random();
        return (foo < probability);
    }

    /**
     * Getter method for the probability
     * @return
     *  The probability of the BooleanSource
     */
    public double getProb() {
        return probability;
    }

    /**
     * Setter method for the probability
     * @param newProb
     *  The probability you wish to set the BooleanSource to
     * @throws IllegalArgumentException
     *  When the probability <= 0 or 1 < probability
     */
    public void setProb(double newProb) throws IllegalArgumentException{
        if (newProb <= 0 || newProb > 1)
            throw new IllegalArgumentException();
        probability = newProb;
    }
}
