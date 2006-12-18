------------------------------------------
How to build the natives for jME Physics 2
------------------------------------------

- check out ODE revision as found in the ode.patch file (from ODE SVN)
- apply the ode.patch
- build ODE (see ODEs INSTALL.txt, configure with --enable-release if you want to have small binaries)
- compile odejava.cpp and odejava_wrap.cxx (with similar command like the ODE compile command, see below)
- link ODEs and odejavas binaries into a shared library

Compile odejava and link with Visual Studio
-------------------------------------------
- I assume you have opened and built the ODE project
- Create a module for creating a dll, add jdk's includes (or simply add the module found in the odejava cvs)
- build odejava module



Compile odejava and link with gcc
---------------------------------
1) make sure you have JAVA_INCLUDE pointing at a jdk's include folder
2) execute the following commands (check paths) to compile:

g++ -shared -fPIC -DHAVE_CONFIG_H -I. -Iinclude/ode -O2 -fno-strict-aliasing -fomit-frame-pointer -ffast-math -Iinclude -IOPCODE -IOPCODE/Ice -I$JAVA_INCLUDE/linux -I$JAVA_INCLUDE -DdTRIMESH_ENABLED -c -o ../odejava/odejava_wrap.o ../odejava/odejava_wrap.cxx

g++ -shared -fPIC -DHAVE_CONFIG_H -I. -Iinclude/ode -O2 -fno-strict-aliasing -fomit-frame-pointer -ffast-math -Iinclude -IOPCODE -IOPCODE/Ice -I$JAVA_INCLUDE/linux -I$JAVA_INCLUDE -DdTRIMESH_ENABLED -c -o ../odejava/odejava.o ../odejava/odejava.cpp

3) execute something similar (paths possibly corrected) to link:

g++ -fPIC -shared `find ode/src -name *.o` ../odejava/odejava_wrap.o ../odejava/odejava.o -o lib/libodejava.so



Universal binaries for MacOSX (ppc+intel)
-----------------------------------------

- check you have a recent version of autotools (newest version from fink suits)

- add some additional parameters to the ODE configure call (possibly correct paths, note --enable-release won't work) :
export CFLAGS="-arch ppc -arch i386 -isysroot /Developer/SDKs/MacOSX10.4u.sdk" 
export CXXFLAGS="-arch ppc -arch i386 -isysroot /Developer/SDKs/MacOSX10.4u.sdk"
./configure --disable-dependency-tracking

- compiling and linking java binaries with adding "-arch ppc -arch i386 -isysroot /Developer/SDKs/MacOSX10.4u.sdk":

g++ -arch ppc -arch i386 -isysroot /Developer/SDKs/MacOSX10.4u.sdk -fPIC -DHAVE_CONFIG_H -I. -Iinclude/ode -O2 -fno-strict-aliasing -fomit-frame-pointer -ffast-math -Iinclude -Iinclude -IOPCODE -IOPCODE/Ice -I$JAVA_INCLUDE/linux -I$JAVA_INCLUDE -DdTRIMESH_ENABLED -c -o ../odejava/odejava_wrap.o ../odejava/odejava_wrap.cxx

g++ -arch ppc -arch i386 -isysroot /Developer/SDKs/MacOSX10.4u.sdk -fPIC -DHAVE_CONFIG_H -I. -Iinclude/ode -O2 -fno-strict-aliasing -fomit-frame-pointer -ffast-math -Iinclude -Iinclude -IOPCODE -IOPCODE/Ice -I$JAVA_INCLUDE/linux -I$JAVA_INCLUDE -DdTRIMESH_ENABLED -c -o ../odejava/odejava.o ../odejava/odejava.cpp

g++ -arch ppc -arch i386 -isysroot /Developer/SDKs/MacOSX10.4u.sdk -fPIC -dynamiclib `find ode/src -name *.o` ../odejava/odejava_wrap.o ../odejava/odejava.o -o lib/libodejava.so
