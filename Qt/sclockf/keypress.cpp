#include "keypress.h"
void skp(BYTE param1, BYTE param2)
{
    BYTE nkey1 = param1, nkey2 = param2;
    keybd_event(nkey1, nkey2, 0, 0);
}
