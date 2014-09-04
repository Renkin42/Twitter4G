package net.renkin42.twitter4g

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.Task

/**
 * Main implementation class for the Twitter4G plugin.
 * Provides Auto-tweeting capabilities to Gradle using the twitter4j library.
 * @author Renkin42
 */
class Twitter4GPlugin implements Plugin<Project> {
	
	/**
	 * Applies the plugin to the buildscript.
	 * Adds the base plugin for cleanGenKeys task.
	 * Creates twitter configuration block.
	 * Creates oAuth, genKeys, and tweet tasks.
	 */
	@Override
	public void apply(Project target) {
		//Apply base plugin to auto-add clean tasks
		target.apply(plugin: 'base')
		
		//Add twitter configuration block
		target.extensions.create("twitter", TwitterPluginExtension)
		
		//Add tasks
		target.getTasks().create("oAuth", TaskOAuth)
		target.getTasks().create("genKeys", TaskGenKeyFile).dependsOn("oAuth")
		target.getTasks().create("tweet", TaskTweet).dependsOn("oAuth")
		
	}

}
