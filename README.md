# chtest
TO Run the automation make sure you have following things setup in your machine:
1. Download Android SDK and it's path in envrionment variable, along with tools and platforms-tools.
2. Once you download the project, add email and password in testdata property file. Devicename will be programatically captured if you are running in windows, for mac enter the devicename in testconfig file.
3. Run the automation via:
	1. Eclipse - right click on pom file and run as maven install 
	2. CMD - go to project directory and run as clean install
	3. CMD - if you run as mvn package, then it will create zip file which can manually upload to AWS device farm and run the automation.
4. For reporting, check the extent report folder	
5. For Jenkins in windows, 
	1. Add git repo
	2. Select build as top level maven, give pom location and goal as clean install
	3. Install html report plugin to add the extent report on build level or  install allure reporting plugin.
