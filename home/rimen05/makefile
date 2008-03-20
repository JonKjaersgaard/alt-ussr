#Benchmark for local communication model.

HOMEDIR = /home/rimen05/workspace

OPTIONS = -cp  .:$(HOMEDIR):$(HOMEDIR)/ussr/bin:$(HOMEDIR)/ussr/zoo/jME/lib/lwjgl.jar:$(HOMEDIR)/ussr/zoo/jME/lib/jogg-0.0.5.jar:$(HOMEDIR)/ussr/zoo/jME/lib/jorbis-0.0.12.jar:$(HOMEDIR)/ussr/zoo/jME/lib/native-mac.jar:$(HOMEDIR)/ussr/zoo/jME/lib/lwjgl_util.jar:$(HOMEDIR)/ussr/zoo/jME/lib/lwjgl_util_applet.jar:$(HOMEDIR)/ussr/zoo/jME/lib/lwjgl_test.jar:lib/lwjgl_fmod3.jar:$(HOMEDIR)/ussr/zoo/jME/lib/lwjgl_applet.jar:$(HOMEDIR)/ussr/zoo/jME/lib/jorbis-0.0.12.jar:$(HOMEDIR)/ussr/zoo/jME/lib/jogg-0.0.5.jar:$(HOMEDIR)/ussr/zoo/jME/lib/jinput.jar -Djava.library.path=$(HOMEDIR)/ussr/zoo/jME/lib:$(HOMEDIR)/ussr/zoo/jmephysics/impl/ode/lib -Xss1024k -XX:StackShadowPages=60

ATRON_PROGRAM = communication.local.ATRONSimulation

ODIN_PROGRAM = communication.local.OdinSimulation

ARGUMENTS = nModules=50 xMax=5 yMax=5 zMax=5 pe=0.1f pne=1.0 pp=1.0f

atron:
	@java $(OPTIONS) $(ATRON_PROGRAM) $(ARGUMENTS)

odin:
	@java $(OPTIONS) $(ODIN_PROGRAM) $(ARGUMENTS)
