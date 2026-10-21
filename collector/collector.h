#ifndef COLLECTOR_H
#define COLLECTOR_H
#include <curl/curl.h>
#define KEY_SIZE 64
short send_as_json(const char *s);
int read_api_key(char *res);
#endif
