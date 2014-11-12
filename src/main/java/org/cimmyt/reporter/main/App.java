package org.cimmyt.reporter.main;

import static java.lang.System.out;

import org.cimmyt.reporter.Reporter;
import org.cimmyt.reporter.ReporterFactory;
/**
 * Hello world!
 *
 */
import org.cimmyt.reporter.exception.MissingReportException;

public class App 
{
	
    public static void main( String[] args )
    {
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
