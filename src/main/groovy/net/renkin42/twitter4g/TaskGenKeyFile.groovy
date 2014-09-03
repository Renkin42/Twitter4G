package net.renkin42.twitter4g

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.OutputFile

class TaskGenKeyFile extends DefaultTask {
	
	@OutputFile
	File keyFile = file("${project.buildDir}/oAuth/keys.txt")
	
	public TaskGenKeyFile() {
		this.dependsOn("oAuth")
	}
	
	@TaskAction
	def genKeyFile() {
		keyFile.withPrintWriter { writer ->
			writer.println "Access Token -> ${project.twitter.accessToken} <-"
			writer.println "Access Token Secret -> ${project.twitter.accessTokenSecret} <-"
		}
		
		logger.quiet("Keys saved to ${keyFile.absolutePath}")
	}

}
