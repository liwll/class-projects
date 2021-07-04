import java.util.Comparator;
import java.util.Date;

/**
 * Class which implements the comparator interface to compare dates.
 *
 * liwll
 */
public class ApproachDateComparator implements Comparator<NearEarthObject> {
    /**
     * The compare method to compare dates
     * @param left
     *  The first NearEarthObject you wish to compare
     * @param right
     *  The other NearEarthObject you wish to compare
     * @return
     *  An int value -1, 0, or 1 depending on the comparison
     */
    public int compare(NearEarthObject left, NearEarthObject right) {
        Date approachDate1 = left.getClosestApproachDate();
        Date approachDate2 = right.getClosestApproachDate();
        return approachDate1.compareTo(approachDate2);
    }
}
