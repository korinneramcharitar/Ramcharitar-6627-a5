# Ramcharitar-6627-a5
Hello!
So this application allows you to Store Inventory Items by Value in U.S dollars, Serial Number, as well as by Name.
The table is preloaded with Dummy values from the example posted on assignment 5.
They can be removed via the delete button. In fact any item that is added on may be deleted.
The add button will allow user input from the respective textfields to add in Inventory Items
The Inventory Name, Value, and Serial Number can be edited by double clicking on the cell that needs to be edited.To save press enter after any changes.
The Search Button takes the user to a new tableview where the preloaded items can be searched via Name, Serial, number and value.
However if the user needs to add more items it will have to be hard coded into the SearchList controller. Unfortunantley the developer was unable to implement the search feature with the add/delete/upload features so it had to be seperated.
Future updates may see unity later between those features.
TO SAVE
if the the user wnts to savr the inventory there is a MenuBar with the words SAVE. The drop down menu allows the user to choose between a JSON, TSV, and HTML file.
However be warned, while clicking the button guarentees the file will be formatted according to the respective file, the user must make sure to name the file accordingly. This means if the file is a TSV file,
it musdt be named as "Example.txt" as a txt file. The same goes for JSON and HTML files with ".json" and ".html" as the ending.
TO LOAD
When loading in files, the LOAD button should hint at to how it works. If loading in a html file, it will convert it to a tsv text file, which then can be loaded by going back to the load tsv file and pulling it up that way.
To be clear, if I click HTML to TSV the file will not load any files, it will convert it to a TSV file which can be loaded after the user goes to click TSV in the load option.
This will create a file called "Html.txt" that can be changed by going into the InventoryManagerController->SaveListasHTML method and changing the direct path to the user's own pathway. It is set to my own Desktop pathway as of now.
The TSV load option does not require these steps, you can simply just click on any tsv file and if it is in the format the app saves tsv files to, it will load in properly.
That should cover how to use the the application.
ENJOY!
