import QtQuick 2.7
import QtQuick.Controls 2.0
import QtQuick.Layouts 1.0

ApplicationWindow {
    visible: true
    width: 360
    height: 480
    title: qsTr("C-Pass")

    SwipeView {
        id: swipeView
        anchors.fill: parent
        currentIndex: tabBar.currentIndex

        Page1 {
            x: 0
            y: 0
            width: 360
        }

        Page {
            x: 360
            y: 0
            width: 360
            Label {
                text: qsTr("Second page")
                anchors.centerIn: parent
            }
        }
    }

    footer: TabBar {
        id: tabBar
        x: 0
        y: 0
        currentIndex: swipeView.currentIndex
        TabButton {
            text: qsTr("Bank")
        }
        TabButton {
            text: qsTr("Ustawienia")
        }
    }
}
