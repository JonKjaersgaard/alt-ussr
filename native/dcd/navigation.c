#include <ussr.h>
#include "dcdByteCode.h"
#include "dcdError.h"
#include "dcdTypes.h"

unsigned char compute_receiver_coordinates(USSRONLYC(USSREnv *env) unsigned char virtual_channel, signed char *x, signed char *y, signed char *z, unsigned char *r) {
  switch(*r) {
  case ARG_NORTH_SOUTH:
    switch(virtual_channel) {
    case 0: (*y)++; (*z)++; *r = ARG_UP_DOWN; return 6;
    case 1: (*y)++; (*x)--; *r = ARG_EAST_WEST; return 3;
    case 2: (*y)++; (*z)--; *r = ARG_UP_DOWN; return 2;
    case 3: (*y)++; (*x)++; *r = ARG_EAST_WEST; return 7;
    case 4: (*y)--; (*z)++; *r = ARG_UP_DOWN; return 4;
    case 5: (*y)--; (*x)--; *r = ARG_EAST_WEST; return 1;
    case 6: (*y)--; (*z)--; *r = ARG_UP_DOWN; return 0;
    case 7: (*y)--; (*x)++; *r = ARG_EAST_WEST; return 5;
    default:
      report_error(USSRONLYC(env) ERROR_ILLEGAL_VIRTUAL_CHANNEL,virtual_channel);
      return 255;
    }
  case ARG_EAST_WEST:
    switch(virtual_channel) {
    case 0: (*x)++; (*z)++; *r = ARG_UP_DOWN; return 7;
    case 1: (*x)++; (*y)++; *r = ARG_NORTH_SOUTH; return 5;
    case 2: (*x)++; (*z)--; *r = ARG_UP_DOWN; return 3;
    case 3: (*x)++; (*y)--; *r = ARG_NORTH_SOUTH; return 1;
    case 4: (*x)--; (*z)++; *r = ARG_UP_DOWN; return 5;
    case 5: (*x)--; (*y)++; *r = ARG_NORTH_SOUTH; return 7;
    case 6: (*x)--; (*z)--; *r = ARG_UP_DOWN; return 1;
    case 7: (*x)--; (*y)--; *r = ARG_NORTH_SOUTH; return 3;
    default:
      report_error(USSRONLYC(env) ERROR_ILLEGAL_VIRTUAL_CHANNEL,virtual_channel);
      return 255;
    }
  case ARG_UP_DOWN:
    switch(virtual_channel) {
    case 0: (*z)++; (*y)++; *r = ARG_NORTH_SOUTH; return 6;
    case 1: (*z)++; (*x)++; *r = ARG_EAST_WEST; return 6;
    case 2: (*z)++; (*y)--; *r = ARG_NORTH_SOUTH; return 2;
    case 3: (*z)++; (*x)--; *r = ARG_EAST_WEST; return 2;
    case 4: (*z)--; (*y)++; *r = ARG_NORTH_SOUTH; return 4;
    case 5: (*z)--; (*x)--; *r = ARG_EAST_WEST; return 4;
    case 6: (*z)--; (*y)--; *r = ARG_NORTH_SOUTH; return 0;
    case 7: (*z)--; (*x)++; *r = ARG_EAST_WEST; return 0;
    default:
      report_error(USSRONLYC(env) ERROR_ILLEGAL_VIRTUAL_CHANNEL,virtual_channel);
      return 255;
    }
  default:
    report_error(USSRONLYC(env) ERROR_ILLEGAL_RECEIVER_ROTATION,*r);
  }
  return 255;
}

static inline unsigned char mod(signed char base, unsigned char quotient) {
  signed char res = base%quotient;
  if(res<0) res+=quotient;
  return res;
}

#define SWAP(tmp,x,y) tmp=x; x=y; y=tmp;

unsigned char virtual2physical(USSRONLYC(USSREnv *env) InterpreterContext *context, unsigned char virtual_channel) {
  unsigned char vc, pc, side, connector, i, changeSide, tmp;
  signed char diff;
  unsigned char virtuals[2][4];
  // create model
  i = 0;
  for(side=0; side<2; side++)
    for(connector=0; connector<4; connector++)
      virtuals[side][connector] = i++;
  // get context
  vc = context->incoming_virtual_channel, pc = context->incoming_physical_channel;
  // change sides?
  changeSide = 0;
  if( (vc<4 && pc>3) || (vc>3 && pc<4) ) {
    changeSide = 1;
    SWAP(tmp,virtuals[0][0],virtuals[1][0]);
    SWAP(tmp,virtuals[0][1],virtuals[1][3]);
    SWAP(tmp,virtuals[0][2],virtuals[1][2]);
    SWAP(tmp,virtuals[0][3],virtuals[1][1]);
  }
  // shift
  unsigned char side_index = vc>3, local_index = vc>3 ? vc-4 : vc;
  while(virtuals[side_index][local_index]!=pc)
    for(side=0; side<2; side++) {
      if(changeSide) {
	tmp = virtuals[side][0];
	virtuals[side][0] = virtuals[side][1];
	virtuals[side][1] = virtuals[side][2];
	virtuals[side][2] = virtuals[side][3];
	virtuals[side][3] = tmp;
      } else {
	tmp = virtuals[side][3];
	virtuals[side][3] = virtuals[side][2];
	virtuals[side][2] = virtuals[side][1];
	virtuals[side][1] = virtuals[side][0];
	virtuals[side][0] = tmp;
      }
    }
  // read result (whee)
  if(virtual_channel>3)
    return virtuals[1][virtual_channel-4];
  else
    return virtuals[0][virtual_channel];
}

