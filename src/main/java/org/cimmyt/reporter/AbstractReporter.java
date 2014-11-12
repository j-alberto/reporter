package org.cimmyt.reporter;

import java.io.IOException;
import java.util.Map;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

/**
 * Defines the base class for all Reporters. Each subclass of AbstractReporter
 * must implement a static block for registering in the ReporterFactory:
 * <br/>
 * <code>
 * static{
 *		ReporterFactory.instance().addReporter(new ExampleReporter());
 *	}
 * </code>
 * @author jarojas
 *
 */
public abstract class AbstractReporter implements Reporter{

	@Override
	public String toString(){
		return String.format("Report[%s : %s]", getReportCode(),this.getClass().getSimpleName());
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
	
	
	public void exportToFile(Map<String, Object> args) throws JRException, IOException{
		JasperPrint jrPrint = buildJRPrint(args);
		JasperExportManager.exportReportToPdfFile(jrPrint, args.get("fileName").toString());		
	}

}
