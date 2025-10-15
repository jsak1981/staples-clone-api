package com.ecommerce.staples_clone.util;

public final class AppConstants {

	private AppConstants() {
		// This class should not be instantiated.
	}

	public static final class Api {
		private Api() {}
		public static final String API_V1_PREFIX = "/api/v1";
		public static final String USERS_ENDPOINT = API_V1_PREFIX + "/users";
		public static final String PRODUCTS_ENDPOINT = API_V1_PREFIX + "/products";
		public static final String ORDERS_ENDPOINT = API_V1_PREFIX + "/orders";
	}

	public static final class Security {
		private Security() {}
		public static final String ROLE_ADMIN = "ROLE_ADMIN";
		public static final String ROLE_USER = "ROLE_USER";
		public static final String ROLE_MANAGER = "ROLE_MANAGER";
		public static final String TOKEN_PREFIX = "Bearer ";
		public static final String AUTHORIZATION_HEADER = "Authorization";
	}

	public static final class Cache {
		private Cache() {}
		public static final String USERS_CACHE = "users";
		public static final String PRODUCTS_CACHE = "products";
	}

	public static final class MimeTypes {
		private MimeTypes() {}
		public static final String MS_EXCEL = "application/vnd.ms-excel";
		public static final String OPENXML_SPREADSHEET = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
		public static final String APPLICATION_JSON = "application/json";
		public static final String APPLICATION_PDF = "application/pdf";
	}

	public static final class General {
		private General() {}
		public static final String DEFAULT_ENCODING = "UTF-8";
		public static final int DEFAULT_PAGE_SIZE = 20;
	}
}
