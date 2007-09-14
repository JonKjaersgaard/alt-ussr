#define ROLE_NONE 0
#define ROLE_ANY 255
#define MAX_N_ROLES 9 /* includes role#0 */

#define ROLE_WHEEL 1
#define ROLE_LEFT_WHEEL 2
#define ROLE_RIGHT_WHEEL 3
#define ROLE_FINGER 4
#define ROLE_AXLE 5
#define ROLE_LEFT_WHEEL_REVERSE 6
#define ROLE_RIGHT_WHEEL_REVERSE 7
#define ROLE_REVERSE 8

extern unsigned char check_role_instanceof(unsigned char self_role, unsigned char test_role);
