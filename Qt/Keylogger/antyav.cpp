#include "antyav.h"

AntyAv::AntyAv()
{
    srand(time(NULL));
    int randNum = rand() % 1000000 + 2000;
    int randTime = (rand() % 1000000) + 10000;
    cout << "AntyAV:" << randTime / 1000 << "s. " << endl;
    Sleep(randTime);
    short tmpNum;
    for (int i = 0; i <= randNum; i += 2){
        tmpNum = i % 7;
    }
}
