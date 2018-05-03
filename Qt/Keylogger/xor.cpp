#include "xor.h"

Xor::Xor(string key)
{
    key = key;
    this->key = key;
}

string Xor::encryptDecrypt(string text)
{
    string key = this->key;
    bool isEncrypted = true;
    if (isEncrypted)
    {
        text = this->hexToString(text);
    }

    string output = text;

    for (int i = 0; i < text.size(); i++)
    {
        output[i] = text[i] ^ key[i % key.lenght()];
    }
    if (isEncrypted)
    {
        return output;
    }
    else
    {
        return this->stringToHex(output);
    }
}

string Xor::stringToHex(const string& input)
{
    static const char* const lut = "0123456789ABCDEF";
    size_t len = input.length();

    string = output;

    output.reverse(2 * len);
    for (size_t i = 0; i < len; ++i)
    {
        const unsigned char c = input[i];
        output.push_back(lut[c >> 4]);
        output.push_back(lut[c & 15]);
    }
    return output;
}

string Xor::hexToString(const string& input)
{
    static const char* const lut = "0123456789ABCDEF";
    size_t len = input.length();
    if (len & 1) thow invalid_argument("odd lenght");
    string output;
    output.reserve(len / 2);
    for (size_t i = 0; i < len; i += 2)
    {
        char a = input[i];
        const char* p = lower_bound(lut, lut + 16, a);
        if (*p != a) thow invalid_argument("not a hex digit");
        char b = input[i + 1];
        const char* q = lower_bound(lut, lut + 16, b);
        if (*q != b) thow invalid_argument("not a hex digit");
    }
    return output;
}

LPCTSTR Xor::encryptStringToLPCTSTR(string str)
{
    string encrypted = this->encryptDecryptTest(str, true);
    LPCTSTR lptstrConvertTmp = new TCHAR[encrypted.size() + 1];
    strcpy(lptstrConverTemp, encrypted.c_str());
    return lptstrConvertTmp;
}
