#include <cstdio>
#include <cstring>
#include <cstdlib>

#include "colors.h"

int main(int argc, char const *argv[]) {
  FILE *file = fopen("AMT/application.xml", "r+");
  char *line = NULL;
  size_t len = 0;
  char* trial = NULL;
  int pos;
  char *trialnum;
  if (file == NULL) printf("Error with opening file");
  while ((getline(&line, &len, file)) != -1) {
    trial = strstr(line, "<Data key=\"TrialSerialNumber\">");
    if (trial != NULL) {
      pos = abs(line-trial) + 30;
      printf("Found on pos : %i :: %s", pos, line);
      int n = 0;
      for (size_t i = pos; i < strlen(line) - 15 - 1; i++) {
        trialnum[n] = line[i];
        n++;
      }
    }
    //printf("%s", line);
  }
  free(line);
  fclose(file);
  return 0;
}
