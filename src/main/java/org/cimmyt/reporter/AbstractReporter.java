package org.cimmyt.reporter;

import java.util.Collection;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.OutputStream;

import org.cimmyt.reporter.exception.BuildReportException;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.engine.query.JsonQueryExecuterFactory;
import net.sf.jasperreports.engine.xml.JasperDesignFactory;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleXlsReportConfiguration;

/**
 * Defines the base class for all Reporters. Each reporter pretending to
 * be discovered by ReporterFactory must extend this class
 * @author jarojas
 *
 */
abstract class AbstractReporter implements Reporter{
	
	private String fileNameExpr = getReportCode()+"-{tid}";
	private String fileName = null;
	private Pattern fileNameParamsPattern = Pattern.compile("\\{[\\w-_]*\\}");
	JasperPrint jrPrint;
	
	@Override
	public String toString(){
		return String.format("Report[%s : %s]", getReportCode(),this.getClass().getSimpleName());
	}

	/**
	 * Uses the data passed to build a JasperPrint, which can be used to generate a file, an outputStream or any other format for distribution.
	 */
	public JasperPrint buildJRPrint(Map<String, Object> args) throws JRException{
		
		String jasperFilesPath = getTemplatePath();
		Map<String, Object> jrParams = null;
		JRDataSource jrDataSource = null;
		
		if(null != args){
			jrParams = buildJRParams(args);
			fileName = buildOutputFileName(jrParams);

			if(args.containsKey("dataSource")){
				jrDataSource = buildJRDataSource((Collection<?>)args.get("dataSource"));
				System.out.println ("datasource done!!!");
			}
			
		}
					
		jrPrint = JasperFillManager.fillReport(jasperFilesPath, jrParams, jrDataSource);

		return jrPrint;
		
	}
	
	/**
	 * Returns a Map with the parameters required for creating a JasperPrint for this Reporter.
	 * This method configures some basic jasper options like language and text formatting;
	 * subclasses extending AbstractReporter may add extra parameters to fill in its particular template.
	 * @return Map of parameters for a JarperPrint 
	 */
	public Map<String, Object> buildJRParams(Map<String,Object> args){
		Map<String, Object> params = new HashMap<String, Object>();

		if(args.containsKey("datePattern"))
			params.put(JsonQueryExecuterFactory.JSON_DATE_PATTERN, args.get("datePattern"));
		
		if(args.containsKey("numberPattern"))
			params.put(JsonQueryExecuterFactory.JSON_NUMBER_PATTERN, args.get("numberPattern"));
		
		if(args.containsKey("locale")){
			if(args.get("locale") instanceof Locale)
				params.put(JRParameter.REPORT_LOCALE, args.get("locale"));
			else
				params.put(JRParameter.REPORT_LOCALE, new Locale(args.get("locale").toString()));
		}else{
			params.put(JRParameter.REPORT_LOCALE, new Locale("en_US"));
		}

		return params;
	}

	/**
	 * Obtains the full path to the .jasper file, specified by getFileName()
	 * @param jasperFileName The name of the compiled .jasper file.
	 * @return
	 */
	public String getTemplatePath() {
		String baseJasperDirectory = "jasper/";
		String jasperFileName = getTemplateName();
	   	ClassLoader loader = AbstractReporter.class.getClassLoader();
        
	   	if(! jasperFileName.endsWith(".jasper"))
        	jasperFileName = jasperFileName + ".jasper";

        return loader.getResource(baseJasperDirectory+jasperFileName).getPath();
	}

	public void setFileNameExpression(String fileNameExpr){
		this.fileNameExpr = fileNameExpr;
	}

	/**
	 * Returns the filename for an instance of this Reporter, applying defined parameters.
	 * @return the file name for this Reporter.
	 */
	public String getFileName(){
		return fileName;
	}
	public void setFileName(String fileName){
		this.fileName = fileName;
	}
	
	protected String buildOutputFileName(Map<String,Object> jrParams){
		String fileName = this.fileNameExpr;
		
		
		Matcher paramsMatcher = fileNameParamsPattern.matcher(this.fileNameExpr);
		
		 while (paramsMatcher.find()) {
			 String paramName = paramsMatcher.group().replaceAll("[\\{\\}]", "");
			 
			 if(null == jrParams || null == jrParams.get(paramName)){
				 fileName = fileName.replace(paramsMatcher.group(), "");
			 }else{
				 fileName = fileName.replace(paramsMatcher.group(), jrParams.get(paramName).toString());
			 }
		 }
		
		return fileName;
	}
	
	@Override
	public void asOutputStream(OutputStream output) throws BuildReportException {
		if(null != jrPrint)
			try {
				JasperExportManager.exportReportToPdfStream(jrPrint, output);
			} catch (JRException e) {
				e.printStackTrace();
			}
		else throw new BuildReportException(getReportCode());
	}

	/**
	 * Does not set the input and output of this exporter, only configures it.
	 * @return
	 */
	protected JRXlsxExporter createDefaultExcelExporter(){
		JRXlsxExporter ex = new JRXlsxExporter();
		
		SimpleXlsReportConfiguration jrConfig = new SimpleXlsReportConfiguration();
		jrConfig.setOnePagePerSheet(false);
		jrConfig.setDetectCellType(true);
		jrConfig.setIgnoreCellBorder(true);
		jrConfig.setWhitePageBackground(true);
		
		return ex;
	}
}
