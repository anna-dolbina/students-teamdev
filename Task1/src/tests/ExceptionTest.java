package tests;



import windows.LastErrorException;

import org.junit.Test;



public class ExceptionTest {

	@Test
	public void lastExceptionThrowing() {
		try{
			throw new LastErrorException(5, "Test");
		}catch(LastErrorException e){
			e.printStackTrace();
		}
	}

}
