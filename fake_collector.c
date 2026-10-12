#include "collector.h"
#include <fcntl.h>
#include <stdio.h>
#include <unistd.h>

#define MOCK_ARR_COUNT 10

int main(void) {
    const char *mock_arr[MOCK_ARR_COUNT] = {"0.31", "0.12", "0.83", "0.12",
                                            "0.12", "0.43", "0.23", "0.17",
                                            "0.43", "0.17"};

    int i = 0;
    while (1) {
        if (send_as_json(mock_arr[(i++) % MOCK_ARR_COUNT]) == -1) {
            printf("Error: curl\n");
            break;
        }
        printf("ITTERATION: %d\n", i);
        sleep(3);
    }
    return 0;
}
