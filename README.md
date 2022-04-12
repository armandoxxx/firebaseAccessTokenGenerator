# Google Firebase api token generator

Simple mavenized project to generate Firebase API access tokens. 



## Program arguments
[0] - firebase API project config file downloaded from firebase console (required)     
[1] - firebase API scopes you would like to use token for (default name scopes.txt)

### configuration
Download configiration from firebase console and save it as json file. 

### scopes
Edit **scopes.txt** and add every scope you need as new line. 

## Generate token
first time:    
**mvn compile**   

every other time:    
**mvn exec:java -D"exec.mainClass"="org.dropchop.firebase.TokenGenerator" -Dexec.args="config.json scopes.txt"**
