package com.anf.core.utils;

import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;

import java.util.HashMap;
import java.util.Map;


/**
 *  resource resolver factory helper class
 */
public final class ResolverUtil {

	public static final String SERVICE_USER = "anfservice";//anfserviceuser
    /**
     * @param  resourceResolverFactory factory
     * @return new resource resolver for Sony service user 
     * @throws LoginException if problems
     */
    public static ResourceResolver gteResolver( ResourceResolverFactory resourceResolverFactory ) throws LoginException {
        final Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put( ResourceResolverFactory.SUBSERVICE, SERVICE_USER );
        return resourceResolverFactory.getServiceResourceResolver(paramMap);
    }
}