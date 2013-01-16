#include "LastErrorException.h"
#include "windows.h"

JNIEXPORT jstring JNICALL Java_windows_LastErrorException_retrieveErrorDescription
  (JNIEnv * env, jclass cls, jint errorCode)
{
	jstring errorMessage;
	LPVOID lpMsgBuf;
	HLOCAL returnValue;
	int error_code;
	char* err;
	int msglen;
	/*Obtaining information for passed error code*/
	msglen=FormatMessage(
        FORMAT_MESSAGE_ALLOCATE_BUFFER | 
        FORMAT_MESSAGE_FROM_SYSTEM,
        NULL,
        (DWORD)errorCode,
        MAKELANGID(LANG_NEUTRAL, SUBLANG_DEFAULT),
        (LPTSTR) &lpMsgBuf,
        0, NULL );
	/*If fails, obtain information about the last error code in system and use it*/
	if(!msglen){
			msglen=FormatMessage(
			FORMAT_MESSAGE_ALLOCATE_BUFFER | 
			FORMAT_MESSAGE_FROM_SYSTEM,
			NULL,
			GetLastError(),
			MAKELANGID(LANG_NEUTRAL, SUBLANG_DEFAULT), 
			(LPTSTR) &lpMsgBuf,
			0,NULL);
	}
	/*Creating a string using obtained information string*/
	errorMessage = (*env)->NewString(env,(const jchar*)lpMsgBuf, msglen);
	/* Free the buffer.*/
	returnValue=LocalFree( lpMsgBuf );
	if(returnValue!=NULL){
		error_code=GetLastError();
		sprintf(err,"Error occurred when freeing memory: %d\n",error_code);
		errorMessage=(*env)->NewStringUTF(env,err);
	}
	return errorMessage;
}