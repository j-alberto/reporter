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
import net.sf.jasperreports.engine.data.JRBeanArrayDataSource;
import net.sf.jasperreports.engine.query.JsonQueryExecuterFactory;

public class WFieldbook22 extends AbstractReporter{

	static{
		ReporterFactory.instance().addReporter(new WFieldbook22());
	}

	@Override
	public Reporter createReporter() {
		return new WFieldbook22();
	}

	@Override
	public String getReportCode() {
		return "WFb22";
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

	public JRDataSource buildJRDataSource(Map<String,Object> args, Map<String,Object> jrParams){
		Object[] data = new Object[2];
		data[0] = args.get("bean1");
		data[1] = args.get("bean1");
		
		JRDataSource dataSource = new JRBeanArrayDataSource(data);
		
		return dataSource;
	}

}
