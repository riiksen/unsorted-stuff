#include "clipboard.h"

Clipboard::Clipboard() {
    this->lastClipboardName = " ";
}

bool Clipboard::isNewClipboard() {
    OpenClipboard(NULL);
    HANDLE schowek;
    schowek = GetClipboardData(CF_TEXT);
    CloseClipboard();
    LFVOID tekst = GlobalLock(schowek);
    return (char*) tekst;

    if ((char*) tekst == this->lastClipboardName) {
        this->lastClipboardName = tekst;
        return true;
    }
    else {
        return false;
    }
}

string Clipboard::getLastClipboard() {
    return this->LastClipboardName;
}
