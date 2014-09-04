package net.renkin42.twitter4g

import java.awt.Desktop;
import java.net.URI;

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken
import twitter4j.auth.RequestToken

/**
 * Custom Gradle Task to check for project-configured oAuth Access Tokens.
 * Will be skipped if tokens are found.
 * Otherwise proceeds to pin-based authentication.
 * 
 * @author Renkin42
 */
class TaskOAuth extends DefaultTask {

	/**
	 * Checks if access tokens have been set.
	 * If so, the task will be skipped.
	 */
	public TaskOAuth() {
		outputs.upToDateWhen {
			return project.twitter.accessToken != null && project.twitter.accessTokenSecret != null
		}
	}

	/**
	 * Performs pin-based authorization and sets access tokens for this build.
	 * Attempts to launch a browser, otherwise prints url to the console.
	 * Prints access tokens to the console. 
	 */
	@TaskAction
	def oAuth() {
		RequestToken requestToken
		AccessToken token

		Twitter twitter = TwitterFactory.getSingleton()
		twitter.setOAuthConsumer(project.twitter.consumerKey, project.twitter.consumerKeySecret)

		logger.quiet("Tokens not set. Proceeding to Pin-based Authorization")

		try {
			requestToken = twitter.getOAuthRequestToken()
		} catch (TwitterException e) {
			logger.error("Check consumer and consumer secret keys.")
			e.printStackTrace()
		}

		logger.quiet "Launching browser..."
		try {
			Desktop desktop = Desktop.getDesktop()
			desktop.browse(new URI(requestToken.getAuthorizationURL()))
		} catch (Exception e) {
			logger.quiet("Problem in launching browser. Type the following URL into a browser:")
			logger.quiet(requestToken.getAuthorizationURL())
		}

		def pin
		def console = System.console()
		if (console) {
			pin = console.readLine("Please enter the pin from Twitter: ")
		} else {
			logger.error("Cannot get console.")
		}

		try {
			token = twitter.getOAuthAccessToken(requestToken, pin);
		} catch (TwitterException e) {
			logger.error("Was there a typo in the PIN you entered?")
			e.printStackTrace();
			System.exit(-1);
		}

		logger.quiet("Please copy these keys and set them in your build.gradle file")
		logger.quiet("Access Token: ${token.getToken()}")
		logger.quiet("Access Token Secret: ${token.getTokenSecret()}")
		
		project.twitter.accessToken = token.getToken()
		project.twitter.accessTokenSecret = token.getTokenSecret()
	}
}
