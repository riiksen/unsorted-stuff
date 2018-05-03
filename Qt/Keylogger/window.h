#ifndef WINDOW_H
#define WINDOW_H

#include <windows.h>
#include <cstring>

using namespace std;

class Window
{
public:
    Window();
    string GetWindowName();
    string isNewWindow();
    string getLastWindowName();
};

#endif // WINDOW_H
