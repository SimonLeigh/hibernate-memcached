/************************************************************************ 
 * Copyright ReserveOut, LLC
 * ============================================= 
 *  Last edited by: $Author: ehab $
 *              on: $Date: 29 May 2013 16:54:24 $
 *        Filename: $RCSfile: ReadWriteNaturalIdRegionAccessStrategy.java $
 * 
 */
package com.googlecode.hibernate.memcached.strategy;

import java.util.Comparator;

import org.hibernate.cache.CacheException;
import org.hibernate.cache.spi.NaturalIdRegion;
import org.hibernate.cache.spi.access.NaturalIdRegionAccessStrategy;
import org.hibernate.cache.spi.access.SoftLock;

import com.googlecode.hibernate.memcached.BaseGeneralDataRegion;
import com.googlecode.hibernate.memcached.NaturalIdRegionImpl;

public class ReadWriteNaturalIdRegionAccessStrategy extends AbstractReadWriteAccessStrategy implements
        NaturalIdRegionAccessStrategy {

    private final NaturalIdRegionImpl region;

    public ReadWriteNaturalIdRegionAccessStrategy(NaturalIdRegionImpl region) {
        this.region = region;
    }

    @Override
    public boolean insert(Object key, Object value) throws CacheException {
        return false;
    }

    @Override
    public boolean update(Object key, Object value) throws CacheException {
        return false;
    }

    @Override
    public boolean afterInsert(Object key, Object value) throws CacheException {

        try {
            writeLock.lock();
            Lockable item = (Lockable) region.get(key);
            if (item == null) {
                region.put(key, new Item(value, null, region.nextTimestamp()));
                return true;
            } else {
                return false;
            }
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public boolean afterUpdate(Object key, Object value, SoftLock lock) throws CacheException {
        try {
            writeLock.lock();
            Lockable item = (Lockable) region.get(key);

            if (item != null && item.isUnlockable(lock)) {
                Lock lockItem = (Lock) item;
                if (lockItem.wasLockedConcurrently()) {
                    decrementLock(key, lockItem);
                    return false;
                } else {
                    region.put(key, new Item(value, null, region.nextTimestamp()));
                    return true;
                }
            } else {
                handleLockExpiry(key, item);
                return false;
            }
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    Comparator getVersionComparator() {
        return region.getCacheDataDescription().getVersionComparator();
    }

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
}
