package org.cimmyt.reporter.testexport;

import static java.lang.System.out;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;

import org.cimmyt.reporter.Reporter;
import org.cimmyt.reporter.ReporterFactory;
import org.cimmyt.reporter.exception.MissingReportException;
import org.cimmyt.reporter.util.UtilFiller;

public class TestCreatePDF{
	
    public static void main( String[] args )
    {
    	TestCreatePDF a = new TestCreatePDF();
    	try {
			a.createPDF();
		} catch (MissingReportException | JRException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
    
    public void createPDF() throws MissingReportException, JRException{
    	ReporterFactory factory = ReporterFactory.instance();
    	Reporter rep = factory.createReporter("WFb61");
		
		long start = System.currentTimeMillis();

		Map<String, Object> params = new HashMap<>();
		params.put("locale",new Locale("en"));

//		params.put("dataSource", Arrays.asList(UtilFiller.getSingleOccData()));
		params.put("dataSource", Arrays.asList(UtilFiller.getMultiOccData()));
		
		JasperPrint jrPrint = rep.buildJRPrint(params);
		JasperExportManager.exportReportToPdfFile(jrPrint, "target/"+rep.getFileName()+".pdf");		

		out.println("[Test PDF file generated] time(ms): " + (System.currentTimeMillis() - start));
		

    }
    

}
