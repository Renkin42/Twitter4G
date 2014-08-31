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

class TaskOAuth extends DefaultTask {
	
	Twitter twitter = TwitterFactory.getSingleton()	
	
	@TaskAction
	def oAuth() {
		RequestToken requestToken
		AccessToken token
		
		twitter.setOAuthConsumer("@CONSUMERKEY@", "@CONSUMERSECRET@")
		
		try {
			token = new AccessToken("${project.twitter.accessToken}", "${project.twitter.accessTokenSecret}")
		} catch (Exception exc) {
			println "Tokens not set. Proceeding to Pin-based Authorization"
			
			try {
				requestToken = twitter.getOAuthRequestToken()
			} catch (TwitterException e) {
				println "Check consumer and consumer secret keys."
				e.printStackTrace()
			}

			println "Launching browser..."
			try {
				Desktop desktop = Desktop.getDesktop()
				desktop.browse(new URI(requestToken.getAuthorizationURL()))
			} catch (Exception e) {
				println "Problem in launching browser. Type the following URL into a browser:"
				println requestToken.getAuthorizationURL()
			}

			def pin
			def console = System.console()
			if (console) {
				pin = console.readLine("> Please enter the pin from Twitter: ")
			} else {
				logger.error("Cannot get console.")
			}

			try {
				token = twitter.getOAuthAccessToken(requestToken, pin);
			} catch (TwitterException e) {
				println "Was there a typo in the PIN you entered?"
				e.printStackTrace();
				System.exit(-1);
			}

			println "Please copy these keys and set them in your build.gradle file"
			println "Access Token: ${token.getAccessToken()}"
			println "Access Token Secret: ${token.getTokenSecret()}"
		}
	}
}
