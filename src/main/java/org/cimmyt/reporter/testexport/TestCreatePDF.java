package org.cimmyt.reporter.testexport;

import static java.lang.System.out;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;

import org.cimmyt.reporter.Reporter;
import org.cimmyt.reporter.ReporterFactory;
import org.cimmyt.reporter.exception.BuildReportException;
import org.cimmyt.reporter.exception.MissingReportException;
import org.cimmyt.reporter.util.UtilFiller;

public class TestCreatePDF{
	
    public static void main( String[] args )
    {
    	TestCreatePDF a = new TestCreatePDF();
    	try {
			
//    		a.createPDF();
    		a.createXLSX();
			
		} catch (MissingReportException | JRException | IOException | BuildReportException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
    
    public void createPDF() throws MissingReportException, JRException, IOException, BuildReportException{
    	ReporterFactory factory = ReporterFactory.instance();
    	Reporter rep = factory.createReporter("MFb3");
		
		long start = System.currentTimeMillis();

		Map<String, Object> params = new HashMap<>();
		params.put("locale",new Locale("en"));
		
		params.put("site", "dummy Site");
		params.put("nursery", "dummy nursery");
		params.put("season", "dummy season");
		params.put("seedPrep", "dummy seedprep");
		params.put("siteNum", "dummy 99");

		params.put("dataSource", UtilFiller.getOneSite().getEntries());
		
//		params.put("dataSource", Arrays.asList(UtilFiller.getSingleOccData()));
//		params.put("dataSource", Arrays.asList(UtilFiller.getMultiOccData()));
		
		JasperPrint jrPrint = rep.buildJRPrint(params);
		JasperExportManager.exportReportToPdfFile(jrPrint, "./"+rep.getFileName()+".pdf");
		
		File pdf = new File(rep.getFileName());
		
		//JasperExportManager.exportReportToPdfStream(jrPrint, new FileOutputStream(pdf));
		rep.asOutputStream(new FileOutputStream(pdf));

		out.println("[Test PDF file generated] time(ms): " + (System.currentTimeMillis() - start));
		

    }
    
    
    public void createXLSX() throws MissingReportException, JRException, IOException, BuildReportException{

    	ReporterFactory factory = ReporterFactory.instance();
    	Reporter rep = factory.createReporter("MFb3");
		
		long start = System.currentTimeMillis();

		Map<String, Object> params = new HashMap<>();
		params.put("locale",new Locale("en"));
		
		params.put("site", "dummy Site");
		params.put("nursery", "dummy nursery");
		params.put("season", "dummy season");
		params.put("seedPrep", "dummy seedprep");
		params.put("siteNum", "dummy 99");

		params.put("location", "a location");
		params.put("country", "a country");
		params.put("environment", "an environment");
		params.put("plantingDate", "2015-01-01");
		params.put("trialName", "Some_Trial-Name");
		params.put("netPlotLength", "xxx");
		params.put("distanceBetweenStations", "xxx");
		params.put("distanceBetweenRows", "xxx");
		params.put("rowsHarvested", "xxx");
		params.put("collaborator", "a collaborator");
		params.put("breedingProgram", "a breedingProgram");
		params.put("harvestDate", "2015-06-01");

		params.put("recLastName", "some recLastName");
		params.put("recFirstName", "some recFirstName");
		params.put("institution", "some institution");
		params.put("shippingAddress", "some shippingAddress");
		params.put("contactNumber", "999-999-99-99");
		params.put("phytoInstr", "some phytoInstr");
		params.put("shippingInstr", "some shippingInstr");
		params.put("shipFrom", "some shipFrom");
		params.put("shipId", "some shipId");
		params.put("prepDate", "2015-01-01");
		params.put("carrier", "some carrier");
		params.put("airwayBill", "some airwayBill");
		params.put("dateSent", "2015-06-01");
		params.put("quantity", "999");
		params.put("commments", "commments xxxxxxxxxxxxxxxxxxxxxx");

		params.put("dataSource", UtilFiller.getOneSite().getEntries());
		params.put("columnHeaders", Arrays.asList( new String[]{"colXXX","colYYY","colZZZ","colAAA","colBBB","colBBB","colBBB","colBBB","colBBB"} ));
		
		JasperPrint jrPrint = rep.buildJRPrint(params);
		
		File xlsx = new File(rep.getFileName()+".xlsx");
		
		rep.asOutputStream(new FileOutputStream(xlsx));

		out.println("[Test PDF file generated] time(ms): " + (System.currentTimeMillis() - start));

    }

}
