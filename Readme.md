# JGroup [Stack and Set]
Implementasi JGroups untuk Replicated Stack and Replicated Set

##### By:
* Aryya Dwisatya Widigda - 13512043
* Muhammad Husain Jakfari - 13512067

## How to run Stack

 1. `git clone https://github.com/adwisatya/SIMLE-IRC-CHAT-JGROUPS.git`
 2. masuk folder out/artifacts/Stack_JGroups_jar, dan buka di terminal atau cmd
 3. `java -cp Simple_IRC_Jgroups.jar simple_irc_jgroups.ReplStack`
 4. Satu Klien Terbentuk (jika ingin lebih dari satu klien jalankan pada terminal atau cmd yang bebeda)
![alt tag](https://raw.githubusercontent.com/adwisatya/SIMLE-IRC-CHAT-JGROUPS/master/Simple_IRC_Jgroups/screenshot/setstart.png)

## How to run Set

 1. `git clone https://github.com/adwisatya/SIMLE-IRC-CHAT-JGROUPS.git`
 2. masuk folder out/artifacts/Set_JGroups_jar, dan buka di terminal atau cmd
 3. `java -cp Simple_IRC_Jgroups.jar simple_irc_jgroups.ReplSet`
 4. Satu Klien Terbentuk (jika ingin lebih dari satu klien jalankan pada terminal atau cmd yang bebeda)
 
## How to test
### Replicated Stack
 1. Untuk melakukan perintah ```Top``` pada Stack, cukup ketik ```/pop```
 2. Untuk melakukan perintah```Pop``` pada Stack, cukup ketik ```/top```
 3. Untuk melakukan perintah ```Push``` pada Stack, cukup ketik ```/push <string>```
 4. Ketika kamu merasa kamu sudah cukup, ketik ```/exit``` untuk keluar dari program

### Test Case

![alt tag](https://raw.githubusercontent.com/adwisatya/SIMLE-IRC-CHAT-JGROUPS/master/Simple_IRC_Jgroups/screenshot/setstart.png)

#### Mengecek elemen teratas stack yang telah terisi oleh client lain

![alt tag](https://raw.githubusercontent.com/adwisatya/SIMLE-IRC-CHAT-JGROUPS/master/Simple_IRC_Jgroups/screenshot/setstart.png)

#### Mengecek elemen teratas stack kosong

 ![alt tag](https://raw.githubusercontent.com/adwisatya/SIMLE-IRC-CHAT-JGROUPS/master/Simple_IRC_Jgroups/screenshot/setstart.png)

#### Komunikasi 3 client

 ![alt tag](https://raw.githubusercontent.com/adwisatya/SIMLE-IRC-CHAT-JGROUPS/master/Simple_IRC_Jgroups/screenshot/setstart.png)

### Replicated Set

![alt tag](https://raw.githubusercontent.com/adwisatya/SIMLE-IRC-CHAT-JGROUPS/master/Simple_IRC_Jgroups/screenshot/setstart.png)
 1. Untuk melakukan perintah ```Add``` pada Set, cukup ketik ```/add <string>```
 2. Untuk melakukan perintah```Contanis``` pada Set, cukup ketik ```/contains <string>```
 3. Untuk melakukan perintah ```Remove``` pada Set, cukup ketik ```/remove <string>```
 4. Ketika kamu merasa kamu sudah cukup, ketik ```/exit``` untuk keluar dari program

### Test Case

#### Mengecek apakah sebuah string terdapat di set

![alt tag](https://raw.githubusercontent.com/adwisatya/SIMLE-IRC-CHAT-JGROUPS/master/Simple_IRC_Jgroups/screenshot/setstart.png)

#### Menghapus member set

![alt tag](https://raw.githubusercontent.com/adwisatya/SIMLE-IRC-CHAT-JGROUPS/master/Simple_IRC_Jgroups/screenshot/setstart.png)

#### Komunikasi 3 client

![alt tag](https://raw.githubusercontent.com/adwisatya/SIMLE-IRC-CHAT-JGROUPS/master/Simple_IRC_Jgroups/screenshot/setstart.png)