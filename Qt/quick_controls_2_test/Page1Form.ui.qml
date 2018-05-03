import QtQuick 2.7
import QtQuick.Controls 2.0
import QtQuick.Layouts 1.0

Item {
    id: item1
    width: 360

    Button {
        id: button
        y: 238
        height: 48
        text: qsTr("Zalogój")
        anchors.right: parent.right
        anchors.rightMargin: 130
        anchors.left: parent.left
        anchors.leftMargin: 130
    }

    TextField {
        id: textField
        y: 189
        height: 43
        text: qsTr("Wprowadź swoje hasło")
        anchors.right: parent.right
        anchors.rightMargin: 75
        anchors.left: parent.left
        anchors.leftMargin: 77
        font.bold: false
        horizontalAlignment: Text.AlignHCenter
    }
    states: [
        State {
            name: "Generowanie Haseł"

            PropertyChanges {
                target: textField
                horizontalAlignment: Text.AlignHCenter
                renderType: Text.QtRendering
                visible: false
            }

            PropertyChanges {
                target: button
                visible: false
                checked: false
                checkable: false
                highlighted: false
            }
        },
        State {
            name: "Ustawienia"
            PropertyChanges {
                target: textField
                horizontalAlignment: Text.AlignHCenter
                renderType: Text.QtRendering
                visible: false
            }

            PropertyChanges {
                target: button
                visible: false
                highlighted: false
                checked: false
                checkable: false
            }
        }
    ]
}
