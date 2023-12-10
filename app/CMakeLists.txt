# Sets the minimum version of CMake required to build the native library.
cmake_minimum_required(VERSION 3.4.1)

# Adds the source files to the native library.
add_library(
        native-lib
        SHARED
        src/main/cpp/native-lib.cpp
)

# Specifies the path to the NDK library that you want CMake to locate.
find_library(
        log-lib
        log
)

# Links the native library to the log library included in the NDK.
target_link_libraries(
        native-lib
        ${log-lib}
)
