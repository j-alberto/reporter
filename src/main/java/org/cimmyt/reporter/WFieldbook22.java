package org.cimmyt.reporter;

import java.util.Map;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanArrayDataSource;

public class WFieldbook22 extends AbstractReporter{

	@Override
	public Reporter createReporter() {
		return new WFieldbook22();
	}

	@Override
	public String getReportCode() {
		return "WFb22";
	}
	
	@Override
	public Map<String, Object> buildJRParams(Map<String,Object> args){
		//TODO add custom params for this report type
		return super.buildJRParams(args);
	}

	public JRDataSource buildJRDataSource(Map<String,Object> args, Map<String,Object> jrParams){
		//TODO add custom data source beans for this report
		Object[] data = new Object[2];
		data[0] = args.get("bean1");
		data[1] = args.get("bean1");
		
		JRDataSource dataSource = new JRBeanArrayDataSource(data);
		
		return dataSource;
	}

}
