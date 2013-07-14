/************************************************************************ 
 * Copyright ReserveOut, LLC
 * ============================================= 
 *  Last edited by: $Author: ehab $
 *              on: $Date: 29 May 2013 16:42:09 $
 *        Filename: $RCSfile: BaseGeneralDataRegion.java $
 * 
 */
package com.googlecode.hibernate.memcached;

import org.hibernate.cache.CacheException;
import org.hibernate.cache.spi.GeneralDataRegion;
import org.hibernate.internal.CoreMessageLogger;
import org.jboss.logging.Logger;



public class BaseGeneralDataRegion extends BaseRegion implements GeneralDataRegion {
    
    private static final CoreMessageLogger LOG = Logger.getMessageLogger(
            CoreMessageLogger.class, BaseGeneralDataRegion.class.getName()
    );

    BaseGeneralDataRegion(String name) {
        super( name );
    }

    public Object get(Object key) throws CacheException {
        LOG.debugf( "Cache[%s] lookup : key[%s]",getName(), key );
        if ( key == null ) {
            return null;
        }
        Object result = cache.get( key );
        if ( result != null ) {
            LOG.debugf( "Cache[%s] hit: %s",getName(), key );
        }
        return result;
    }

    public void put(Object key, Object value) throws CacheException {
        LOG.debugf( "Caching[%s] : [%s] -> [%s]",getName(), key, value );
        if ( key == null || value == null ) {
            LOG.debug( "Key or Value is null" );
            return;
        }
        cache.put( key, value );
    }

    public void evict(Object key) throws CacheException {
        LOG.debugf( "Evicting[%s]: %s",getName(), key );
        if ( key == null ) {
            LOG.debug( "Key is null" );
            return;
        }
        cache.remove( key );
    }

    public void evictAll() throws CacheException {
        LOG.debugf( "evict cache[%s]", getName() );
        cache.clear();
    }
}
