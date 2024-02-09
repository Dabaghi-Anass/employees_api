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
<code style="background : black; width : 300px; display : block; padding : 1rem;color : green">{
    name : string,
    age : number ,
    email : string ,
    phone : string ,
    bio : string ,
    imageUrl : string
}
</code>


##### Parameters
- `city`: returns employees in a city
- `name`: returns employees with a given name

### Endpoint 2 (/api/employees?limit=$&offset=$)

#### GET
returns a limited number of employees (limit) by page (offset)

*offset starts from 0*


##### Response
<code style="background : black; width : 300px; display : block; padding : 1rem;color : green">{
    hasNext : boolean
    employees : Employee[]
}
</code>


### Endpoint 2 (/users/{id})

#### GET
returns employee by id
#### response
<code style="background : black; width : 300px; display : block; padding : 1rem;color : green">{
    name : string,
    age : number ,
    email : string ,
    phone : string ,
    bio : string ,
    imageUrl : string
}
</code>

#### PUT
modify employee by id (specify only fields to change)


##### Response
returns saved employee

<code style="background : black; width : 300px; display : block; padding : 1rem;color : green">{
    name : string,
    age : number ,
    email : string ,
    phone : string ,
    bio : string ,
    imageUrl : string
}
</code>

#### DELETE

deletes an employee

##### Response
*null*
### Endpoint 2 (/usersfiles)
returns a list of images links on the server (for developers)
