
#ENVIRONMENT
    linux      
      ant	
      eclipse
      tmux

#SET UP PROJECT

    generate project.properties
	
	select one of the target of which the API level is 17+

        > android list targets

    update project

    	> android update project -t ${id-with-17+} -p .

    and add one line to project.properties

     	> echo "proguard.config=proguard.cfg" >> project.properties

    setup some files

   	> ant release-prebuild (or ant debug-prebuild)


