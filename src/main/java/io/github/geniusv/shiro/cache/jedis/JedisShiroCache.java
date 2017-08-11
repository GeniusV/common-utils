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
package io.github.geniusv.shiro.cache.jedis;

import io.github.geniusv.jedis.JedisDao;
import io.github.geniusv.util.LoggerUtil;
import io.github.geniusv.util.SerializeUtil;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;

import java.util.Collection;
import java.util.Set;

public class JedisShiroCache<v,k> implements Cache<k,v> {
    private JedisDao dao;

    public JedisShiroCache(JedisDao dao) {
        this.dao = dao;
    }

    @Override
    public v get(k key) throws CacheException {
        byte[] byteValue = new byte[0];
        v result = null;
        try {
            byte[] byteKey = SerializeUtil.serialize(key);
            byteValue = dao.getValueByKey(byteKey);
            result = (v) SerializeUtil.unserialize(byteValue);
        } catch (Exception e) {
            LoggerUtil.error(getClass(), "get shiro.xml cache value by cache throw exception", e);
        }
        if (result != null) {
            LoggerUtil.debug(getClass(), "shiro.xml getting cache: getAllKeys: %s, value: %s", key.toString(), result.toString());
        }
        return result;
    }

    @Override
    public v put(k key, v value) throws CacheException {
        LoggerUtil.debug(getClass(), "shiro.xml putting cache: getAllKeys: %s, value: %s", key.toString(), value.toString());
        v previous = get(key);
        try {
            dao.saveValueByKey(SerializeUtil.serialize(key), SerializeUtil.serialize(value), -1);
        } catch (Exception e) {
            LoggerUtil.error(getClass(), "put shiro.xml cache throw exception", e);
        }
        return previous;
    }

    @Override
    public v remove(k key) throws CacheException {
        LoggerUtil.debug(getClass(), "shiro.xml deleting cache: getAllKeys: %s", key.toString());
        v previous = get(key);
        try {
            dao.deleteByKey(SerializeUtil.serialize(key));
        } catch (Exception e) {
            LoggerUtil.error(getClass(), "remove shiro.xml cache throw exception", e);
        }
        return previous;
    }

    @Override
    public void clear() throws CacheException {
        LoggerUtil.debug(getClass(), "shiro.xml clearing all cache...");
        try {
            dao.flushdb();
        } catch (Exception e) {
            LoggerUtil.error(getClass(), "clear shiro.xml cache throw exception", e);
        }
    }

    @Override
    public int size() {
        if (keys() == null)
            return 0;
        return keys().size();
    }

    @Override
    public Set<k> keys() {
        return null;
    }

    @Override
    public Collection<v> values() {
        return null;
    }
}
