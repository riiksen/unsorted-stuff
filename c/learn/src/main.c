#include <iostream>
#include <algorithm>
#include <vector>
#include "stdio.h"

#include "colors.h"

using namespace std;

struct czlowiek {
  int wiek;
  string name;
  string surname;
};

int randwiek(int a, int b){
  for(;;){
    int c = rand() % b;
    if (c >= a) {
      return c;
    }
  }
}

int main(int argc, char const *argv[]) {
  czlowiek adam[100];

  for (int i = 0; i <= 100; i++) {
    adam[i] = {randwiek(10, 80), "Adam", "Nowak"};
  }

  for (int i = 0; i < sizeof(adam-1); i++) {
    for (int n = 0; n < sizeof(adam-1); n++) {
      if (adam[n].wiek > adam[n+1].wiek) {
        adam[n], adam[n+1] = adam[n+1], adam[n];
      }
    }
  }

  for (int i = 0; i < sizeof(adam); i++) {
    cout << "Imie: " << adam[i].name << " Nazwisko: " << adam[i].surname << " Wiek: " << adam[i].wiek << endl;
  }

  return 0;
}
