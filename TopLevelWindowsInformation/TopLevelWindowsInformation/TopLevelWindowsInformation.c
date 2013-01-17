#include <Windows.h>

#include "TopLevelWindowsInformation.h"
#define MAX_CLASSNAME_LENGTH 1024


JavaVM *jvm;
jobject callbackReceiver;

BOOL CALLBACK windowHandlerCallback(HWND hwnd,LPARAM lParam)
{
	JNIEnv *env;
	jthrowable exc;
	jclass cls;
	jobject obj=(jobject)lParam;
	jmethodID methodID;

	(*jvm)->AttachCurrentThread(jvm, (void **)&env, NULL);
	cls = (*env)->GetObjectClass(env, callbackReceiver);
	methodID = (*env)->GetMethodID(env,cls, "callback", "(I)V");
	if (methodID == NULL) {
		SetLastError(ERROR_NOT_FOUND);
		return FALSE;//Aborts further windows enumeration
	}
	(*env)->CallVoidMethod(env,callbackReceiver,methodID,(int)hwnd);
	if((*env)->ExceptionCheck(env)==JNI_TRUE)
	{
		SetLastError(ERROR_INVALID_HANDLE);
		(*env)->ExceptionClear(env);
		return FALSE;//Aborts further windows enumeration
	}
	return TRUE;
}

JNIEXPORT jint JNICALL Java_windows_TopLevelWindowsInformation_init
	(JNIEnv * env, jobject obj)
{	
	int status;
	(*env)->GetJavaVM(env, &jvm);
	callbackReceiver = (*env)->NewGlobalRef( env, obj );
	status=EnumWindows((WNDENUMPROC)(windowHandlerCallback), 0);
	(*env)->DeleteGlobalRef(env, obj);
	if(status==0) return GetLastError();
	return 0;
}


jboolean isWindowVisible
	(JNIEnv *env, jobject obj, jint handle)
{
	HWND hWnd=(HWND)((int)handle);
	jclass Exception;
	if(!IsWindow(hWnd)){
		Exception = (*env)->FindClass(env,"java/lang/IllegalArgumentException");
		(*env)->ThrowNew(env,Exception,"Passed argument is not a valid window handle.");
		return FALSE;
	}

	if(IsWindowVisible(hWnd)){
		return TRUE;
	}
	return FALSE;
}


jstring JNICALL getWindowTitle
	(JNIEnv *env, jobject obj, jint handle)
{	
	wchar_t* res;
	int len;
	jchar* raw;
	jstring result=(*env)->NewStringUTF(env,"");
	jclass Exception;
	HWND hWnd=(HWND)((int)handle);
	if(!IsWindow(hWnd)){
		Exception = (*env)->FindClass(env,"java/lang/IllegalArgumentException");
		(*env)->ThrowNew(env,Exception,"Passed argument is not a valid window handle.");
		return result;
	}
	len=GetWindowTextLength(hWnd);
	len+=1;
	res=(wchar_t*)malloc(sizeof(wchar_t)*len);
	GetWindowText(hWnd,(LPWSTR)res,len);
	raw=(jchar*)malloc(sizeof(jchar)*len);
	memcpy(raw, res, len*sizeof(wchar_t));
	result = (*env)->NewString(env,raw, len);
	free(raw);
	free(res);
	return result;
}


jstring JNICALL getWindowClassName
	(JNIEnv *env, jobject obj, jint handle)
{
	char* res;
	jstring result=(*env)->NewStringUTF(env,"");
	jclass Exception;
	HWND hWnd=(HWND)((int)handle);
	if(!IsWindow(hWnd)){
		Exception = (*env)->FindClass(env,"java/lang/IllegalArgumentException");
		(*env)->ThrowNew(env,Exception,"Passed argument is not a valid window handle.");
		return result;
	}
	res=(char*)malloc(sizeof(char)*MAX_CLASSNAME_LENGTH);
	GetClassNameA(hWnd,(LPSTR)res,MAX_CLASSNAME_LENGTH);
	result = (*env)->NewStringUTF(env,res);
	free(res);
	return result;

}

jobject JNICALL getTopLeftCorner
	(JNIEnv * env, jobject obj, jint handle){

		RECT rect;
		LPRECT lpRect=&rect;
		HWND hWnd=(HWND)((int)handle);
		jclass Exception;
		jobject cls;
		jmethodID constructor;

		cls = (*env)->FindClass(env, "java/awt/Point");
		constructor = (*env)->GetMethodID(env, cls, "<init>", "(II)V");
		if(!IsWindow(hWnd)){
			Exception = (*env)->FindClass(env,"java/lang/IllegalArgumentException");
			(*env)->ThrowNew(env,Exception,"Passed argument is not a valid window handle.");
			return (*env)->NewObject(env, cls, constructor, 0, 0);
		}
		GetWindowRect(hWnd,lpRect);
		return (*env)->NewObject(env, cls, constructor, lpRect->left, lpRect->top);
}
JNIEXPORT jobject JNICALL Java_windows_TopLevelWindowsInformation_createWindowInformation
  (JNIEnv * env, jobject obj, jint windowHandle){
	  jclass cls;
	  jobject info;
	  jmethodID constructor;
	  HWND hWnd=(HWND)((int)windowHandle);

	  cls = (*env)->FindClass(env, "windows/WindowInformation");
	  constructor = (*env)->GetMethodID(env, cls, "<init>", "(ILjava/lang/String;Ljava/lang/String;ZLjava/awt/Point;)V");
	  
	  (*env)->NewGlobalRef( env, obj );
	  info=(*env)->NewObject(env, cls, constructor, windowHandle, 
		  getWindowTitle(env, obj, windowHandle),
		  getWindowClassName(env, obj, windowHandle),
		  isWindowVisible(env, obj, windowHandle),
		  getTopLeftCorner(env, obj, windowHandle));
	  (*env)->DeleteGlobalRef(env, obj);
	  return info;

}
