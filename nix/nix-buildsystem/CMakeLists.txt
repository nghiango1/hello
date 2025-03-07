cmake_minimum_required(VERSION 3.30)
set(CMAKE_EXPORT_COMPILE_COMMANDS ON)

project(example)

add_executable(${PROJECT_NAME} ./src/main.cpp ./src/math.cpp)
target_include_directories(${PROJECT_NAME} PRIVATE include)
install(TARGETS ${PROJECT_NAME} DESTINATION ${CMAKE_INSTALL_BINDIR})
