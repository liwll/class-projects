import java.util.Comparator;

/**
 * Class which implements the comparator interface to compare referenceIDs.
 *
 * liwll
 */
public class ReferenceIDComparator implements Comparator<NearEarthObject> {
    /**
     * The compare method to compare referenceIDs
     * @param left
     *  The first NearEarthObject you wish to compare
     * @param right
     *  The other NearEarthObject you wish to compare
     * @return
     *  An int value -1, 0, or 1 depending on the comparison
     */
    public int compare(NearEarthObject left, NearEarthObject right) {
        int referenceID1 = left.getReferenceID();
        int referenceID2 = right.getReferenceID();
        if (referenceID1 > referenceID2)
            return 1;
        else if (referenceID1 == referenceID2)
            return 0;
        else
            return -1;
    }
}
