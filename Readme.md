# JGroup [Stack and Set]
Implementasi JGroups untuk Replicated Stack and Replicated Set

##### By:
* Aryya Dwisatya Widigda - 13512043
* Muhammad Husain Jakfari - 13512067

## How to run
### `Stack`

 1. `git clone https://github.com/adwisatya/SIMLE-IRC-CHAT-JGROUPS.git`
 2. masuk folder out/artifacts/Stack_JGroups_jar, dan buka di terminal atau cmd
 3. `java -cp Simple_IRC_Jgroups.jar simple_irc_jgroups.ReplStack`
 4. Satu Klien Terbentuk (jika ingin lebih dari satu klien jalankan pada terminal atau cmd yang bebeda)
![alt tag](https://raw.githubusercontent.com/adwisatya/SIMLE-IRC-CHAT-JGROUPS/master/Simple_IRC_Jgroups/screenshot/stackstart.png)

### `Set`

 1. `git clone https://github.com/adwisatya/SIMLE-IRC-CHAT-JGROUPS.git`
 2. masuk folder out/artifacts/Set_JGroups_jar, dan buka di terminal atau cmd
 3. `java -cp Simple_IRC_Jgroups.jar simple_irc_jgroups.ReplSet`
 4. Satu Klien Terbentuk (jika ingin lebih dari satu klien jalankan pada terminal atau cmd yang bebeda)
![alt tag](https://raw.githubusercontent.com/adwisatya/SIMLE-IRC-CHAT-JGROUPS/master/Simple_IRC_Jgroups/screenshot/setstart.png)

## Fitur-fitur
### Replicated Stack
 1. Untuk melakukan perintah ```Top``` pada Stack, cukup ketik ```/pop```
 2. Untuk melakukan perintah```Pop``` pada Stack, cukup ketik ```/top```
 3. Untuk melakukan perintah ```Push``` pada Stack, cukup ketik ```/push <string>```
 4. Ketika kamu merasa kamu sudah cukup, ketik ```/exit``` untuk keluar dari program

### Replicated Set
 1. Untuk melakukan perintah ```Add``` pada Set, cukup ketik ```/add <string>```
 2. Untuk melakukan perintah```Contanis``` pada Set, cukup ketik ```/contains <string>```
 3. Untuk melakukan perintah ```Remove``` pada Set, cukup ketik ```/remove <string>```
 4. Ketika kamu merasa kamu sudah cukup, ketik ```/exit``` untuk keluar dari program

## Testing
### Stack
#### Skenario
* Jalankan 2 Client
* `top` pada client `1` `(pengecekan stack kosong)`
* `push` 5 pada client `2`
* `top` pada client `1`
* jalankan 1 client lagi `(pengecekan 3 client)`
* `pop` pada client `3`
* `push` 3 pada client `1`
* `push` 4 pada client `3`
* `pop` pada client `2`
* `pop` pada client `3`
* `exit`

#### Hasil Test Case
* pada client 1
![alt tag](https://raw.githubusercontent.com/adwisatya/SIMLE-IRC-CHAT-JGROUPS/master/Simple_IRC_Jgroups/screenshot/stack1.png)
* pada client 2
![alt tag](https://raw.githubusercontent.com/adwisatya/SIMLE-IRC-CHAT-JGROUPS/master/Simple_IRC_Jgroups/screenshot/stack2.png)
* pada client 3
![alt tag](https://raw.githubusercontent.com/adwisatya/SIMLE-IRC-CHAT-JGROUPS/master/Simple_IRC_Jgroups/screenshot/stack3.png)

### Set
#### Skenario
* Jalankan 2 Client
* `contains` pada client `1` `(pengecekan set kosong)`
* `add` 5 pada client `2`
* `add` 6 pada Client `1`
* `remove` 5 pada client 1
* jalankan 1 client lagi  `(pengecekan 3 client)`
* `contains` 5 pada client `3`
* `contains` 3 pada client `3
* `add` 76 pada client `3`
* `contains` 76 pada client `1`
* `remove` 76 pada client `2`
* `remove` 7 pada client `1`
* `exit`

#### Hasil
* pada client 1
![alt tag](https://raw.githubusercontent.com/adwisatya/SIMLE-IRC-CHAT-JGROUPS/master/Simple_IRC_Jgroups/screenshot/set1.png)
* pada client 2
![alt tag](https://raw.githubusercontent.com/adwisatya/SIMLE-IRC-CHAT-JGROUPS/master/Simple_IRC_Jgroups/screenshot/set2.png)
* pada client 3
![alt tag](https://raw.githubusercontent.com/adwisatya/SIMLE-IRC-CHAT-JGROUPS/master/Simple_IRC_Jgroups/screenshot/set3.png)
