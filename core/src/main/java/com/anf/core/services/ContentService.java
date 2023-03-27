// ***Begin Code - Tejaswini Nallamothu ***
package com.anf.core.services;

import org.apache.sling.api.resource.Resource;

import java.util.List;
import java.util.Map;

public interface ContentService {
	short commitUserDetails(Resource resource, Map<String,String> userDetails);
	List<Map<String, Object>> getChildNodeProperties(Resource resource);
	Map<String, Object> getNodeProperties(Resource resource);
}

// ***END Code*****