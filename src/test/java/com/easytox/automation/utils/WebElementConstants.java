package com.easytox.automation.utils;

import java.util.HashMap;
import java.util.Map;

public class WebElementConstants {

    public static Map USER_NAME = new HashMap<String, LocatorType>(){{
        put("username", LocatorType.ID);
    }};
}
