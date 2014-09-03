package net.renkin42.twitter4g

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction;
import twitter4j.Status
import twitter4j.Twitter
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken
import twitter4j.conf.ConfigurationBuilder

class TaskTweet extends DefaultTask {
	
	/**
	 * Checks if the most recent tweet on the user's timeline is the same as the project-configured message.
	 * Task is skipped is this returns true.
	 * Also declares dependency on oAuth task.
	 */
	public TaskTweet() {
		outputs.upToDateWhen {
			Twitter twitter = this.authorize()
			String latest = twitter.getUserTimeline().get(0).getText()
			return latest == project.twitter.message
		}
		if (project.twitter.message == null) {
			project.twitter.message = "Project ${project.archivesBaseName} version ${project.version} built successfully! #Gradle #Twitter4G"
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
		
		ConfigurationBuilder conf = new ConfigurationBuilder()
		conf.setOAuthConsumerKey(project.twitter.consumerKey)
		conf.setOAuthConsumerSecret(project.twitter.consumerKeySecret)
		conf.setOAuthAccessToken(project.twitter.accessToken)
		conf.setOAuthAccessTokenSecret(project.twitter.accessTokenSecret)
		
		TwitterFactory factory = new TwitterFactory(conf.build())
		Twitter twitter = factory.getInstance()
		
		return twitter
		
	}
}
