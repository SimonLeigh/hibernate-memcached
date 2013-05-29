/************************************************************************ 
 * Copyright ReserveOut, LLC
 * ============================================= 
 *  Last edited by: $Author: ehab $
 *              on: $Date: 29 May 2013 16:49:34 $
 *        Filename: $RCSfile: BaseNaturalIdRegionAccessStrategy.java $
 * 
 */
package com.googlecode.hibernate.memcached.strategy;

import org.hibernate.cache.CacheException;
import org.hibernate.cache.spi.NaturalIdRegion;
import org.hibernate.cache.spi.access.NaturalIdRegionAccessStrategy;
import org.hibernate.cache.spi.access.SoftLock;

import com.googlecode.hibernate.memcached.BaseGeneralDataRegion;
import com.googlecode.hibernate.memcached.NaturalIdRegionImpl;

class BaseNaturalIdRegionAccessStrategy extends BaseRegionAccessStrategy implements NaturalIdRegionAccessStrategy {
    private final NaturalIdRegionImpl region;

    @Override
    protected BaseGeneralDataRegion getInternalRegion() {
        return region;
    }

    @Override
    protected boolean isDefaultMinimalPutOverride() {
        return region.getSettings().isMinimalPutsEnabled();
    }

    @Override
    public NaturalIdRegion getRegion() {
        return region;
    }

    @Override
    public boolean insert(Object key, Object value ) throws CacheException {
        return putFromLoad( key, value, 0 , null );
    }

    @Override
    public boolean afterInsert(Object key, Object value ) throws CacheException {
        return false;
    }

    @Override
    public boolean update(Object key, Object value ) throws CacheException {
        return putFromLoad( key, value, 0 , null );
    }

    @Override
    public boolean afterUpdate(Object key, Object value, SoftLock lock) throws CacheException {
        return false;
    }

    BaseNaturalIdRegionAccessStrategy(NaturalIdRegionImpl region) {
        this.region = region;
    }
}