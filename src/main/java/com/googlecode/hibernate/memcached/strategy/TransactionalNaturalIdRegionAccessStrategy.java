/************************************************************************ 
 * Copyright ReserveOut, LLC
 * ============================================= 
 *  Last edited by: $Author: ehab $
 *              on: $Date: 29 May 2013 16:57:55 $
 *        Filename: $RCSfile: TransactionalNaturalIdRegionAccessStrategy.java $
 * 
 */
package com.googlecode.hibernate.memcached.strategy;

import org.hibernate.cache.CacheException;

import com.googlecode.hibernate.memcached.NaturalIdRegionImpl;

/**
 * @author Eric Dalquist
 */
public class TransactionalNaturalIdRegionAccessStrategy extends BaseNaturalIdRegionAccessStrategy {
    public TransactionalNaturalIdRegionAccessStrategy(NaturalIdRegionImpl region) {
        super(region);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void remove(Object key) throws CacheException {
        evict(key);
    }

}