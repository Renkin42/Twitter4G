package net.renkin42.twitter4g

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction;
import twitter4j.Status
import twitter4j.Twitter

class TaskTweet extends DefaultTask {
	
	@TaskAction
	def tweet() {
		Twitter twitter = TaskOAuth.twitter
		try {
			Status status = twitter.updateStatus("${project.twitter.message}")
			println "Status successfully updated to [${status.getText()}]"
		} catch (Exception e) {
			println "Status could not be updated."
			e.printStackTrace()
		}
	}

}
