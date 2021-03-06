package org.cimmyt.reporter;

import static java.lang.System.err;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.cimmyt.reporter.exception.MissingReportException;
import org.reflections.Reflections;

public final class ReporterFactory {

	static{
		reportersMap = new HashMap<>();
		factory = new ReporterFactory();

		try {		
			initFactory();

		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Scans for subclasses of AbstractReporter and registers them in the factory
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 */
	private static void initFactory() throws ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException{
		Reflections reflections = new Reflections("org.cimmyt.reporter");
		Set<Class<? extends AbstractReporter>> classes = reflections.getSubTypesOf(AbstractReporter.class);
		
		
		for(Class<?> c : classes){
			System.out.print("CLASS: "+c.getName());
			
			int classModifiers = c.getModifiers();
			System.out.println(" modif: "+Modifier.toString(classModifiers));
			if(! Modifier.toString(classModifiers).contains("abstract")){
				Class<?> r = Class.forName(c.getName());
				Reporter instance = (Reporter)r.newInstance();
				ReporterFactory.instance().addReporter(instance);
			}
		}
		
	}

	private static Map<String,Reporter> reportersMap;
	private static ReporterFactory factory;
	
	private ReporterFactory(){	}
	
	/**
	 * Returns a singleton of ReporterFactory class
	 * @return an instance of ReporterFactory
	 */
	public static ReporterFactory instance(){
		return factory;
	}
	
	/**
	 * Registers a Reporter in the Map of known Reporter types.
	 * The key for the map is the reporterCode from the Report being registered. 
	 * @param report
	 */
	private void addReporter(Reporter report) {
		if(reportersMap.containsKey(report.getReportCode()))
			err.println("WARNINIG - ReporterFactory: overwriting report with code: "+report.getReportCode());
		
		reportersMap.put(report.getReportCode(), report);
	}
	
	/**
	 * Factory method to obtain new instances of Reporter
	 * @param reportKey
	 * @return an appropriate Reporter type for the reportKey parameter provided
	 * @throws MissingReportException when there is no Reporter for a given reportKey
	 */
	public Reporter createReporter(String reportKey) throws MissingReportException{
		if(reportersMap.containsKey(reportKey))
			
			
			return reportersMap.get(reportKey).createReporter();
		
		throw new MissingReportException(reportKey);
	}

}
