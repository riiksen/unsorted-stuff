#include <stdio.h>
#include <stdlib.h>
#include <string.h>

int main(int argc, char **argv){
 File *file = fopen(argv[1], "r+"); 
 if(argv != 3)
  printf("Usage %s <file> encrypt/decrypt <password>", argv[0]);
  return 0;
 }elseif(argv[2] != "encrypt" || argv[2] != "decrypt"){
  printf("Invalid parameter 2: %s", argv[2]);
  return 0; 
 }elseif(file == NULL){
  printf("Can't open such file: %s", argv[1]);
  return 0;
 }else{
  if(argv[2] == "encrypt"){
   int ctf = 0; 
   while (true){ 
    char buf = fgetc();
    buf = ((int)buf * (int)argv[3]) << 2;
    File encf = fopen(argv[1] + ".enc", "r+";
    encf.write(buf);
    ctf++; 
   }
  }
 }
 return 0;
}
