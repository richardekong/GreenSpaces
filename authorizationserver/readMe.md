**GreenSpaces Authorization Service**

**Documentation**

*Richard W. Ekong*

>This application provides authorization to other GreenSpaces clients to access GreenSpaces resource through a GreenSpaces resource server. Green Spaces is an  Application  that  will  enable  users  recommend  parks,  fields  and  relevant recreational  and  communal  places  and  in  turn  enable  citizens  search  for facilities, post comments and review, view available events within these spaces.
>
**Performing Authorization through Authorization Code Grant Type**

>**cURL Request:**
>
>$ curl 'http://localhost:8080/oauth2/authorize?response\_type=code&client\_id=76e09d7a- 1095-4b47-80c5- 5f8bcba70f1d&scope=read&redirect\_uri=https://localhost:1000&code\_challenge=BUJilj4kra3 vxk6TPzZH\_yNEkBZVB9yfAvrvlpmDM3E&code\_challenge\_method=S256' -i -X GET
>

>**Httpie Request:**
>
>$ http GET 'http://localhost:8080/oauth2/authorize?response\_type=code&client\_id=76e09d7a-1095- 4b47-80c5- 5f8bcba70f1d&scope=read&redirect\_uri=https://localhost:1000&code\_challenge=BUJilj4kra3 vxk6TPzZH\_yNEkBZVB9yfAvrvlpmDM3E&code\_challenge\_method=S256'
>

>**Http Request:**
>
>GET /oauth2/authorize?response\_type=code&client\_id=76e09d7a-1095-4b47-80c5- 5f8bcba70f1d&scope=read&redirect\_uri=https://localhost:1000&code\_challenge=BUJilj4kra3 vxk6TPzZH\_yNEkBZVB9yfAvrvlpmDM3E&code\_challenge\_method=S256 HTTP/1.1 Host: localhost:8080
>

>**Http Response:**
>
>HTTP/1.1 302 Found
>
>X-Content-Type-Options: nosniff
>
>X-XSS-Protection: 0
>
>Cache-Control: no-cache, no-store, max-age=0, must-revalidate
>
>Pragma: no-cache
>
>Expires: 0
>
>X-Frame-Options: DENY
>
>Location: https://localhost:1000?code=IDG88NKf0oXs9t3FdI1BCDP9VziZsR\_aBA0KaLq4ClzVhO4zReDHVZN8W8 8l053oZOMCK5MWUElLOLAEHJlWYT5z8tbmni99BxakLTrDZTGk8N2Ucxif2iGQno\_Hqpxb
>

**Query Parameters:**

|**Parameter**|**Description**|
| - | - |
|response\_type|grant type of the client|
|client\_id|id of the client|
|**Parameter**|**Description**|
|scope|scope of user data available to the client|
|redirect\_uri|Uri that user will be directed to after successful login|
|code\_challenge|Verifier Code or Proof Key for Code Exchange (PKCE) security enhancement provided by the client|
|code\_challenge\_method|Algorithm for generating the PKCE e.g SHA-256|

**Response Header:**

| **Name**|**Description**|
|-|-|
|Location| The Current URI containing the authorization code after a successful redirect operation |


**Creating a user**

>cURL Request:
>
>$ curl 'http://localhost:8080/users?\_csrf=bJ7glA- u9qD9MOedW1ljgLTivFknBwFfvztEqlDRTnOh1HhjX\_zW9m3IlZLQBdSsanRX44HakWBCMGJyhgwhzmnkL0XE4 h5T' -i -X POST \
>
>`   `-H 'Content-Type: application/json;charset=UTF-8' \
>
>`   `-d '{
>
>` `"id" : "b583b456-9300-4cbd-4bcd-199225f5d42c",
>
>` `"username" : "user.test@mail.com",
>
>` `"password" : "13Tm31n@16351t3001",
>
>` `"roles" : [ {
>
>`   `"id" : 1,
>
>`   `"role" : "ADMIN",
>
>`   `"authorities" : [ {
>
>`     `"id" : 3,
>
>`     `"authority" : "execute"
>
>`   `}, {
>
>`     `"id" : 2,
>
>`     `"authority" : "write"
>
>`   `}, {
>
>`     `"id" : 1,
>
>`     `"authority" : "read"
>
>`   `} ]
>
>` `} ],
>
>` `"enabled" : true,
>
>` `"accountNonLocked" : true,
>
>` `"credentialsNonExpired" : true,
>
>` `"accountNonExpired" : true
>
>}'
>

Httpie Request:

$ echo '{

` `"id" : "b583b456-9300-4cbd-4bcd-199225f5d42c",

` ` "username" : "user.test@mail.com",

` `"password" : "13Tm31n@16351t3001",

` `"roles" : [ {

`   `"id" : 1,

`   `"role" : "ADMIN",

`   `"authorities" : [ {

`     `"id" : 3,

`     `"authority" : "execute"

`   `}, {

`     `"id" : 2,

`     `"authority" : "write"

`   `}, {

`     `"id" : 1,

`     `"authority" : "read"

`   `} ]

` `} ],

` `"enabled" : true,

` `"accountNonLocked" : true,

` `"credentialsNonExpired" : true,

` `"accountNonExpired" : true

}' | http POST 'http://localhost:8080/users?\_csrf=bJ7glA- u9qD9MOedW1ljgLTivFknBwFfvztEqlDRTnOh1HhjX\_zW9m3IlZLQBdSsanRX44HakWBCMGJyhgwhzmnkL0XE4 h5T' \

`   `'Content-Type:application/json;charset=UTF-8'

Http Request:

POST /users?\_csrf=bJ7glA- u9qD9MOedW1ljgLTivFknBwFfvztEqlDRTnOh1HhjX\_zW9m3IlZLQBdSsanRX44HakWBCMGJyhgwhzmnkL0XE4 h5T HTTP/1.1

Content-Type: application/json;charset=UTF-8

Content-Length: 502

Host: localhost:8080

{

` `"id" : "b583b456-9300-4cbd-4bcd-199225f5d42c",  "username" : "user.test@mail.com",

` `"password" : "13Tm31n@16351t3001",

` `"roles" : [ {

`   `"id" : 1,

`   `"role" : "ADMIN",

`   `"authorities" : [ {

`     `"id" : 3,

`     `"authority" : "execute"

`   `}, {

`     `"id" : 2,

`     `"authority" : "write"

`   `}, {

`     `"id" : 1,

`     `"authority" : "read"

`   `} ]

` `} ],

` `"enabled" : true,

` `"accountNonLocked" : true,

` `"credentialsNonExpired" : true,

` `"accountNonExpired" : true

}

HTTP/1.1 201 Created Content-Type: application/json

X-Content-Type-Options: nosniff

X-XSS-Protection: 0

Cache-Control: no-cache, no-store, max-age=0, must-revalidate Pragma: no-cache

Expires: 0

X-Frame-Options: DENY

Content-Length: 308

{"id":"b583b456-9300-4cbd-4bcd- 199225f5d42c","username":"user.test@mail.com","roles":[{"id":1,"role":"ADMIN","authorities":[{"id":3,"authority":"execute"},{"id":2,"authority":"write"},{"id":1,"authority" :"read"}]}],"enabled":true,"accountNonLocked":true,"credentialsNonExpired":true,"accountNonExpired":true}

Requests and Responses:

{

` `"id" : "b583b456-9300-4cbd-4bcd-199225f5d42c", 

` `   "username" : "user.test@mail.com",

` `"password" : "13Tm31n@16351t3001",

` `"roles" : [ {

`   `"id" : 1,

`   `"role" : "ADMIN",

`   `"authorities" : [ {

`     `"id" : 3,

`     `"authority" : "execute"

`   `}, {

`     `"id" : 2,

`     `"authority" : "write"

`   `}, {

`     `"id" : 1,

`     `"authority" : "read"

`   `} ]

` `} ],

` `"enabled" : true,

` `"accountNonLocked" : true,

` `"credentialsNonExpired" : true,

` `"accountNonExpired" : true

}



|**Path**|**Type**|**Description**|
| - | - | - |
|username|class java.lang.String|username of the user to be save|
|password|class java.lang.String|password of the user to save|
|roles|interface java.util.Set|roles of the user to be saved|
|roles.[].id|class java.lang.Integer|role id of a single role within roles of the user to be saved|
|roles.[].role|class java.lang.String|role name of a single role within roles of a single role within roles of the user to be saved|
|roles.[].authorities|interface java.util.Set|authorities within a single role of roles of the user to be saved|
|roles.[].authorities.[].id|class java.lang.Integer|id of a authority within a role of roles of the user to be saved|
|roles.[].authorities.[].author ity|class java.lang.String|authority name of an authority within a role of roles of the user to be saved|
|enabled|class java.lang.Boolean|status describing if the user account is enabled|
|credentialsNonExpired|class java.lang.Boolean|status describing the validity of user’s credentials|
|accountNonExpired|class java.lang.Boolean|Status describing user’s account validity|
|accountNonLocked|class java.lang.Boolean|Status describing whether user’s account is locked or unlocked|

{"id":"b583b456-9300-4cbd-4bcd- 199225f5d42c","username":"user.test@mail.com","roles":[{"id":1,"role":"ADMIN","authorities":[{"id":3,"authority":"execute"},{"id":2,"authority":"write"},{"id":1,"authority" :"read"}]}],"enabled":true,"accountNonLocked":true,"credentialsNonExpired":true,"accountNonExpired":true}

**Retrieving a user by id**

cURL Request:

$ curl 'http://localhost:8080/users/b583b456-9300-4cbd-4bcd-199225f5d42c' -i -X GET \    -H 'Content-Type: application/json;charset=UTF-8'

Httpie Request:

$ http GET 'http://localhost:8080/users/b583b456-9300-4cbd-4bcd-199225f5d42c' \    'Content-Type:application/json;charset=UTF-8'

Http Request:

GET /users/b583b456-9300-4cbd-4bcd-199225f5d42c HTTP/1.1 Content-Type: application/json;charset=UTF-8

Host: localhost:8080

HTTP/1.1 200 OK

Content-Type: application/json

X-Content-Type-Options: nosniff

X-XSS-Protection: 0

Cache-Control: no-cache, no-store, max-age=0, must-revalidate Pragma: no-cache

Expires: 0

X-Frame-Options: DENY

Content-Length: 308

{"id":"b583b456-9300-4cbd-4bcd- 199225f5d42c","username":"user.test@mail.com","roles":[{"id":1,"role":"ADMIN","authorities":[{"id":3,"authority":"execute"},{"id":2,"authority":"write"},{"id":1,"authority" :"read"}]}],"enabled":true,"accountNonLocked":true,"credentialsNonExpired":true,"accountNonExpired":true}

Request Parameters:

*Table 1. /users/{id}*

|**Parameter**|**Description**|
|- | - |
|id|UUID of the user to retrieve|

Requests and Responses:

{"id":"b583b456-9300-4cbd-4bcd- 199225f5d42c","username":"user.test@mail.com","roles":[{"id":1,"role":"ADMIN","authorities":[{"id":3,"authority":"execute"},{"id":2,"authority":"write"},{"id":1,"authority" :"read"}]}],"enabled":true,"accountNonLocked":true,"credentialsNonExpired":true,"accountNonExpired":true}

|**Path**|**Type**|**Description**|
| - | - | - |
|id|String|UUID of the retrieved user|
|username|class java.lang.String|username of the user to be save|
|roles|interface java.util.Set|roles of the user to be saved|
|roles.[].id|class java.lang.Integer|role id of a single role within roles of the user to be saved|
|roles.[].role|class java.lang.String|role name of a single role within roles of a single role within roles of the user to be saved|
|roles.[].authorities|interface java.util.Set|authorities within a single role of roles of the user to be saved|
|roles.[].authorities.[].id|class java.lang.Integer|id of a authority within a role of roles of the user to be saved|
|roles.[].authorities.[].author ity|class java.lang.String|authority name of an authority within a role of roles of the user to be saved|
|enabled|class java.lang.Boolean|status describing if the user account is enabled|
|credentialsNonExpired|class java.lang.Boolean|status describing the validity of user’s credentials|
|accountNonExpired|class java.lang.Boolean|Status describing user’s account validity|
|accountNonLocked|class java.lang.Boolean|Status describing whether user’s account is locked or unlocked|


**Retrieving a user’s Role**

cURL Request:

$ curl 'http://localhost:8080/users/b583b456-9300-4cbd-4bcd-199225f5d42c/roles/1' -i -X GET \

`   `-H 'Content-Type: application/json;charset=UTF-8'

Httpie Request:

$ http GET 'http://localhost:8080/users/b583b456-9300-4cbd-4bcd-199225f5d42c/roles/1' \

`   `'Content-Type:application/json;charset=UTF-8'

Http Request:

GET /users/b583b456-9300-4cbd-4bcd-199225f5d42c/roles/1 HTTP/1.1 Content-Type: application/json;charset=UTF-8

Host: localhost:8080

HTTP/1.1 200 OK

Content-Type: application/json

X-Content-Type-Options: nosniff

X-XSS-Protection: 0

Cache-Control: no-cache, no-store, max-age=0, must-revalidate Pragma: no-cache

Expires: 0

X-Frame-Options: DENY

Content-Length: 127

{"id":1,"role":"ADMIN","authorities":[{"id":3,"authority":"execute"},{"id":2,"authority":"write"},{"id":1,"authority":"read"}]}

Request Parameters:

*Table 2. /users/{id}/roles/{roleId}*



|**Parameter**|**Description**|
| - | - |
|id|UUID of the user with role to retrieve|
|roleId|id of role to retrieve|

Requests and Responses:

{"id":1,"role":"ADMIN","authorities":[{"id":3,"authority":"execute"},{"id":2,"authorit y":"write"},{"id":1,"authority":"read"}]}



|**Path**|**Type**|**Description**|
| - | - | - |
|id|class java.lang.Integer|id of a role|
|role|class java.lang.String|role name|
|authorities|interface java.util.Set|authorities within this role|
|authorities.[].id|class java.lang.Integer|id of an authority within a role of roles|
|authorities.[].authority|class java.lang.String|authority name of an authority within a role|


**Retrieving a user’s Roles**

cURL Request:

$ curl 'http://localhost:8080/users/b583b456-9300-4cbd-4bcd-199225f5d42c/roles' -i -X GET \

`   `-H 'Content-Type: application/json;charset=UTF-8'

Httpie Request:

$ http GET 'http://localhost:8080/users/b583b456-9300-4cbd-4bcd-199225f5d42c/roles' \    'Content-Type:application/json;charset=UTF-8'

Http Request:

GET /users/b583b456-9300-4cbd-4bcd-199225f5d42c/roles HTTP/1.1 Content-Type: application/json;charset=UTF-8

Host: localhost:8080

HTTP/1.1 200 OK

Content-Type: application/json

X-Content-Type-Options: nosniff

X-XSS-Protection: 0

Cache-Control: no-cache, no-store, max-age=0, must-revalidate Pragma: no-cache

Expires: 0

X-Frame-Options: DENY

Content-Length: 129

[{"id":1,"role":"ADMIN","authorities":[{"id":3,"authority":"execute"},{"id":2,"authori ty":"write"},{"id":1,"authority":"read"}]}]

Request Parameters:

*Table 3. /users/{id}/roles*

|**Parameter**|**Description**|
| - | - |
|id| UUID of the user to retrieve|

Requests and Responses:

[{"id":1,"role":"ADMIN","authorities":[{"id":3,"authority":"execute"},{"id":2,"authority":"write"},{"id":1,"authority":"read"}]}]



|**Path**|**Type**|**Description**|
| - | - | - |
|[].id|class java.lang.Integer|id of a role within roles of the user|
|[].role|class java.lang.String|role name of role within roles of a user|
|[].authorities|interface java.util.Set|authorities within a single role of roles|
|[].authorities.[].id|class java.lang.Integer|id of authority within a role of roles|
|[].authorities.[].authority|class java.lang.String|authority name of an authority within a role of roles|

**Retrieving a User’s Authority**

cURL Request:

$ curl 'http://localhost:8080/users/b583b456-9300-4cbd-4bcd- 199225f5d42c/roles/1/authorities/1' -i -X GET \!

`   `-H 'Content-Type: application/json;charset=UTF-8'

Httpie Request:

$ http GET 'http://localhost:8080/users/b583b456-9300-4cbd-4bcd- 199225f5d42c/roles/1/authorities/1' \    'Content-Type:application/json;charset=UTF-8'

Http Request:

GET /users/b583b456-9300-4cbd-4bcd-199225f5d42c/roles/1/authorities/1 HTTP/1.1 Content-Type: application/json;charset=UTF-8

Host: localhost:8080

HTTP/1.1 200 OK!

Content-Type: application/json

X-Content-Type-Options: nosniff

X-XSS-Protection: 0

Cache-Control: no-cache, no-store, max-age=0, must-revalidate Pragma: no-cache

Expires: 0

X-Frame-Options: DENY

Content-Length: 27

{"id":1,"authority":"read"}

Request Parameters:

*Table 4. /users/{id}/roles/{roleId}/authorities/{authorityId}*



|**Parameter**|**Description**|
| - | - |
|id|UUID of the user with authority to retrieve|
|roleId|id of role associated with the authority to be retrieved|
|authorityId|id of the authority to retrieve|

Requests and Responses:

{"id":1,"authority":"read"}

|**Path**|**Type**|**Description**|
| - | - | - |
|id|class java.lang.Integer|id of the authority to retrieve|
|authority|class java.lang.String|authority name of an authority to retrieve|

15
**Retrieving a User’s Authorities**

cURL Request:

$ curl 'http://localhost:8080/users/b583b456-9300-4cbd-4bcd- 199225f5d42c/roles/authorities' -i -X GET \

`   `-H 'Content-Type: application/json;charset=UTF-8'

Httpie Request:

$ http GET 'http://localhost:8080/users/b583b456-9300-4cbd-4bcd- 199225f5d42c/roles/authorities' \    'Content-Type:application/json;charset=UTF-8'

Http Request:

GET /users/b583b456-9300-4cbd-4bcd-199225f5d42c/roles/authorities HTTP/1.1 Content-Type: application/json;charset=UTF-8

Host: localhost:8080

HTTP/1.1 200 OK

Content-Type: application/json

X-Content-Type-Options: nosniff

X-XSS-Protection: 0

Cache-Control: no-cache, no-store, max-age=0, must-revalidate Pragma: no-cache

Expires: 0

X-Frame-Options: DENY

Content-Length: 89

[{"id":3,"authority":"execute"},{"id":2,"authority":"write"},{"id":1,"authority":"read "}]

Request Parameters:

*Table 5. /users/{id}/roles/authorities*

|**Parameter**|**Description**|
|-|-|
|id|id of the user associated with retrieved authorities|

Requests and Responses:

[{"id":3,"authority":"execute"},{"id":2,"authority":"write"},{"id":1,"authority":"read "}]



|**Path**|**Type**|**Description**|
| - | - | - |
|[].id|Number|id of an authority within list of authorities retrieved|
|[].authority|String|authority name of an authority within list of authorities retrieved|


**Retrieving all Users**

cURL Request:

$ curl 'http://localhost:8080/users' -i -X GET \

`   `-H 'Content-Type: application/json;charset=UTF-8'

Httpie Request:

$ http GET 'http://localhost:8080/users' \    'Content-Type:application/json;charset=UTF-8'

Http Request:

GET /users HTTP/1.1

Content-Type: application/json;charset=UTF-8 Host: localhost:8080

HTTP/1.1 200 OK

Content-Type: application/json

X-Content-Type-Options: nosniff

X-XSS-Protection: 0

Cache-Control: no-cache, no-store, max-age=0, must-revalidate Pragma: no-cache

Expires: 0

X-Frame-Options: DENY

Content-Length: 310

[{"id":"b583b456-9300-4cbd-4bcd- 199225f5d42c","username":"user.test@mail.com","roles":[{"id":1,"role":"ADMIN","authorities":[{"id":3,"authority":"execute"},{"id":2,"authority":"write"},{"id":1,"authority" :"read"}]}],"enabled":true,"accountNonLocked":true,"credentialsNonExpired":true,"accountNonExpired":true}]

Requests and Responses:

[{"id":"b583b456-9300-4cbd-4bcd- 199225f5d42c","username":"user.test@mail.com","roles":[{"id":1,"role":"ADMIN","authorities":[{"id":3,"authority":"execute"},{"id":2,"authority":"write"},{"id":1,"authority" :"read"}]}],"enabled":true,"accountNonLocked":true,"credentialsNonExpired":true,"accountNonExpired":true}]

|**Path**|**Type**|**Description**|
| - | - | - |
|[].id|String|UUID of the retrieved user|
|[].username|class java.lang.String|username of the user to be save|
|[].roles|interface java.util.Set|roles of the user to be saved|
|[].roles.[].id|class java.lang.Integer|role id of a single role within roles of the user to be saved|
|[].roles.[].role|class java.lang.String|role name of a single role within roles of a single role within roles of the user to be saved|
|[].roles.[].authorities|interface java.util.Set|authorities within a single role of roles of the user to be saved|
|[].roles.[].authorities.[].id|class java.lang.Integer|id of a authority within a role of roles of the user to be saved|
|[].roles.[].authorities.[].aut hority|class java.lang.String|authority name of an authority within a role of roles of the user to be saved|
|[].enabled|class java.lang.Boolean|status describing if the user account is enabled|
|[].credentialsNonExpired|class java.lang.Boolean|status describing the validity of user’s credentials|
|[].accountNonExpired|class java.lang.Boolean|Status describing user’s account validity|
|[].accountNonLocked|class java.lang.Boolean|Status describing whether user’s account is locked or unlocked|

**Updating a User (Adding Authorities to User)**

cURL Request:

$ curl 'http://localhost:8080/users/b583b456-9300-4cbd-4bcd- 199225f5d42c/roles/1/authorities?\_csrf=nj38kU54vdi2ABxRNfFp-D4xkFtfCzLL- hHW8hE4zn524Tg5rwXI9HpA2O2bN3llDdxdzQYFvTk5bVbmyyC0wXUJrxtD2FkA' -i -X PATCH \    -H 'Content-Type: application/json;charset=UTF-8' \

`   `-d '[ {

` `"id" : 2,

` `"authority" : "update"

} ]'

Httpie Request:

$ echo '[ {

` `"id" : 2,

` `"authority" : "update"

} ]' | http PATCH 'http://localhost:8080/users/b583b456-9300-4cbd-4bcd- 199225f5d42c/roles/1/authorities?\_csrf=nj38kU54vdi2ABxRNfFp-D4xkFtfCzLL- hHW8hE4zn524Tg5rwXI9HpA2O2bN3llDdxdzQYFvTk5bVbmyyC0wXUJrxtD2FkA' \    'Content-Type:application/json;charset=UTF-8'

Http Request:

PATCH /users/b583b456-9300-4cbd-4bcd- 199225f5d42c/roles/1/authorities?\_csrf=nj38kU54vdi2ABxRNfFp-D4xkFtfCzLL- hHW8hE4zn524Tg5rwXI9HpA2O2bN3llDdxdzQYFvTk5bVbmyyC0wXUJrxtD2FkA HTTP/1.1 Content-Type: application/json;charset=UTF-8

Content-Length: 47

Host: localhost:8080

[ {

` `"id" : 2,

` `"authority" : "update" 

}]

HTTP/1.1 200 OK

X-Content-Type-Options: nosniff

X-XSS-Protection: 0

Cache-Control: no-cache, no-store, max-age=0, must-revalidate Pragma: no-cache

Expires: 0

X-Frame-Options: DENY

Request Parameters:

*Table 6. /users/{id}/roles/{roleId}/authorities*



|**Parameter**|**Description**|
| - | - |
|id|UUID of the user adding authorities|
|roleId|id of the role to add the authorities|

Requests and Responses:

[ {

` `"id" : 2,

` `"authority" : "update" 

} ]



|**Path**|**Type**|**Description**|
| - | - | - |
|[].authority|String|authority name of authority within authorities to add|


**Updating a User (Adding Roles to User)**

cURL Request:

$ curl 'http://localhost:8080/users/b583b456-9300-4cbd-4bcd- 199225f5d42c/roles?\_csrf=tBZRHLLak1k6Hb7eQi87ky43i37Zeo2LajitQf1hYUAS2rX\_giBjKtHi8mEXe 4\_pdwIPpUpUpka4HO6mUlmYc84CU3YkuITG' -i -X PATCH \

`   `-H 'Content-Type: application/json;charset=UTF-8' \

`   `-d '[ {

` `"id" : 2,

` `"role" : "USER",

` `"authorities" : null

} ]'

Httpie Request:

$ echo '[ {

` `"id" : 2,

` `"role" : "USER",

` `"authorities" : null

} ]' | http PATCH 'http://localhost:8080/users/b583b456-9300-4cbd-4bcd- 199225f5d42c/roles?\_csrf=tBZRHLLak1k6Hb7eQi87ky43i37Zeo2LajitQf1hYUAS2rX\_giBjKtHi8mEXe 4\_pdwIPpUpUpka4HO6mUlmYc84CU3YkuITG' \

`   `'Content-Type:application/json;charset=UTF-8'

Http Request:

PATCH /users/b583b456-9300-4cbd-4bcd- 199225f5d42c/roles?\_csrf=tBZRHLLak1k6Hb7eQi87ky43i37Zeo2LajitQf1hYUAS2rX\_giBjKtHi8mEXe 4\_pdwIPpUpUpka4HO6mUlmYc84CU3YkuITG HTTP/1.1

Content-Type: application/json;charset=UTF-8

Content-Length: 65

Host: localhost:8080

[ {

` `"id" : 2,

` `"role" : "USER",  

` `"authorities" : null 

} ]

HTTP/1.1 200 OK

X-Content-Type-Options: nosniff

X-XSS-Protection: 0

Cache-Control: no-cache, no-store, max-age=0, must-revalidate Pragma: no-cache

Expires: 0

X-Frame-Options: DENY

Request Parameters: *Table 7. /users/{id}/roles*



|**Parameter**|**Description**|
| - | - |
|id|UUID of the user with roles to be retrieved|

Requests and Responses:

[{

` `"id" : 2,

` `"role" : "USER", 

` `"authorities" : null 

}]

**Updating a User’s account status**

cURL Request:

$ curl 'http://localhost:8080/users/accounts/b583b456-9300-4cbd-4bcd- 199225f5d42c?\_csrf=msYQuHhBciXcnniaTFNysimVajiIAl49dxbVxShRZ1SuLQZ3-fQnjhx0RRzxqxr- fn5GgR6hR1m\_MmoQQHPtoxllAW2XGzNF' -i -X PATCH \

`   `-H 'Content-Type: application/json;charset=UTF-8' \

`   `-d '{

` `"id" : null,

` `"username" : "user.test@mail.com",

` `"password" : null,

` `"roles" : null,

` `"enabled" : true,

` `"accountNonLocked" : true,

` `"credentialsNonExpired" : true,

` `"accountNonExpired" : true

}'

Httpie Request:

$ echo '{

` `"id" : null,

` `"username" : "user.test@mail.com",

` `"password" : null,

` `"roles" : null,

` `"enabled" : true,

` `"accountNonLocked" : true,

` `"credentialsNonExpired" : true,

` `"accountNonExpired" : true

}' | http PATCH 'http://localhost:8080/users/accounts/b583b456-9300-4cbd-4bcd- 199225f5d42c?\_csrf=msYQuHhBciXcnniaTFNysimVajiIAl49dxbVxShRZ1SuLQZ3-fQnjhx0RRzxqxr- fn5GgR6hR1m\_MmoQQHPtoxllAW2XGzNF' \

`   `'Content-Type:application/json;charset=UTF-8'

Http Request:

PATCH /users/accounts/b583b456-9300-4cbd-4bcd- 199225f5d42c?\_csrf=msYQuHhBciXcnniaTFNysimVajiIAl49dxbVxShRZ1SuLQZ3-fQnjhx0RRzxqxr- fn5GgR6hR1m\_MmoQQHPtoxllAW2XGzNF HTTP/1.1

Content-Type: application/json;charset=UTF-8

Content-Length: 215

Host: localhost:8080

{

` `"id" : null,

` `"username" : "user.test@mail.com",

` `"password" : null,

` `"roles" : null,

` `"enabled" : true,

` `"accountNonLocked" : true,

` `"credentialsNonExpired" : true,  "accountNonExpired" : true

}

HTTP/1.1 200 OK

X-Content-Type-Options: nosniff

X-XSS-Protection: 0

Cache-Control: no-cache, no-store, max-age=0, must-revalidate Pragma: no-cache

Expires: 0

X-Frame-Options: DENY

Request Parameters:

*Table 8. /users/accounts/{id}*



|**Parameter**|**Description**|
| - | - |
|id|Id of the user account status to update|

Requests and Responses:

{

` `"id" : null,

` `"username" : "user.test@mail.com",

` `"password" : null,

` `"roles" : null,

` `"enabled" : true,

` `"accountNonLocked" : true,

` `"credentialsNonExpired" : true,

` `"accountNonExpired" : true

}

25
**Updating a User’s Password**

cURL Request:

$ curl 'http://localhost:8080/users/b583b456-9300-4cbd-4bcd- 199225f5d42c/accounts/password-reset?\_csrf=ApmB7tFQ2lj89oJQjoLvT8- \_q8pm97anfZmBsLOhCBgF78NLMv2x2bJn7GzRx7dpvq\_bLfyIhvIFwtCKTf-2g4KVaSAz3vR-' -i -X PATCH \

`   `-H 'Content-Type: application/json;charset=UTF-8' \

`   `-d '{

` `"id" : null,

` `"username" : "user.test@mail.com",

` `"password" : "P@55w07d4t35t",

` `"roles" : null,

` `"enabled" : false,

` `"accountNonLocked" : false,

` `"credentialsNonExpired" : false,

` `"accountNonExpired" : false

}'

Httpie Request:

$ echo '{

` `"id" : null,

` `"username" : "user.test@mail.com",

` `"password" : "P@55w07d4t35t",

` `"roles" : null,

` `"enabled" : false,

` `"accountNonLocked" : false,

` `"credentialsNonExpired" : false,

` `"accountNonExpired" : false

}' | http PATCH 'http://localhost:8080/users/b583b456-9300-4cbd-4bcd- 199225f5d42c/accounts/password-reset?\_csrf=ApmB7tFQ2lj89oJQjoLvT8- \_q8pm97anfZmBsLOhCBgF78NLMv2x2bJn7GzRx7dpvq\_bLfyIhvIFwtCKTf-2g4KVaSAz3vR-' \    'Content-Type:application/json;charset=UTF-8'

Http Request:

PATCH /users/b583b456-9300-4cbd-4bcd-199225f5d42c/accounts/password- reset?\_csrf=ApmB7tFQ2lj89oJQjoLvT8- \_q8pm97anfZmBsLOhCBgF78NLMv2x2bJn7GzRx7dpvq\_bLfyIhvIFwtCKTf-2g4KVaSAz3vR- HTTP/1.1 Content-Type: application/json;charset=UTF-8

Content-Length: 230

Host: localhost:8080

{

` `"id" : null,

` `"username" : "user.test@mail.com",

` `"password" : "P@55w07d4t35t",

` `"roles" : null,

` `"enabled" : false,

` `"accountNonLocked" : false,

` `"credentialsNonExpired" : false,

` `"accountNonExpired" : false

}

HTTP/1.1 200 OK
X-Content-Type-Options: nosniff

X-XSS-Protection: 0

Cache-Control: no-cache, no-store, max-age=0, must-revalidate Pragma: no-cache

Expires: 0

X-Frame-Options: DENY

Request Parameters:

*Table 9. /users/{id}/accounts/password-reset*

|**Parameter**|**Description**|
| - | - |
|id| Id of the user with password to reset|

Requests and Responses:

{

` `"id" : null,

` `"username" : "user.test@mail.com",

` `"password" : "P@55w07d4t35t",

` `"roles" : null,

` `"enabled" : false,

` `"accountNonLocked" : false,

` `"credentialsNonExpired" : false,

` `"accountNonExpired" : false

}



|**Path**|**Type**|**Description**|
| - | - | - |
|username|class java.lang.String|username of the user to be save|
|password|class java.lang.String|password of the user to save|


**Deleting a User**

cURL Request:

$ curl 'http://localhost:8080/users/b583b456-9300-4cbd-4bcd-199225f5d42c' -i -X DELETE \

`   `-H 'Content-Type: application/json;charset=UTF-8'

Httpie Request:

$ http DELETE 'http://localhost:8080/users/b583b456-9300-4cbd-4bcd-199225f5d42c' \    'Content-Type:application/json;charset=UTF-8'

Http Request:

DELETE /users/b583b456-9300-4cbd-4bcd-199225f5d42c HTTP/1.1 Content-Type: application/json;charset=UTF-8

Host: localhost:8080

HTTP/1.1 200 OK

X-Content-Type-Options: nosniff

X-XSS-Protection: 0

Cache-Control: no-cache, no-store, max-age=0, must-revalidate Pragma: no-cache

Expires: 0

X-Frame-Options: DENY

Request Parameters:

*Table 10. /users/{id}*

**Parameter Description

id Id of the user to delete

Requests and Responses:

**Creating a Client**

cURL Request:

$ curl 'http://localhost:8080/clients?\_csrf=QpzkTIHKtOPQ9SYD1YmqyNuCX5hzk5xc6VUz3bBwR4G- pYOdJ6XXL7Kv19b9xkU34qSe\_Om2cqEXo6xxj2QC5IgWJbDYkLes' -i -X POST \

`   `-H 'Content-Type: application/json;charset=UTF-8' \

`   `-d '{

` `"id" : "001i",

` `"clientId" : "001ci",

` `"name" : "Client03",

` `"secret" : "53cr3t50$9feW1t6m3",

` `"authenticationMethods" : [ {

`   `"id" : 1,

`   `"method" : "client\_secret\_basic"

` `} ],

` `"grantTypes" : [ {

`   `"id" : 1,

`   `"grantType" : "authorization\_code"

` `} ],

` `"redirectUris" : [ {

`   `"id" : 1,

`   `"uri" : "http://127.0.0.1:1000"

` `} ],

` `"scopes" : [ {

`   `"id" : 1,

`   `"scope" : "openid"

` `} ],

` `"tokenSettings" : {

`   `"id" : 1,

`   `"format" : "self-contained",

`   `"accessTokenTTL" : 30

` `}

}'

Httpie Request:

$ echo '{

` `"id" : "001i",

` `"clientId" : "001ci",

` `"name" : "Client03",

` `"secret" : "53cr3t50$9feW1t6m3",

` `"authenticationMethods" : [ {

`   `"id" : 1,

`   `"method" : "client\_secret\_basic"  } ],

` `"grantTypes" : [ {

`   `"id" : 1,

`   `"grantType" : "authorization\_code"

` `} ],

` `"redirectUris" : [ {

`   `"id" : 1,

`   `"uri" : "http://127.0.0.1:1000"

` `} ],

` `"scopes" : [ {

`   `"id" : 1,

`   `"scope" : "openid"

` `} ],

` `"tokenSettings" : {

`   `"id" : 1,

`   `"format" : "self-contained",

`   `"accessTokenTTL" : 30

` `}

}' | http POST 'http://localhost:8080/clients?\_csrf=QpzkTIHKtOPQ9SYD1YmqyNuCX5hzk5xc6VUz3bBwR4G- pYOdJ6XXL7Kv19b9xkU34qSe\_Om2cqEXo6xxj2QC5IgWJbDYkLes' \

`   `'Content-Type:application/json;charset=UTF-8'

Http Request:

POST /clients?\_csrf=QpzkTIHKtOPQ9SYD1YmqyNuCX5hzk5xc6VUz3bBwR4G- pYOdJ6XXL7Kv19b9xkU34qSe\_Om2cqEXo6xxj2QC5IgWJbDYkLes HTTP/1.1 Content-Type: application/json;charset=UTF-8

Content-Length: 539

Host: localhost:8080

{

` `"id" : "001i",

` `"clientId" : "001ci",

` `"name" : "Client03",

` `"secret" : "53cr3t50$9feW1t6m3",

` `"authenticationMethods" : [ {

`   `"id" : 1,

`   `"method" : "client\_secret\_basic"

` `} ],

` `"grantTypes" : [ {

`   `"id" : 1,

`   `"grantType" : "authorization\_code"  } ],

` `"redirectUris" : [ {

`   `"id" : 1,

`   `"uri" : "http://127.0.0.1:1000"

` `} ],

` `"scopes" : [ {

`   `"id" : 1,

`   `"scope" : "openid"

` `} ],

` `"tokenSettings" : {

`   `"id" : 1,

`   `"format" : "self-contained",    "accessTokenTTL" : 30

` `}

}

HTTP/1.1 201 Created

Content-Type: application/json

X-Content-Type-Options: nosniff

X-XSS-Protection: 0

Cache-Control: no-cache, no-store, max-age=0, must-revalidate Pragma: no-cache

Expires: 0

X-Frame-Options: DENY

Content-Length: 337

{"id":"001i","clientId":"001ci","name":"Client03","authenticationMethods":[{"id":1,"me thod":"client\_secret\_basic"}],"grantTypes":[{"id":1,"grantType":"authorization\_code"}] ,"redirectUris":[{"id":1,"uri":"http://127.0.0.1:1000"}],"scopes":[{"id":1,"scope":"op enid"}],"tokenSettings":{"id":1,"format":"self-contained","accessTokenTTL":30}}

Requests and Responses:

{

` `"id" : "001i",

` `"clientId" : "001ci",

` `"name" : "Client03",

` `"secret" : "53cr3t50$9feW1t6m3",

` `"authenticationMethods" : [ {

`   `"id" : 1,

`   `"method" : "client\_secret\_basic"

` `} ],

` `"grantTypes" : [ {

`   `"id" : 1,

`   `"grantType" : "authorization\_code"  } ],

` `"redirectUris" : [ {

`   `"id" : 1,

`   `"uri" : "http://127.0.0.1:1000"

` `} ],

` `"scopes" : [ {

`   `"id" : 1,

`   `"scope" : "openid"

` `} ],

` `"tokenSettings" : {

`   `"id" : 1,

`   `"format" : "self-contained",

`   `"accessTokenTTL" : 30  }

}



|**Path**|**Type**|**Description**|
| - | - | - |
|name|class java.lang.String|Desired client name|
|secret|class java.lang.String|Desired client secret|
|authenticationMethods|interface java.util.Set|Authentication Methods of the client|
|authenticationMethods.[].metho d|class java.lang.String|Authentication method name of an authentication method of the client|
|grantTypes|interface java.util.Set|Desired list/set of auth2.0 authentication grant types|
|grantTypes.[].grantType|class java.lang.String|authorization grant type name of a grant type within a list of grant types|
|redirectUris|class java.lang.String|Desired list/set of redirect URIs|
|redirectUris.[].uri|class java.lang.String|redirect uri within a list of redirect uris|
|scopes|interface java.util.Set|Desired oauth2.0 authentication scopes|
|scopes.[].scope|class java.lang.String|Scope’s name within a list of scopes|
|tokenSettings|class com.daveace.greenspaces.tokens ettings.TokenSettings|Desired oauth2.0 token settings|
|tokenSettings.format|String|Format of the token from the authorization server|
|tokenSettings.accessTokenTTL|Number|Time to live or expiry time of an access token|

{"id":"001i","clientId":"001ci","name":"Client03","authenticationMethods":[{"id":1,"me thod":"client\_secret\_basic"}],"grantTypes":[{"id":1,"grantType":"authorization\_code"}] ,"redirectUris":[{"id":1,"uri":"http://127.0.0.1:1000"}],"scopes":[{"id":1,"scope":"op enid"}],"tokenSettings":{"id":1,"format":"self-contained","accessTokenTTL":30}}



|**Path**|**Type**|**Description**|
| - | - | - |
|id|String|id of created client|
|clientId|String|client id of created client|





|**Path**|**Type**|**Description**|
| - | - | - |
|name|String|name of created client|
|authenticationMethods|interface java.util.Set|Authentication Methods of the client|
|authenticationMethods.[].metho d|class java.lang.String|Authentication method name of an authentication method of the client|
|grantTypes|Array|Grant types of the created clients|
|grantTypes.[].grantType|class java.lang.String|authorization grant type name of a grant type within a list of grant types|
|redirectUris|Array|Redirect URIs of the created client|
|redirectUris.[].uri|class java.lang.String|redirect uri within a list of redirect uris|
|scopes|Array|Oauth2.0 Scopes of the create client|
|scopes.[].scope|class java.lang.String|Scope’s name within a list of scopes|
|tokenSettings|Object|Token Settings of the create client|
|tokenSettings.format|String|Format of the token from the authorization server|
|tokenSettings.accessTokenTTL|Number|Time to live or expiry time of an access token|


**Retrieving a Client**

cURL Request:

$ curl 'http://localhost:8080/clients/001i' -i -X GET \    -H 'Content-Type: application/json;charset=UTF-8'

Httpie Request:

$ http GET 'http://localhost:8080/clients/001i' \    'Content-Type:application/json;charset=UTF-8'

Http Request:

GET /clients/001i HTTP/1.1

Content-Type: application/json;charset=UTF-8 Host: localhost:8080

HTTP/1.1 200 OK

Content-Type: application/json

X-Content-Type-Options: nosniff

X-XSS-Protection: 0

Cache-Control: no-cache, no-store, max-age=0, must-revalidate Pragma: no-cache

Expires: 0

X-Frame-Options: DENY

Content-Length: 337

{"id":"001i","clientId":"001ci","name":"Client03","authenticationMethods":[{"id":1,"me thod":"client\_secret\_basic"}],"grantTypes":[{"id":1,"grantType":"authorization\_code"}] ,"redirectUris":[{"id":1,"uri":"http://127.0.0.1:1000"}],"scopes":[{"id":1,"scope":"op enid"}],"tokenSettings":{"id":1,"format":"self-contained","accessTokenTTL":30}}

Request Parameters:

*Table 11. /clients/{id}*

|**Parameter**|**Description**|
|-|-|
|id| UUID of client to retrieve|

Requests and Responses:

{"id":"001i","clientId":"001ci","name":"Client03","authenticationMethods":[{"id":1,"me thod":"client\_secret\_basic"}],"grantTypes":[{"id":1,"grantType":"authorization\_code"}] ,"redirectUris":[{"id":1,"uri":"http://127.0.0.1:1000"}],"scopes":[{"id":1,"scope":"op enid"}],"tokenSettings":{"id":1,"format":"self-contained","accessTokenTTL":30}}


|**Path**|**Type**|**Description**|
| - | - | - |
|id|String|id of retrieved client|
|clientId|String|client id of retrieved client|
|name|String|name of retrieved client|
|authenticationMethods|interface java.util.Set|Authentication Methods of the retrieved client|
|authenticationMethods.[].id|class java.lang.Integer|Id of an authentication method of the retrieved client|
|authenticationMethods.[].metho d|class java.lang.String|Authentication method name of an authentication method in the retrieved client|
|grantTypes|Array|Grant types of the retrieved clients|
|grantTypes.[].grantType|class java.lang.String|authorization grant type name of a grant type within a list of grant types|
|redirectUris|Array|Redirect URIs of the retrieved client|
|redirectUris.[].uri|class java.lang.String|redirect uri within a list of redirect uris|
|scopes|Array|Oauth2.0 Scopes of the retrieved client|
|scopes.[].scope|class java.lang.String|Scope’s name within a list of scopes|
|tokenSettings|Object|Token Settings of the retrieved client|
|tokenSettings.id|Number|id of the token setting from the authorization server|
|tokenSettings.format|String|Format of the token from the authorization server|
|tokenSettings.accessTokenTTL|Number|Time to live or expiry time of an access token|


**Retrieving all Clients**

cURL Request:

$ curl 'http://localhost:8080/clients' -i -X GET \![ref2]

`   `-H 'Content-Type: application/json;charset=UTF-8'

Httpie Request:

$ http GET 'http://localhost:8080/clients' \    'Content-Type:application/json;charset=UTF-8'

Http Request:

GET /clients HTTP/1.1

Content-Type: application/json;charset=UTF-8 Host: localhost:8080

HTTP/1.1 200 OK

Content-Type: application/json

X-Content-Type-Options: nosniff

X-XSS-Protection: 0

Cache-Control: no-cache, no-store, max-age=0, must-revalidate Pragma: no-cache

Expires: 0

X-Frame-Options: DENY

Content-Length: 339

[{"id":"001i","clientId":"001ci","name":"Client03","authenticationMethods":[{"id":1,"m ethod":"client\_secret\_basic"}],"grantTypes":[{"id":1,"grantType":"authorization\_code"} ],"redirectUris":[{"id":1,"uri":"http://127.0.0.1:1000"}],"scopes":[{"id":1,"scope":"openid"}],"tokenSettings":{"id":1,"format":"self-contained","accessTokenTTL":30}}]

Requests and Responses:

[{"id":"001i","clientId":"001ci","name":"Client03","authenticationMethods":[{"id":1,"method":"client\_secret\_basic"}],"grantTypes":[{"id":1,"grantType":"authorization\_code"} ],"redirectUris":[{"id":1,"uri":"http://127.0.0.1:1000"}],"scopes":[{"id":1,"scope":"o penid"}],"tokenSettings":{"id":1,"format":"self-contained","accessTokenTTL":30}}]

|**Path**|**Type**|**Description**|
| - | - | - |
|[].id|class java.lang.Integer|id of retrieved client within a list of clients|
|[].clientId|class java.lang.String|client id of a retrieved client within a list of clients|
|[].name|class java.lang.String|name of a retrieved client within a list of clients|
|[].authenticationMethods|interface java.util.Set|Authentication Methods of a retrieved client within a list of clients|
|[].authenticationMethods.[].id|class java.lang.Integer|id of one of the authentication methods of a retrieved client within a list of clients|
|[].authenticationMethods.[].me thod|class java.lang.String|One of the authentication methods' name of a retrieved client within a list of clients|
|[].grantTypes|interface java.util.Set|Grant types of a retrieved client within a list of clients|
|[].grantTypes.[].id|class java.lang.Integer|id of a Grant type of the retrieved clients|
|[].grantTypes.[].grantType|class java.lang.String|Grant type name of one of the retrieved clients|
|[].redirectUris|interface java.util.Set|Redirect URIs of a retrieved client within a list of clients|
|[].redirectUris.[].uri|class java.lang.String|redirect uri within a list of redirect uris|
|[].scopes|interface java.util.Set|Oauth2.0 Scopes of a retrieved client within a list of clients|
|[].scopes.[].scope|class java.lang.String|Scope’s name within a list of scopes|
|[].tokenSettings|class com.daveace.greenspaces.tokens ettings.TokenSettings|Token Settings of a retrieved client within a list of clients|
|[].tokenSettings.id|class java.lang.Integer|id of a token setting of a retrieved client within a list of clients|
|[].tokenSettings.format|class java.lang.String|Format of the token from the authorization server|
|[].tokenSettings.accessTokenTT L|class java.lang.Integer|Time to live or expiry time of an access token|


**Updating a Client**

cURL Request:

$ curl 'http://localhost:8080/clients/001i?\_csrf=uoszX\_AiaIQNLnuuZazkNcaznjwaIAOt0DIvhfmnvZPw lK0I27wLbpYXUbMgFkPPVoHQB\_eLs14rFTaAtVdJsZ-RjKDApstp' -i -X PATCH \![ref15]

`   `-H 'Content-Type: application/json;charset=UTF-8' \

`   `-d '{

` `"id" : "001i",

` `"clientId" : "001ci",

` `"name" : "Client03",

` `"secret" : "53cr3t50$9feW1t6m3",

` `"authenticationMethods" : [ {

`   `"id" : 1,

`   `"method" : "client\_secret\_basic"

` `} ],

` `"grantTypes" : [ {

`   `"id" : 1,

`   `"grantType" : "authorization\_code"

` `} ],

` `"redirectUris" : [ {

`   `"id" : 1,

`   `"uri" : "http://127.0.0.1:1000"

` `} ],

` `"scopes" : [ {

`   `"id" : 1,

`   `"scope" : "openid"

` `} ],

` `"tokenSettings" : {

`   `"id" : 1,

`   `"format" : "self-contained",

`   `"accessTokenTTL" : 30

` `}

}'

Httpie Request:

$ echo '{

` `"id" : "001i",

` `"clientId" : "001ci",

` `"name" : "Client03",

` `"secret" : "53cr3t50$9feW1t6m3",

` `"authenticationMethods" : [ {

`   `"id" : 1,

`   `"method" : "client\_secret\_basic"  } ],

` `"grantTypes" : [ {

`   `"id" : 1,

`   `"grantType" : "authorization\_code"

` `} ],

` `"redirectUris" : [ {

`   `"id" : 1,

`   `"uri" : "http://127.0.0.1:1000"

` `} ],

` `"scopes" : [ {

`   `"id" : 1,

`   `"scope" : "openid"

` `} ],

` `"tokenSettings" : {

`   `"id" : 1,

`   `"format" : "self-contained",

`   `"accessTokenTTL" : 30

` `}

}' | http PATCH 'http://localhost:8080/clients/001i?\_csrf=uoszX\_AiaIQNLnuuZazkNcaznjwaIAOt0DIvhfmnvZPw lK0I27wLbpYXUbMgFkPPVoHQB\_eLs14rFTaAtVdJsZ-RjKDApstp' \

`   `'Content-Type:application/json;charset=UTF-8'

Http Request:

PATCH /clients/001i?\_csrf=uoszX\_AiaIQNLnuuZazkNcaznjwaIAOt0DIvhfmnvZPwlK0I27wLbpYXUbMgFkPPVo HQB\_eLs14rFTaAtVdJsZ-RjKDApstp HTTP/1.1

Content-Type: application/json;charset=UTF-8

Content-Length: 539

Host: localhost:8080

{

` `"id" : "001i",

` `"clientId" : "001ci",

` `"name" : "Client03",

` `"secret" : "53cr3t50$9feW1t6m3",

` `"authenticationMethods" : [ {

`   `"id" : 1,

`   `"method" : "client\_secret\_basic"

` `} ],

` `"grantTypes" : [ {

`   `"id" : 1,

`   `"grantType" : "authorization\_code"  } ],

` `"redirectUris" : [ {

`   `"id" : 1,

`   `"uri" : "http://127.0.0.1:1000"

` `} ],

` `"scopes" : [ {

`   `"id" : 1,

`   `"scope" : "openid"

` `} ]

` `"tokenSettings" : {

`   `"id" : 1,

`   `"format" : "self-contained",    "accessTokenTTL" : 30

` `}

}

HTTP/1.1 200 OK

X-Content-Type-Options: nosniff

X-XSS-Protection: 0

Cache-Control: no-cache, no-store, max-age=0, must-revalidate Pragma: no-cache

Expires: 0

X-Frame-Options: DENY

Request Parameters:

*Table 12. /clients/{id}*

**Parameter Description

id UUID of the client to update

Requests and Responses:

{

` `"id" : "001i",

` `"clientId" : "001ci",

` `"name" : "Client03",

` `"secret" : "53cr3t50$9feW1t6m3",

` `"authenticationMethods" : [ {

`   `"id" : 1,

`   `"method" : "client\_secret\_basic"

` `} ],

` `"grantTypes" : [ {

`   `"id" : 1,

`   `"grantType" : "authorization\_code"  } ],

` `"redirectUris" : [ {

`   `"id" : 1,

`   `"uri" : "http://127.0.0.1:1000"

` `} ],

` `"scopes" : [ {

`   `"id" : 1,

`   `"scope" : "openid"

` `} ],

` `"tokenSettings" : {

`   `"id" : 1,

`   `"format" : "self-contained",

`   `"accessTokenTTL" : 30  }

}

|**Path**|**Type**|**Description**|
| - | - | - |
|name|class java.lang.String|Desired client name of client to be updated|
|secret|class java.lang.String|Desired client secret of client to be updated|
|authenticationMethods|interface java.util.Set|Desired oauth2.0 authentication methods of client to be updated|
|authenticationMethods.[].metho d|class java.lang.String|Authentication method name of desired oauth2.0 authentication methods of client to be updated|
|grantTypes|interface java.util.Set|Desired list/set of auth2.0 authentication grant types of client to be updated|
|grantTypes.[].grantType|String|Authorization grant type of the client to be updated|
|redirectUris|class java.lang.String|Desired list/set of redirect URIs of client to be updated|
|redirectUris.[].uri|class java.lang.String|A redirect Uri of the client to be updated|
|scopes|interface java.util.Set|Desired oauth2.o authentication scopes of client to be updated|
|scopes.[].scope|class java.lang.String|A scope of the client to be updated|
|tokenSettings|class com.daveace.greenspaces.tokens ettings.TokenSettings|Desired oauth2.0 token settings of client to be updated|
|tokenSettings.format|class java.lang.String|Format of the access token of the client to be updated|
|tokenSettings.accessTokenTTL|class java.lang.Integer|Expiry time of the access token of the client to be updated|

**Deleting a Client**

cURL Request:

$ curl 'http://localhost:8080/clients/001i' -i -X DELETE \    -H 'Content-Type: application/json;charset=UTF-8'

Httpie Request:

$ http DELETE 'http://localhost:8080/clients/001i' \    'Content-Type:application/json;charset=UTF-8'

Http Request:

DELETE /clients/001i HTTP/1.1 Content-Type: application/json;charset=UTF-8 Host: localhost:8080

HTTP/1.1 200 OK

X-Content-Type-Options: nosniff

X-XSS-Protection: 0

Cache-Control: no-cache, no-store, max-age=0, must-revalidate Pragma: no-cache

Expires: 0

X-Frame-Options: DENY

Request Parameters:

*Table 13. /clients/{id}*

|**Parameter**|**Description**|
|-|-|
|id| UUID of client to delete|
