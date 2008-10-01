CC=gcc
CFLAGS=-Wall
LIBFLAGS=
#-L. -lnsl -lresolv 
#-L. -L../lib -lASE
APPLICATION=-o appX
FULLSOURCE=client.c

SOURCES=$(notdir $(FULLSOURCE))
OBJECTS=$(SOURCES:.c=.o)


all: $(FULLSOURCE) $(OBJECTS)
	$(CC) $(CFLAGS) $(FULLSOURCE) $(LIBFLAGS) $(APPLICATION)

.PHONY: clean
clean:
	rm -f *.o *.out *.gch *.a
	
