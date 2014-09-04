package net.renkin42.twitter4g

/**
 * Creates twitter configuration block.
 * @author Renkin42
 */
class TwitterPluginExtension {
	
	/**
	 * The message to be tweeted.
	 * project.twitter.message
	 */
	String message
	
	/**
	 * the oAuth access token for this user.
	 * project.twitter.accessToken
	 */
	String accessToken
	
	/**
	 * The secret access token for this user.
	 * As the name suggests, this should be kept a SECRET!
	 * project.twitter.accessTokenSecret
	 */
	String accessTokenSecret
	
	/**
	 * The consumer key for this app.
	 * Defaults to the key for the 'Twitter4G' app.
	 * to compile this properly, you'll need to create your own app at dev.twitter.com.
	 */
	String consumerKey = '@CONSUMERKEY@'
	
	/**
	 * The secret consumer key for this app.
	 * As the name suggests, this should be kept a SECRET!
	 * Defaults to the key for the 'Twitter4G' app.
	 * to compile this properly, you'll need to create your own app at dev.twitter.com.
	 */
	String consumerKeySecret = '@CONSUMERSECRET@'
}
