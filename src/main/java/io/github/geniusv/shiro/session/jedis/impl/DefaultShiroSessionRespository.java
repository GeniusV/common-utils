/**
 * Copyright 2017 GeniusV
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.github.geniusv.shiro.session.jedis.impl;

import io.github.geniusv.jedis.JedisDao;
import io.github.geniusv.shiro.session.jedis.ShiroSessionRespository;
import io.github.geniusv.util.LoggerUtil;
import io.github.geniusv.util.SerializeUtil;
import org.apache.shiro.session.Session;

import java.io.Serializable;
import java.util.Collection;

/**
 * Created by GeniusV on 8/8/17.
 */
public class DefaultShiroSessionRespository implements ShiroSessionRespository {

    private JedisDao jedisDao;

    public JedisDao getJedisDao() {
        return jedisDao;
    }

    public void setJedisDao(JedisDao jedisDao) {
        this.jedisDao = jedisDao;
    }

    @Override
    public void saveSession(Session session) {
        if (session == null || session.getId() == null) {
            throw new NullPointerException("session is empty");
        }

        try {
            byte[] key = SerializeUtil.serialize(session.getId());
            byte[] value = SerializeUtil.serialize(session);
            Long sessionTimeOut = session.getTimeout() / 1000 + 60;
            jedisDao.saveValueByKey(key, value, sessionTimeOut.intValue());
        } catch (Exception e) {
            LoggerUtil.error(getClass(), e, "save session error, id:[%s]", session.getId());
        }
    }

    @Override
    public void deleteSession(Serializable sessionId) {
        if (sessionId == null) {
            throw new NullPointerException("session id is empty");
        }
        try {
            jedisDao.deleteByKey(SerializeUtil.serialize(sessionId));
        } catch (Exception e) {
            LoggerUtil.error(getClass(), e, "delete session throw exception: id:[%s]]", sessionId);
        }
    }

    @Override
    public Session getSession(Serializable sessionId) {
        if (sessionId == null) {
            throw new NullPointerException("session id is empty");
        }
        Session result = null;
        try {
            byte[] value = jedisDao.getValueByKey(SerializeUtil.serialize(sessionId));
            result = (Session) SerializeUtil.unserialize(value);
        } catch (Exception e) {
            LoggerUtil.error(getClass(), e, "get session throw exception: id:[%s]", sessionId);
        }
        return result;
    }

    @Override
    public Collection<Session> getAllSessions() {
        Collection<Session> result = null;
        try {
            Collection<byte[]> keys = jedisDao.getAllKeys("*".getBytes());
            for (byte[] item : keys) {
                result.add((Session) SerializeUtil.unserialize(item));
            }
        } catch (Exception e) {
            LoggerUtil.error(getClass(), e, "get all session throw exception");
        }
        return result;
    }
}
