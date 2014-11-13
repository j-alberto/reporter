package org.cimmyt.reporter;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.cimmyt.reporter.domain.Item;

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
	public String getTemplateName() {
		return "Example.jasper";
	}

	
	@Override
	public Map<String, Object> buildJRParams(Map<String,Object> args){
		Map<String, Object> params = super.buildJRParams(args);
		params.put("ReportTitle", "My Title");
		params.put("ReportCaption", "My Caption");
		
		return params;
	}

	@Override
	public JRDataSource buildJRDataSource(Collection<?> args){
		//TODO add custom data source beans for this report
		
		List<Item> items = Arrays.asList(
				new Item(1, "item A", "a city", "a street"),
				new Item(2, "item B", "a city2", "a street2"));
		
		JRDataSource dataSource = new JRBeanCollectionDataSource(items);
		return dataSource;
	}

}
