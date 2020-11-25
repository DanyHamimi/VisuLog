plugins {
	java
	application
}

application.mainClass.set("up.visulog.cli.CliApplication")

dependencies {
    implementation(project(":analyzer"))
    implementation(project(":config"))
    implementation("org.slf4j:slf4j-nop:1.7.30")
    implementation(project(":gitrawdata"))
    testImplementation("junit:junit:4.+")
	
}


