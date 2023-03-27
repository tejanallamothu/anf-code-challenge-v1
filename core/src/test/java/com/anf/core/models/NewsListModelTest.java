// ***Begin Code - Tejaswini Nallamothu ***
package com.anf.core.models;

import com.anf.core.services.ContentService;
import com.anf.core.services.impl.ContentServiceImpl;
import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.apache.sling.testing.mock.sling.ResourceResolverType;

import java.util.*;

import static com.anf.core.utils.DateUtils.getCurrentDate;
import static com.anf.core.utils.ServletConstants.DATE_FORMAT;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(AemContextExtension.class)
class NewsListModelTest {
    NewsListModel newsListModel;
    ContentService service;
    private final AemContext context = new AemContext(ResourceResolverType.RESOURCERESOLVER_MOCK);
    @BeforeEach    
    public void setUp() {
        context.addModelsForClasses(NewsListModel.class);
        newsListModel  = context.request().adaptTo(NewsListModel.class);
        service = context.registerService(ContentService.class, new ContentServiceImpl());
        newsListModel =  new NewsListModel();
        newsListModel.service = service;
        newsListModel.resourceResolver = context.resourceResolver();
    }
    @Test
    void getNewsList(AemContext context) {
        context.build().resource(NewsListModel.JCR_PATH)
                .siblingsMode()
                .resource("test1.1", "stringParam", "configValue1.1")
                .resource("test1.2", "stringParam", "configValue1.2")
                .resource("test1.3", "stringParam", "configValue1.3");
        assertEquals(getExpectedList(), newsListModel.getNewsList());
    }

    private List<Map<String, Object>> getExpectedList() {
        List<Map<String, Object>> list = new ArrayList<>();
        Map<String, Object> map1 = new HashMap<>();
        map1.put("stringParam", "configValue1.1");
        Map<String, Object> map2 = new HashMap<>();
        map2.put("stringParam", "configValue1.2");
        Map<String, Object> map3 = new HashMap<>();
        map3.put("stringParam", "configValue1.3");
        list.add(map1);
        list.add(map2);
        list.add(map3);
        return list;
    }
    @Test
    void getCurrentDateTest(){
        assertEquals(getCurrentDate(DATE_FORMAT) ,newsListModel.getCurrentDate());
    }
}

// ***END Code*****