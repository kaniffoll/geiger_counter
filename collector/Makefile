CU ?= /dev/cu.usbserial-140

build:
	gcc collector.c data_sender.c $$(pkg-config --cflags --libs yyjson) \
	$$(pkg-config --cflags --libs libcurl)

fake:
	gcc fake_collector.c data_sender.c $$(pkg-config --cflags --libs yyjson) \
	$$(pkg-config --cflags --libs libcurl)

run:
	./a.out $(CU) 

fake_run:
	./a.out
