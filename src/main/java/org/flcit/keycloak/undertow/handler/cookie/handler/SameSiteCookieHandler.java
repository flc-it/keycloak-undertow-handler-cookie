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

package org.flcit.keycloak.undertow.handler.cookie.handler;

import java.util.regex.Pattern;

import org.flcit.keycloak.undertow.handler.cookie.util.SameSiteNoneIncompatibleClientChecker;

import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.server.handlers.Cookie;
import io.undertow.util.Headers;

public class SameSiteCookieHandler implements HttpHandler {

    protected static final String NONE = "None";

    private final HttpHandler next;
    private final String mode;
    private final Pattern cookiePattern;
    private final boolean isNone;

    public SameSiteCookieHandler(final HttpHandler next, final String mode) {
        this(next, mode, null);
    }

    public SameSiteCookieHandler(final HttpHandler next, final String mode, final String cookiePattern) {
        this(next, mode, cookiePattern, true);
    }

    public SameSiteCookieHandler(final HttpHandler next, final String mode, final String cookiePattern, final boolean caseSensitive) {
        this.next = next;
        this.mode = mode;
        final int sensitive = caseSensitive ? 0 : Pattern.CASE_INSENSITIVE;
        this.cookiePattern = cookiePattern != null && !cookiePattern.isEmpty() ? Pattern.compile(cookiePattern, sensitive) : null;
        this.isNone = NONE.equalsIgnoreCase(mode);
    }

    @Override
    public void handleRequest(HttpServerExchange exchange) throws Exception {
        exchange.addResponseCommitListener(ex -> {
            final String userAgent = ex.getRequestHeaders().getFirst(Headers.USER_AGENT);
            if (userAgent != null && !SameSiteNoneIncompatibleClientChecker.shouldSendSameSiteNone(userAgent)) {
                return;
            }
            for (Cookie cookie : ex.getResponseCookies().values()) {
                if (cookiePattern == null
                        || cookiePattern.matcher(cookie.getName()).matches()) {
                    cookie.setSameSiteMode(mode);
                    if (isNone) {
                        cookie.setSecure(true);
                    }
                }
            }
        });
        next.handleRequest(exchange);
    }

}
