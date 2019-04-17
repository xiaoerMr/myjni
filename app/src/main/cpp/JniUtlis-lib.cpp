#include <jni.h>
#include <string>

extern "C" JNIEXPORT jstring JNICALL
Java_com_vexcellent_myjni_JniUtils_stringFromJniUtils(
        JNIEnv *env,
        jobject /* this */) {
    std::string hello = "Hello from JniUtils C++";
    return env->NewStringUTF(hello.c_str());
}
