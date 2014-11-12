package org.cimmyt.reporter;

import java.util.HashMap;
import java.util.Map;
import static java.lang.System.err;
import org.cimmyt.reporter.exception.MissingReportException;

public final class ReporterFactory {

	private static Map<String,Reporter> reportersMap = new HashMap<>();
	private static ReporterFactory factory;
	
	private ReporterFactory(){	}
	
	public void addReporter(Reporter report) {
		if(reportersMap.containsKey(report.getReportCode()))
			err.println("ReporterFactory: overwriting report with code: "+report.getReportCode());
		
		reportersMap.put(report.getReportCode(), report);
	}
	
	public Reporter createReporter(String reportKey) throws MissingReportException{
		if(reportersMap.containsKey(reportKey))
			return reportersMap.get(reportKey).createReporter();
		
		throw new MissingReportException(reportKey);
	}
	
	public static ReporterFactory instance(){
		if(null == factory){
			factory = new ReporterFactory();
		}
		return factory;
	}
}
