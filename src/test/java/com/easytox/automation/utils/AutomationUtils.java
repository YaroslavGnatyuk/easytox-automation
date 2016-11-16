package com.easytox.automation.utils;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * Created by dhanumandla on 01/08/16.
 */
public enum AutomationUtils {

    INSTANCE;

    public Map<String, LocatorType> getReferenceToConstant(String variableName) {
        try {
            Field field = WebElementConstants.class.getDeclaredField(variableName);
            field.setAccessible(true);
            return (Map<String, LocatorType>) field.get(null);
        } catch(Exception e) {
            return null;
        }
    }

    public String getLocator(Map constant) {
        if (constant != null) {
            return (String) constant.get("locator");
        } else {
            return null;
        }
    }

    public LocatorType getLocatorType(Map<String, LocatorType> constant) {
        if (constant != null) {
            return constant.get("locatorType");
        } else {
            return null;
        }
    }
}
