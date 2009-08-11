#include "ussr_atomic_functions.h"

/* Enables interrupts. */
inline void __nesc_enable_interrupt() {
	/*     sei(); */
}
/* Disables all interrupts. */
inline void __nesc_disable_interrupt() {
	/*     cli(); */
}

/* Saves current interrupt mask state and disables interrupts. */
inline __nesc_atomic_t 
__nesc_atomic_start(void) 
{
/*     __nesc_atomic_t result = 0; */
/*     __nesc_disable_interrupt(); */
/*     asm volatile("" : : : "memory"); /\* ensure atomic section effect visibility *\/ */
/*     return result; */
  return 0;
}

/* Restores interrupt mask to original state. */
inline void 
__nesc_atomic_end(__nesc_atomic_t original_SREG) 
{
/*   asm volatile("" : : : "memory"); /\* ensure atomic section effect visibility *\/ */
/*   SREG = original_SREG; */
}
