package net.renkin42.twitter4g;

public class TwitterPluginExtension {
	
	private static String message;
	private static String accessToken;
	private static String accessTokenSecret;
	
	public static String getMessage() {
		return message;
	}
	
	public static void setMessage(String message) {
		TwitterPluginExtension.message = message;
	}

	public static String getAccessToken() {
		return accessToken;
	}

	public static void setAccessToken(String accessToken) {
		TwitterPluginExtension.accessToken = accessToken;
	}

	public static String getAccessTokenSecret() {
		return accessTokenSecret;
	}

	public static void setAccessTokenSecret(String accessTokenSecret) {
		TwitterPluginExtension.accessTokenSecret = accessTokenSecret;
	}

}
