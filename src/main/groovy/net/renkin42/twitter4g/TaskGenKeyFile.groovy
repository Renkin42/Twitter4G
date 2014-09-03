package net.renkin42.twitter4g

import java.io.File
import java.net.URI

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.OutputFile

class TaskGenKeyFile extends DefaultTask {
	
	@OutputFile
	File keyFile = new File("${project.buildDir}\\oAuth\\keys.txt")
	
	@TaskAction
	def genKeyFile() {
		
		keyFile.withPrintWriter { writer ->
			writer.println "Access Token -> ${project.twitter.accessToken} <-"
			writer.println "Access Token Secret -> ${project.twitter.accessTokenSecret} <-"
		}
		
		logger.quiet("Keys saved to ${keyFile.absolutePath}")
	}

}
