#include <iostream>
#include <ctype.h>

#include "colors.h"

int main(int argc, char **argv){
  std::cout << BOLDGREEN << "Enter your name :) >>" << RESET;
  std::string name;
  std::cin >> name;
  name[0] = toupper(name[0]);
  std::cout << BOLDBLUE << "Hello " << name << RESET << "\n";
}
