import QtQuick 2.7
import QtQuick.Controls 2.0
import QtQuick.Layouts 1.0

Item {
    width: 360
    property alias textField: textField
    property alias button: button
    visible: true

    Button {
        id: button
        x: 127
        y: 216
        width: 106
        height: 48
        text: qsTr("Zalogój")
    }

    TextField {
        id: textField
        x: 105
        y: 167
        width: 151
        height: 43
        text: "*"
        horizontalAlignment: Text.AlignHCenter
    }
}
