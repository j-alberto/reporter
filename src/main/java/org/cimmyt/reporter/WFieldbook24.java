package org.cimmyt.reporter;

import java.util.Collection;

import net.sf.jasperreports.engine.JRDataSource;

public class WFieldbook24 extends AbstractReporter{

	@Override
	public Reporter createReporter() {
		return new WFieldbook24();
	}

	@Override
	public String getReportCode() {
		return "WFb24";
	}

	@Override
	public String getTemplateName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JRDataSource buildJRDataSource(Collection<?> dataRecords) {
		// TODO Auto-generated method stub
		return null;
	}
	
}

