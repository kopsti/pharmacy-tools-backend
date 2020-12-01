package gr.appleton.ms.pharmacytools.common.utils;

import java.util.Calendar;
import java.util.Date;

/**
 * The Utility class with static useful methods.
 */
public final class Utilities {

    private Utilities() {
    }

    /**
     * Gets calendar with offset.
     *
     * @param type   the type
     * @param offset the offset
     * @return the calendar with offset
     */
    public static Calendar getCalendarWithOffset(final int type, final int offset) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(type, offset);
        return cal;
    }

}
