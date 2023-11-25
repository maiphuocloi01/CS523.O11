#include <jni.h>
#include <string>

#include "aredblacktree.h"

/*struct TreeWrapper {

public:
    const static ARedBlackTree<int>* tree = new ARedBlackTree<int>();

    static void init() {
      //tree = new ARedBlackTree<int>();
    }

    static void getInstance() {

    }
};*/

ARedBlackTree<int>* tree = new ARedBlackTree<int>();

extern "C" JNIEXPORT jint
JNICALL
Java_com_uit_demoredblacktree_VisualizeActivity_init(
  JNIEnv *env,
  jobject j) {
  //TreeWrapper::init();
  return 1;
}

extern "C" JNIEXPORT jstring
JNICALL
Java_com_uit_demoredblacktree_VisualizeActivity_insert(
  JNIEnv *env,
  jobject j,
  jint n) {
  //TreeWrapper::
  tree->Insert(n);
  return env->NewStringUTF(tree->APrint2D(false).c_str());
}

extern "C" JNIEXPORT jstring
JNICALL
Java_com_uit_demoredblacktree_VisualizeActivity_delete(
  JNIEnv *env,
  jobject j,
  jint n) {
  tree->Delete(n);
  return env->NewStringUTF(tree->APrint2D(false).c_str());
}

extern "C" JNIEXPORT jstring
JNICALL
Java_com_uit_demoredblacktree_VisualizeActivity_print2d(
  JNIEnv *env,
  jobject j,
  jint n) {
  if(n == 1) {
    return env->NewStringUTF(tree->APrint2D(true).c_str());
  } else {
    return env->NewStringUTF(tree->APrint2D(false).c_str());

  }
}

extern "C" JNIEXPORT jstring
JNICALL
Java_com_uit_demoredblacktree_VisualizeActivity_printInorder(
  JNIEnv *env,
  jobject j,
  jint n) {
  if(n == 1) {
    return env->NewStringUTF(tree->APrint(INORDER, true).c_str());
  } else {
    return env->NewStringUTF(tree->APrint(INORDER, false).c_str());

  }
}

extern "C" JNIEXPORT jstring
JNICALL
Java_com_uit_demoredblacktree_VisualizeActivity_printPreorder(
  JNIEnv *env,
  jobject j,
  jint n) {
  if(n == 1) {
    return env->NewStringUTF(tree->APrint(PREORDER, true).c_str());
  } else {
    return env->NewStringUTF(tree->APrint(PREORDER, false).c_str());

  }
}

extern "C" JNIEXPORT jstring
JNICALL
Java_com_uit_demoredblacktree_VisualizeActivity_printPostorder(
  JNIEnv *env,
  jobject j,
  jint n) {
  if(n == 1) {
    return env->NewStringUTF(tree->APrint(POSTORDER, true).c_str());
  } else {
    return env->NewStringUTF(tree->APrint(POSTORDER, false).c_str());

  }
}


