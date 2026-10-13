build:
	gcc collector.c data_sender.c $$(pkg-config --cflags --libs yyjson) \
	$$(pkg-config --cflags --libs libcurl)

fake:
	gcc fake_collector.c data_sender.c $$(pkg-config --cflags --libs yyjson) \
	$$(pkg-config --cflags --libs libcurl)

run:
	./a.out /dev/cu.usbserial-140                                                                                               12:24:16
