package org.cimmyt.reporter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperExportManager;
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

	public void exportToFile(Map<String, Object> args) throws JRException, IOException{
		JasperPrint jrPrint = buildJRPrint(args);
		JasperExportManager.exportReportToPdfFile(jrPrint, args.get("fileName").toString());		
	}

	public final JasperPrint buildJRPrint(Map<String, Object> args) throws JRException{
		long start = System.currentTimeMillis();

		String jasperFilesPath;
		jasperFilesPath = "/jasper/"+args.get("templateName");
		
		Map<String, Object> jrParams = buildJRParams(args);
		JRDataSource jrDataSource = buildJRDataSource(args, jrParams);
					
		JasperPrint jrPrint = JasperFillManager.fillReport(jasperFilesPath, jrParams, jrDataSource);

		System.err.println("Filling time : " + (System.currentTimeMillis() - start));
		
		return jrPrint;
	}
	
	
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


}
