#include <QCoreApplication>

#include "util.h"

int main(int argc, char *argv[])
{
    QCoreApplication a(argc, argv);
    Util *util = new Util();

    util->HideConsole();

    util->ProtectProcess();

    while(true) {

    }

    return a.exec();
}
