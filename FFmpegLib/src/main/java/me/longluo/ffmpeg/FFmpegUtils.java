package me.longluo.ffmpeg;

public class FFmpegUtils {

    static {
        System.loadLibrary("native-lib");
    }

    private static FFmpegUtils instance = null;

    public static FFmpegUtils getInstance() {
        if (instance == null) {
            instance = new FFmpegUtils();
        }

        return instance;
    }

    public native String ffmpegInfo();

}
