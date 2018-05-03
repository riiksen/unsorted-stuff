#include <stdio.h>
#include <string.h>

int check_lic(char lic[16]) {
  int s = 0;
  for (int i = 0; i < strlen(lic); i++) {
    s += (int)lic[i];
  }
  if (lic[4] == '-' && lic[8] == '-' && strlen(lic) == 15) {
    if (s == 1288) {
      return 0;
    }
  }
  else {
    return 2;
  }
  return 1;
}

int main(int argc, char **argv) {
  if (argc != 2) {
    printf("Usage: %s <license>\n", argv[0]);
    return 0;
  }
  else {
    int x = check_lic(argv[1]);
    switch (x) {
      case 0:
        printf("Correct license\n");
      break;
      case 2:
        printf("Incorrect format\n");
      break;
      default:
        printf("NOPE\n");
    }
  }
  return 0;
}
