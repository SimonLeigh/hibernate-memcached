/************************************************************************ 
 * Copyright ReserveOut, LLC
 * ============================================= 
 *  Last edited by: $Author: ehab $
 *              on: $Date: 29 May 2013 16:48:57 $
 *        Filename: $RCSfile: ReadOnlyNaturalIdRegionAccessStrategy.java $
 * 
 */
package com.googlecode.hibernate.memcached.strategy;

import org.hibernate.cache.CacheException;
import org.hibernate.cache.spi.access.SoftLock;

import com.googlecode.hibernate.memcached.NaturalIdRegionImpl;

public class ReadOnlyNaturalIdRegionAccessStrategy extends BaseNaturalIdRegionAccessStrategy {

    public ReadOnlyNaturalIdRegionAccessStrategy(NaturalIdRegionImpl region) {
        super( region );
    }

    @Override
    public void unlockItem(Object key, SoftLock lock) throws CacheException {
        evict( key );
    }
}
