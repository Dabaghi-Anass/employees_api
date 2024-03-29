# API Documentation

## Overview
This document provides information about the endpoints of our API, their functionalities, and how to use them.

## Base URL
The base URL for the API is `https://employees-api-92f4.onrender.com`

## Endpoints

### Endpoint 1 (/api/employees)

#### GET
returns a list of all employees
#### POST
adds a new employee to the list
#### Response

```json
{
    name : string,
    age : number ,
    email : string ,
    phone : string ,
    bio : string ,
    imageUrl : string
}
```

##### Parameters
- `city`: returns employees in a city
- `name`: returns employees with a given name

### Endpoint 2 (/api/employees?limit=$&offset=$)

#### GET
returns a limited number of employees (limit) by page (offset)

*offset starts from 0*


##### Response
```json
{
    hasNext : boolean
    employees : Employee[]
}
```


### Endpoint 3 (/users/{id})

#### GET
returns employee by id
#### response

```json
{
    name : string,
    age : number ,
    email : string ,
    phone : string ,
    bio : string ,
    imageUrl : string
}
```

#### PUT
modify employee by id (specify only fields to change)


##### Response
returns saved employee

```json
{
    name : string,
    age : number ,
    email : string ,
    phone : string ,
    bio : string ,
    imageUrl : string
}
```

#### DELETE

deletes an employee

##### Response
**null**
### Endpoint 4 (/usersfiles)
returns a list of images links on the server (for developers)

### Endpoint 4 (/media/upload)

#### POST
post an image and the server will compress it to (500x500) and save it

#### Response
image url