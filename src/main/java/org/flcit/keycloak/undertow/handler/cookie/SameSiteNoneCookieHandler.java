/*
 * Copyright 2002-2023 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.flcit.keycloak.undertow.handler.cookie;

import org.flcit.keycloak.undertow.handler.cookie.configuration.Configuration;
import io.undertow.server.HttpHandler;
import io.undertow.server.handlers.CookieSameSiteMode;
import io.undertow.server.handlers.SameSiteCookieHandler;

/**
 * 
 * @since 
 * @author Florian Lestic
 */
public class SameSiteNoneCookieHandler extends SameSiteCookieHandler {

    /**
     * @param next
     */
    public SameSiteNoneCookieHandler(HttpHandler next) {
        super(next, CookieSameSiteMode.NONE.toString(), getOrNull(Configuration.getProperties().getProperty("cookie.pattern")));
    }

    private static final String getOrNull(String value) {
        return value == null || value.isEmpty() ? null : value;
    }

}
