#include <jni.h>
#include <string>
#include <unistd.h>

extern "C" {

#include <libavcodec/avcodec.h>
#include <libavformat/avformat.h>
#include <libavfilter/avfilter.h>
#include <libavcodec/jni.h>

JNIEXPORT jstring JNICALL
Java_me_longluo_ffmpeg_FFmpegUtils_ffmpegInfo(JNIEnv *env, jobject  /* this */) {

    char info[40000] = {0};

    // 初始化编码器遍历器
    void *opaque = NULL;
    const AVCodec *c_temp = av_codec_iterate(&opaque);

    // 遍历所有支持的编码器
    while (c_temp != NULL) {

        switch (c_temp->type) {
            case AVMEDIA_TYPE_VIDEO:
                sprintf(info, "%s(video):", info);
                break;

            case AVMEDIA_TYPE_AUDIO:
                sprintf(info, "%s(audio):", info);
                break;

            default:
                sprintf(info, "%s(other):", info);
                break;
        }

        sprintf(info, "%s[%s]\n", info, c_temp->long_name);
        c_temp = av_codec_iterate(&opaque);
    }

    return env->NewStringUTF(info);
}

}
