package net.renkin42.twitter4g

import java.io.File
import java.net.URI

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.OutputDirectory
import twitter4j.Twitter
import twitter4j.TwitterFactory
import twitter4j.conf.ConfigurationBuilder

class TaskGenKeyFile extends DefaultTask {
	
	@OutputDirectory
	File keyDir = new File("${project.buildDir}\\oAuth\\")
	
	@TaskAction
	def genKeyFile() {
		
		ConfigurationBuilder conf = new ConfigurationBuilder()
		conf.setOAuthConsumerKey(project.twitter.consumerKey)
		conf.setOAuthConsumerSecret(project.twitter.consumerKeySecret)
		conf.setOAuthAccessToken(project.twitter.accessToken)
		conf.setOAuthAccessTokenSecret(project.twitter.accessTokenSecret)
		
		TwitterFactory factory = new TwitterFactory(conf.build())
		Twitter twitter = factory.getInstance()
		String name = twitter.getScreenName()
		
		def keyFile = new File(keyDir, "${name}.txt")
		keyFile.withPrintWriter { writer ->
			String line =  "+--------------------"
			for (int i = 0; i < name.length(); i++) {
				line += "-"
			}
			line += "-+"
			writer.println line
			writer.println "| Acceess Tokens for ${name} |"
			line =  "+--------------------"
			for (int i = 0; i < name.length(); i++) {
				line += "-"
			}
			line += "-+"
			String token = "Access Token -> ${project.twitter.accessToken} <-"
			String tokenSecret = "Access Token Secret -> ${project.twitter.accessTokenSecret} <-"
			int length = line.length()
			if (token.length() > tokenSecret.length()) {
				for (int i = 0; i < token.length()-length; i++) {
					line += "-"
				}
			} else {
				for (int i = 0; i < tokenSecret.length()-length; i++) {
					line += "-"
				}
			}
			writer.println line
			writer.println token
			writer.println tokenSecret
			line = ""
			if (token.length() > tokenSecret.length()) {
				for (int i = 0; i < token.length(); i++) {
					line += "-"
				}
			} else {
				for (int i = 0; i < tokenSecret.length(); i++) {
					line += "-"
				}
			}
			writer.println line
			writer.println "Auto-generated on ${new Date()}"
		}
		
		logger.quiet("Keys saved to ${keyFile.absolutePath}")
	}

}
