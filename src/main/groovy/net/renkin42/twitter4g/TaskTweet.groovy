package net.renkin42.twitter4g

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction;
import twitter4j.Status
import twitter4j.Twitter
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken

class TaskTweet extends DefaultTask {
	
	/**
	 * Checks if the most recent tweet on the user's timeline is the same as the project-configured message.
	 * Task is skipped is this returns true.
	 * Also declares dependency on oAuth task.
	 */
	public TaskTweet() {
		this.dependsOn("oAuth")
		outputs.upToDateWhen {
			Twitter twitter = this.authorize()
			String latest = twitter.getUserTimeline().get(0).getText()
			return latest == project.twitter.message
		}
	}
	
	/**
	 * Attempts to send a tweet using the project-configured message
	 */
	@TaskAction
	def tweet() {
		Twitter twitter = this.authorize()
		
		try {
			Status status = twitter.updateStatus("${project.twitter.message}")
			logger.quiet("Status successfully updated to [${status.getText()}]")
		} catch (Exception e) {
			logger.error("Status could not be updated.")
			e.printStackTrace()
		}
	}
	
	/**
	 * Gets the twitter singleton and sets the authorization based on the project configurations.
	 * 
	 * @return fully authorized Twitter instance
	 */
	public Twitter authorize() {
		
		Twitter twitter = TwitterFactory.getSingleton()
		twitter.setOAuthConsumer(project.twitter.consumerKey, project.twitter.consumerKeySecret)
		twitter.setOAuthAccessToken(new AccessToken(project.twitter.accessToken, project.twitter.accessTokenSecret))
		return twitter
		
	}
}
