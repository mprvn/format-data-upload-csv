# format-data-upload-csv

# format-data-upload-csv

Run the application using  mvn spring-boot:run

Application will start with localhost:8080/ 

Application will load with Option to Add and Upload CSV

you can see the application data populated in a grid when you add a bank details for now there are no validations.

Please eneter the Card Number with Hypne as per the input int he csv file.

You can also use post man to test the data as i have created rest controllers for the same.

localhost:8080/bankdetails get and post service are available 

Sample Input for post 
{
    
    "bank": "US Canada",
    "cardNumber": "5601-xxxx-xxxx-xxxx",
    "expiryDate": "2015-11-02"
 }
 
 For Upload CSV 

localhost:8080//upload-csv-file  can be used 

for Sending csv file use the sample csv file in the resouces bankdetail.csv

in post man select formdata and don't set any Headers (clear any if you have set it ) and choose the file to upload.

