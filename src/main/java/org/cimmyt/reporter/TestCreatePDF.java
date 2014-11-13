package org.cimmyt.reporter;

import static java.lang.System.out;

import java.util.HashMap;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;

import org.cimmyt.reporter.exception.MissingReportException;

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
    	Reporter rep22 = factory.createReporter("WFb22");
		
		long start = System.currentTimeMillis();

		JasperPrint jrPrint = rep22.buildJRPrint(new HashMap<String, Object>());
		JasperExportManager.exportReportToPdfFile(jrPrint, "target/Example.pdf");		

		out.println("[Test PDF file generated] time(ms): " + (System.currentTimeMillis() - start));
		

    }
    

}
