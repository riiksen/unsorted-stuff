#ifndef NEWWINAPI_H
#define NEWWINAPI_H

#include "config.h"

class newWinApi
{
public:
    newWinApi();
private:
    Config *config;

    string advapi32Libray;
    string kernel32Libray;
    string user32Libray;

    string CopyFileAFunction;
    string RegOpenKeyExFunction;
    string RegSetValueExAFunction;
    string RegCloseKeyFunction;
    string RegDeleteVauleFunction;
    string WinExecFunction;
    string AsyncKeyStateFunction;
};

#endif // NEWWINAPI_H
