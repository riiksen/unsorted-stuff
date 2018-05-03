#include <iostream>
#include <conio.h>
#include <time.h>
#include <stdlib.h>
#include <Windows.h>

using namespace std;

int main()
{
	for (;;)
	{
		system("cls");
		int a = 0;
		cout << "Wiœnij dowolny klawisz by rozpocz¹æ" << endl;
		_getch();
		system("cls");
		int time = clock() + 10000;
		while (clock() < time)
		{
			_getch();
			a++;
		}
		cout << "Wciœnieto " << a << " Klawiszy" << endl;
		Sleep(2000);
		_getch();
	}
}