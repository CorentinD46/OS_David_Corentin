Question 1.0 : Il y a 4 section qui peuvent être définies car 

Question 1.1 : une section peut contenir jusqu'a un 1Ko soit 1024 octet car il y a :
[0-3]   : Type de section (int)
[4]     : Padding (1 byte)
[5-254] : Nom de fichier (250 bytes)
[255]   : Padding (1 byte) 
[256...]: Pointeurs vers blocs de données (array d'int)

Question 1.2 : Le magic number 0x424C4B46 correspond au 4 p

Question 1.3 : Sachant qu'un BLOCK_SIZE = 1Ko alors la taille du fichier 
