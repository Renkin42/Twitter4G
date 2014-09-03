package net.renkin42.twitter4g

class TwitterPluginExtension {
	
	def String message = "Project ${project.archivesBaseName} version ${project.version} built successfully! #Gradle #Twitter4G"
	
	String accessToken
	String accessTokenSecret
	
	def String consumerKey = "@CONSUMERKEY@"
	def String consumerKeySecret = "@CONSUMERSECRET@"
}
