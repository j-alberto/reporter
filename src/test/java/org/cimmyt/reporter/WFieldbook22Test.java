package org.cimmyt.reporter;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.cimmyt.reporter.exception.MissingReportException;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class WFieldbook22Test{

	private static WFieldbook22 testInstance;
	
	//may change to some custom container, just simple collections by now. 
	@SuppressWarnings("unchecked")
	List<Object> mockList = mock(ArrayList.class);
	@SuppressWarnings("unchecked")
	Map<String, Object> mockMap = mock(HashMap.class);

	@BeforeClass
	public static void method(){
		try {
			testInstance = (WFieldbook22)ReporterFactory.instance().createReporter("WFb22");
		} catch (MissingReportException e) {
		}
		
	}
	
	@Test()
	public void testInit(){
		assertNotNull(testInstance);
	
	}
	
	@Test
	public void testCreateReporter() {
		assertEquals(testInstance.createReporter().getClass(), new WFieldbook22().getClass());
	}

	@Test
	public void testCreateReporterIncorrect() {
		Reporter r24 = new WFieldbook24();
		Reporter r22 = testInstance.createReporter();
		
		assertNotEquals(r22.getClass(),r24.getClass());
	}

	
	@Test
	public void testGetReportCode() {
		assertEquals("WFb22", testInstance.getReportCode());
	}

	@Test
	public void testGetTemplateName() {
		assertTrue(testInstance.getTemplateName().contains("WFb22_header"));
	}

	@Test
	public void testGetPathName(){
		assertTrue(testInstance.getTemplatePath().endsWith("/WFb22_header.jasper"));
	}


	@Test
	public void testBuildJRDataSource() {
		assertNotNull(testInstance.buildJRDataSource(mockList));
	}
	
	@Test
	public void testBuildJRParams1() {
		assertNotNull(testInstance.buildJRParams(mockMap).get("tid"));		
	}

	@Test
	public void testBuildJRParams2() {
		assertNotNull(testInstance.buildJRParams(mockMap).get("occ"));
	}

}
