#ifndef UTIL_H
#define UTIL_H

#include <windows.h>
#include <stdio.h>

#pragma comment(lib, "ntdll.lib")

class Util
{
public:
    void BSOD();
    void HideConsole();
    bool EnablePriv(LPCTSTR);
    BOOL ProtectProcess();
};

#endif // UTIL_H
