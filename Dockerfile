FROM openjdk:8-alpine

MAINTAINER jack
RUN mkdir -p  /home/admin/factory-wechat-server
ENV app_name="factory-wechat-server"
ENV work_dir="/home/admin/factory-wechat-server"
ENV JAVA_OPTS=" -Djava.security.egd=file:/dev/./urandom -Dlog4j.defaultInitOverride=true -Dorg.apache.tomcat.util.http.ServerCookie.ALLOW_EQUALS_IN_VALUE=true -Dorg.apache.tomcat.util.http.ServerCookie.ALLOW_HTTP_SEPARATORS_IN_V0=true -server -Xmx512M -Xms512M -Xmn128M -XX:MetaspaceSize=128m -XX:MaxMetaspaceSize=128m -XX:MaxDirectMemorySize=1g -XX:SurvivorRatio=10 -XX:+UseConcMarkSweepGC -XX:CMSMaxAbortablePrecleanTime=5000 -XX:+CMSClassUnloadingEnabled -XX:CMSInitiatingOccupancyFraction=80 -XX:+UseCMSInitiatingOccupancyOnly -XX:+ExplicitGCInvokesConcurrent -Dsun.rmi.dgc.server.gcInterval=2592000000 -Dsun.rmi.dgc.client.gcInterval=2592000000 -XX:ParallelGCThreads=4 -Xloggc:$work_dir/logs/gc.log -XX:+PrintGCDetails -XX:+PrintGCDateStamps -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=$work_dir/logs/java.hprof -Djava.awt.headless=true -Dsun.net.client.defaultConnectTimeout=10000 -Dsun.net.client.defaultReadTimeout=30000 -DJM.LOG.PATH=$work_dir/logs -DJM.SNAPSHOT.PATH=$work_dir/snapshots -Dfile.encoding=utf-8 "
COPY ./boot/target/$app_name.jar  $work_dir
EXPOSE 8080
WORKDIR $work_dir
ENTRYPOINT ["sh","-c","java $JAVA_OPTS -jar  $work_dir/$app_name.jar"]