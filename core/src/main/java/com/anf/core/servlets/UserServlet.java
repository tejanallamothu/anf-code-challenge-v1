/*
 *  Copyright 2015 Adobe Systems Incorporated
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
package com.anf.core.servlets;

import com.anf.core.services.ContentService;
import com.anf.core.utils.JsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.PersistenceException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceUtil;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.servlet.Servlet;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import static com.anf.core.utils.ServletConstants.APPLICATION_JSON;
import static com.anf.core.utils.ServletConstants.UTF_ENCODING;

@Component(immediate = true, service = Servlet.class, property = {
        "sling.servlet.methods=POST",
        "sling.servlet.paths=/bin/ageValidation",
        "sling.servlet.paths=/bin/saveUserDetails"
})
public class UserServlet extends SlingAllMethodsServlet {

    private static final long serialVersionUID = 1L;
    public static final String JCR_PATH = "/var/anf-code-challenge";
    public static final String JCR_VALIDATION_PATH = "/etc/age";

    @Reference
    ContentService contentService;

    @Override
    protected void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response) throws PersistenceException {
        ResourceResolver resolver = request.getResourceResolver();
        Map<String, String> params = request.getParameterMap().entrySet()
                .stream()
                .collect(Collectors.toMap(Map.Entry::getKey,
                        e -> Arrays.stream(e.getValue()).findFirst().orElse(StringUtils.EMPTY)));
        Resource resource = resolver.getResource(JCR_PATH);
        if (resource == null) {
            resource = ResourceUtil.getOrCreateResource(resolver, JCR_PATH,
                    Collections.singletonMap("jcr:primaryType", "nt:unstructured"), null, false);
        }
        response.setStatus(contentService.commitUserDetails(resource, params));
    }
    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws IOException {
        ResourceResolver resourceResolver = request.getResourceResolver();
        String output = Optional.ofNullable(resourceResolver.getResource(JCR_VALIDATION_PATH))
                .map(res -> contentService.getNodeProperties(res))
                .map(JsonUtils::getJson)
                .orElse(StringUtils.EMPTY);
        response.setContentType(APPLICATION_JSON);
        response.getWriter().write(output);
        response.setCharacterEncoding(UTF_ENCODING);
    }
}
