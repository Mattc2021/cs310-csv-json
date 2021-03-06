Introduction
	
  CSV-JSON is a Java program designed to convert CSV data to JSON data and JSON data to CSV data, but instead of storing the data, it is
  printed to the screen. Though the program only has two classes, Main and Converter, both programs are decently lengthy. The 
  delimiter used for reading data is set by the file, using “File.separator.” The purpose of this program is file-type conversion.

Main

   Because of the error-prone nature of file modifications, the main function is encased in a try statement that logs the error as severe   if execution fails. Starting chronologically in the code, a ClassLoader is initialized to a variable to assist in retrieving files from  the resources folder. A StringBuilder is also initialized to a variable to assist in building the CSV data structure from the files. An additional try statement surrounds a BufferedReader, using an InputStreamReader, using the aforementioned ClassLoader, and finally using  the resource folder and file separator to read the data from the correct file and store it in a BufferedReader variable. Each line of  the reader is then iterated through and appended to the StringBuilder (with a new line appended after every line of the reader). If the code within the try statement fails to execute, the thrown exception’s StackTrace is printed. With all the data in a 
StringBuilder, it is converted to a string and trimmed of any new lines or white space at the end. The same, exact method is used 
for getting JSON data, but the variables that contain CSV are instead named JSON. Now with all CSV and JSON data read, they are
output to the screen. First, the string “CONVERSION RESULTS (CSV to JSON)” is printed, followed by a new lines and thirty-two equal 
signs (to mark a barrier) and another new line. Next, the converter class is called to convert the data, and it is printed to the 
screen. Finally, just as before, those steps are repeated for the JSON data -- “CONVERSION RESULTS (JSON to CSV)” being its header.

Converter

   While the steps for CSV and JSON files have been similar or identical to this point, that will no longer be true. Though three
functions exist, only two (csvToJson and jsonToCsv) are relevant as the third (getJsonData) is used for a separate test file
involving MySQL, but it is never called in the program. Try statements surround both functions with a blank string declared to a 
variable. This is where the class splits off into the different types of conversion.  
CSV to JSON conversion first involves a CSVReader using a StringReader to read the input string provided by the main class.
A list of string arrays are then populated with the lines of the reader. Next, and Iterator class is used to separate the 
individual lines by the file separator. Using the “next” method of the Iterator class, the next array of strings, as delimited
by the file delimiter, is returned. Now with the structures necessary for populating a smarter structure, a JSONArray object and
LinkedHashMap using that JSONArray is initialized. This complicated code is essentially row headers, column headers, and the 
corresponding, two-dimensional array. Since the column headers were the first row of the CSV file, retrieving the next line is
all that is needed for the column headers. When iterating through the Iterator data structure, the first string of each list of
strings is the row header and is stored as such. The remaining collected data is the two-dimensional array of data that
corresponds to column and row header strings. With all the CSV data successfully separated in the three structures needed for 
JSON data, they are populated into the LinkedHashMap. The toJSONString function is then called on the map so that the JSON string
is displayed by the main class.JSON to CSV conversion is far more simple. First, a StringWriter is initialized, then a CSVWriter 
is initialized with the StringWriter, a comma, a quotation mark, and a new line character; this gives the writer the ability to 
read the string data and compile it with the last three parameters as as the format (new lines separating rows, commas separating
columns, and quotation marks surrounding each string). From this point, there is only data manipulation to change the JSONString 
format to the CSV format just initialized. To do this, the string is split using “],\"” as the delimiter. Next, the first section
of that is split using “:\\[“ as its delimiter. Then, the second section of that has all backslashes removed and is further split
by commas; this single array is the column headers. When the original string is first split, the third string in that set is split
by a colon, and the first section of that has all backslashes, close curly brackets, and open and close square brackets removed. 
With that string split by commas, the array returned is the row headers. Finally, the second section of the first split of the
original JSONString must be interpreted into the data. After being split with “:\\[“ as the delimiter, the first section of that 
is split using “],\\[”. The length of the array returned is equal to the number of rows, meaning the two dimensional array can 
now be created with the known length in consideration. A data array is then populated one line at a time with the row header 
first, then the data. To get the data, the latest mentioned split will be further split. The current string is split by commas
with square brackets removed, and each string is used by the for loop to populate the array. To finalize the CSV string, the
CSVWriter has the column headers, then the data (with the row headers inside) written to it. The trimmed string is returned in 
the format of a CSV file for the user to view.

Sample Output

	CONVERSION RESULTS (CSV to JSON)
	================================
	{"rowHeaders":["111278","111352","111373","111305","111399","111160","111276","111241"],
 	 "data":[[611,146,128,337],[867,227,228,412] ,[461,96,90,275],[835,220,217,398],[898,226,229,443],[454,77,125,252],[579,130,111,338],[973,236,237,500]],
  	"colHeaders":["ID","Total","Assignment 1","Assignment 2","Exam 1"]}

	CONVERSION RESULTS (JSON to CSV)
	================================
	"ID","Total","Assignment 1","Assignment 2","Exam 1"
	"111278","611","146","128","337"
	"111352","867","227","228","412"
	"111373","461","96","90","275"
	"111305","835","220","217","398"
	"111399","898","226","229","443"
	"111160","454","77","125","252"
	"111276","579","130","111","338"
	"111241","973","236","237","500"
	
Graphical Possiblities

   With little code neccessary, a graphical version of this application takes little effort. First, a class ending a JFrame must be created and initialized. Next, two labels are created to represent the output of both conversions. A text field is also created for the user to enter the file path, and both are added to a JPanel which is added to the JFrame. Once the user clicks the submit button,
the text field and read and sent to the converter to replace the file destination of the original path. After the converter finishes,
the output is sent to the main, then graphical class where the labels are set to the CSV and JSON string respecively.
