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
package io.github.geniusv.shiro.listener.jedis;

import io.github.geniusv.shiro.session.jedis.ShiroSessionRespository;
import io.github.geniusv.util.LoggerUtil;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionListener;


public class JedisSessionListener implements SessionListener {

    private ShiroSessionRespository shiroSessionRespository;


    @Override
    public void onStart(Session session) {
        LoggerUtil.debug(getClass(), "a session start: session id:[%s]", session.getId().toString());
    }

    @Override
    public void onStop(Session session) {
        LoggerUtil.debug(getClass(), "a session stop: session id:[%s]", session.getId().toString());
    }

    @Override
    public void onExpiration(Session session) {
        shiroSessionRespository.deleteSession(session.getId());
        LoggerUtil.debug(getClass(), "a session expire: session id:[%s]", session.getId().toString());
    }

    public ShiroSessionRespository getShiroSessionRespository() {
        return shiroSessionRespository;
    }

    public void setShiroSessionRespository(ShiroSessionRespository shiroSessionRespository) {
        this.shiroSessionRespository = shiroSessionRespository;
    }
}
