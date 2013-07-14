/************************************************************************ 
 * Copyright ReserveOut, LLC
 * ============================================= 
 *  Last edited by: $Author: ehab $
 *              on: $Date: 29 May 2013 16:41:17 $
 *        Filename: $RCSfile: BaseTransactionalDataRegion.java $
 * 
 */
package com.googlecode.hibernate.memcached;

import org.hibernate.cache.spi.CacheDataDescription;
import org.hibernate.cache.spi.TransactionalDataRegion;

class BaseTransactionalDataRegion extends BaseGeneralDataRegion implements TransactionalDataRegion {
    private final CacheDataDescription metadata;

    BaseTransactionalDataRegion(String name, CacheDataDescription metadata) {
        super( name );
        this.metadata = metadata;
    }

    public CacheDataDescription getCacheDataDescription() {
        return metadata;
    }

    public boolean isTransactionAware() {
        return false;
    }

}

