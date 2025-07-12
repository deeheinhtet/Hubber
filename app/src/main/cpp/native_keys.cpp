#include <jni.h>
#include <string>

extern "C" JNIEXPORT jstring JNICALL
Java_com_dee_hubber_utils_security_NativeKeysManager_loadClientToken(
        JNIEnv *env,
        jobject) {
    return env->NewStringUTF(""); // put your token
}

extern "C" JNIEXPORT jstring JNICALL
Java_com_dee_hubber_utils_security_NativeKeysManager_loadClientId(
        JNIEnv *env,
        jobject) {
    return env->NewStringUTF("Ov23lix98BAI1eZQmTC3");
}
extern "C" JNIEXPORT jstring JNICALL
Java_com_dee_hubber_utils_security_NativeKeysManager_loadClientSecret(
        JNIEnv *env,
        jobject) {
    return env->NewStringUTF("1a935ce857c558d8292d6ec5993718370f6d7ccd");
}


