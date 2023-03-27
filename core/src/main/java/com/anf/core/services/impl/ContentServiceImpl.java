package com.anf.core.services.impl;

import com.anf.core.services.ContentService;
import org.apache.sling.api.resource.*;
import org.osgi.service.component.annotations.Component;

import java.util.*;

import static com.anf.core.utils.ServletConstants.*;

@Component(immediate = true, service = ContentService.class)
public class ContentServiceImpl implements ContentService {

    @Override
    public short commitUserDetails(Resource resource, Map<String, String> userDetails) {
        return Optional.ofNullable(resource.adaptTo(ModifiableValueMap.class))
                .map(valueMap -> {
                    try {
                        userDetails.forEach((key, value) -> {
                            if (valueMap.containsKey(key)) {
                                valueMap.replace(key, value);
                            } else {
                                valueMap.putIfAbsent(key, value);
                            }
                        });
                        resource.getResourceResolver().commit();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                    return SUCCESS_CODE;
                }).orElse(ERROR_CODE);
    }

    @Override
    public List<Map<String, Object>> getChildNodeProperties(Resource resource) {
        List<Map<String, Object>> list = new ArrayList<>();
        if (resource != null) {
            Iterator<Resource> iterator = resource.listChildren();
            while (iterator.hasNext()) {
                Resource pccNode = iterator.next();
                list.add(getNodeProperties(pccNode));
            }
        }
        return list;
    }

    @Override
    public Map<String, Object> getNodeProperties(Resource resource) {
        Map<String, Object> map = new HashMap<>();
        return Optional.ofNullable(resource.adaptTo(ValueMap.class))
                .map(it -> {
                    map.putAll(it);
                    return map;
                })
                .orElse(map);
    }
}
