// ***Begin Code - Tejaswini Nallamothu ***
package com.anf.core.utils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;

public class JsonUtils {
    private static ObjectMapper objectMapper;

    static {
        objectMapper = new ObjectMapper();
    }

    public static String getJson(Object obj) {
        String json = StringUtils.EMPTY;
        try {
            json = objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return json;
    }
}

// ***END Code*****