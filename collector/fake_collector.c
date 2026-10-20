#include "collector.h"
#include <fcntl.h>
#include <stdio.h>
#include <unistd.h>

#define MOCK_ARR_COUNT 10

int main(void) {
    const char *mock_arr[MOCK_ARR_COUNT] = {"1.31", "0.12", "0.83", "0.12",
                                            "1.12", "0.43", "1.23", "0.17",
                                            "0.43", "0.17"};

    int i = 0;
    while (1) {
        if (send_as_json(mock_arr[(i++) % MOCK_ARR_COUNT]) == -1) {
            printf("Error: curl\n");
            break;
        }
        sleep(3);
    }
    return 0;
}
