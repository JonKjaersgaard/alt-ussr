#include "navigation.h"
#include "dcdBytecode.h"
#include <stdio.h>

unsigned char getJointPosition() { return 0; }
int getRole() { return 0; }
unsigned char isOtherConnectorNearby(unsigned char ignore) { return 0; }
char sendMessage(unsigned char *message, unsigned char messageSize, unsigned char connector) { return 0; }

void breakpoint() { ; }

#define N_VIRTUAL2PHYSICAL 82
typedef struct _V2Pdata {
  unsigned char context_vc, context_pc, in, out;
} V2Pdata;

V2Pdata virtual2physical_data[N_VIRTUAL2PHYSICAL] = {
  { 0, 2, 2, 0 },
  { 0, 2, 3, 1 },
  { 0, 0, 2, 2 },
  { 0, 0, 3, 3 },
  { 0, 0, 4, 4 },
  { 0, 0, 5, 5 },
  { 0, 0, 6, 6 },
  { 0, 0, 7, 7 },
  { 2, 2, 0, 0 },
  { 2, 2, 1, 1 },
  { 2, 2, 2, 2 },
  { 2, 2, 3, 3 },
  { 2, 2, 4, 4 },
  { 2, 2, 5, 5 },
  { 2, 2, 6, 6 },
  { 2, 2, 7, 7 },
  { 6, 6, 0, 0 },
  { 6, 6, 1, 1 },
  { 6, 6, 2, 2 },
  { 6, 6, 3, 3 },
  { 6, 6, 4, 4 },
  { 6, 6, 5, 5 },
  { 6, 6, 6, 6 },
  { 6, 6, 7, 7 },
  { 0, 2, 0, 2 },
  { 0, 2, 1, 3 },
  { 0, 2, 2, 0 },
  { 0, 2, 3, 1 },
  { 0, 2, 4, 6 },
  { 0, 2, 5, 7 },
  { 0, 2, 6, 4 },
  { 0, 2, 7, 5 },
  { 2, 0, 0, 2 },
  { 2, 0, 1, 3 },
  { 2, 0, 2, 0 },
  { 2, 0, 3, 1 },
  { 2, 0, 4, 6 },
  { 2, 0, 5, 7 },
  { 2, 0, 6, 4 },
  { 2, 0, 7, 5 },
  { 2, 5, 0, 7 },
  { 2, 5, 1, 6 },
  { 2, 5, 2, 5 },
  { 2, 5, 3, 4 },
  { 2, 5, 4, 3 },
  { 2, 5, 5, 2 },
  { 2, 5, 6, 1 },
  { 2, 5, 7, 0 },
  { 6, 1, 0, 7 },
  { 6, 1, 1, 6 },
  { 6, 1, 2, 5 },
  { 6, 1, 3, 4 },
  { 6, 1, 4, 3 },
  { 6, 1, 5, 2 },
  { 6, 1, 6, 1 },
  { 6, 1, 7, 0 },
  { 4, 5, 0, 1 },
  { 4, 5, 1, 2 },
  { 4, 5, 2, 3 },
  { 4, 5, 3, 0 },
  { 4, 5, 4, 5 },
  { 4, 5, 5, 6 },
  { 4, 5, 6, 7 },
  { 4, 5, 7, 4 },
  { 0, 4, 0, 4 },
  { 0, 4, 1, 7 },
  { 0, 4, 2, 6 },
  { 0, 4, 3, 5 },
  { 0, 4, 4, 0 },
  { 0, 4, 5, 3 },
  { 0, 4, 6, 2 },
  { 0, 4, 7, 1 },
  { 7, 0, 0, 7 },
  { 7, 0, 1, 6 },
  { 7, 0, 2, 5 },
  { 7, 0, 3, 4 },
  { 7, 0, 4, 3 },
  { 7, 0, 5, 2 },
  { 7, 0, 6, 1 },
  { 7, 0, 7, 0 },
  // Specific test cases
  { 3, 6, 7, 2 },
  { 1, 0, 2, 1 }
};

void report_error(unsigned char x, unsigned char y) {
  printf("Error reported: %d.%d\n", x, y);
}

void v2p_assert(InterpreterContext *context, unsigned char virtual, unsigned char physical) {
  unsigned char result = virtual2physical(context,virtual);
  if(physical!=result) printf("Error for virtual2physical (v=%d, p=%d): %d should yield %d but gave %d\n",
			     context->incoming_virtual_channel,
			     context->incoming_physical_channel,
			     virtual, physical, result);
}
							
void test_virtual2physical() {
  InterpreterContext context;
  int index;
  unsigned char c;
  for(index=0; index<N_VIRTUAL2PHYSICAL; index++) {
    context.incoming_virtual_channel = virtual2physical_data[index].context_vc;
    context.incoming_physical_channel = virtual2physical_data[index].context_pc;
    v2p_assert(&context, virtual2physical_data[index].in, virtual2physical_data[index].out);
  }
}

typedef struct _CRCData {
  unsigned char in_ch;
  signed char in_x, in_y, in_z;
  signed char in_r;
  unsigned char out_ch;
  signed char out_x, out_y, out_z;
  unsigned char out_r;
} CRCData;

#define N_CRC 7
CRCData crc_data[N_CRC] = {
  { 2, 0, 0, 0, ARG_NORTH_SOUTH, /* */ 2, 0, 1, -1, ARG_UP_DOWN    },
  { 1, 0, 0, 0, ARG_NORTH_SOUTH, /* */ 3, -1, 1, 0, ARG_EAST_WEST  },
  { 3, 0, 1, -1, ARG_UP_DOWN,    /* */ 2, -1, 1, 0, ARG_EAST_WEST  },
  { 2, 0, 1, -1, ARG_UP_DOWN,    /* */ 2, 0, 0, 0, ARG_NORTH_SOUTH },
  { 3, -1, 1, 0, ARG_EAST_WEST,  /* */ 1, 0, 0, 0, ARG_NORTH_SOUTH },
  { 2, -1, 1, 0, ARG_EAST_WEST,  /* */ 3, 0, 1, -1, ARG_UP_DOWN    },
  { 7, -1, 1, 0, ARG_EAST_WEST,  /* */ 3, -2, 0, 0, ARG_NORTH_SOUTH }
};

void crc_uassert(CRCData *data, char *name, unsigned char whatwegot, unsigned char whatwewant) {
  if(whatwegot!=whatwewant)
    printf("Error for CRC: ch=%d x=%d y=%d z=%d r=%d: %s computed %d != wanted %d\n",
	   data->in_ch, data->in_x, data->in_y, data->in_z, data->in_r, name, whatwegot, whatwewant);
}

void crc_sassert(CRCData *data, char *name, signed char whatwegot, signed char whatwewant) {
  if(whatwegot!=whatwewant)
    printf("Error for CRC: ch=%d x=%d y=%d z=%d r=%d: %s computed %d != wanted %d\n",
	   data->in_ch, data->in_x, data->in_y, data->in_z, data->in_r, name, whatwegot, whatwewant);
}

void test_compute_receiver_coordinates() {
  unsigned char channel;
  signed char x, y, z;
  unsigned char r;
  unsigned char out;
  int index;
  for(index=0;index<N_CRC;index++) {
    channel = crc_data[index].in_ch;
    x = crc_data[index].in_x;
    y = crc_data[index].in_y;
    z = crc_data[index].in_z;
    r = crc_data[index].in_r;
    out = compute_receiver_coordinates(channel,&x,&y,&z,&r);
    crc_uassert(crc_data+index,"channel", out, crc_data[index].out_ch);
    crc_sassert(crc_data+index,"x", x, crc_data[index].out_x);
    crc_sassert(crc_data+index,"y", y, crc_data[index].out_y);
    crc_sassert(crc_data+index,"z", z, crc_data[index].out_z);
    crc_uassert(crc_data+index,"r", r, crc_data[index].out_r);
  }
}

int main(int argc, char *argv[]) {
  test_virtual2physical();
  printf("virtual2physical test complete\n");
  test_compute_receiver_coordinates();
  printf("compute_receiver_coordinates test complete\n");
}

void old_foo() {  
  InterpreterContext context;
  unsigned char c;
  for(c=0; c<8; c++)
    printf("v=%d, p=%d: %d becomes %d\n", context.incoming_virtual_channel, context.incoming_physical_channel, c, virtual2physical(&context, c));
  context.incoming_virtual_channel = 2;
  context.incoming_physical_channel = 2;
  for(c=0; c<8; c++)
    printf("v=%d, p=%d: %d becomes %d\n", context.incoming_virtual_channel, context.incoming_physical_channel, c, virtual2physical(&context, c));
  context.incoming_virtual_channel = 6;
  context.incoming_physical_channel = 6;
  for(c=0; c<8; c++)
    printf("v=%d, p=%d: %d becomes %d\n", context.incoming_virtual_channel, context.incoming_physical_channel, c, virtual2physical(&context, c));
  context.incoming_virtual_channel = 0;
  context.incoming_physical_channel = 2;
  breakpoint();
  for(c=0; c<8; c++)
    printf("v=%d, p=%d: %d becomes %d\n", context.incoming_virtual_channel, context.incoming_physical_channel, c, virtual2physical(&context, c));
  context.incoming_virtual_channel = 2;
  context.incoming_physical_channel = 0;
  for(c=0; c<8; c++)
    printf("v=%d, p=%d: %d becomes %d\n", context.incoming_virtual_channel, context.incoming_physical_channel, c, virtual2physical(&context, c));
  context.incoming_virtual_channel = 2;
  context.incoming_physical_channel = 5;
  for(c=0; c<8; c++)
    printf("v=%d, p=%d: %d becomes %d\n", context.incoming_virtual_channel, context.incoming_physical_channel, c, virtual2physical(&context, c));
  context.incoming_virtual_channel = 6;
  context.incoming_physical_channel = 1;
  for(c=0; c<8; c++)
    printf("v=%d, p=%d: %d becomes %d\n", context.incoming_virtual_channel, context.incoming_physical_channel, c, virtual2physical(&context, c));
  context.incoming_virtual_channel = 4;
  context.incoming_physical_channel = 5;
  for(c=0; c<8; c++)
    printf("v=%d, p=%d: %d becomes %d\n", context.incoming_virtual_channel, context.incoming_physical_channel, c, virtual2physical(&context, c));
}
