Basic idea: the controller C file is compiled along with the JNI wrapper,
simulator support, and robot-specific API into a single shared library.
This is done every time the controller is modified, but can be done using
a generic makefile.

Approach: copy the appropriate makefile for your OS to simple be named 'makefile',
and then modify it to specify the controller file name and API to use (two top
lines).  Then type 'make'.  On the Java side, the controller should load the
resulting library, e.g. see ussr.samples.atron.ATRONNativeController.java

Implementation note: uses GNU make specific functionality.

Note for eclipse users: add the 'lib' directory at the root of the project to 
the native path for the project to avoid having to set the link path manually
for every run configuration.
