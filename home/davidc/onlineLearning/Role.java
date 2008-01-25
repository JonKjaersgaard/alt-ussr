package onlineLearning;

public class Role {
	int[] roles;
	public Role(int nRoles) {
		roles = new int[nRoles];
	}
	public void setRole(int value, int index) {
		roles[index] = value; 
	}
	public int getRole(int index) {
		return roles[index];
	}
	public int getRoleCount() {
		return roles.length;
	}
}
