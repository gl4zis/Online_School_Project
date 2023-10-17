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

#### API description

URL: '/' (dev)
   - GET - Returns 'Hello From Spring App!' - (all access)
---
URL: '/info' (dev)
   - GET - Returns (user principal | 401) - (authorized access)
---
URL: '/login'
   - POST - Body {username, password} - Returns (JWToken | 400) - (all access)
---
URL: '/register' (student registration)
   - POST - Body {username, password, firstname, lastname,
dateOfBirth, grade} - Returns (JWToken | 400) - (all access)
---
URL: '/profile'
    - GET - Returns (your profile info | 401) - (authorized access)
    - DELETE - Returns (success message | 401) - (authorized access)
    - PATCH - *In development*
    - PUT - *In development*
---
URL: '/profile/{username}'
    - GET - Returns profile info - (all access)
---
URL: '/admin/register' (for registration admins and teachers)
    - POST - Body {username, password, role=(ADMIN | UNCONFIRMED_TEACHER)} -
Returns (JWToken | 400 | 401) - (admin access)