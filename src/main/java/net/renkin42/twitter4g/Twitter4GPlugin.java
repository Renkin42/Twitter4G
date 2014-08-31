package net.renkin42.twitter4g;

import org.gradle.api.Plugin;
import org.gradle.api.Project;

public class Twitter4GPlugin implements Plugin<Project> {

	public static String message;
	public static String accessToken;
	public static String accessTokenSecret;
	
	@Override
	public void apply(Project target) {
		
		target.getExtensions().add("twitter", new TwitterPluginExtension());
		try {
			message = target.property("project.twitter.message").toString();
		} catch (Exception e) {
			System.out.println("No message found");
		}
		try {
			accessToken = target.property("project.twitter.accessToken").toString();
			accessTokenSecret = target.property("project.twitter.accessTokenSecret").toString();
		} catch (Exception e) {
			System.out.println("Access Tokens not found");
		}
		
		target.getTasks().create("oAuth", TaskOAuth.class);
		target.getTasks().create("tweet", TaskTweet.class).dependsOn("oAuth");
		
	}

}
