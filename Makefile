build:
	gcc collector.c data_sender.c $$(pkg-config --cflags --libs yyjson) \
	$$(pkg-config --cflags --libs libcurl)

fake:
	gcc fake_collector.c data_sender.c $$(pkg-config --cflags --libs yyjson) \
	$$(pkg-config --cflags --libs libcurl)
