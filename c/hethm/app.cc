#include "app.h"

int app::start(){
  NetSock::InitNetworking();

  NetSock socket;
  int ret;
  unsigned char buffer[8] = {0};

  if (!s.Connect("127.0.0.1", 9325))
    printf("Wystąpił problem z połączeniem z serwerem")

  ret = s.Write((unsigned char*)"test", 4);

  ret = s.Read
}
