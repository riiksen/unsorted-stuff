using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using System.Net.Sockets;
using System.Net;

namespace WindowsFormsApp1
{
    public partial class Form1 : Form
    {
        public Form1()
        {
            InitializeComponent();
        }
        public void connect(String hostname, String msg)
        {
            Int32 port = 13750;
            TcpClient client = new TcpClient(hostname, port);
            Byte[] data = System.Text.Encoding.ASCII.GetBytes(msg);
            NetworkStream stream = client.GetStream();
            stream.Write(data, 0, data.Length);
            data = new Byte[256];
            String responseData = String.Empty;
            Int32 bytes = stream.Read(data, 0, data.Length);
            responseData = System.Text.Encoding.ASCII.GetString(data, 0, bytes);

        }

        private void send_Click(object sender, EventArgs e)
        {
            connect(ip.Text, message.Text);

        }
    }
}
