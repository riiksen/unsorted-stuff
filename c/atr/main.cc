#include <cstdio>
#include <cstring>
#include <windows.h>
#include <shlwapi.h>

#include "colors.h"

char *strndup(char *str, int chars) {
    char *buffer;
    int n;
    buffer = (char *)malloc(chars +1);
    if (buffer) {
        for (n = 0; ((n < chars) && (str[n] != 0)) ; n++) buffer[n] = str[n];
        buffer[n] = 0;
    }
    return buffer;
}

int main(int argc, char *argv[]) {
  if (argc == 2) {
    if (PathFileExists(argv[1])) {
      if (argv[1][strlen(argv[1])-1] == 'e' && argv[1][strlen(argv[1])-2] == 'x' && argv[1][strlen(argv[1])-3] == 'e' && argv[1][strlen(argv[1])-4] == '.') {
        printf("%s %i\n", argv[1], strlen(argv[1])-1);
        char *buf = strrchr(argv[1], '\\');
        char *amt = strndup(argv[1], strlen(argv[1])-(strlen(buf)-1));
        printf("%sFile: %s\nDir: %s\nBuf: %s%s\n", GREEN, argv[1], amt, buf, RESET); //Debug
        strcat(amt, "AMT\\application.xml");
        printf("New dir: %s\n", amt);
        if (PathFileExists(amt)) {
          FILE *app = fopen(amt, "r+");
          
          fclose(app);
        }
        else printf("%sDid you really open a adobe product with this program??? That doesn't look like a adobe program%s\n", RED, RESET);
      }
      else printf("%sDid you open a adobe product with this program??? That doesn't look like a program%s\n", RED, RESET);
      if (argv[1][strlen(argv[1])-1] == 'k' && argv[1][strlen(argv[1])-2] == 'n' && argv[1][strlen(argv[1])-3] == 'l' && argv[1][strlen(argv[1])-4] == '.') {
        printf("%sDo not open links with this program. Only .exe flie%s\n", RED, RESET); //Here will be code for checking where the symlink goes
      }
    }
    else printf("%sDid you really open a something with this program??? That doesn't look like a path%s\n", RED, RESET);
  }
  else printf("%sOpen adobe product with this program to reset trial%s\n", RED, RESET);
  return 0;
}
