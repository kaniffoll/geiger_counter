#include "collector.h"
#include <stdio.h>

int read_api_key(char *res) {
    FILE *api_file = fopen(".env", "r");
    int out = 1;

    if (api_file == NULL) {
        out = 0;
    } else {
        int ch;
        while ((ch = getc(api_file)) != '=' && ch != EOF)
            ;

        int i = 0;
        while (i < KEY_SIZE && (ch = getc(api_file)) != '\n' && ch != EOF)
            res[i++] = (char)ch;
        res[i] = '\0';

        fclose(api_file);
    }
    return out;
}
