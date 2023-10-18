# Online School
### (Can't think of a name)

---

This is server part of my project

Written on Java, using Spring Framework

Using REST API

All used technologies:
- Java
- Spring
    - MVC
    - Data JPA
    - Security
- JSON Web Token
- PostgreSQL
- REST API

[Repository](https://github.com/gl4zis/Online_school_frontend)
of frontend app, written on Vue.js

### API description

*URL:* ```/``` (dev)
1. GET
   - Returns 'Hello From Spring App!'
   - all access
---
*URL:* ```/info``` (dev)
1. GET
   - Returns (user principal | 401)
   - authorized access
---
*URL:* ```/login```
1. POST
   - Body ```{username, password}```
   - Returns (JWToken | 400)
   - all access
---
*URL:* ```/register``` (student registration)
1. POST
   - Body ```{username, password, firstname, lastname,
   birthdate, grade}```
   - Returns (JWToken | 400)
   - all access
---
*URL:* ```/profile```
1. GET
   - Returns (your profile info | 401)
   - authorized access
2. DELETE
   - Returns (success message | 401)
   - authorized access
3. PATCH
   - *In development*
4. PUT
   - *In development*
---
*URL:* ```/profile/{username}```
1. GET
   - Returns profile info
   - all access
---
*URL:* ```/admin/register``` (for registration admins and teachers)
1. POST
   - Body ```{username, password, role=(ADMIN | UNCONFIRMED_TEACHER)}```
   - Returns (JWToken | 400 | 401)
   - admin access