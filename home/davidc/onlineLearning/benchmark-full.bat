@echo off
set options=-cp .;D:\workspace\ussr\bin;D:\workspace\ussr\zoo\jME\lib\lwjgl.jar;D:\workspace\zoo\jME\lib\jogg-0.0.5.jar;D:\workspace\zoo\jME\lib\jorbis-0.0.12.jar;D:\workspace\zoo\jME\lib\native-mac.jar;D:\workspace\zoo\jME\lib\lwjgl_util.jar;D:\workspace\zoo\jME\lib\lwjgl_util_applet.jar;D:\workspace\zoo\jME\lib\lwjgl_test.jar;D:\workspace\zoo\jME\lib\lwjgl_fmod3.jar;D:\workspace\zoo\jME\lib\lwjgl_applet.jar;D:\workspace\zoo\jME\lib\jorbis-0.0.12.jar;D:\workspace\zoo\jME\lib\jogg-0.0.5.jar;D:\workspace\zoo\jME\lib\jinput.jar -Djava.library.path=D:\workspace\ussr\zoo\jME\lib;D:\workspace\ussr\zoo\jmephysics\impl\ode\lib

@echo ----Starting ATRON car Simulation----


java %options% onlineLearning.atron.AtronSkillSimulationMotion TrialID=HARDMAX1 RoleSelectionStrategy=HARDMAX
java %options% onlineLearning.atron.AtronSkillSimulationMotion TrialID=HARDMAX2 RoleSelectionStrategy=HARDMAX
java %options% onlineLearning.atron.AtronSkillSimulationMotion TrialID=HARDMAX3 RoleSelectionStrategy=HARDMAX
java %options% onlineLearning.atron.AtronSkillSimulationMotion TrialID=HARDMAX4 RoleSelectionStrategy=HARDMAX
java %options% onlineLearning.atron.AtronSkillSimulationMotion TrialID=HARDMAX5 RoleSelectionStrategy=HARDMAX
java %options% onlineLearning.atron.AtronSkillSimulationMotion TrialID=HARDMAX6 RoleSelectionStrategy=HARDMAX
java %options% onlineLearning.atron.AtronSkillSimulationMotion TrialID=HARDMAX7 RoleSelectionStrategy=HARDMAX
java %options% onlineLearning.atron.AtronSkillSimulationMotion TrialID=HARDMAX8 RoleSelectionStrategy=HARDMAX
java %options% onlineLearning.atron.AtronSkillSimulationMotion TrialID=HARDMAX9 RoleSelectionStrategy=HARDMAX

java %options% onlineLearning.atron.AtronSkillSimulationMotion TrialID=EPSILON_GREEDY1 RoleSelectionStrategy=EPSILON_GREEDY
java %options% onlineLearning.atron.AtronSkillSimulationMotion TrialID=EPSILON_GREEDY2 RoleSelectionStrategy=EPSILON_GREEDY
java %options% onlineLearning.atron.AtronSkillSimulationMotion TrialID=EPSILON_GREEDY3 RoleSelectionStrategy=EPSILON_GREEDY
java %options% onlineLearning.atron.AtronSkillSimulationMotion TrialID=EPSILON_GREEDY4 RoleSelectionStrategy=EPSILON_GREEDY
java %options% onlineLearning.atron.AtronSkillSimulationMotion TrialID=EPSILON_GREEDY5 RoleSelectionStrategy=EPSILON_GREEDY
java %options% onlineLearning.atron.AtronSkillSimulationMotion TrialID=EPSILON_GREEDY6 RoleSelectionStrategy=EPSILON_GREEDY
java %options% onlineLearning.atron.AtronSkillSimulationMotion TrialID=EPSILON_GREEDY7 RoleSelectionStrategy=EPSILON_GREEDY
java %options% onlineLearning.atron.AtronSkillSimulationMotion TrialID=EPSILON_GREEDY8 RoleSelectionStrategy=EPSILON_GREEDY
java %options% onlineLearning.atron.AtronSkillSimulationMotion TrialID=EPSILON_GREEDY9 RoleSelectionStrategy=EPSILON_GREEDY

java %options% onlineLearning.atron.AtronSkillSimulationMotion TrialID=EPSILON_FIRST1 RoleSelectionStrategy=EPSILON_FIRST
java %options% onlineLearning.atron.AtronSkillSimulationMotion TrialID=EPSILON_FIRST2 RoleSelectionStrategy=EPSILON_FIRST
java %options% onlineLearning.atron.AtronSkillSimulationMotion TrialID=EPSILON_FIRST3 RoleSelectionStrategy=EPSILON_FIRST
java %options% onlineLearning.atron.AtronSkillSimulationMotion TrialID=EPSILON_FIRST4 RoleSelectionStrategy=EPSILON_FIRST
java %options% onlineLearning.atron.AtronSkillSimulationMotion TrialID=EPSILON_FIRST5 RoleSelectionStrategy=EPSILON_FIRST
java %options% onlineLearning.atron.AtronSkillSimulationMotion TrialID=EPSILON_FIRST6 RoleSelectionStrategy=EPSILON_FIRST
java %options% onlineLearning.atron.AtronSkillSimulationMotion TrialID=EPSILON_FIRST7 RoleSelectionStrategy=EPSILON_FIRST
java %options% onlineLearning.atron.AtronSkillSimulationMotion TrialID=EPSILON_FIRST8 RoleSelectionStrategy=EPSILON_FIRST
java %options% onlineLearning.atron.AtronSkillSimulationMotion TrialID=EPSILON_FIRST9 RoleSelectionStrategy=EPSILON_FIRST

java %options% onlineLearning.atron.AtronSkillSimulationMotion TrialID=EPSILON_DECREASING1 RoleSelectionStrategy=EPSILON_DECREASING
java %options% onlineLearning.atron.AtronSkillSimulationMotion TrialID=EPSILON_DECREASING2 RoleSelectionStrategy=EPSILON_DECREASING
java %options% onlineLearning.atron.AtronSkillSimulationMotion TrialID=EPSILON_DECREASING3 RoleSelectionStrategy=EPSILON_DECREASING
java %options% onlineLearning.atron.AtronSkillSimulationMotion TrialID=EPSILON_DECREASING4 RoleSelectionStrategy=EPSILON_DECREASING
java %options% onlineLearning.atron.AtronSkillSimulationMotion TrialID=EPSILON_DECREASING5 RoleSelectionStrategy=EPSILON_DECREASING
java %options% onlineLearning.atron.AtronSkillSimulationMotion TrialID=EPSILON_DECREASING6 RoleSelectionStrategy=EPSILON_DECREASING
java %options% onlineLearning.atron.AtronSkillSimulationMotion TrialID=EPSILON_DECREASING7 RoleSelectionStrategy=EPSILON_DECREASING
java %options% onlineLearning.atron.AtronSkillSimulationMotion TrialID=EPSILON_DECREASING8 RoleSelectionStrategy=EPSILON_DECREASING
java %options% onlineLearning.atron.AtronSkillSimulationMotion TrialID=EPSILON_DECREASING9 RoleSelectionStrategy=EPSILON_DECREASING

java %options% onlineLearning.atron.AtronSkillSimulationMotion TrialID=SOFTMAX1 RoleSelectionStrategy=SOFTMAX
java %options% onlineLearning.atron.AtronSkillSimulationMotion TrialID=SOFTMAX2 RoleSelectionStrategy=SOFTMAX
java %options% onlineLearning.atron.AtronSkillSimulationMotion TrialID=SOFTMAX3 RoleSelectionStrategy=SOFTMAX
java %options% onlineLearning.atron.AtronSkillSimulationMotion TrialID=SOFTMAX4 RoleSelectionStrategy=SOFTMAX
java %options% onlineLearning.atron.AtronSkillSimulationMotion TrialID=SOFTMAX5 RoleSelectionStrategy=SOFTMAX
java %options% onlineLearning.atron.AtronSkillSimulationMotion TrialID=SOFTMAX6 RoleSelectionStrategy=SOFTMAX
java %options% onlineLearning.atron.AtronSkillSimulationMotion TrialID=SOFTMAX7 RoleSelectionStrategy=SOFTMAX
java %options% onlineLearning.atron.AtronSkillSimulationMotion TrialID=SOFTMAX8 RoleSelectionStrategy=SOFTMAX
java %options% onlineLearning.atron.AtronSkillSimulationMotion TrialID=SOFTMAX9 RoleSelectionStrategy=SOFTMAX