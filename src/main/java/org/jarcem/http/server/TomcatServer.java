package org.jarcem.http.server;
/*
  User: Jarcem
  Date: 2019/3/20
  Purpose: 
*/

import org.apache.catalina.Context;
import org.apache.catalina.Host;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.Wrapper;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;
import org.jarcem.http.servlet.DemoServlet;

public class TomcatServer {
    String class_path = null;
    Tomcat tomcat = null;
    Connector connector = null;
    Host host = null;
    Context context = null;
    public void init() throws LifecycleException {
        class_path = System.getProperty("user.dir");
        tomcat = new Tomcat();
        connector = tomcat.getConnector();
        connector.setPort(8080);
        host = tomcat.getHost();
        host.setName("Project J.A.R.V.I.S.");
        host.setAppBase("/org/jarcem/web");
        context = tomcat.addContext(host, "/", class_path);
        if (context instanceof StandardContext){
            StandardContext standardContext = (StandardContext) context;
            standardContext.setDefaultContextXml((new TomcatServer().getClass().getResource("/org/jarcem/web/WEB-INF/web.xml").toString()).substring(6));
            Wrapper wrapper = tomcat.addServlet("/","DemoServlet",new DemoServlet());
            wrapper.addMapping("/demo");
        }
        tomcat.start();
        tomcat.getServer().await();
    }
}
