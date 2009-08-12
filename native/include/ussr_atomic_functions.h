#ifndef USSR_ATOMIC_FUNCTIONS_H_
#define USSR_ATOMIC_FUNCTIONS_H_

#include "stdint.h"

typedef uint8_t __nesc_atomic_t;
inline void __nesc_enable_interrupt();
inline void __nesc_disable_interrupt();
__nesc_atomic_t __nesc_atomic_start(void);
void __nesc_atomic_end(__nesc_atomic_t original_SREG);

#endif /*USSR_ATOMIC_FUNCTIONS_H_*/

