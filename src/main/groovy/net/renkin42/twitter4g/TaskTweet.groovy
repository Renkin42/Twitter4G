package net.renkin42.twitter4g

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction;
import twitter4j.Status

class TaskTweet extends DefaultTask {
	
	@TaskAction
	def tweet() {
		try {
			Status status = TaskOAuth.twitter.updateStatus("${project.twitter.status}")
			println "Status successfully updated to [${status.getText()}]"
		} catch (Exception e) {
			println "Status could not be updated."
			e.printStackTrace()
		}
	}

}
