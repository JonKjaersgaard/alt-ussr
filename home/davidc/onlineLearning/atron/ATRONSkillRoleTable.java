package onlineLearning.atron;

import java.util.ArrayList;

import onlineLearning.SkillController;
import onlineLearning.skills.SkillQ;
import onlineLearning.skills.SkillRoleTable;
import onlineLearning.skills.SkillRoleTable.DelayedRewardCollector;

public class ATRONSkillRoleTable extends SkillRoleTable {

	public ATRONSkillRoleTable(float periodeTime, SkillController controller) {
		super(periodeTime, controller);
		skills.add(new SkillQ(3)); //center
		skills.add(new SkillQ(4)); //homePos
		skills.add(new SkillQ(2)); //connector 0
		skills.add(new SkillQ(2)); //connector 2
		skills.add(new SkillQ(2)); //connector 4
		skills.add(new SkillQ(2)); //connector 6
		
		collectors = new ArrayList<DelayedRewardCollector>();
		collectors.add(new DelayedRewardCollector(1));
		collectors.add(new DelayedRewardCollector(3));
		collectors.add(new DelayedRewardCollector(1));
		collectors.add(new DelayedRewardCollector(1));
		collectors.add(new DelayedRewardCollector(1));
		collectors.add(new DelayedRewardCollector(1));
	}

	

}
