## UserPayrollSystem - simple web application with the following HTTP endpoints

| Path    | Method |
|---------|--------|
| /users  | GET    |
| /upload | POST   |

### 1. /users - endpoint
#### Params
- min - minimum salary. Optional, defaults to 0.0..
- max - maximum salary. Optional, defaults to 4000.0.
- offset - first result among set to be returned. Optional, defaults to 0.
- limit - number of results to include. Optional, defaults to no limit.
- sort - NAME or SALARY, non-case sensitive. Optional, defaults to no sorting. Sort only in ascending sequence.

#### Description
Return list of users that match specified criteria and ordering. in JSON form:

```
{
“results”: [
{
“name”: “John”,
“salary”: 3000.0
},
{
“name”: “John 2”,
“salary”: 3500.0
}
]
}
```

### 2. /upload - endpoint
#### Params
- Content type: multipart/form-data
- Form field name: file
- Contents: CSV data. See below.

#### Description
Return success or failure. 1 if successful and 0 if failure. If failure, HTTP status code should not be HTTP_OK. File upload is an all-or-nothing operation. The entire file’s changes are only applied after the whole file passes validation. If the file has an error, none of its rows should be updated in the database.

```
{
“success”: 1
}
```

#### CSV file is structured as follows.
- Two columns NAME and SALARY.
  - Name is text.
  - Salary is a floating point number, You do not need to ensure a specific number of decimal points.
  - Salary must be >= 0.0. All rows with salary < 0.0 are ignored.
- First row of the CSV file is always ignored and treated as a header row.
- If there is a formatting error (ie. salary number cannot be parsed), or incorrect number of columns during input, the CSV file should be rejected. However, rows with salary < 0.0 are skipped, without skipping the entire file.
- User name must be unique in the system. If there is already another user with the same name in the database during loading, the record in the database is replaced with the data from the file.
- The database should be pre-loaded with seed data on first invocation of the application.
- Bonus: It is not required but desirable to be able to process concurrent upload requests simultaneously.

### Run Instructions
- JDK 17 is required
- Run through IDE
  - run `UserPayrollSystem.java` through Eclipse/Intellij
- Hit http endpoints /users, /upload using POSTMAN