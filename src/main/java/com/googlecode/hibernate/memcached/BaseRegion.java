/************************************************************************ 
 * Copyright ReserveOut, LLC
 * ============================================= 
 *  Last edited by: $Author: ehab $
 *              on: $Date: 29 May 2013 16:44:09 $
 *        Filename: $RCSfile: BaseRegion.java $
 * 
 */
package com.googlecode.hibernate.memcached;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.hibernate.cache.CacheException;
import org.hibernate.cache.spi.Region;

class BaseRegion implements Region {
    protected final Map<Object,Object> cache = new ConcurrentHashMap<Object,Object>();
    private final String name;
    private static int timeout = Timestamper.ONE_MS * 60000;  //60s

    BaseRegion(String name) {
        this.name = name;
    }

    public boolean contains(Object key) {
        return key != null ? cache.containsKey( key ) : false;
    }

    public String getName() {
        return name;
    }

    public void destroy() throws CacheException {
        cache.clear();
    }

    public long getSizeInMemory() {
        return -1;
    }

    public long getElementCountInMemory() {
        return cache.size();
    }

    public long getElementCountOnDisk() {
        return 0;
    }

    public Map<Object, Object> toMap() {
        return Collections.unmodifiableMap( cache );
    }

    public long nextTimestamp() {
        return Timestamper.next();
    }

    public int getTimeout() {
        return timeout;
    }

}
