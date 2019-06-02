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

package com.geniusver.jedis;

import com.geniusver.util.SerializeUtil;

import java.util.ArrayList;
import java.util.Collection;


public interface JedisDao {

    byte[] getValueByKey(byte[] key) throws Exception;

    void deleteByKey(byte[] key) throws Exception;

    void saveValueByKey(byte[] key, byte[] value, int expireTime) throws Exception;

    void flushdb() throws Exception;

    Collection<byte[]> getAllKeys(byte[] pattern) throws Exception;

    @SuppressWarnings("unchecked")
    default <T> T getValueByKey(Object key, Class<T> requiredType) throws Exception {
        byte[] serialKey = SerializeUtil.serialize(key);
        byte[] value = getValueByKey(serialKey);
        return (T) SerializeUtil.unserialize(value);
    }

    default void deleteByKey(Object key) throws Exception {
        byte[] serialKey = SerializeUtil.serialize(key);
        deleteByKey(serialKey);
    }

    default void saveValueByKey(Object key, Object value, int expireTime) throws Exception {
        byte[] serialKey = SerializeUtil.serialize(key);
        byte[] serialValue = SerializeUtil.serialize(value);
        saveValueByKey(serialKey, serialValue, expireTime);
    }

    @SuppressWarnings("unchecked")
    default <T> Collection<T> getAllKeys(String pattern, Class<T> requiredType) throws Exception {
        Collection<byte[]> data = getAllKeys(pattern.getBytes());
        Collection<T> result = new ArrayList<>();
        for (byte[] item : data) {
            result.add((T)SerializeUtil.unserialize(item));
        }
        return result;
    }


}
