#ifndef XOR_H
#define XOR_H

#include <iostream>
#include <windows.h>

using namespace std;

class Xor
{
public:
    Xor(string key);
    string encryptDecrypt(string text);\
    string stringToHex(const string& input);
    string hexToString(const string& input);
    LPCTSTR encryptStringToLPCTSTR(string str);
private:
    string key;
};

#endif // XOR_H
