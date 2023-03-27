// ***Begin Code - Tejaswini Nallamothu ***
package com.anf.core.servlets;

import com.anf.core.services.ContentService;
import com.anf.core.services.impl.ContentServiceImpl;
import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import org.apache.sling.api.resource.PersistenceException;
import org.apache.sling.testing.mock.sling.servlet.MockSlingHttpServletRequest;
import org.apache.sling.testing.mock.sling.servlet.MockSlingHttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.servlet.ServletException;
import java.io.IOException;

import static com.anf.core.utils.ServletConstants.SUCCESS_CODE;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(AemContextExtension.class)
class UserServletTest {
    String validationJson = "{\"jcr:primaryType\":\"nt:unstructured\",\"maxAge\":\"50\",\"minAge\":\"18\"}";
    UserServlet servletTest = new UserServlet();
    MockSlingHttpServletRequest request;
    MockSlingHttpServletResponse response;
    @Test
    void doGet(AemContext context) throws ServletException, IOException {
        context.build()
                .resource(UserServlet.JCR_VALIDATION_PATH,"jcr:primaryType","nt:unstructured","maxAge", "50","minAge","18");
        context.currentResource(UserServlet.JCR_VALIDATION_PATH);
        servletTest.doGet(request, response);
        assertEquals(validationJson, response.getOutputAsString());
        assertEquals(SUCCESS_CODE, response.getStatus());
        assertEquals("application/json;charset=UTF-8", response.getContentType());
    }
   @Test
    void doPost() throws PersistenceException {
        request.addRequestParameter("firstName","AAAA");
        request.addRequestParameter("lastName","BBBB");
        request.addRequestParameter("country","USA");
        servletTest.doPost(request,response);
       assertEquals(SUCCESS_CODE, response.getStatus());
    }
    @Test
    void doPostReplace(AemContext context) throws PersistenceException {
        context.build()
                .resource(UserServlet.JCR_PATH,"jcr:primaryType","nt:unstructured","firstName",
                        "AAAA","lastName","BBBB","country","USA");
        context.currentResource(UserServlet.JCR_PATH);
        request.addRequestParameter("firstName","AAAA");
        request.addRequestParameter("lastName","BBBB");
        request.addRequestParameter("country","USA");
        servletTest.doPost(request,response);
        assertEquals(SUCCESS_CODE, response.getStatus());
    }
    @BeforeEach
    void setup(AemContext context) {
        ContentService service = context.registerService(ContentService.class, new ContentServiceImpl());
        request = context.request();
        response = context.response();
        servletTest.contentService = service;
    }

}
// ***END Code*****