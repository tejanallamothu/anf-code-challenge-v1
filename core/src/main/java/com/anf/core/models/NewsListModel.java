// ***Begin Code - Tejaswini Nallamothu ***
package com.anf.core.models;
import com.anf.core.services.ContentService;
import com.anf.core.utils.ServletConstants;
import com.anf.core.utils.DateUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
// @Model(adaptables = Resource.class)
// @Model(adaptables = { Resource.class, SlingHttpServletRequest.class})
@Model(adaptables = { Resource.class, SlingHttpServletRequest.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class NewsListModel {
    public static final String JCR_PATH = "/var/commerce/products/anf-code-challenge/newsData";
    @OSGiService
    ContentService service;
    @SlingObject
    ResourceResolver resourceResolver;

    /*
     *
     * @return All list of news
     */
    public 	List<Map<String, Object>> getNewsList() {
        return Optional.ofNullable(resourceResolver.getResource(JCR_PATH))
                .map(res -> service.getChildNodeProperties(res))
                .orElse(Collections.emptyList());
    }

    public String getCurrentDate(){        
        return DateUtils.getCurrentDate(ServletConstants.DATE_FORMAT);        
    }

    
}

// ***END Code*****