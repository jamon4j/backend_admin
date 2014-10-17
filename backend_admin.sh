#!/bin/sh
set -x
MVN_PATH=/home/liyang14/apache-maven-3.2.1/bin
export PATH="$MVN_PATH:$PATH"
source_path="/home/liyang14/work_space"
deploy_path="/usr/local/apache-tomcat-7.0.42"
#deploy_path="/usr/local/test"
branch=$(git branch --no-color 3> /dev/null | sed -e '/^[^*]/d' -e 's/* \(.*\)/\1/')
log=$source_path/backend_admin/log/$(date '+%Y%m%d').log
echo $log
current_branch='release'
if [ $branch!=$current_branch ];then
     git checkout $current_branch>/dev/null
fi

git pull origin $current_branch >/dev/null

if [ $? -eq 0 ]; then

     cp $source_path/backend_admin/target/mvn_vm_web-1.0-SNAPSHOT.war  /tmp/mvn_vm_web-1.0-SNAPSHOTBACK.war
     mvn clean>/dev/null
     mvn install > /dev/null
     if [ $? -eq 0 ] ;then
	  #delete files	 
	  rm -rf $deploy_path/webapps/ROOT/>  /dev/null
          #decompress war 
	  unzip $source_path/backend_admin/target/mvn_vm_web-1.0-SNAPSHOT.war  -d $deploy_path/webapps/ROOT/  > /dev/null
	  #copy backup config file
	  rm -rf   $deploy_path/webapps/ROOT/WEB-INF/classes/conf.properties >/dev/null
	  #cp -rf /tmp/ROOT/WEB-INF/classes/conf.properties  $deploy_path/webapps/ROOT/WEB-INF/classes/conf.properties  > /dev/null
	  cp -rf /home/liyang14/backupconfig/conf.properties  $deploy_path/webapps/ROOT/WEB-INF/classes/conf.properties  > /dev/null
          rm -rf  $deploy_path/webapps/ROOT/WEB-INF/classes/api.properties  
	  #cp -rf /tmp/ROOT/WEB-INF/classes/api.properties  $deploy_path/webapps/ROOT/WEB-INF/classes/api.properties > /dev/null
	  cp -rf /home/liyang14/backupconfig/api.properties  $deploy_path/webapps/ROOT/WEB-INF/classes/api.properties > /dev/null
	 
	  #shutdown tomcat
          #sh  $deploy_path/bin/shutdown.sh
	  a=$(ps -ef |grep java|grep -v grep|awk  '{print $2}')
	  for item in $a;do
		kill -9 $item
          done 
	  #start tomcat
          sh $deploy_path/bin/startup.sh >/dev/null
          if [ $? -eq 0 ];then
		echo 'deploy success'
	  else
		
          	echo 'tomcat deploy  error--'$time >>$log
          fi
	   
     else
          time=date '+%Y%m%d%H%M%s'
          echo 'mvn error--'$time >>$log
     fi
     

else
	
     time=date '+%Y%m%d%H%M%s'
     echo 'git pull error--'$time>>$log
fi

echo "this script execute over"
set +x

