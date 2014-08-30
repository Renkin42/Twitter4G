package net.renkin42.twitter4g;

import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.TaskAction;

import twitter4j.Status;
import twitter4j.TwitterException;

public class TaskTweet extends DefaultTask {
	
	@TaskAction
	public void tweet() {
		this.dependsOn("oAuth");
		try {
			Status status = TaskOAuth.twitter.updateStatus(Twitter4GPlugin.message);
			System.out.println("Status successfully updated to [" + status.getText() + "]");
		} catch (TwitterException e) {
			System.out.println("Status could not be updated.");
			e.printStackTrace();
		}
	}

}
