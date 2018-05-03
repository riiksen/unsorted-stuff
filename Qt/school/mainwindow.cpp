#include "mainwindow.h"
#include "ui_mainwindow.h"

MainWindow::MainWindow(QWidget *parent) :
    QMainWindow(parent),
    ui(new Ui::MainWindow) {
    ui->setupUi(this);
}

MainWindow::~MainWindow() {
    delete ui;
}

void MainWindow::paintEvent(QPaintEvent *e) {
    QPainter painter(this);

    painter.setPen(QPen(Qt::black, 4, Qt::SolidLine, Qt::RoundCap));
    painter.drawLine(10, 10, 100, 100);
}
