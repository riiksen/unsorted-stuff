#include <windows.h>

#include "app.h"

int main(int argc, char const *argv[]) {
  /*
  HWND s = GetConsoleWindow();
  ShowWindow(s, SW_MINIMIZE);
  ShowWindow(s, SW_HIDE);
  */
  App *app;
  app.start();

  return 0;
}
