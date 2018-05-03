#include <stdio.h>
#include <string.h>

/*
bool frequeny(char *str) {

}
*/

int check_lic(char *lic) {
  int sum = 0;
  for (size_t i = 0; i < strlen(lic); ++i) {
    if ((int)lic[i] % 2 == 0) sum += ((int)lic[i] * 5 - 24) / 5;
    else sum += ((int)lic[i] / 5 - 1) * 5;
  }
  printf("%i\n", sum);
  if (lic[5] != 45 || lic[11] != 45 || lic[17] != 45 || lic[23] != 45 || strlen(lic) != 29) return 2;
  else return sum != 2678;
}

int main(int argc, const char **argv) {
  if (argc == 2) {
    int lic = check_lic((char *)argv[1]);
    if (lic) {
      if (lic == 2) puts("Incorrect format");
      else puts("NOPE");
    }
    else puts("Correct license");
  }
  else printf("Usage: %s <license>\n", *argv);
  return 0;
}
