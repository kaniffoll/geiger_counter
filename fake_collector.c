#include "collector.h"
#include <fcntl.h>
#include <stdio.h>
#include <unistd.h>

#define MOCK_ARR_COUNT 10

int main(void) {
    const char *mock_arr[MOCK_ARR_COUNT] = {"12.3", "32.1", "85.0", "49.3",
                                            "33.2", "03.2", "24.0", "43.0",
                                            "35.0", "11.1"};

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
