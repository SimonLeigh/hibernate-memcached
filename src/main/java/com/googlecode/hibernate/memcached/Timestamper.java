/************************************************************************ 
 * Copyright ReserveOut, LLC
 * ============================================= 
 *  Last edited by: $Author: ehab $
 *              on: $Date: 29 May 2013 16:45:15 $
 *        Filename: $RCSfile: Timestamper.java $
 * 
 */

package com.googlecode.hibernate.memcached;

import java.util.concurrent.atomic.AtomicLong;

public final class Timestamper {
    private static final int BIN_DIGITS = 12;
    public static final short ONE_MS = 1 << BIN_DIGITS;
    private static final AtomicLong VALUE = new AtomicLong();

    public static long next() {
        while (true) {
            long base = System.currentTimeMillis() << BIN_DIGITS;
            long maxValue = base + ONE_MS - 1;

            for (long current = VALUE.get(), update = Math.max(base, current + 1); update < maxValue; current = VALUE
                    .get(), update = Math.max(base, current + 1)) {
                if (VALUE.compareAndSet(current, update)) {
                    return update;
                }
            }
        }
    }

    private Timestamper() {
    }
}
