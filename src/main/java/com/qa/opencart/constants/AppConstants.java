package com.qa.opencart.constants;

import java.util.Arrays;
import java.util.List;

public class AppConstants {
	public static final String CONFIG_FILE_PATH = "./src/test/resource/config/config.properties";
	public static final String CONFIG_QA_FILE_PATH = "./src/test/resource/config/QA.properties";
	public static final String CONFIG_STAGE_FILE_PATH = "./src/test/resource/config/Stage.properties";
	public static final String CONFIG_UAT_FILE_PATH = "./src/test/resource/config/UAT.properties";
	public static final String CONFIG_DEV_FILE_PATH = "./src/test/resource/config/Dev.properties";
	
	public static final String LOGIN_PAGE_TITLE = "Account Login";
	public static final String ACCOUNT_PAGE_TITLE = "My Account";
	public static final String LOGIN_PAGE_FRACTION_URL = "account/login";
	public static final String ACCOUNT_PAGE_FRACTION_URL = "route=account/account";
	public static final List<String> ACC_PAGE_HEADERS_LIST = Arrays.asList("My Account","My Orders","My Affiliate Account","Newsletter");
	public static final String USER_REGISTER_SUCCESS_MSG = "Your Account Has Been Created!";

}
