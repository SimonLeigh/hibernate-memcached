/************************************************************************ 
 * Copyright ReserveOut, LLC
 * ============================================= 
 *  Last edited by: $Author: ehab $
 *              on: $Date: 29 May 2013 16:50:21 $
 *        Filename: $RCSfile: BaseRegionAccessStrategy.java $
 * 
 */
package com.googlecode.hibernate.memcached.strategy;

import org.hibernate.cache.CacheException;
import org.hibernate.cache.spi.access.RegionAccessStrategy;
import org.hibernate.cache.spi.access.SoftLock;
import org.hibernate.internal.CoreMessageLogger;
import org.jboss.logging.Logger;

import com.googlecode.hibernate.memcached.BaseGeneralDataRegion;

abstract class BaseRegionAccessStrategy implements RegionAccessStrategy {
    private static final CoreMessageLogger LOG = Logger.getMessageLogger(
            CoreMessageLogger.class, BaseRegionAccessStrategy.class.getName()
    );

    protected abstract BaseGeneralDataRegion getInternalRegion();

    protected abstract boolean isDefaultMinimalPutOverride();

    public Object get(Object key, long txTimestamp) throws CacheException {
        return getInternalRegion().get( key );
    }

    public boolean putFromLoad(Object key, Object value, long txTimestamp, Object version) throws CacheException {
        return putFromLoad( key, value, txTimestamp, version, isDefaultMinimalPutOverride() );
    }

    public boolean putFromLoad(Object key, Object value, long txTimestamp, Object version, boolean minimalPutOverride)
            throws CacheException {

        if ( key == null || value == null ) {
            return false;
        }
        if ( minimalPutOverride && getInternalRegion().contains( key ) ) {
            LOG.debugf( "Item already cached: %s", key );
            return false;
        }
        LOG.debugf( "Caching: %s", key );
        getInternalRegion().put( key, value );
        return true;

    }

    /**
     * Region locks are not supported.
     *
     * @return <code>null</code>
     *
     * @see org.hibernate.cache.spi.access.EntityRegionAccessStrategy#lockRegion()
     * @see org.hibernate.cache.spi.access.CollectionRegionAccessStrategy#lockRegion()
     */
    public SoftLock lockRegion() throws CacheException {
        return null;
    }

    /**
     * Region locks are not supported - perform a cache clear as a precaution.
     *
     * @see org.hibernate.cache.spi.access.EntityRegionAccessStrategy#unlockRegion(org.hibernate.cache.spi.access.SoftLock)
     * @see org.hibernate.cache.spi.access.CollectionRegionAccessStrategy#unlockRegion(org.hibernate.cache.spi.access.SoftLock)
     */
    public void unlockRegion(SoftLock lock) throws CacheException {
        evictAll();
    }

    public SoftLock lockItem(Object key, Object version) throws CacheException {
        return null;
    }

    public void unlockItem(Object key, SoftLock lock) throws CacheException {
    }


    /**
     * A no-op since this is an asynchronous cache access strategy.
     *
     * @see org.hibernate.cache.spi.access.EntityRegionAccessStrategy#remove(java.lang.Object)
     * @see org.hibernate.cache.spi.access.CollectionRegionAccessStrategy#remove(java.lang.Object)
     */
    public void remove(Object key) throws CacheException {
    }
        /**
     * Called to evict data from the entire region
     *
     * @throws CacheException Propogated from underlying {@link org.hibernate.cache.spi.Region}
     * @see org.hibernate.cache.spi.access.EntityRegionAccessStrategy#removeAll()
     * @see org.hibernate.cache.spi.access.CollectionRegionAccessStrategy#removeAll()
     */
    public void removeAll() throws CacheException {
        evictAll();
    }

    public void evict(Object key) throws CacheException {
        getInternalRegion().evict( key );
    }

    public void evictAll() throws CacheException {
        getInternalRegion().evictAll();
    }
}