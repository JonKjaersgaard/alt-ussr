package onlineLearning.mtran;

import java.util.ArrayList;

import onlineLearning.SkillController;
import onlineLearning.skills.Skill;
import onlineLearning.skills.SkillQ;
import onlineLearning.skills.SkillRoleTable;
import onlineLearning.skills.SkillTimeTable;

public class MTRANSkillRoleTable extends SkillRoleTable {

	public MTRANSkillRoleTable(float periodeTime, SkillController controller) {
		super(periodeTime, controller);
		SkillQ.alpha = 0.0333f;//0.1f/5;
		skills.add(new SkillTimeTable(3,periodeTime,5,controller)); //actuator 1
		skills.add(new SkillTimeTable(3,periodeTime,5,controller)); //actuator 2
		
		collectors = new ArrayList<DelayedRewardCollector>();
		collectors.add(new DelayedRewardCollector(1));
		collectors.add(new DelayedRewardCollector(1));
		
	}
	public boolean isContinuous() {
		return true;
	}
	
}
