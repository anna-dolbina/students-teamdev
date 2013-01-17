package tests;

import static org.junit.Assert.*;
import windows.LastErrorException;

import org.junit.Test;

public class ExceptionTest {
	private static final int ERROR_CODE = 120;
	private static final String ERROR_DESCRIPTION="This function is not supported on this system.";

	/**
	 * Tests creating a last error exception and retrieving the error code
	 * description.
	 */
	@Test
	public void lastExceptionThrowing() {
		try {
			LastErrorException e = new LastErrorException(ERROR_CODE, "Test");
			assertTrue("Retrieved error description is not correct",e.getErrorDescription().matches(ERROR_DESCRIPTION));		
		} catch (Exception e) {
			fail("Exception was thrown when testing LastErrorException creating:"+e.getMessage());
		}
	}

}
