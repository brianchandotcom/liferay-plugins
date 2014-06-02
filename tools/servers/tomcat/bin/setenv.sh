JMX_OPTS="-Dcom.sun.management.jmxremote -Dcom.sun.management.jmxremote.port=@arquillian.jmx.port@ -Dcom.sun.management.jmxremote.ssl=false -Dcom.sun.management.jmxremote.authenticate=false"

CATALINA_OPTS="$CATALINA_OPTS $JMX_OPTS -Dfile.encoding=UTF8 -Djava.net.preferIPv4Stack=true @java.security.config@ -Dorg.apache.catalina.loader.WebappClassLoader.ENABLE_CLEAR_REFERENCES=false -Duser.timezone=GMT -Xmx1024m -XX:MaxPermSize=256m"
