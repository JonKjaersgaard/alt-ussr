package onlineLearning.skills;

import java.util.Random;

import onlineLearning.Role;
import onlineLearning.tools.PSO;


public class SkillPSO implements Skill {
	PSO pso;
	int nParticles;
	int nRoles;
	int currentRole;
	int currentParticle;
	static Random rand = new Random(); 
	public SkillPSO(int nParticles, int nRoles) {
		this.nParticles = nParticles;
		this.nRoles = nRoles;
		pso = new PSO(nParticles, nRoles, 0);
		currentParticle = 0;
	}
	public Role getRole() {
		float[] particle = pso.getParticle(currentParticle);
		currentRole = particle2Role(particle);
		Role role = new Role(1);
		role.setRole(currentRole, 0);
		return role; 
	}
	public Role getBestRole() {
		float[] bestParticle = pso.findGlobalBest();
		currentParticle = pso.gBestIndex();
		Role role = new Role(1);
		role.setRole(particle2Role(bestParticle), 0);
		return role; 
	}
	private int particle2Role(float[] particle) {
		int bestRole = 0;
		float maxFitness = particle[0];
		for(int i=0;i<nRoles;i++) {
			if(maxFitness<particle[i]) {
				bestRole = i;
				maxFitness = particle[i];
			}
		}
		return bestRole;
	}
	public void update(float reward, float evalTime, Role roles) {
		//for(int i=0;i<nParticles;i++) {
		//	currentParticle = i;
		//	if(getRole()==role) {
				pso.setFitness(currentParticle, reward*evalTime);
				pso.update(currentParticle); //training step
				currentParticle = (currentParticle+1)%nParticles;
		//	}
		//}
	}
	public void fromFloats(float[] skillData) {
		throw new RuntimeException("Unimplemented method");
	}
	public boolean isContinuous() {
		return false;
	}
}
