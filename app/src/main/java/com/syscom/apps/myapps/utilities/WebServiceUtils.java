package com.syscom.apps.myapps.utilities;

import com.syscom.apps.myapps.BuildConfig;

/**
 * Stored Web Service URLs
 */

public class WebServiceUtils {

    private WebServiceUtils() {
        throw new IllegalAccessError("Utility class");
    }

    public static final String AUTHORIZATION = "Authorization";

    public static final String LOGIN_API = BuildConfig.API_LOGIN;

    public static final String REGISTER_API = BuildConfig.API_REGISTER_CUSTOMER;

    public static final String ADD_ADVERT_API = BuildConfig.API_ADVERT;

}
