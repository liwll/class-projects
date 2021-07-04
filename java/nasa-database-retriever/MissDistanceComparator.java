import java.util.Comparator;

/**
 * Class which implements the comparator interface to compare miss distances.
 *
 * liwll
 */
public class MissDistanceComparator implements Comparator<NearEarthObject> {
    /**
     * The compare method to compare miss distances
     * @param left
     *  The first NearEarthObject you wish to compare
     * @param right
     *  The other NearEarthObject you wish to compare
     * @return
     *  An int value -1, 0, or 1 depending on the comparison
     */
    public int compare(NearEarthObject left, NearEarthObject right) {
        double missDistance1 = left.getMissDistance();
        double missDistance2 = right.getMissDistance();
        if (missDistance1 > missDistance2)
            return 1;
        else if (missDistance1 == missDistance2)
            return 0;
        else
            return -1;
    }
}
