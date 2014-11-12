package org.cimmyt.reporter.main;

import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.query.JsonQueryExecuterFactory;

public class ReportBuilder{

	public void buildDocument() throws JRException, IOException {
		Locale locale = new Locale("en_US");
		
		JasperPrint jrPrint = buildJRPrint("master.jasper", locale);

		exportToFile(jrPrint, "report.pdf");

	}
	
	private JasperPrint buildJRPrint(String templateName, Locale locale) throws JRException{
		String jasperFilesPath;
		jasperFilesPath = "/jasper/"+templateName;
		
		long start = System.currentTimeMillis();
		Map<String, Object> params = new HashMap<String, Object>();

		params.put(JsonQueryExecuterFactory.JSON_DATE_PATTERN, "YYY-MM-DD");
		params.put(JsonQueryExecuterFactory.JSON_NUMBER_PATTERN, "#,##0.##");
		params.put(JRParameter.REPORT_LOCALE, locale);

		
		JasperPrint jrPrint = null;
		try {
			JRDataSource dataSource = new JREmptyDataSource(); //datasource
			
			params.put(JRParameter.REPORT_DATA_SOURCE, dataSource);
			jrPrint = JasperFillManager.fillReport(jasperFilesPath, params, dataSource);
		} catch (JRException e) {
			e.printStackTrace();
		}

		System.err.println("Filling time : " + (System.currentTimeMillis() - start));
		
		return jrPrint;
	}
	
	private void exportToFile(JasperPrint jrPrint, String fileName) throws JRException, IOException{
		JasperExportManager.exportReportToPdfFile(jrPrint, "fileName");		
	}

}
