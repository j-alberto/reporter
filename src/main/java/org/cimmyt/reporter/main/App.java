package org.cimmyt.reporter.main;

import java.util.Set;

import org.cimmyt.reporter.AbstractReporter;
import org.cimmyt.reporter.ReporterFactory;
import org.cimmyt.reporter.Reporter;


/**
 * Hello world!
 *
 */

import org.cimmyt.reporter.exception.MissingReportException;
import org.reflections.Reflections;

import static java.lang.System.out;

public class App 
{
	static{
		try {
			initializeReporterFactory();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static final void initializeReporterFactory() throws ClassNotFoundException{
		Reflections reflections = new Reflections("org.cimmyt.reporter");
		Set<Class<? extends AbstractReporter>> classes = reflections.getSubTypesOf(AbstractReporter.class);
		for(Class<?> c : classes){
			Class.forName(c.getName());
		}
		
	}
	
	
    public static void main( String[] args )
    {
        //ReportBuilder reportBuilder = new ReportBuilder();
    	ReporterFactory factory = ReporterFactory.instance();

		try {
    	Reporter r1 = factory.createReporter("WFb22");
    	out.println(r1);
    	Reporter r2 = factory.createReporter("WFb22");
    	out.println(r2);
//    	Reporter r3 = factory.createReporter("WFb24");
//    	out.println(r3);
//    	Reporter r4 = factory.createReporter("WTags");
//    	out.println(r4);

		} catch (MissingReportException e) {
			e.printStackTrace();
		}

	}
}
