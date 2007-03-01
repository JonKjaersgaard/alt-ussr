all: nativeController.dll
	copy nativeController.dll ..\lib
nativeController.h: ../bin/ussr/samples/ATRONSampleController1.class
	javah -jni -classpath ../bin ussr.samples.ATRONSampleController1
nativeController.o: nativeController.c nativeController.h
	gcc -I"C:\Program Files\Java\jdk1.5.0_02\include" -I "C:\Program Files\Java\jdk1.5.0_02\include\win32" -c nativeController.c -o nativeController.o
nativeController.dll: nativeController.o
	gcc -shared --enable-stdcall-fixup -o nativeController.dll nativeController.o nativeController.def
