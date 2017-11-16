# SpringVideoUploadTask

This project is a web-app focused on providing a user registration system along with feature to allow registered users as **ADMIN** to upload video files.
To keep in mind, video files of duration less than 10 minutes are allowed to be uploaded. The uploaded file is stored directly to the AWS S3 bucket and 
referenced in the database. The system is made secure by using Spring security token based authentication. And the web-app is hosted on an AWS EC2 Linux 
instance with Apache Tomcat and MySQL server installed on it.

## Registering user profile in the system

This API lets user register in the system by providing the required parameters. Returns to registrationsuccess page after successful registration.

#### • URL:
http://ec2-54-85-2-66.compute-1.amazonaws.com:8080/SpringVideoUploadTask/newuser

#### • Method:
`POST`

#### • URL Params:
None

#### • Data Params:
`firstName=[string]`

`lastName=[string]`

`email=[string]`

`password=[string]`

`role=[string]("ADMIN" or "DBA" or "USER")`

## Login API for the system

This API route is only reponsible to view the Login jsp page. However, Login authentication is managed by **spring-security.xml** and **CustomUserDetailsService**.
Password encryption is enabled by using BCrypt library.

#### • Method:
`POST`

#### • Data Params:
`email=[string]`

`password=[string]`

## Upload video files to AWS S3

With the help of this API, user can upload a video **`mp4`** file of duration less than **10** minutes. This restriction was implemented because of the requirement which can further be modified as needed.
This API route generates an S3 accessible URL which is stored in the database corresponding to the user. This is a **pre-signed URL**(secure) which can only be genrated with the correct AWS credentials.
#### • URL:
http://ec2-54-85-2-66.compute-1.amazonaws.com:8080/SpringVideoUploadTask/upload

#### • Method:
`POST`

#### • URL Params:
`Authentication token`

#### • Data Params:
`name=[string]`

`file=[MultipartFile]`
