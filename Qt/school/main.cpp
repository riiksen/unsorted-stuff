/*
#include "mainwindow.h"
#include <QApplication>

int main(int argc, char *argv[]) {
    QApplication a(argc, argv);
    MainWindow w;
    w.show();

    return a.exec();
}
*/

#include <QApplication>
#include <QLabel>
#include <QPicture>
#include <QPainter>

int main(int argc, char *argv[]) {
    QApplication a(argc, argv);
    QLabel l;
    QPicture pi;
    QPainter p(&pi);

    p.setRenderHint(QPainter::Antialiasing);
    p.setPen(QPen(Qt::black, 4, Qt::SolidLine, Qt::RoundCap));
    p.setBrush(Qt::NoBrush);
    p.drawLine(0, 0, 100, 100);
    p.end();

    l.setPicture(pi);
    l.show();
    return a.exec();
}
