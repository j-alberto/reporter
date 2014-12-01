package org.cimmyt.reporter;

import java.util.Collection;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.query.JsonQueryExecuterFactory;

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
	
	@Override
	public String toString(){
		return String.format("Report[%s : %s]", getReportCode(),this.getClass().getSimpleName());
	}

	/**
	 * Uses the data passed to build a JasperPrint, which can be used to generate a file, an outputStream or any other format for distribution.
	 */
	public final JasperPrint buildJRPrint(Map<String, Object> args) throws JRException{
		
		String jasperFilesPath = getTemplatePath();
		Map<String, Object> jrParams = null;
		JRDataSource jrDataSource = null;
		
		if(null != args){
			jrParams = buildJRParams(args);
			fileName = buildOutputFileName(jrParams);

			if(args.containsKey("dataSource"))
				jrDataSource = buildJRDataSource((Collection<?>)args.get("dataSource"));
			
		}
					
		JasperPrint jrPrint = JasperFillManager.fillReport(jasperFilesPath, jrParams, jrDataSource);

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
		String jasperFileName = getTemplateName();
	   	ClassLoader loader = AbstractReporter.class.getClassLoader();
        
	   	if(! jasperFileName.endsWith(".jasper"))
        	jasperFileName = jasperFileName + ".jasper";

        return loader.getResource(jasperFileName).getPath();
	}

	/**
	 * Defines the expression for the file name generated for this Reporter.
	 * Examples of file name expressions and possible outputs (depending on output file format):
	 * <pre>
	 *   report1: report1.pdf / report1.xls / report1.txt
	 *   fieldbook14: fieldbook14.pdf / fieldbook14.xls / fieldbook14.txt
	 *   CB-{trialId}: CB-25644.pdf / CB-889211.xls / CB-112.txt
	 *   Summer-{trialAbbr}_{cycle}: Summer-Obr_2014A.pdf / Summer-BW-14A.xls / Summer-Nur1_14B.xls
	 * </pre> 
	 * Parameters can be passed in expressions, and they will be replaced for the value of such parameter, 
	 * defined in the Map returned from buildJRParams() method. If no parameter is found, it is just omitted.
	 *
	 * @param fileNameExpr the name expression. The default is {reportCode}-{tid}, where {reportCode} is the code of a concrete subclass,
	 * and {tid} is a parameter in the parameters Map passed to the JRPrint, ignored if not found.
	 */
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
	
	private String buildOutputFileName(Map<String,Object> jrParams){
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

}
