## Need: ##
An automated process is desired to periodically provide an independent listing of Allegro user access and privilege changes.  Listing should be emailed to an email distribution list including the Allegro administrator. The intent is to ensure that application users are not involved in the generation of the security report.


## Solution: High-Level Architecture: ##
A Java application will be written that will access the Allegro Db (PRODACI1 using jdbc), write Allegro security setting to an Excel spreadsheet (using Apache POI), and then email to a distribution list.

The Java .jar file will (_**AllegroSecurity-runnable.jar**_) - be on an IT computer and IT will manage and control source and execution code as well as any associated files.  CRON will be utilized to run the application at a predefined time (initially to be set at once a month).


## Specifics: ##
The java application utilizes jdbc to call the production Allegro database (PRODACI1).  Because jdbc is used the application needs to include the _**ojdbc6.jar**_ file.  The specific parameters required for the database call are obtained from the external _**allegro.security.properties.txt**_ file (see Exhibit A).  It has been requested that Information Technology (IT) obtain a unique ID and Password for use with this application.

The application obtains the following Allegro access/security information:

  1. SecurityGroupAccessByMethod: lists the Allegro security access group in use and the associated table privileges within the system.
  1. UserAccess: lists all Allegro security groups along with listing which user accounts are associated (and if user if active or inactive).  Also included are the application methods available to the security group.
  1. UserGroupAccess: Summary listing of which users and in which access group and if access is active or inactive.
  1. AuditSecurity: Lists all changes to Allegro security for the past two months.
  1. AdminAccess:  Lists who has “Admin” access to the Allegro application

The above information will then be exported to Excel (using the Apache POI API, which requires the _**poi-3.8-20121203.jar**_ file).  The name of the file created and the location where it is written is obtained from the _**allegro.security.properties.txt**_ file.

After the Excel file is created the file will be mailed to the AllegroAdmins@ArchCoal.com email group (see Exhibit E).

Logging: All key activity is logged using Apache’s log4j API. Log name and location is obtained from the _**log4j.properties**_ file (see Exhibit B).


Application Dependencies are noted at Exhibit F.