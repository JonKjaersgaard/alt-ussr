/* Instruction arguments */
#define ARG_EAST_WEST 1
#define ARG_NORTH_SOUTH 2
#define ARG_UP_DOWN 3
#define ARG_EAST 4
#define ARG_WEST 5
#define ARG_NORTH 6
#define ARG_SOUTH 7
#define ARG_UP 8
#define ARG_DOWN 9

/* Event types */
#define EVENT_PROXIMITY_0 0
#define EVENT_PROXIMITY_1 1
#define EVENT_PROXIMITY_2 2
#define EVENT_PROXIMITY_3 3
#define EVENT_PROXIMITY_4 4
#define EVENT_PROXIMITY_5 5
#define EVENT_PROXIMITY_6 6
#define EVENT_PROXIMITY_7 7

/* Generic instructions */
#define INS_CENTER_POSITION_EW    1
#define INS_CENTER_POSITION_NS    2
#define INS_CENTER_POSITION_UD    3
#define INS_CONNECTED             4
#define INS_CONNECTED_SIZEOF      5
#define INS_EQUALS_0              6
#define INS_EQUALS_1              7
#define INS_EQUALS_2              8
#define INS_EQUALS_3              9
#define INS_EQUALS_4             10
#define INS_CONNECTED_DIR        11
#define INS_CONNECTED_DIR_SIZEOF 12
#define INS_END_TERMINATE        13
#define INS_END_REPEAT           14
#define INS_IF_FALSE_GOTO        15
#define INS_GOTO                 16
#define INS_SET_ROLE_NOTIFY      17
#define INS_MIGRATE_CONTINUE     18
#define INS_COORD_X              19
#define INS_COORD_Y              20
#define INS_COORD_Z              21
#define INS_GREATER              22
#define INS_LESSER               23
#define INS_COORD_X_GREATER      24
#define INS_COORD_X_LESSER       25
#define INS_COORD_Y_GREATER      26
#define INS_COORD_Y_LESSER       27
#define INS_COORD_Z_GREATER      28
#define INS_COORD_Z_LESSER       29
#define INS_HANDLE_EVENT         30
#define INS_SEND_COMMAND         31
#define INS_CLEAR_EVENT          32
#define INS_EVAL_COMMAND         33
#define INS_HAS_ROLE             34
#define INS_CONNECTED_ROLE       35
#define INS_CONNECTED_DOWN_ROLE  36
#define INS_CONNECTED_UP_ROLE    37
#define INS_CONNECTED_EAST_ROLE  38
#define INS_CONNECTED_WEST_ROLE  39
#define INS_CONNECTED_NORTH_ROLE 40
#define INS_CONNECTED_SOUTH_ROLE 41
#define INS_INSTALL_COMMAND      42
#define INS_SLEEP                43
#define INS_DISABLE_EVENT        44
#define INS_ENABLE_EVENT         45
#define INS_CLEAR_COMMAND_QUEUE  46
/* (unorganized) */
#define INS_IF_TRUE_GOTO         47
#define INS_SLEEP_ROTATIONS      48
#define INS_SIZEOF               49
#define INS_NEGATE               50
#define INS_ACTIVATE_DEBUG       51
#define INS_NOP                 127

/* MSB reserved for constant-push operations */
#define MK_INS_CONSTANT(x) (128|x)

/*
  (defun number-instructions (start end)
    (while (< start end)
      (insert (number-to-string start))
      (forward-char -2)
      (next-line 1)
      (setq start (1+ start))))
*/
