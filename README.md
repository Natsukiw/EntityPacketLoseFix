This is a temporary fix, I don't know if there is a better one, but at least it works and Uranium and Thermos are tested on the server side;

When there are many online players, I noticed that the world I am in generates NPCs in another world; another situation is that when I use the MOD monster egg to generate entities, the entities are invisible, but this is not the case when using the original MC monster egg to generate;

So the conclusion is that when there are many people, the MOD entity data packets are sometimes sent to other players' clients
So it is difficult to reproduce this BUG
Basically, this fix is ​​to bypass Forge's entity packet sending system.


I don't know much about network packets, so I have to bypass this method to fix it
![image](https://github.com/user-attachments/assets/ab9a1545-53f2-4b4d-9bd1-c46d64d63d13)

