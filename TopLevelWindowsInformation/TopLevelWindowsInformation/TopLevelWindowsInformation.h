/* DO NOT EDIT THIS FILE - it is machine generated */
#include <jni.h>
/* Header for class windows_TopLevelWindowsInformation */

#ifndef _Included_windows_TopLevelWindowsInformation
#define _Included_windows_TopLevelWindowsInformation
#ifdef __cplusplus
extern "C" {
#endif
#undef windows_TopLevelWindowsInformation_ERROR_SUCCESS
#define windows_TopLevelWindowsInformation_ERROR_SUCCESS 0L
/*
 * Class:     windows_TopLevelWindowsInformation
 * Method:    init
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_windows_TopLevelWindowsInformation_init
  (JNIEnv *, jobject);

/*
 * Class:     windows_TopLevelWindowsInformation
 * Method:    createWindowInformation
 * Signature: (I)Lwindows/WindowInformation;
 */
JNIEXPORT jobject JNICALL Java_windows_TopLevelWindowsInformation_createWindowInformation
  (JNIEnv *, jobject, jint);

#ifdef __cplusplus
}
#endif
#endif
