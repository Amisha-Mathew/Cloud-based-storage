# Project Nimbus
To test this application, you can use tools such as Postman or cURL to send HTTP requests to the API endpoints defined in the Controller class.

Here's an example of how you could test the API using cURL:

Start the Spring Boot application.

Open a terminal window and run the following command to add a file for a user with ID 1:

```sh
curl -X POST -F "numFiles=2" -F "filename=<filepath>" http://localhost:8080/users/1/files

```

Run the following command to retrieve all files for the user with ID 1:

```sh
curl http://localhost:8080/users/1/files
```

Run the following command to retrieve a specific file (with ID 1) for the user with ID 1:

```sh
curl http://localhost:8080/users/1/files/1
```

Run the following command to update a file (with ID 1) for the user with ID 1:

```sh
curl -X PUT -F "numFiles=3" -F "filename=@/path/to/updated-file.txt" http://localhost:8080/users/1/files/1
```

Run the following command to delete a file (with ID 1) for the user with ID 1:
```sh
curl -X DELETE http://localhost:8080/users/1/files/1
```

Replace <filename> with the data you need to upload
=======

### Team Details

- Abhay Hiremath (PES2UG20CS008)
- Adarsh Liju Abraham (PES2UG20CS017)
- Amisha Mathew (PES2UG20CS038)
- Apoorva Naronikar (PES2UG20CS062)

