package org.cimmyt.reporter;

import java.util.HashMap;
import java.util.Map;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.query.JsonQueryExecuterFactory;

/**
 * Defines the base class for all Reporters. Each reporter pretending to
 * be discovered by ReporterFactory must extend this class
 * @author jarojas
 *
 */
abstract class AbstractReporter implements Reporter{
	
	@Override
	public String toString(){
		return String.format("Report[%s : %s]", getReportCode(),this.getClass().getSimpleName());
	}

	public final JasperPrint buildJRPrint(Map<String, Object> args) throws JRException{
		
		String jasperFilesPath = getTemplatePath();
		Map<String, Object> jrParams = null;
		JRDataSource jrDataSource = null;
		
		if(null != args){
			jrParams = buildJRParams(args);
			jrDataSource = buildJRDataSource(args.values());
		}
					
		JasperPrint jrPrint = JasperFillManager.fillReport(jasperFilesPath, jrParams, jrDataSource);

		return jrPrint;
	}
	
	/**
	 * Returns a Map with the parameters required for creating a JasperPrint for this Reporter.
	 * This method configures some basic jasper options like language and text formatting;
	 * subclasses extending AbstractReporter may add extra parameters to fill in its particular template.
	 * @return Map of parameters for a JarperPrint 
	 */
	public Map<String, Object> buildJRParams(Map<String,Object> args){
		Map<String, Object> params = new HashMap<String, Object>();

		if(args.containsKey("datePattern"))
			params.put(JsonQueryExecuterFactory.JSON_DATE_PATTERN, args.get("datePattern"));
		
		if(args.containsKey("numberPattern"))
			params.put(JsonQueryExecuterFactory.JSON_NUMBER_PATTERN, args.get("numberPattern"));
		
		if(args.containsKey("locale"))
			params.put(JRParameter.REPORT_LOCALE, args.get("locale"));

		return params;
	}

	/**
	 * Obtains the full path to the .jasper file, specified by getFileName()
	 * @param jasperFileName The name of the compiled .jasper file.
	 * @return
	 */
	public String getTemplatePath() {
		String jasperFileName = getTemplateName();
	   	ClassLoader loader = AbstractReporter.class.getClassLoader();
        
	   	if(! jasperFileName.endsWith(".jasper"))
        	jasperFileName = jasperFileName + ".jasper";

        return loader.getResource(jasperFileName).getPath();
	}

}
