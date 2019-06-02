/**
 * Copyright 2017 GeniusV
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.geniusver.shiro.cache.jedis;

import com.geniusver.jedis.JedisDao;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;

import javax.security.auth.DestroyFailedException;
import javax.security.auth.Destroyable;

public class JedisShiroCacheManager implements CacheManager, Destroyable {

    private JedisDao jedisDao;

    public <K, V> Cache<K, V> getCache(String s) throws CacheException {
        return null;
    }

    public void destroy() throws DestroyFailedException {

    }

    public boolean isDestroyed() {
        return true;
    }
}