Pre-requisites:
- Jetty (for this project, Im using 9.4.0 August 8th 2016 version): https://repo1.maven.org/maven2/org/eclipse/jetty/jetty-distribution/9.4.0.v20161208/jetty-distribution-9.4.0.v20161208.zip
- Look at the lib folder, what Jetty library I use for this project.

This code are compiled with Visual Studio Code along with Java 8. Before go to compile & run this code:
- Open Java Project Settings (make sure Java Extension Project is installed);
- JDK Runtime set to JavaSE-1.8;
- In compiler tab, uncheck "--release" & "--enable-preview" and set source and target compatibility to 1.8.
After that, to compile this project, simple press Ctrl+Shift+B, since I already configure everything in tasks.json.