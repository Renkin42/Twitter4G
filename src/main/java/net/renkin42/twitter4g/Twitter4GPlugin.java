package net.renkin42.twitter4g;

import org.gradle.api.Plugin;
import org.gradle.api.Project;

public class Twitter4GPlugin implements Plugin<Project> {

	public static String message;
	public static String accessToken;
	public static String accessTokenSecret;
	
	@Override
	public void apply(Project target) {
		
		message = target.property("project.twitter.message").toString();
		accessToken = target.property("project.twitter.accessToken").toString();
		accessTokenSecret = target.property("project.twitter.accessTokenSecret").toString();
		
		target.getTasks().create("oAuth", TaskOAuth.class);
		
	}

}
