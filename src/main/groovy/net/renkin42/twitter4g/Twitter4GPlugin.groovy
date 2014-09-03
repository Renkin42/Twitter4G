package net.renkin42.twitter4g

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.Task

class Twitter4GPlugin implements Plugin<Project> {
	
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
