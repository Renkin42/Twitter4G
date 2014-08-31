package net.renkin42.twitter4g

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.Task

class Twitter4GPlugin implements Plugin<Project> {
	
	@Override
	public void apply(Project target) {
		target.extensions.create("twitter", TwitterPluginExtension)
		target.getTasks().create("oAuth", TaskOAuth)
		Task tweet = target.getTasks().create("tweet", TaskTweet)
		tweet.dependsOn("oAuth")
		
	}

}
