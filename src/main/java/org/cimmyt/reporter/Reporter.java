package org.cimmyt.reporter;

import java.util.Collection;
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
	 * Gets the unique key associated to this reporter, and registered in ReportFactory
	 * @return The key defining this reporter type.
	 */
	String getReportCode();
	
	/**
	 * Creates a Datasource for this concrete Report, if needed.
	 * @param dataRecords a Collection with information to generate a data source.
	 * @return
	 */
	JRDataSource buildJRDataSource(Collection<?> dataRecords);

	/**
	 * Returns a Map with the parameters required for creating a JasperPrint for a concrete Reporter.
	 * @return Map of parameters for a JarperPrint 
	 */
	Map<String, Object> buildJRParams(Map<String,Object> args);
	
	/**
	 * Creates a JarperPrint using beans and raw data passed in to this method.
	 * Concrete classes need to define the extraction process.
	 * @param args a Map with a variety of objects with all information needed to fill in a jasper template.
	 * @return The processed JasperPrint (template + data)
	 * @throws JRException when method cannot create a valid JasperPrint with current inputs.
	 */
	JasperPrint buildJRPrint(Map<String, Object> args) throws JRException;
	
	/**
	 * Retrieves the name of the jasper file to be used for constructing this report.
	 * The extension '.jasper' in the name is optional, but the file must exist in the classpath.
	 * 
	 * @return
	 */
	String getTemplateName();
}
