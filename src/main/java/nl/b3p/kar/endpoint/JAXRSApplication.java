package nl.b3p.kar.endpoint;

	
import javax.ws.rs.core.Application;
import javax.servlet.ServletConfig;
import io.swagger.jaxrs.config.*;


import javax.ws.rs.core.Context;
import javax.ws.rs.ApplicationPath;

@ApplicationPath("api")
public class JAXRSApplication extends Application {	
	
	public JAXRSApplication(@Context ServletConfig servletConfig) {
		BeanConfig beanConfig = new BeanConfig();
		beanConfig.setVersion("1.0.0");
		beanConfig.setTitle("Kargeotool API");
		beanConfig.setBasePath("/api");
		beanConfig.setResourcePackage("nl.b3p.kar.endpoint");
		beanConfig.setScan(true);		
	}	 
	
}
