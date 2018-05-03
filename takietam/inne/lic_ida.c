#include <stdio.h>
#include <string.h>

int main(int argc, const char **argv, const char **envp)
{
  int result; // eax@2
  int v4; // eax@3

  if ( argc == 2 )
  {
    v4 = check_lic((char *)argv[1]);
    if ( v4 )
    {
      if ( v4 == 2 )
        puts("Incorrect format");
      else
        puts("NOPE");
    }
    else
    {
      puts("Correct license");
    }
    result = 0;
  }
  else
  {
    printf("Usage: %s <license>\n", *argv);
    result = 0;
  }
  return result;
}
int check_lic(char *lic)
{
  int result; // eax@7
  size_t i; // [sp+18h] [bp-10h]@1
  int v3; // [sp+1Ch] [bp-Ch]@1

  v3 = 0;
  for ( i = 0; strlen(lic) > i; ++i )
    v3 += lic[i];
  if ( a1[4] != 45 || a1[8] != 45 || strlen(lic) != 15 )
    result = 2;
  else
    result = v3 != 1288;
  return result;
}
