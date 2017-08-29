package com.syscom.apps.myapps.utilities;

import com.syscom.apps.myapps.BuildConfig;

/**
 * Stored Web Service URLs
 */

public class WebServiceUtils {

    public static final String UTF8_ENCODING = "UTF-8";

    public static final String CONTENT_TYPE = "application/json; charset="+UTF8_ENCODING;

    public static final String AUTHORIZATION = "Authorization";


    public static  final String API_BASE_URL = BuildConfig.API_BASE_URL;

    public static final String VERSION = "v1";

    public static final String LOGIN_API = BuildConfig.API_LOGIN;

    public static final String REGISTER_API = BuildConfig.API_REGISTER_CUSTOMER;

    public static final String ADD_ADVERT_API = BuildConfig.API_ADVERT;

}
