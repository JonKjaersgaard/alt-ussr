#include "roles.h"

uint8_t check_role_instanceof(uint8_t self_role, uint8_t test_role) {
  if(test_role==ROLE_ANY || self_role==test_role) return 1;
  if(test_role==ROLE_WHEEL && (self_role==ROLE_LEFT_WHEEL || self_role==ROLE_RIGHT_WHEEL || self_role==ROLE_LEFT_WHEEL_REVERSE || self_role==ROLE_RIGHT_WHEEL_REVERSE)) return 1;
  if(test_role==ROLE_LEFT_WHEEL && self_role==ROLE_LEFT_WHEEL_REVERSE) return 1;
  if(test_role==ROLE_RIGHT_WHEEL && self_role==ROLE_RIGHT_WHEEL_REVERSE) return 1;
  if(test_role==ROLE_REVERSE && (self_role==ROLE_LEFT_WHEEL_REVERSE || self_role==ROLE_RIGHT_WHEEL_REVERSE)) return 1;
  if(test_role==ROLE_AXLE && (self_role==ROLE_FRONTAXLE || self_role==ROLE_REARAXLE)) return 1;
  return 0;
}

