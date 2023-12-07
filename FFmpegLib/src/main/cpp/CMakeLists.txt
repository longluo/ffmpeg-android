cmake_minimum_required(VERSION 3.10.2)

project(ffmpeg)

# 支持gnu++11
set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -std=gnu++11")

# 1. 定义so库和头文件所在目录，方面后面使用
set(ffmpeg_header_dir ${CMAKE_SOURCE_DIR})
set(ffmpeg_lib_dir ${CMAKE_SOURCE_DIR}/../jniLibs/${ANDROID_ABI})
set(ffmpeg_libs ${ffmpeg_lib_dir})

# 2. 添加头文件目录
include_directories(${ffmpeg_header_dir}/include)

# 3. 添加ffmpeg相关的so库

set(
        # List variable name
        ffmpeg_libs_names
        # Values in the list
        avutil swresample avcodec avfilter avformat swscale avdevice postproc)

foreach (ffmpeg_lib_name ${ffmpeg_libs_names})

    add_library(
            ${ffmpeg_lib_name}
            SHARED
            IMPORTED)

    set_target_properties(
            ${ffmpeg_lib_name}
            PROPERTIES
            IMPORTED_LOCATION
            ${ffmpeg_libs}/lib${ffmpeg_lib_name}.so)
endforeach ()

# 查找代码中使用到的系统库
find_library( # Sets the name of the path variable.
        log-lib

        # Specifies the name of the NDK library that
        # you want CMake to locate.
        log)

# 配置目标so库编译信息
add_library( # Sets the name of the library.
        native-lib

        # Sets the library as a shared library.
        SHARED

        # Provides a relative path to your source file(s).
        native-lib.cpp
        )

# 指定编译目标库时，cmake要链接的库
target_link_libraries(

        # 指定目标库，native-lib 是在上面 add_library 中配置的目标库
        native-lib

        # Links the target library to the log library
        # included in the NDK.
        # Library for writing messages in LogCat
        log

        # 4. 连接 FFmpeg 相关的库
        # FFmpeg libraries
        ${ffmpeg_libs_names})
