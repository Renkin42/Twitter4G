package net.renkin42.twitter4g;

import java.awt.Desktop;
import java.net.URI;
import java.util.Scanner;

import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.TaskAction;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;

public class TaskOAuth extends DefaultTask {
	
	public static Twitter twitter;
	
	@TaskAction
	public void oAuth() {
		RequestToken requestToken = null;
		AccessToken token = null;
		
		twitter = TwitterFactory.getSingleton();
		twitter.setOAuthConsumer("@CONSUMERKEY@", "@CONSUMERSECRET@");
		
		try {
			token = new AccessToken(Twitter4GPlugin.accessToken, Twitter4GPlugin.accessTokenSecret);
		} catch (Exception exc) {
			System.out.println("Access Tokens not set.");
			try {
				requestToken = twitter.getOAuthRequestToken();
			} catch (TwitterException e) {
				System.out.println("Check consumer and consumer secret keys.");
				e.printStackTrace();
			}

			System.out.println("Launching browser..."); 
			try { 
				Desktop desktop = Desktop.getDesktop(); 
				desktop.browse(new URI(requestToken.getAuthorizationURL()));
			} catch (Exception e) { 
				System.out.println("Problem in launching browser. Type the following URL into a browser:"); 
				System.out.println(requestToken.getAuthorizationURL()); 
			}

			System.out.print("Please enter the PIN from Twitter: "); 
			Scanner keyboard = new Scanner(System.in); 
			String pin = keyboard.next();
			keyboard.close();

			try { 
				token = twitter.getOAuthAccessToken(requestToken, pin); 
			} catch (TwitterException e) { 
				System.out.println("Was there a typo in the PIN you entered?"); 
				e.printStackTrace(); 
				System.exit(-1); 
			}

			System.out.println("Please copy these keys and set them in your build.gradle file");
			System.out.println("Access Token: " + token.getToken());
			System.out.println("Access Token Secret: " + token.getTokenSecret());
		}
		
		twitter.setOAuthAccessToken(token);
	}

}
