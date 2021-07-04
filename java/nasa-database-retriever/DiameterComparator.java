import java.util.Comparator;

/**
 * Class which implements the comparator interface to compare diameters.
 *
 * liwll
 */
public class DiameterComparator implements Comparator<NearEarthObject> {
    /**
     * The compare method to compare diameters
     * @param left
     *  The first NearEarthObject you wish to compare
     * @param right
     *  The other NearEarthObject you wish to compare
     * @return
     *  An int value -1, 0, or 1 depending on the comparison
     */
    public int compare(NearEarthObject left, NearEarthObject right) {
        double diameter1 = left.getAverageDiameter();
        double diameter2 = right.getAverageDiameter();
        if (diameter1 > diameter2)
            return 1;
        else if (diameter1 == diameter2)
            return 0;
        else
            return -1;
    }
}
