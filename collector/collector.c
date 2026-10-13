#include "collector.h"
#include <fcntl.h>
#include <stdio.h>
#include <termios.h>
#include <unistd.h>

#define BUF_SIZE 16

int main(int argc, char *argv[]) {
    if (argc < 2) {
        fprintf(stderr, "Error: not enough arguments");
        return 1;
    }

    // file must be /dev/cu.usbserial-***... or /dev/tty.usbserial-***.. on
    // linux systems
    int fd = open(argv[1], O_RDONLY | O_NOCTTY);

    if (fd == -1) {
        perror("open");
        return 1;
    }

    struct termios serial_port_settings;
    tcgetattr(fd, &serial_port_settings);

    cfsetispeed(&serial_port_settings, B9600);
    cfsetospeed(&serial_port_settings, B9600);
    tcsetattr(fd, TCSANOW, &serial_port_settings);

    char buf[BUF_SIZE];
    int linepos = 0;
    char c;

    while (1) {
        int n = read(fd, &c, 1);
        if (n <= 0)
            continue;

        if (c == '\n') {
            buf[linepos] = '\0';
            if (send_as_json(buf) == -1) {
                fprintf(stderr, "Error: can't init curl");
                break;
            }
            linepos = 0;
        } else if (c != '\r' && linepos < BUF_SIZE - 1) {
            buf[linepos++] = c;
        }
    }
    return 0;
}
