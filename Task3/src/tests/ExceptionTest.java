package tests;

import static org.junit.Assert.*;


import org.junit.Test;

import com.jniwrapper.win32.LastErrorException;

public class ExceptionTest {

	@Test
	public void test() {
		throw new LastErrorException(3, "Test");
		
	}

}
