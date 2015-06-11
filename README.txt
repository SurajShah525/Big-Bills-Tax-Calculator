TO run the program,
1)first create a database using the available sql queries in Database Queries.txt.(use oracle 10g express preferably)

2)Then run guicaller.java using command line

3)Use the minimalistic GUI as a test case interface.

Eg: Simply select a value in combobox and say Add to list.
    Add multiple items to have sales tax and total displayed in the textboxes.


Notes:   1)Emphasis was not n the GUI, as it can be seen, but only minimal necessary things were added for ease in testing
      
	2)Use top most panel to add a new item, along with price, in the database.change will be reflected in combobox for product selection
	
	3)Use bottom panel to add items in the cart. All details will be displayed accordingly.

	4)Refer the images folder to get an idea of already implemented test cases.
	
	5)Check condition in database avoids addition of null or negative values to database. An error message can be displayed (but is not, bcoz of time constaint)

