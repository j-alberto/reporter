package org.cimmyt.reporter;

import java.util.Map;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;

public interface Reporter {

	/**
	 * Factory method, used to create new instances of this class.
	 * @return An instance of this class
	 */
	Reporter createReporter();
	
	/**
	 * Gets the unique key registered in ReportFactory
	 * @return The key defining this reporter type.
	 */
	String getReportCode();
	
	
	JRDataSource buildJRDataSource(Map<String,Object> args, Map<String,Object> jrParams);
	Map<String, Object> buildJRParams(Map<String,Object> args);
	JasperPrint buildJRPrint(Map<String, Object> args) throws JRException;
}
