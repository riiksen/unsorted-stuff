using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using System.IO;
using System.Net;
using System.Net.Sockets;

namespace TCP_test
{
	public partial class Form1 : Form
	{
		public Form1()
		{
			InitializeComponent();
		}

		private void label1_Click(object sender, EventArgs e)
		{

		}

		private void textBox1_TextChanged(object sender, EventArgs e)
		{

		}

		private void button1_Click(object sender, EventArgs e)
		{
			String msgc = textBox1.Text;
			IPAddress server = IPAddress.Parse("127.0.0.1");
			Int32 port = 13000;
			TcpClient client = new TcpClient("127.0.0.1", port);
			Byte[] datac = System.Text.Encoding.ASCII.GetBytes(msgc);
			NetworkStream streamc = client.GetStream();
			streamc.Write(datac, 0, datac.Length);
			datac = new byte[256];
			String responseData = String.Empty;
			Int32 bytesc = streamc.Read(datac, 0, datac.Length);
			responseData = System.Text.Encoding.ASCII.GetString(datac, 0, datac.Length);
			streamc.Close();
			client.Close();
		}

		private void Form1_Load(object sender, EventArgs e)
		{
			Int32 port = 13000;
			IPAddress localAddr = IPAddress.Parse("127.0.0.1");
			TcpListener server = new TcpListener(localAddr, port);
			server.Start();
			Byte[] bytes = new Byte[256];
			String data = null;
			while(true)
			{
				label4.Text = "Waiting for a Connection";
				TcpClient client = server.AcceptTcpClient();
				label4.Text = "Connected";
				data = null;
				NetworkStream stream = client.GetStream();
				int i;
				while((i = stream.Read(bytes, 0, bytes.Length))!= 0)
				{
					data = System.Text.Encoding.ASCII.GetString(bytes, 0, i);
					label3.Text = data;
					data = data.ToUpper();
					byte[] msg = System.Text.Encoding.ASCII.GetBytes(data);
				}
				client.Close();
			}
			server.Stop();
		}
	}
}
