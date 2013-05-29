/************************************************************************ 
 * Copyright ReserveOut, LLC
 * ============================================= 
 *  Last edited by: $Author: ehab $
 *              on: $Date: 29 May 2013 16:57:03 $
 *        Filename: $RCSfile: NonstrictReadWriteNaturalIdRegionAccessStrategy.java $
 * 
 */
package com.googlecode.hibernate.memcached.strategy;

import org.hibernate.cache.CacheException;
import org.hibernate.cache.spi.access.SoftLock;

import com.googlecode.hibernate.memcached.NaturalIdRegionImpl;

/**
 * @author Eric Dalquist
 */
public class NonstrictReadWriteNaturalIdRegionAccessStrategy extends BaseNaturalIdRegionAccessStrategy {
    public NonstrictReadWriteNaturalIdRegionAccessStrategy(NaturalIdRegionImpl region) {
        super(region);
    }

    @Override
    public void unlockItem(Object key, SoftLock lock) throws CacheException {
        evict(key);
    }

    @Override
    public void remove(Object key) throws CacheException {
        evict(key);
    }

    /**
     * Returns <code>false</code> since this is an asynchronous cache access
     * strategy.
     * 
     * @see org.hibernate.cache.ehcache.internal.strategy.NonStrictReadWriteEhcacheNaturalIdRegionAccessStrategy
     */
    @Override
    public boolean insert(Object key, Object value) throws CacheException {
        return false;
    }

    /**
     * Returns <code>false</code> since this is a non-strict read/write cache
     * access strategy
     * 
     * @see org.hibernate.cache.ehcache.internal.strategy.NonStrictReadWriteEhcacheNaturalIdRegionAccessStrategy
     */
    @Override
    public boolean afterInsert(Object key, Object value) throws CacheException {
        return false;
    }

    /**
     * Removes the entry since this is a non-strict read/write cache strategy.
     * 
     * @see org.hibernate.cache.ehcache.internal.strategy.NonStrictReadWriteEhcacheNaturalIdRegionAccessStrategy
     */
    public boolean update(Object key, Object value) throws CacheException {
        remove(key);
        return false;
    }
}