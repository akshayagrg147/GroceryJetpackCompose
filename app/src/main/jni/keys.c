#include <jni.h>
#include <string>

extern "C" JNIEXPORT jstring

JNICALL
Java_com_grocery_mandixpress_LoginActivity_getEncryptedKey(JNIEnv *env, jobject object) {
    std::string encrypted_key = "AIzaSyBmYp6mt0CNOiZALzbN10jwBxgN6n2E8-U";
    return env->NewStringUTF(encrypted_key.c_str());
}