package com.anf.core.listeners;

import com.anf.core.services.ContentService;
import com.day.cq.wcm.api.PageEvent;
import com.day.cq.wcm.api.PageModification;
import org.apache.sling.api.resource.ModifiableValueMap;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventConstants;
import org.osgi.service.event.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;
import java.util.Optional;

import static com.anf.core.utils.ResolverUtil.gteResolver;

@Component(service = EventHandler.class,
        immediate = true,
        property = {
                EventConstants.EVENT_TOPIC + "=" + PageEvent.EVENT_TOPIC
        })
public class PageEventHandler implements EventHandler {
    @Reference
    ResourceResolverFactory resourceResolverFactory;
    @Reference
    ContentService contentService;
    private static final Logger LOG = LoggerFactory.getLogger(PageEventHandler.class);
    private static final String PAGE_PATH = "/content/anf-code-challenge/us/en/";

    @Override
    public void handleEvent(Event event) {
        Iterator<PageModification> pageInfo = PageEvent.fromEvent(event).getModifications();
        while (pageInfo.hasNext()) {
            PageModification pageModification = pageInfo.next();
            String pagePath = pageModification.getPath();
            if (pageModification.getType().equals(PageModification.ModificationType.CREATED)
                    && pagePath.startsWith(PAGE_PATH)) {
                addPropertyToPage(pagePath);
            }
        }
    }
    private void addPropertyToPage(String pagePath) {
        try (ResourceResolver resolver = gteResolver(resourceResolverFactory)) {
            Optional.of(resolver)
                    .map(r -> resolver.getResource(pagePath + "/jcr:content"))
                    .map(res -> res.adaptTo(ModifiableValueMap.class))
                    .map(m -> m.put("pageCreated", true));
            resolver.commit();
        } catch (Exception e) {
            LOG.error("Can't save the property on page {}", pagePath);
        }
    }
}