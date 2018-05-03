#include <QApplication>
#include <QLabel>
#include <QPicture>
#include <QPainter>

#include <stdio.h>
#include <stdlib.h>

int main(int argc, char **argv) {
  QApplication a(argc, argv);
  QLabel l;
  QPicture pi;
  QPainter p(&pi);

  p.setRenderHint(QPainter::Antialiasing);
  p.setPen(QPen(Qt::black, 12, Qt::DashDotLine, Qt::RoundCap));
  p.drawLine(0, 0, 200, 200);
  p.end();

  l.setPicture(pi);
  l.show();
  return a.exec();
}