#include <jni.h>
#include <string>

void LOGE(jstring str) {
    printf("---Jni-lib---",str);
}

extern "C" JNIEXPORT jstring JNICALL
Java_com_vexcellent_myjni_JniActivity_stringFromJNI(
        JNIEnv *env,
        jobject /* this */) {

    jstring msg = (jstring) "测试打印";
    LOGE(msg);
    system("pause");
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}



extern "C" JNIEXPORT jstring JNICALL
Java_com_vexcellent_myjni_JniActivity_StrFromJni(JNIEnv *env, jobject instance) {


    std::string hello = "撒娇发顺丰";
    return env->NewStringUTF(hello.c_str());
}