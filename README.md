# FarmManagementSystem

  This tool is for personal use and as such I didn't feel the need to build a UI. Data percistance and business logic were my main focus. 
  It also is still very much a prototype and work in progress. It will requires a MySQL db to run. This currently is only running on my local machine. 
  For future plans I may possibly host the DB on a AWS RDS server, and then convert this code into a Sprint Boot Rest API service and host it on a 
  AWS EC2 instance. Only then would I want to play around with creating a front end. 
  
# How To Use
  
  The code its self is the UI, since it was only built with me in mind. Everything is done from the main function found in the FMS.main.main file. 
  I have a couple different report generating functions found in the FMS.main.display.Display class. This display must be passed a DAOInterface object, which
  holds a connection to the database and provides database functions. MySQLDAO is the implementation of this interface that is used. 
  
# Sample Reports

  These are the two reports It currently generates to track data on making Hay for our farm. Their not really pretty, just functional. 
  
  ### Field report - shows harvest data for the season
  
```  
  --------------------Field Report For FieldID: 1 and Season: 2021-------------------
------------------- Jobs -------------------
Job [id=1, name=1st Crop Bus Field, startDate=6/20/2021, assignedFieldId=1, seasonId=2021]
----------------------------------------------
------------------- All Tasks -------------------
FarmTask [id=9, type=CUT, jobId=1, hours=3.0]
FarmTask [id=10, type=CUT, jobId=1, hours=2.0]
FarmTask [id=21, type=RAKE, jobId=1, hours=2.0]
FarmTask [id=22, type=RAKE, jobId=1, hours=1.5]
FarmTask [id=31, type=BALE, jobId=1, hours=2.5]
FarmTask [id=32, type=BALE, jobId=1, hours=2.0]
-------------------------------------------------
Number of bales made: 35.5        - Per Acre: 1.6542404473438956
Gross revenue: $2130.0            - Per Acre: $99.25442684063373
Costs of labor and fuel: $351.0   - Per Acre: $16.35601118359739
Returns to equipment: $1779.0     - Per Acre: $82.89841565703634
```

  ### Season report - shows agregate data for alls fields for a given season
  
  ```
  --------------------Season Hay Report For 2021-------------------
Number of bales made: 197.0        - Per Acre: 1.3952829520504286
Gross revenue: $11820.0            - Per Acre: $83.71697712302571
Costs of labor and fuel: $2093.175   - Per Acre: $14.825235498264751
Returns to equipment: $9726.825     - Per Acre: $68.89174162476097
  ```
  
