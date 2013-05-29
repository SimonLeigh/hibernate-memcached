/************************************************************************ 
 * Copyright ReserveOut, LLC
 * ============================================= 
 *  Last edited by: $Author: ehab $
 *              on: $Date: 29 May 2013 16:25:49 $
 *        Filename: $RCSfile: NaturalIdRegionImpl.java $
 * 
 */
package com.googlecode.hibernate.memcached;

import org.hibernate.cache.CacheException;
import org.hibernate.cache.spi.CacheDataDescription;
import org.hibernate.cache.spi.NaturalIdRegion;
import org.hibernate.cache.spi.access.AccessType;
import org.hibernate.cache.spi.access.NaturalIdRegionAccessStrategy;
import org.hibernate.cfg.Settings;

import com.googlecode.hibernate.memcached.strategy.NonstrictReadWriteNaturalIdRegionAccessStrategy;
import com.googlecode.hibernate.memcached.strategy.ReadOnlyNaturalIdRegionAccessStrategy;
import com.googlecode.hibernate.memcached.strategy.ReadWriteNaturalIdRegionAccessStrategy;
import com.googlecode.hibernate.memcached.strategy.TransactionalNaturalIdRegionAccessStrategy;

public class NaturalIdRegionImpl extends BaseTransactionalDataRegion implements NaturalIdRegion {
    
   
    private final Settings settings;
    NaturalIdRegionImpl(String name, CacheDataDescription metadata, Settings settings) {
        super( name, metadata );
        this.settings=settings;
    }

    public Settings getSettings() {
        return settings;
    }

    @Override
    public NaturalIdRegionAccessStrategy buildAccessStrategy(AccessType accessType) throws CacheException {
        switch ( accessType ) {
            case READ_ONLY:
                return new ReadOnlyNaturalIdRegionAccessStrategy( this );
            case READ_WRITE:
                 return new ReadWriteNaturalIdRegionAccessStrategy( this );
            case NONSTRICT_READ_WRITE:
                return new NonstrictReadWriteNaturalIdRegionAccessStrategy( this );
            case TRANSACTIONAL:
                return new TransactionalNaturalIdRegionAccessStrategy( this );
            default:
                throw new IllegalArgumentException( "unrecognized access strategy type [" + accessType + "]" );
        }
    }


}
