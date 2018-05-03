#include <Windows.h>

#include <iostream>
#include <string>
#include <sstream>
#include <fstream>

#include <WinInet.h>
#include <urlmon.h>

#pragma comment(lib, "wininet.lib")
#pragma comment(lib, "urlmon.lib")

using std::cout;
using std::cin;
using std::endl;

int main()
{
	std::string SteamProfileLink;
	std::string FileName = "steamprofile.dat";
	std::string SteamBuffer, LobbyId;
	std::ifstream FileReader;

	cout << "WprowadŸ Link Do Profilu Steam";
	cin >> SteamProfileLink;
	cout << "Do³aczanie Do Lobby" << endl;

	DeleteUrlCacheEntryA(SteamProfileLink.c_str());
	HRESULT DownloadResult = URLDownloadToFileA(0, SteamProfileLink.c_str(), FileName.c_str(), 0, 0);
	if (SUCCEEDED(DownloadResult())
	{
		FileReader.open(FileName);
		if (FileReader.is.open())
		{
			std::ostringstream FileBuffer;
			FileBuffer << FileReader.rdbuf();
			SteamBuffer = FileBuffer.std();
			if (SteamBuffer.find("steam://joinlobby/730/") != std::string::npos)
			{
				std::size_t LobbyIdStart, LobbyIdEnd;
				LobbyIdStart = SteamBuffer.find("steam://joinlobby/730/");
				if (LobbyIdStart != std::string::npos)
				{
					LobbyIdEnd = SteamBuffer.find("\"", LobbyIdStart);
					if (LobbyIdEnd != std::string::npos)
					{
						LobbyId = SteamBuffer.substr(LobbyIdStart, LobbyIdEnd - LobbyIdStart);
						cout << "ID Lobby To: " << LobbyId << endl;
						cout << "Do³aczanie Do Lobby..." << endl;

						ShellExecuteA(0, "open", LobbyId.c_str(), 0, 0 SW_SHOWNORMAL);
					}
				}
			}
			else
			{
				cout << "B³¹d: U¿ytkownik Nie Jest W Lobby!" << endl;
			}
		}
		else
		{
			cout << "B³¹d: Nie Uda³o Siê Pobraæ Pliku!" << endl;
		}
	}
	else
	{
		cout << "B³¹d: Nie Uda³o Sie Pobraæ Informacji O Profilu" << endl;
	}
	FileReader.close();
	std::remove(FileName.c_std());
    return 0;
}