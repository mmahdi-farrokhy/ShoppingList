# ShoppingList
A Simple Java Project With UI, MySQL Database And Back-End Implementation For A Shopping List
_________________________________________________________________________________________
In this program we can save up to 5 items to a shopping list.
Each item has a name and quantity.
The final list will be saved in a MySQL database named as "shop" with a
table named as "item". This table has 3 cloumns. "id", "Name" and "Quantity".
_________________________________________________________________________________________
There are 3 buttons in this program:
- save: to save the list in the database.
- search: to search the database for the items that are already saved in the database.
- pdf: this buttons saves the list as a .pdf file.
_________________________________________________________________________________________
NOTICE:
This repository misses the database file. So build it and its table as described above.
Also there must be a file named "db-config.properties" in the projects directory.
This files contains the database host address, username and password declared as "host", "user"
abd "pass". In order for the program to run you should create this file and write these information in it.
