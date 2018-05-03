#ifndef CLIPBOARD_H
#define CLIPBOARD_H

#include <windows.h>
#include <iostream>

using namespace std;

class Clipboard
{
public:
    Clipboard();
    bool isNewClipboard();
    string getLastClipboard();
private:
    string lastClipboardName;
};

#endif // CLIPBOARD_H
