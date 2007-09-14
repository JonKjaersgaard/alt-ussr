#include <avr/interrupt.h>
#include <api/API.h>
#include <stdlib.h>

extern void activate();

int getRole() { return 0; }

int main(void)
{
  setup();
  home();
  activate();
}

void disable_interrupts() {
  cli();
}

void enable_interrupts() {
  sei();
}
