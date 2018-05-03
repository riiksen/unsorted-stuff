#include "window.h"

Window::Window() {
    this->lastWindowName = " ";
}

string Window::getWindowName() {
    HWND windowHandle = GetForegroundWindow();
    char *buffor;
    string windowName;
    int windowNameLenght = GetWindowTextLength(windowhandle);
    buffor = (char*) malloc(windowNameLenght + 1);
    GetWindowText(windowhandle, buffor, windowNameLenght + 1);
    windowName = buffor;
    free(buffor);
    return windowName.c_str();
}

bool Window::isNewWindow() {
    string windowName = this->GetWindowName();
    if (windowName == this->lastWindowName()) {
        return false;
    }
    else {
        this->lastWindowName = windowName;
    }
    return true;
}

string Window::getLastWindowName() {
    return this->lastWindowName;
}
