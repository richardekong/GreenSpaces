> GreenSpaces Authorization Service Documentation

Richard W. Ekong

> This application provides authorization to other GreenSpaces clients
> to access GreenSpaces resource through a GreenSpaces resource server.
> Green Spaces is an Application that will enable users to recommend parks,
> fields and relevant recreational and communal places and in turn
> enable citizens to search for facilities, post comments and review, view
> available events within these spaces.

1

> **Performing Authorization through Authorization Code Grant Type**\
> cURL Request:

+-----------------------------------------------------------------------+
| > \$ curl                                                             |
| > \'http://localhost:8080/oa                                          |
| uth2/authorize?response_type=code&client_id=76e09d7a-1095-4b47-80c5-\ |
| > 5f8bcba70f1d&scop                                                   |
| e=read&redirect_uri=https://localhost:1000&code_challenge=BUJilj4kra3 |
| > vxk6TPzZH_yNEkBZVB9yfAvrvlpmDM3E&code_challenge_method=S256\' -i -X |
| > GET                                                                 |
+-----------------------------------------------------------------------+
|                                                                       |
+-----------------------------------------------------------------------+

> Httpie Request:

+-----------------------------------------------------------------------+
| > \$ http GET\                                                        |
| > \'http://localhost:8080/oa                                          |
| uth2/authorize?response_type=code&client_id=76e09d7a-1095-4b47-80c5-\ |
| > 5f8bcba70f1d&scop                                                   |
| e=read&redirect_uri=https://localhost:1000&code_challenge=BUJilj4kra3 |
| > vxk6TPzZH_yNEkBZVB9yfAvrvlpmDM3E&code_challenge_method=S256\'       |
+-----------------------------------------------------------------------+
|                                                                       |
+-----------------------------------------------------------------------+

> Http Request:

+-----------------------------------------------------------------------+
| > GET                                                                 |
| > /oa                                                                 |
| uth2/authorize?response_type=code&client_id=76e09d7a-1095-4b47-80c5-\ |
| > 5f8bcba70f1d&scop                                                   |
| e=read&redirect_uri=https://localhost:1000&code_challenge=BUJilj4kra3 |
| > vxk6TPzZH_yNEkBZVB9yfAvrvlpmDM3E&code_challenge_method=S256         |
| > HTTP/1.1\                                                           |
| > Host: localhost:8080                                                |
+-----------------------------------------------------------------------+
|                                                                       |
+-----------------------------------------------------------------------+

> Http Response:

+-----------------------------------------------------------------------+
| > HTTP/1.1 302 Found\                                                 |
| > X-Content-Type-Options: nosniff\                                    |
| > X-XSS-Protection: 0\                                                |
| > Cache-Control: no-cache, no-store, max-age=0, must-revalidate\      |
| > Pragma: no-cache\                                                   |
| > Expires: 0\                                                         |
| > X-Frame-Options: DENY\                                              |
| > Location:\                                                          |
| > https://localhost                                                   |
| :1000?code=IDG88NKf0oXs9t3FdI1BCDP9VziZsR_aBA0KaLq4ClzVhO4zReDHVZN8W8 |
| > 8                                                                   |
| l053oZOMCK5MWUElLOLAEHJlWYT5z8tbmni99BxakLTrDZTGk8N2Ucxif2iGQno_Hqpxb |
+-----------------------------------------------------------------------+
|                                                                       |
+-----------------------------------------------------------------------+

> Query Parameters:

+-----------------------------------+-----------------------------------+
| > **Parameter**                   | > **Description**                 |
+===================================+===================================+
| > response_type                   | > grant type of the client        |
+-----------------------------------+-----------------------------------+
| > client_id                       | > id of the client                |
+-----------------------------------+-----------------------------------+

> 2

+-----------------------------------+-----------------------------------+
| > **Parameter**                   | > **Description**                 |
+===================================+===================================+
| > scope                           | > scope of user data available to |
|                                   | > the client                      |
+-----------------------------------+-----------------------------------+
| > redirect_uri                    | > Uri that user will be directed  |
|                                   | > to after successful login       |
+-----------------------------------+-----------------------------------+
| > code_challenge                  | > Verifier Code or Proof Key for  |
|                                   | > Code Exchange (PKCE) security   |
|                                   | > enhancement provided by the     |
|                                   | > client                          |
+-----------------------------------+-----------------------------------+
| > code_challenge_method           | > Algorithm for generating the    |
|                                   | > PKCE e.g SHA-256                |
+-----------------------------------+-----------------------------------+

> Response Header:

+-----------------------------------+-----------------------------------+
| > **Name**                        | > **Description**                 |
+===================================+===================================+
| > Location                        | > The Current URI containing the  |
|                                   | > authorization code after a      |
|                                   | > successful redirect operation   |
+-----------------------------------+-----------------------------------+

3

> **Creating a user**\
> cURL Request:

+-----------------------------------------------------------------------+
| > \$ curl \'http://localhost:8080/users?\_csrf=bJ7glA-\               |
| > u9qD9MOedW1ljgLTi                                                   |
| vFknBwFfvztEqlDRTnOh1HhjX_zW9m3IlZLQBdSsanRX44HakWBCMGJyhgwhzmnkL0XE4 |
| > h5T\' -i -X POST \\\                                                |
| > -H \'Content-Type: application/json;charset=UTF-8\' \\\             |
| > -d \'{\                                                             |
| > \"id\" : \"b583b456-9300-4cbd-4bcd-199225f5d42c\",\                 |
| > \"username\" : \"user.test@mail.com\",\                             |
| > \"password\" : \"13Tm31n@16351t3001\",\                             |
| > \"roles\" : \[ {\                                                   |
| > \"id\" : 1,\                                                        |
| > \"role\" : \"ADMIN\",\                                              |
| > \"authorities\" : \[ {\                                             |
| > \"id\" : 3,\                                                        |
| > \"authority\" : \"execute\"\                                        |
| > }, {\                                                               |
| > \"id\" : 2,\                                                        |
| > \"authority\" : \"write\"\                                          |
| > }, {\                                                               |
| > \"id\" : 1,\                                                        |
| > \"authority\" : \"read\"\                                           |
| > } \]\                                                               |
| > } \],\                                                              |
| > \"enabled\" : true,\                                                |
| > \"accountNonLocked\" : true,\                                       |
| > \"credentialsNonExpired\" : true,\                                  |
| > \"accountNonExpired\" : true\                                       |
| > }\'                                                                 |
+=======================================================================+
|                                                                       |
+-----------------------------------------------------------------------+

> Httpie Request:

+-----------------------------------------------------------------------+
| > \$ echo \'{\                                                        |
| > \"id\" : \"b583b456-9300-4cbd-4bcd-199225f5d42c\", \"username\" :   |
| > \"user.test@mail.com\",\                                            |
| > \"password\" : \"13Tm31n@16351t3001\",\                             |
| > \"roles\" : \[ {\                                                   |
| > \"id\" : 1,\                                                        |
| > \"role\" : \"ADMIN\",\                                              |
| > \"authorities\" : \[ {\                                             |
| > \"id\" : 3,\                                                        |
| > \"authority\" : \"execute\"\                                        |
| > }, {\                                                               |
| > \"id\" : 2,\                                                        |
| > \"authority\" : \"write\"\                                          |
| > }, {                                                                |
+=======================================================================+
|                                                                       |
+-----------------------------------------------------------------------+

> 4

+-----------------------------------------------------------------------+
| > \"id\" : 1,\                                                        |
| > \"authority\" : \"read\"\                                           |
| > } \]\                                                               |
| > } \],\                                                              |
| > \"enabled\" : true,\                                                |
| > \"accountNonLocked\" : true,\                                       |
| > \"credentialsNonExpired\" : true,\                                  |
| > \"accountNonExpired\" : true\                                       |
| > }\' \| http POST \'http://localhost:8080/users?\_csrf=bJ7glA-\      |
| > u9qD9MOedW1ljgLTi                                                   |
| vFknBwFfvztEqlDRTnOh1HhjX_zW9m3IlZLQBdSsanRX44HakWBCMGJyhgwhzmnkL0XE4 |
| > h5T\' \\\                                                           |
| > \'Content-Type:application/json;charset=UTF-8\'                     |
+=======================================================================+
|                                                                       |
+-----------------------------------------------------------------------+

> Http Request:

+-----------------------------------------------------------------------+
| > POST /users?\_csrf=bJ7glA-\                                         |
| > u9qD9MOedW1ljgLTi                                                   |
| vFknBwFfvztEqlDRTnOh1HhjX_zW9m3IlZLQBdSsanRX44HakWBCMGJyhgwhzmnkL0XE4 |
| > h5T HTTP/1.1\                                                       |
| > Content-Type: application/json;charset=UTF-8\                       |
| > Content-Length: 502\                                                |
| > Host: localhost:8080                                                |
+=======================================================================+
+-----------------------------------------------------------------------+

+-----------------------------------------------------------------------+
| > {\                                                                  |
| > \"id\" : \"b583b456-9300-4cbd-4bcd-199225f5d42c\", \"username\" :   |
| > \"user.test@mail.com\",\                                            |
| > \"password\" : \"13Tm31n@16351t3001\",\                             |
| > \"roles\" : \[ {\                                                   |
| > \"id\" : 1,\                                                        |
| > \"role\" : \"ADMIN\",\                                              |
| > \"authorities\" : \[ {\                                             |
| > \"id\" : 3,\                                                        |
| > \"authority\" : \"execute\"\                                        |
| > }, {\                                                               |
| > \"id\" : 2,\                                                        |
| > \"authority\" : \"write\"\                                          |
| > }, {\                                                               |
| > \"id\" : 1,\                                                        |
| > \"authority\" : \"read\"\                                           |
| > } \]\                                                               |
| > } \],\                                                              |
| > \"enabled\" : true,\                                                |
| > \"accountNonLocked\" : true,\                                       |
| > \"credentialsNonExpired\" : true,\                                  |
| > \"accountNonExpired\" : true\                                       |
| > }                                                                   |
+=======================================================================+
+-----------------------------------------------------------------------+

+-----------------------------------------------------------------------+
| > HTTP/1.1 201 Created                                                |
+=======================================================================+
| > Content-Type: application/json                                      |
+-----------------------------------------------------------------------+

5

+-----------------------------------------------------------------------+
| > X-Content-Type-Options: nosniff\                                    |
| > X-XSS-Protection: 0\                                                |
| > Cache-Control: no-cache, no-store, max-age=0, must-revalidate       |
| > Pragma: no-cache\                                                   |
| > Expires: 0\                                                         |
| > X-Frame-Options: DENY\                                              |
| > Content-Length: 308                                                 |
+=======================================================================+
+-----------------------------------------------------------------------+

+-----------------------------------------------------------------------+
| > {\"id\":\"b583b456-9300-4cbd-4bcd-\                                 |
| > 199225f5d42c\",\"username\":\"us                                    |
| er.test@mail.com\",\"roles\":\[{\"id\":1,\"role\":\"ADMIN\",\"authori |
| > ties\":\[{\"id\":3,\"authority\":\"                                 |
| execute\"},{\"id\":2,\"authority\":\"write\"},{\"id\":1,\"authority\" |
| > :\"read\"}\]}\],\"enabled\":                                        |
| true,\"accountNonLocked\":true,\"credentialsNonExpired\":true,\"accou |
| > ntNonExpired\":true}                                                |
+=======================================================================+
+-----------------------------------------------------------------------+

> Requests and Responses:

+-----------------------------------------------------------------------+
| > {\                                                                  |
| > \"id\" : \"b583b456-9300-4cbd-4bcd-199225f5d42c\", \"username\" :   |
| > \"user.test@mail.com\",\                                            |
| > \"password\" : \"13Tm31n@16351t3001\",\                             |
| > \"roles\" : \[ {\                                                   |
| > \"id\" : 1,\                                                        |
| > \"role\" : \"ADMIN\",\                                              |
| > \"authorities\" : \[ {\                                             |
| > \"id\" : 3,\                                                        |
| > \"authority\" : \"execute\"\                                        |
| > }, {\                                                               |
| > \"id\" : 2,\                                                        |
| > \"authority\" : \"write\"\                                          |
| > }, {\                                                               |
| > \"id\" : 1,\                                                        |
| > \"authority\" : \"read\"\                                           |
| > } \]\                                                               |
| > } \],\                                                              |
| > \"enabled\" : true,\                                                |
| > \"accountNonLocked\" : true,\                                       |
| > \"credentialsNonExpired\" : true,\                                  |
| > \"accountNonExpired\" : true\                                       |
| > }                                                                   |
+=======================================================================+
|                                                                       |
+-----------------------------------------------------------------------+

+-----------------------+-----------------------+-----------------------+
| > **Path**            | > **Type**            | > **Description**     |
+=======================+=======================+=======================+
| > username            | > class               | username of the user  |
|                       | > java.lang.String    | to be save            |
+-----------------------+-----------------------+-----------------------+
| > password            | > class               | > password of the     |
|                       | > java.lang.String    | > user to save        |
+-----------------------+-----------------------+-----------------------+
| > roles               | > interface           | > roles of the user   |
|                       | > java.util.Set       | > to be saved         |
+-----------------------+-----------------------+-----------------------+
| > roles.\[\].id       | > class               | > role id of a single |
|                       | > java.lang.Integer   | > role within roles   |
|                       |                       | > of the user to be   |
|                       |                       | > saved               |
+-----------------------+-----------------------+-----------------------+

> 6

+-----------------------+-----------------------+-----------------------+
| > **Path**            | > **Type**            | > **Description**     |
+=======================+=======================+=======================+
| > roles.\[\].role     | > class               | > role name of a      |
|                       | > java.lang.String    | > single role\        |
|                       |                       | > within roles of a   |
|                       |                       | > single role within  |
|                       |                       | > roles of the user   |
|                       |                       | > to be saved         |
+-----------------------+-----------------------+-----------------------+
| > r                   | > interface           | authorities within a  |
| oles.\[\].authorities | > java.util.Set       | single role of roles  |
|                       |                       | of the user to be     |
|                       |                       | saved                 |
+-----------------------+-----------------------+-----------------------+
| > roles.\[\           | > class               | > id of a authority   |
| ].authorities.\[\].id | > java.lang.Integer   | > within a role of    |
|                       |                       | > roles of the user   |
|                       |                       | > to be saved         |
+-----------------------+-----------------------+-----------------------+
| > roles.\[\].au       | > class               | > authority name of   |
| thorities.\[\].author | > java.lang.String    | > an authority within |
| > ity                 |                       | > a role of roles of  |
|                       |                       | > the user to be      |
|                       |                       | > saved               |
+-----------------------+-----------------------+-----------------------+
| > enabled             | > class               | > status describing   |
|                       | > java.lang.Boolean   | > if the user account |
|                       |                       | > is enabled          |
+-----------------------+-----------------------+-----------------------+
| >                     | > class               | > status describing   |
| credentialsNonExpired | > java.lang.Boolean   | > the validity of     |
|                       |                       | > user's credentials  |
+-----------------------+-----------------------+-----------------------+
| > accountNonExpired   | > class               | > Status describing   |
|                       | > java.lang.Boolean   | > user's account      |
|                       |                       | > validity            |
+-----------------------+-----------------------+-----------------------+
| > accountNonLocked    | > class               | > Status describing   |
|                       | > java.lang.Boolean   | > whether user's      |
|                       |                       | > account is locked   |
|                       |                       | > or unlocked         |
+-----------------------+-----------------------+-----------------------+

+-----------------------------------------------------------------------+
| > {\"id\":\"b583b456-9300-4cbd-4bcd-\                                 |
| > 199225f5d42c\",\"username\":\"us                                    |
| er.test@mail.com\",\"roles\":\[{\"id\":1,\"role\":\"ADMIN\",\"authori |
| > ties\":\[{\"id\":3,\"authority\":\"                                 |
| execute\"},{\"id\":2,\"authority\":\"write\"},{\"id\":1,\"authority\" |
| > :\"read\"}\]}\],\"enabled\":                                        |
| true,\"accountNonLocked\":true,\"credentialsNonExpired\":true,\"accou |
| > ntNonExpired\":true}                                                |
+=======================================================================+
|                                                                       |
+-----------------------------------------------------------------------+

7

> **Retrieving a user by id** cURL Request:

+-----------------------------------------------------------------------+
| > \$ curl                                                             |
| >                                                                     |
|  \'http://localhost:8080/users/b583b456-9300-4cbd-4bcd-199225f5d42c\' |
| > -i -X GET \\ -H \'Content-Type: application/json;charset=UTF-8\'    |
+=======================================================================+
|                                                                       |
+-----------------------------------------------------------------------+

> Httpie Request:

+-----------------------------------------------------------------------+
| > \$ http GET                                                         |
| >                                                                     |
|  \'http://localhost:8080/users/b583b456-9300-4cbd-4bcd-199225f5d42c\' |
| > \\ \'Content-Type:application/json;charset=UTF-8\'                  |
+-----------------------------------------------------------------------+
|                                                                       |
+-----------------------------------------------------------------------+

> Http Request:

+-----------------------------------------------------------------------+
| > GET /users/b583b456-9300-4cbd-4bcd-199225f5d42c HTTP/1.1            |
| > Content-Type: application/json;charset=UTF-8\                       |
| > Host: localhost:8080                                                |
+=======================================================================+
|                                                                       |
+-----------------------------------------------------------------------+

+-----------------------------------------------------------------------+
| > HTTP/1.1 200 OK\                                                    |
| > Content-Type: application/json\                                     |
| > X-Content-Type-Options: nosniff\                                    |
| > X-XSS-Protection: 0\                                                |
| > Cache-Control: no-cache, no-store, max-age=0, must-revalidate       |
| > Pragma: no-cache\                                                   |
| > Expires: 0\                                                         |
| > X-Frame-Options: DENY\                                              |
| > Content-Length: 308                                                 |
+=======================================================================+
+-----------------------------------------------------------------------+

+-----------------------------------------------------------------------+
| > {\"id\":\"b583b456-9300-4cbd-4bcd-\                                 |
| > 199225f5d42c\",\"username\":\"us                                    |
| er.test@mail.com\",\"roles\":\[{\"id\":1,\"role\":\"ADMIN\",\"authori |
| > ties\":\[{\"id\":3,\"authority\":\"                                 |
| execute\"},{\"id\":2,\"authority\":\"write\"},{\"id\":1,\"authority\" |
| > :\"read\"}\]}\],\"enabled\":                                        |
| true,\"accountNonLocked\":true,\"credentialsNonExpired\":true,\"accou |
| > ntNonExpired\":true}                                                |
+=======================================================================+
+-----------------------------------------------------------------------+

> Request Parameters:\
> *Table 1. /users/{id}*

+-----------------------------------+-----------------------------------+
| > **Parameter**                   | > **Description**                 |
+===================================+===================================+
| > id                              | > UUID of the user to retrieve    |
+-----------------------------------+-----------------------------------+

> Requests and Responses:
>
> 8

+-----------------------------------------------------------------------+
| > {\"id\":\"b583b456-9300-4cbd-4bcd-\                                 |
| > 199225f5d42c\",\"username\":\"us                                    |
| er.test@mail.com\",\"roles\":\[{\"id\":1,\"role\":\"ADMIN\",\"authori |
| > ties\":\[{\"id\":3,\"authority\":\"                                 |
| execute\"},{\"id\":2,\"authority\":\"write\"},{\"id\":1,\"authority\" |
| > :\"read\"}\]}\],\"enabled\":                                        |
| true,\"accountNonLocked\":true,\"credentialsNonExpired\":true,\"accou |
| > ntNonExpired\":true}                                                |
+=======================================================================+
|                                                                       |
+-----------------------------------------------------------------------+

+-----------------------+-----------------------+-----------------------+
| > **Path**            | > **Type**            | > **Description**     |
+=======================+=======================+=======================+
| > id                  | > String              | > UUID of the         |
|                       |                       | > retrieved user      |
+-----------------------+-----------------------+-----------------------+
| > username            | > class               | username of the user  |
|                       | > java.lang.String    | to be save            |
+-----------------------+-----------------------+-----------------------+
| > roles               | > interface           | > roles of the user   |
|                       | > java.util.Set       | > to be saved         |
+-----------------------+-----------------------+-----------------------+
| > roles.\[\].id       | > class               | > role id of a single |
|                       | > java.lang.Integer   | > role within roles   |
|                       |                       | > of the user to be   |
|                       |                       | > saved               |
+-----------------------+-----------------------+-----------------------+
| > roles.\[\].role     | > class               | > role name of a      |
|                       | > java.lang.String    | > single role\        |
|                       |                       | > within roles of a   |
|                       |                       | > single role within  |
|                       |                       | > roles of the user   |
|                       |                       | > to be saved         |
+-----------------------+-----------------------+-----------------------+
| > r                   | > interface           | authorities within a  |
| oles.\[\].authorities | > java.util.Set       | single role of roles  |
|                       |                       | of the user to be     |
|                       |                       | saved                 |
+-----------------------+-----------------------+-----------------------+
| > roles.\[\           | > class               | > id of a authority   |
| ].authorities.\[\].id | > java.lang.Integer   | > within a role of    |
|                       |                       | > roles of the user   |
|                       |                       | > to be saved         |
+-----------------------+-----------------------+-----------------------+
| > roles.\[\].au       | > class               | > authority name of   |
| thorities.\[\].author | > java.lang.String    | > an authority within |
| > ity                 |                       | > a role of roles of  |
|                       |                       | > the user to be      |
|                       |                       | > saved               |
+-----------------------+-----------------------+-----------------------+
| > enabled             | > class               | > status describing   |
|                       | > java.lang.Boolean   | > if the user account |
|                       |                       | > is enabled          |
+-----------------------+-----------------------+-----------------------+
| >                     | > class               | > status describing   |
| credentialsNonExpired | > java.lang.Boolean   | > the validity of     |
|                       |                       | > user's credentials  |
+-----------------------+-----------------------+-----------------------+
| > accountNonExpired   | > class               | > Status describing   |
|                       | > java.lang.Boolean   | > user's account      |
|                       |                       | > validity            |
+-----------------------+-----------------------+-----------------------+
| > accountNonLocked    | > class               | > Status describing   |
|                       | > java.lang.Boolean   | > whether user's      |
|                       |                       | > account is locked   |
|                       |                       | > or unlocked         |
+-----------------------+-----------------------+-----------------------+

9

> **Retrieving a user's Role** cURL Request:

+-----------------------------------------------------------------------+
| > \$ curl                                                             |
| > \'http:                                                             |
| //localhost:8080/users/b583b456-9300-4cbd-4bcd-199225f5d42c/roles/1\' |
| > -i-X GET \\\                                                        |
| > -H \'Content-Type: application/json;charset=UTF-8\'                 |
+-----------------------------------------------------------------------+
|                                                                       |
+-----------------------------------------------------------------------+

> Httpie Request:

+-----------------------------------------------------------------------+
| > \$ http GET                                                         |
| > \'http:                                                             |
| //localhost:8080/users/b583b456-9300-4cbd-4bcd-199225f5d42c/roles/1\' |
| > \\\                                                                 |
| > \'Content-Type:application/json;charset=UTF-8\'                     |
+-----------------------------------------------------------------------+
|                                                                       |
+-----------------------------------------------------------------------+

> Http Request:

+-----------------------------------------------------------------------+
| > GET /users/b583b456-9300-4cbd-4bcd-199225f5d42c/roles/1 HTTP/1.1    |
| > Content-Type: application/json;charset=UTF-8\                       |
| > Host: localhost:8080                                                |
+-----------------------------------------------------------------------+
|                                                                       |
+-----------------------------------------------------------------------+

+-----------------------------------------------------------------------+
| > HTTP/1.1 200 OK\                                                    |
| > Content-Type: application/json\                                     |
| > X-Content-Type-Options: nosniff\                                    |
| > X-XSS-Protection: 0\                                                |
| > Cache-Control: no-cache, no-store, max-age=0, must-revalidate       |
| > Pragma: no-cache\                                                   |
| > Expires: 0\                                                         |
| > X-Frame-Options: DENY\                                              |
| > Content-Length: 127                                                 |
+=======================================================================+
+-----------------------------------------------------------------------+

+-----------------------------------------------------------------------+
| > {\"id\":1,\"role\":\"ADMIN\",\"auth                                 |
| orities\":\[{\"id\":3,\"authority\":\"execute\"},{\"id\":2,\"authorit |
| > y\":\"write\"},{\"id\":1,\"authority\":\"read\"}\]}                 |
+=======================================================================+
+-----------------------------------------------------------------------+

> Request Parameters:\
> *Table 2. /users/{id}/roles/{roleId}*

+-----------------------------------+-----------------------------------+
| > **Parameter**                   | > **Description**                 |
+===================================+===================================+
| > id                              | > UUID of the user with role to   |
|                                   | > retrieve                        |
+-----------------------------------+-----------------------------------+
| > roleId                          | > id of role to retrieve          |
+-----------------------------------+-----------------------------------+

> Requests and Responses:
>
> 10

+-----------------------------------------------------------------------+
| > {\"id\":1,\"role\":\"ADMIN\",\"auth                                 |
| orities\":\[{\"id\":3,\"authority\":\"execute\"},{\"id\":2,\"authorit |
| > y\":\"write\"},{\"id\":1,\"authority\":\"read\"}\]}                 |
+=======================================================================+
|                                                                       |
+-----------------------------------------------------------------------+

+-----------------------+-----------------------+-----------------------+
| > **Path**            | > **Type**            | > **Description**     |
+=======================+=======================+=======================+
| > id                  | > class               | > id of a role        |
|                       | > java.lang.Integer   |                       |
+-----------------------+-----------------------+-----------------------+
| > role                | > class               | > role name           |
|                       | > java.lang.String    |                       |
+-----------------------+-----------------------+-----------------------+
| > authorities         | > interface           | > authorities within  |
|                       | > java.util.Set       | > this role           |
+-----------------------+-----------------------+-----------------------+
| > authorities.\[\].id | > class               | > id of an authority  |
|                       | > java.lang.Integer   | > within a role of    |
|                       |                       | > roles               |
+-----------------------+-----------------------+-----------------------+
| > autho               | > class               | > authority name of   |
| rities.\[\].authority | > java.lang.String    | > an authority within |
|                       |                       | > a role              |
+-----------------------+-----------------------+-----------------------+

11

> **Retrieving a user's Roles** cURL Request:

+-----------------------------------------------------------------------+
| > \$ curl                                                             |
| > \'htt                                                               |
| p://localhost:8080/users/b583b456-9300-4cbd-4bcd-199225f5d42c/roles\' |
| > -i -X GET \\\                                                       |
| > -H \'Content-Type: application/json;charset=UTF-8\'                 |
+=======================================================================+
|                                                                       |
+-----------------------------------------------------------------------+

> Httpie Request:

+-----------------------------------------------------------------------+
| > \$ http GET                                                         |
| > \'htt                                                               |
| p://localhost:8080/users/b583b456-9300-4cbd-4bcd-199225f5d42c/roles\' |
| > \\ \'Content-Type:application/json;charset=UTF-8\'                  |
+=======================================================================+
|                                                                       |
+-----------------------------------------------------------------------+

> Http Request:

+-----------------------------------------------------------------------+
| > GET /users/b583b456-9300-4cbd-4bcd-199225f5d42c/roles HTTP/1.1      |
| > Content-Type: application/json;charset=UTF-8\                       |
| > Host: localhost:8080                                                |
+-----------------------------------------------------------------------+
|                                                                       |
+-----------------------------------------------------------------------+

+-----------------------------------------------------------------------+
| > HTTP/1.1 200 OK\                                                    |
| > Content-Type: application/json\                                     |
| > X-Content-Type-Options: nosniff\                                    |
| > X-XSS-Protection: 0\                                                |
| > Cache-Control: no-cache, no-store, max-age=0, must-revalidate       |
| > Pragma: no-cache\                                                   |
| > Expires: 0\                                                         |
| > X-Frame-Options: DENY\                                              |
| > Content-Length: 129                                                 |
+=======================================================================+
+-----------------------------------------------------------------------+

+-----------------------------------------------------------------------+
| > \[{\"id\":1,\"role\":\"ADMIN\",\"aut                                |
| horities\":\[{\"id\":3,\"authority\":\"execute\"},{\"id\":2,\"authori |
| > ty\":\"write\"},{\"id\":1,\"authority\":\"read\"}\]}\]              |
+=======================================================================+
+-----------------------------------------------------------------------+

> Request Parameters:\
> *Table 3. /users/{id}/roles*

+-----------------------------------+-----------------------------------+
| > **Parameter**                   | > **Description**                 |
+===================================+===================================+
| > id                              | > UUID of the user to retrieve    |
+-----------------------------------+-----------------------------------+

> Requests and Responses:

  -----------------------------------------------------------------------------------------------------------
  \[{\"id\":1,\"role\":\"ADMIN\",\"authorities\":\[{\"id\":3,\"authority\":\"execute\"},{\"id\":2,\"authori
  -----------------------------------------------------------------------------------------------------------

  -----------------------------------------------------------------------------------------------------------

> 12

+-----------------------------------------------------------------------+
| > ty\":\"write\"},{\"id\":1,\"authority\":\"read\"}\]}\]              |
+=======================================================================+
|                                                                       |
+-----------------------------------------------------------------------+

+-----------------------+-----------------------+-----------------------+
| > **Path**            | > **Type**            | > **Description**     |
+=======================+=======================+=======================+
| > \[\].id             | > class               | > id of a role within |
|                       | > java.lang.Integer   | > roles of the user   |
+-----------------------+-----------------------+-----------------------+
| > \[\].role           | > class               | > role name of role   |
|                       | > java.lang.String    | > within roles of a   |
|                       |                       | > user                |
+-----------------------+-----------------------+-----------------------+
| > \[\].authorities    | > interface           | > authorities within  |
|                       | > java.util.Set       | > a single role of    |
|                       |                       | > roles               |
+-----------------------+-----------------------+-----------------------+
| > \[\                 | > class               | > id of authority     |
| ].authorities.\[\].id | > java.lang.Integer   | > within a role of    |
|                       |                       | > roles               |
+-----------------------+-----------------------+-----------------------+
| > \[\].autho          | > class               | > authority name of   |
| rities.\[\].authority | > java.lang.String    | > an authority within |
|                       |                       | > a role of roles     |
+-----------------------+-----------------------+-----------------------+

13

> **Retrieving a User's Authority** cURL Request:

+-----------------------------------------------------------------------+
| > \$ curl                                                             |
| > \'http://localhost:80                                               |
| 80/users/b583b456-9300-4cbd-4bcd-199225f5d42c/roles/1/authorities/1\' |
| > -i -X GET \\\                                                       |
| > -H \'Content-Type: application/json;charset=UTF-8\'                 |
+-----------------------------------------------------------------------+
|                                                                       |
+-----------------------------------------------------------------------+

> Httpie Request:

+-----------------------------------------------------------------------+
| > \$ http GET                                                         |
| > \'http://localhost:80                                               |
| 80/users/b583b456-9300-4cbd-4bcd-199225f5d42c/roles/1/authorities/1\' |
| > \\\                                                                 |
| > \'Content-Type:application/json;charset=UTF-8\'                     |
+=======================================================================+
|                                                                       |
+-----------------------------------------------------------------------+

> Http Request:

+-----------------------------------------------------------------------+
| > GET                                                                 |
| > /users/b583b456-9300-4cbd-4bcd-199225f5d42c/roles/1/authorities/1   |
| > HTTP/1.1 Content-Type: application/json;charset=UTF-8\              |
| > Host: localhost:8080                                                |
+=======================================================================+
|                                                                       |
+-----------------------------------------------------------------------+

+-----------------------------------------------------------------------+
| > HTTP/1.1 200 OK\                                                    |
| > Content-Type: application/json\                                     |
| > X-Content-Type-Options: nosniff\                                    |
| > X-XSS-Protection: 0\                                                |
| > Cache-Control: no-cache, no-store, max-age=0, must-revalidate       |
| > Pragma: no-cache\                                                   |
| > Expires: 0\                                                         |
| > X-Frame-Options: DENY\                                              |
| > Content-Length: 27                                                  |
+=======================================================================+
+-----------------------------------------------------------------------+

+-----------------------------------------------------------------------+
| > {\"id\":1,\"authority\":\"read\"}                                   |
+=======================================================================+
+-----------------------------------------------------------------------+

> Request Parameters:\
> *Table 4. /users/{id}/roles/{roleId}/authorities/{authorityId}*

+-----------------------------------+-----------------------------------+
| > **Parameter**                   | > **Description**                 |
+===================================+===================================+
| > id                              | > UUID of the user with authority |
|                                   | > to retrieve                     |
+-----------------------------------+-----------------------------------+
| > roleId                          | > id of role associated with the  |
|                                   | > authority to be retrieved       |
+-----------------------------------+-----------------------------------+
| > authorityId                     | > id of the authority to retrieve |
+-----------------------------------+-----------------------------------+

> Requests and Responses:
>
> 14

+-----------------------------------------------------------------------+
| > {\"id\":1,\"authority\":\"read\"}                                   |
+=======================================================================+
|                                                                       |
+-----------------------------------------------------------------------+

+-----------------------+-----------------------+-----------------------+
| > **Path**            | > **Type**            | > **Description**     |
+=======================+=======================+=======================+
| > id                  | > class               | > id of the authority |
|                       | > java.lang.Integer   | > to retrieve         |
+-----------------------+-----------------------+-----------------------+
| > authority           | > class               | > authority name of   |
|                       | > java.lang.String    | > an authority to     |
|                       |                       | > retrieve            |
+-----------------------+-----------------------+-----------------------+

15

> **Retrieving a User's Authorities** cURL Request:

+-----------------------------------------------------------------------+
| > \$ curl                                                             |
| > \'http://localhos                                                   |
| t:8080/users/b583b456-9300-4cbd-4bcd-199225f5d42c/roles/authorities\' |
| > -i -X GET \\\                                                       |
| > -H \'Content-Type: application/json;charset=UTF-8\'                 |
+=======================================================================+
|                                                                       |
+-----------------------------------------------------------------------+

> Httpie Request:

+-----------------------------------------------------------------------+
| > \$ http GET                                                         |
| > \'http://localhos                                                   |
| t:8080/users/b583b456-9300-4cbd-4bcd-199225f5d42c/roles/authorities\' |
| > \\\                                                                 |
| > \'Content-Type:application/json;charset=UTF-8\'                     |
+-----------------------------------------------------------------------+
|                                                                       |
+-----------------------------------------------------------------------+

> Http Request:

+-----------------------------------------------------------------------+
| > GET /users/b583b456-9300-4cbd-4bcd-199225f5d42c/roles/authorities   |
| > HTTP/1.1 Content-Type: application/json;charset=UTF-8\              |
| > Host: localhost:8080                                                |
+-----------------------------------------------------------------------+
|                                                                       |
+-----------------------------------------------------------------------+

+-----------------------------------------------------------------------+
| > HTTP/1.1 200 OK\                                                    |
| > Content-Type: application/json\                                     |
| > X-Content-Type-Options: nosniff\                                    |
| > X-XSS-Protection: 0\                                                |
| > Cache-Control: no-cache, no-store, max-age=0, must-revalidate       |
| > Pragma: no-cache\                                                   |
| > Expires: 0\                                                         |
| > X-Frame-Options: DENY\                                              |
| > Content-Length: 89                                                  |
+=======================================================================+
+-----------------------------------------------------------------------+

+-----------------------------------------------------------------------+
| > \[{\"id\":3,\"authority\":\"execute                                 |
| \"},{\"id\":2,\"authority\":\"write\"},{\"id\":1,\"authority\":\"read |
| > \"}\]                                                               |
+=======================================================================+
+-----------------------------------------------------------------------+

> Request Parameters:\
> *Table 5. /users/{id}/roles/authorities*

+-----------------------------------+-----------------------------------+
| > **Parameter**                   | > **Description**                 |
+===================================+===================================+
| > id                              | > id of the user associated with  |
|                                   | > retrieved authorities           |
+-----------------------------------+-----------------------------------+

> Requests and Responses:
>
> 16

+-----------------------------------------------------------------------+
| > \[{\"id\":3,\"authority\":\"execute                                 |
| \"},{\"id\":2,\"authority\":\"write\"},{\"id\":1,\"authority\":\"read |
| > \"}\]                                                               |
+=======================================================================+
|                                                                       |
+-----------------------------------------------------------------------+

+-----------------------+-----------------------+-----------------------+
| > **Path**            | > **Type**            | > **Description**     |
+=======================+=======================+=======================+
| > \[\].id             | > Number              | > id of an authority  |
|                       |                       | > within list of      |
|                       |                       | > authorities         |
|                       |                       | > retrieved           |
+-----------------------+-----------------------+-----------------------+
| > \[\].authority      | > String              | > authority name of   |
|                       |                       | > an authority within |
|                       |                       | > list of             |
|                       |                       | > authorities\        |
|                       |                       | > retrieved           |
+-----------------------+-----------------------+-----------------------+

17

> **Retrieving all Users**\
> cURL Request:

+-----------------------------------------------------------------------+
| > \$ curl \'http://localhost:8080/users\' -i -X GET \\ -H             |
| > \'Content-Type: application/json;charset=UTF-8\'                    |
+=======================================================================+
|                                                                       |
+-----------------------------------------------------------------------+

> Httpie Request:

+-----------------------------------------------------------------------+
| > \$ http GET \'http://localhost:8080/users\' \\\                     |
| > \'Content-Type:application/json;charset=UTF-8\'                     |
+-----------------------------------------------------------------------+
|                                                                       |
+-----------------------------------------------------------------------+

> Http Request:

+-----------------------------------------------------------------------+
| > GET /users HTTP/1.1\                                                |
| > Content-Type: application/json;charset=UTF-8\                       |
| > Host: localhost:8080                                                |
+-----------------------------------------------------------------------+
|                                                                       |
+-----------------------------------------------------------------------+

+-----------------------------------------------------------------------+
| > HTTP/1.1 200 OK\                                                    |
| > Content-Type: application/json\                                     |
| > X-Content-Type-Options: nosniff\                                    |
| > X-XSS-Protection: 0\                                                |
| > Cache-Control: no-cache, no-store, max-age=0, must-revalidate       |
| > Pragma: no-cache\                                                   |
| > Expires: 0\                                                         |
| > X-Frame-Options: DENY\                                              |
| > Content-Length: 310                                                 |
+=======================================================================+
+-----------------------------------------------------------------------+

+-----------------------------------------------------------------------+
| > \[{\"id\":\"b583b456-9300-4cbd-4bcd-\                               |
| > 199225f5d42c\",\"username\":\"us                                    |
| er.test@mail.com\",\"roles\":\[{\"id\":1,\"role\":\"ADMIN\",\"authori |
| > ties\":\[{\"id\":3,\"authority\":\"                                 |
| execute\"},{\"id\":2,\"authority\":\"write\"},{\"id\":1,\"authority\" |
| > :\"read\"}\]}\],\"enabled\":                                        |
| true,\"accountNonLocked\":true,\"credentialsNonExpired\":true,\"accou |
| > ntNonExpired\":true}\]                                              |
+=======================================================================+
+-----------------------------------------------------------------------+

> Requests and Responses:

+-----------------------------------------------------------------------+
| > \[{\"id\":\"b583b456-9300-4cbd-4bcd-\                               |
| > 199225f5d42c\",\"username\":\"us                                    |
| er.test@mail.com\",\"roles\":\[{\"id\":1,\"role\":\"ADMIN\",\"authori |
| > ties\":\[{\"id\":3,\"authority\":\"                                 |
| execute\"},{\"id\":2,\"authority\":\"write\"},{\"id\":1,\"authority\" |
| > :\"read\"}\]}\],\"enabled\":                                        |
| true,\"accountNonLocked\":true,\"credentialsNonExpired\":true,\"accou |
| > ntNonExpired\":true}\]                                              |
+=======================================================================+
|                                                                       |
+-----------------------------------------------------------------------+

> 18

+-----------------------+-----------------------+-----------------------+
| > **Path**            | > **Type**            | > **Description**     |
+=======================+=======================+=======================+
| > \[\].id             | > String              | > UUID of the         |
|                       |                       | > retrieved user      |
+-----------------------+-----------------------+-----------------------+
| > \[\].username       | > class               | username of the user  |
|                       | > java.lang.String    | to be save            |
+-----------------------+-----------------------+-----------------------+
| > \[\].roles          | > interface           | > roles of the user   |
|                       | > java.util.Set       | > to be saved         |
+-----------------------+-----------------------+-----------------------+
| > \[\].roles.\[\].id  | > class               | > role id of a single |
|                       | > java.lang.Integer   | > role within roles   |
|                       |                       | > of the user to be   |
|                       |                       | > saved               |
+-----------------------+-----------------------+-----------------------+
| >                     | > class               | > role name of a      |
|  \[\].roles.\[\].role | > java.lang.String    | > single role\        |
|                       |                       | > within roles of a   |
|                       |                       | > single role within  |
|                       |                       | > roles of the user   |
|                       |                       | > to be saved         |
+-----------------------+-----------------------+-----------------------+
| > \[\].r              | > interface           | authorities within a  |
| oles.\[\].authorities | > java.util.Set       | single role of roles  |
|                       |                       | of the user to be     |
|                       |                       | saved                 |
+-----------------------+-----------------------+-----------------------+
| > \[\].roles.\[\      | > class               | > id of a authority   |
| ].authorities.\[\].id | > java.lang.Integer   | > within a role of    |
|                       |                       | > roles of the user   |
|                       |                       | > to be saved         |
+-----------------------+-----------------------+-----------------------+
| > \[\].roles.\[\]     | > class               | > authority name of   |
| .authorities.\[\].aut | > java.lang.String    | > an authority within |
| > hority              |                       | > a role of roles of  |
|                       |                       | > the user to be      |
|                       |                       | > saved               |
+-----------------------+-----------------------+-----------------------+
| > \[\].enabled        | > class               | > status describing   |
|                       | > java.lang.Boolean   | > if the user account |
|                       |                       | > is enabled          |
+-----------------------+-----------------------+-----------------------+
| > \[\].               | > class               | > status describing   |
| credentialsNonExpired | > java.lang.Boolean   | > the validity of     |
|                       |                       | > user's credentials  |
+-----------------------+-----------------------+-----------------------+
| > \                   | > class               | > Status describing   |
| [\].accountNonExpired | > java.lang.Boolean   | > user's account      |
|                       |                       | > validity            |
+-----------------------+-----------------------+-----------------------+
| >                     | > class               | > Status describing   |
| \[\].accountNonLocked | > java.lang.Boolean   | > whether user's      |
|                       |                       | > account is locked   |
|                       |                       | > or unlocked         |
+-----------------------+-----------------------+-----------------------+

19

> **Updating a User (Adding Authorities to User)** cURL Request:

+-----------------------------------------------------------------------+
| > \$ curl \'http://localhost:8080/users/b583b456-9300-4cbd-4bcd-\     |
| >                                                                     |
| 199225f5d42c/roles/1/authorities?\_csrf=nj38kU54vdi2ABxRNfFp-D4xkFtfC |
| zLL-hHW8hE4zn524Tg5rwXI9HpA2O2bN3llDdxdzQYFvTk5bVbmyyC0wXUJrxtD2FkA\' |
| > -i -X PATCH \\ -H \'Content-Type: application/json;charset=UTF-8\'  |
| > \\\                                                                 |
| > -d \'\[ {\                                                          |
| > \"id\" : 2,\                                                        |
| > \"authority\" : \"update\"\                                         |
| > } \]\'                                                              |
+-----------------------------------------------------------------------+
|                                                                       |
+-----------------------------------------------------------------------+

> Httpie Request:

+-----------------------------------------------------------------------+
| > \$ echo \'\[ {\                                                     |
| > \"id\" : 2,\                                                        |
| > \"authority\" : \"update\"\                                         |
| > } \]\' \| http PATCH                                                |
| > \'http://localhost:8080/users/b583b456-9300-4cbd-4bcd-              |
| 199225f5d42c/roles/1/authorities?\_csrf=nj38kU54vdi2ABxRNfFp-D4xkFtfC |
| zLL-hHW8hE4zn524Tg5rwXI9HpA2O2bN3llDdxdzQYFvTk5bVbmyyC0wXUJrxtD2FkA\' |
| > \\ \'Content-Type:application/json;charset=UTF-8\'                  |
+=======================================================================+
|                                                                       |
+-----------------------------------------------------------------------+

> Http Request:

+-----------------------------------------------------------------------+
| > PATCH /users/b583b456-9300-4cbd-4bcd-\                              |
| > 199225f5d42c/roles/1/authorities?\_csrf=nj38kU54vdi2ABxRNfFp-D4xkFt |
| fCzLL-hHW8hE4zn524Tg5rwXI9HpA2O2bN3llDdxdzQYFvTk5bVbmyyC0wXUJrxtD2FkA |
| > HTTP/1.1 Content-Type: application/json;charset=UTF-8\              |
| > Content-Length: 47\                                                 |
| > Host: localhost:8080                                                |
+=======================================================================+
+-----------------------------------------------------------------------+

+-----------------------------------------------------------------------+
| > \[ {\                                                               |
| > \"id\" : 2,\                                                        |
| > \"authority\" : \"update\"\                                         |
| > } \]                                                                |
+=======================================================================+
+-----------------------------------------------------------------------+

+-----------------------------------------------------------------------+
| > HTTP/1.1 200 OK\                                                    |
| > X-Content-Type-Options: nosniff\                                    |
| > X-XSS-Protection: 0\                                                |
| > Cache-Control: no-cache, no-store, max-age=0, must-revalidate       |
| > Pragma: no-cache\                                                   |
| > Expires: 0\                                                         |
| > X-Frame-Options: DENY                                               |
+=======================================================================+
|                                                                       |
+-----------------------------------------------------------------------+

> 20
>
> Request Parameters:\
> *Table 6. /users/{id}/roles/{roleId}/authorities*

+-----------------------------------+-----------------------------------+
| > **Parameter**                   | > **Description**                 |
+===================================+===================================+
| > id                              | > UUID of the user adding         |
|                                   | > authorities                     |
+-----------------------------------+-----------------------------------+
| > roleId                          | > id of the role to add the       |
|                                   | > authorities                     |
+-----------------------------------+-----------------------------------+

> Requests and Responses:

+-----------------------------------------------------------------------+
| > \[ {\                                                               |
| > \"id\" : 2,\                                                        |
| > \"authority\" : \"update\"\                                         |
| > } \]                                                                |
+=======================================================================+
|                                                                       |
+-----------------------------------------------------------------------+

+-----------------------+-----------------------+-----------------------+
| > **Path**            | > **Type**            | > **Description**     |
+=======================+=======================+=======================+
| > \[\].authority      | > String              | > authority name of   |
|                       |                       | > authority within    |
|                       |                       | > authorities to add  |
+-----------------------+-----------------------+-----------------------+

21

> **Updating a User (Adding Roles to User)** cURL Request:

+-----------------------------------------------------------------------+
| > \$ curl \'http://localhost:8080/users/b583b456-9300-4cbd-4bcd-\     |
| > 199225f5d42c/roles                                                  |
| ?\_csrf=tBZRHLLak1k6Hb7eQi87ky43i37Zeo2LajitQf1hYUAS2rX_giBjKtHi8mEXe |
| > 4_pdwIPpUpUpka4HO6mUlmYc84CU3YkuITG\' -i -X PATCH \\\               |
| > -H \'Content-Type: application/json;charset=UTF-8\' \\\             |
| > -d \'\[ {\                                                          |
| > \"id\" : 2,\                                                        |
| > \"role\" : \"USER\",\                                               |
| > \"authorities\" : null\                                             |
| > } \]\'                                                              |
+-----------------------------------------------------------------------+
|                                                                       |
+-----------------------------------------------------------------------+

> Httpie Request:

+-----------------------------------------------------------------------+
| > \$ echo \'\[ {\                                                     |
| > \"id\" : 2,\                                                        |
| > \"role\" : \"USER\",\                                               |
| > \"authorities\" : null\                                             |
| > } \]\' \| http PATCH                                                |
| > \'http://localhost:8080/users/b583b456-9300-4cbd-4bcd-\             |
| > 199225f5d42c/roles                                                  |
| ?\_csrf=tBZRHLLak1k6Hb7eQi87ky43i37Zeo2LajitQf1hYUAS2rX_giBjKtHi8mEXe |
| > 4_pdwIPpUpUpka4HO6mUlmYc84CU3YkuITG\' \\\                           |
| > \'Content-Type:application/json;charset=UTF-8\'                     |
+-----------------------------------------------------------------------+
|                                                                       |
+-----------------------------------------------------------------------+

> Http Request:

+-----------------------------------------------------------------------+
| > PATCH /users/b583b456-9300-4cbd-4bcd-\                              |
| > 199225f5d42c/roles                                                  |
| ?\_csrf=tBZRHLLak1k6Hb7eQi87ky43i37Zeo2LajitQf1hYUAS2rX_giBjKtHi8mEXe |
| > 4_pdwIPpUpUpka4HO6mUlmYc84CU3YkuITG HTTP/1.1\                       |
| > Content-Type: application/json;charset=UTF-8\                       |
| > Content-Length: 65\                                                 |
| > Host: localhost:8080                                                |
+=======================================================================+
+-----------------------------------------------------------------------+

+-----------------------------------------------------------------------+
| > \[ {\                                                               |
| > \"id\" : 2,\                                                        |
| > \"role\" : \"USER\",\                                               |
| > \"authorities\" : null\                                             |
| > } \]                                                                |
+=======================================================================+
+-----------------------------------------------------------------------+

+-----------------------------------------------------------------------+
| > HTTP/1.1 200 OK\                                                    |
| > X-Content-Type-Options: nosniff\                                    |
| > X-XSS-Protection: 0\                                                |
| > Cache-Control: no-cache, no-store, max-age=0, must-revalidate       |
| > Pragma: no-cache\                                                   |
| > Expires: 0                                                          |
+=======================================================================+
|                                                                       |
+-----------------------------------------------------------------------+

> 22

+-----------------------------------------------------------------------+
| > X-Frame-Options: DENY                                               |
+=======================================================================+
|                                                                       |
+-----------------------------------------------------------------------+

> Request Parameters:\
> *Table 7. /users/{id}/roles*

+-----------------------------------+-----------------------------------+
| > **Parameter**                   | > **Description**                 |
+===================================+===================================+
| > id                              | > UUID of the user with roles to  |
|                                   | > be retrieved                    |
+-----------------------------------+-----------------------------------+

> Requests and Responses:

+-----------------------------------------------------------------------+
| > \[ {\                                                               |
| > \"id\" : 2,\                                                        |
| > \"role\" : \"USER\",\                                               |
| > \"authorities\" : null\                                             |
| > } \]                                                                |
+=======================================================================+
|                                                                       |
+-----------------------------------------------------------------------+

23

> **Updating a User's account status** cURL Request:

+-----------------------------------------------------------------------+
| > \$ curl                                                             |
| > \'http://localhost:8080/users/accounts/b583b456-9300-4cbd-4bcd-\    |
| > 199225f5d42c?\_csrf=msYQuHhBciXcnniaTFNysimVajiIA                   |
| l49dxbVxShRZ1SuLQZ3-fQnjhx0RRzxqxr-fn5GgR6hR1m_MmoQQHPtoxllAW2XGzNF\' |
| > -i -X PATCH \\\                                                     |
| > -H \'Content-Type: application/json;charset=UTF-8\' \\\             |
| > -d \'{\                                                             |
| > \"id\" : null,\                                                     |
| > \"username\" : \"user.test@mail.com\",\                             |
| > \"password\" : null,\                                               |
| > \"roles\" : null,\                                                  |
| > \"enabled\" : true,\                                                |
| > \"accountNonLocked\" : true,\                                       |
| > \"credentialsNonExpired\" : true,\                                  |
| > \"accountNonExpired\" : true\                                       |
| > }\'                                                                 |
+=======================================================================+
|                                                                       |
+-----------------------------------------------------------------------+

> Httpie Request:

+-----------------------------------------------------------------------+
| > \$ echo \'{\                                                        |
| > \"id\" : null,\                                                     |
| > \"username\" : \"user.test@mail.com\",\                             |
| > \"password\" : null,\                                               |
| > \"roles\" : null,\                                                  |
| > \"enabled\" : true,\                                                |
| > \"accountNonLocked\" : true,\                                       |
| > \"credentialsNonExpired\" : true,\                                  |
| > \"accountNonExpired\" : true\                                       |
| > }\' \| http PATCH                                                   |
| > \'http://localhost:8080/users/accounts/b583                         |
| b456-9300-4cbd-4bcd-199225f5d42c?\_csrf=msYQuHhBciXcnniaTFNysimVajiIA |
| l49dxbVxShRZ1SuLQZ3-fQnjhx0RRzxqxr-fn5GgR6hR1m_MmoQQHPtoxllAW2XGzNF\' |
| > \\\                                                                 |
| > \'Content-Type:application/json;charset=UTF-8\'                     |
+=======================================================================+
|                                                                       |
+-----------------------------------------------------------------------+

> Http Request:

+-----------------------------------------------------------------------+
| > PATCH /users/accounts/b583b456-9300-4cbd-4bcd-\                     |
| > 199225f5d42c?\_csrf=msYQuHhBciXcnniaTFNysimVaji                     |
| IAl49dxbVxShRZ1SuLQZ3-fQnjhx0RRzxqxr-fn5GgR6hR1m_MmoQQHPtoxllAW2XGzNF |
| > HTTP/1.1\                                                           |
| > Content-Type: application/json;charset=UTF-8\                       |
| > Content-Length: 215\                                                |
| > Host: localhost:8080                                                |
+=======================================================================+
+-----------------------------------------------------------------------+

+-----------------------------------------------------------------------+
| > {\                                                                  |
| > \"id\" : null,\                                                     |
| > \"username\" : \"user.test@mail.com\",                              |
+=======================================================================+
+-----------------------------------------------------------------------+

> 24

+-----------------------------------------------------------------------+
| > \"password\" : null,\                                               |
| > \"roles\" : null,\                                                  |
| > \"enabled\" : true,\                                                |
| > \"accountNonLocked\" : true,\                                       |
| > \"credentialsNonExpired\" : true,\                                  |
| > \"accountNonExpired\" : true\                                       |
| > }                                                                   |
+=======================================================================+
|                                                                       |
+-----------------------------------------------------------------------+

+-----------------------------------------------------------------------+
| > HTTP/1.1 200 OK\                                                    |
| > X-Content-Type-Options: nosniff\                                    |
| > X-XSS-Protection: 0\                                                |
| > Cache-Control: no-cache, no-store, max-age=0, must-revalidate       |
| > Pragma: no-cache\                                                   |
| > Expires: 0\                                                         |
| > X-Frame-Options: DENY                                               |
+=======================================================================+
|                                                                       |
+-----------------------------------------------------------------------+

> Request Parameters:\
> *Table 8. /users/accounts/{id}*

+-----------------------------------+-----------------------------------+
| > **Parameter**                   | > **Description**                 |
+===================================+===================================+
| > id                              | > Id of the user account status   |
|                                   | > to update                       |
+-----------------------------------+-----------------------------------+

> Requests and Responses:

+-----------------------------------------------------------------------+
| > {\                                                                  |
| > \"id\" : null,\                                                     |
| > \"username\" : \"user.test@mail.com\",\                             |
| > \"password\" : null,\                                               |
| > \"roles\" : null,\                                                  |
| > \"enabled\" : true,\                                                |
| > \"accountNonLocked\" : true,\                                       |
| > \"credentialsNonExpired\" : true,\                                  |
| > \"accountNonExpired\" : true\                                       |
| > }                                                                   |
+=======================================================================+
+-----------------------------------------------------------------------+

25

> **Updating a User's Password** cURL Request:

+-----------------------------------------------------------------------+
| > \$ curl \'http://localhost:8080/users/b583b456-9300-4cbd-4bcd-\     |
| >                                                                     |
|  199225f5d42c/accounts/password-reset?\_csrf=ApmB7tFQ2lj89oJQjoLvT8-\ |
| > \_q8pm9                                                             |
| 7anfZmBsLOhCBgF78NLMv2x2bJn7GzRx7dpvq_bLfyIhvIFwtCKTf-2g4KVaSAz3vR-\' |
| > -i -X PATCH \\\                                                     |
| > -H \'Content-Type: application/json;charset=UTF-8\' \\\             |
| > -d \'{\                                                             |
| > \"id\" : null,\                                                     |
| > \"username\" : \"user.test@mail.com\",\                             |
| > \"password\" : \"P@55w07d4t35t\",\                                  |
| > \"roles\" : null,\                                                  |
| > \"enabled\" : false,\                                               |
| > \"accountNonLocked\" : false,\                                      |
| > \"credentialsNonExpired\" : false,\                                 |
| > \"accountNonExpired\" : false\                                      |
| > }\'                                                                 |
+=======================================================================+
|                                                                       |
+-----------------------------------------------------------------------+

> Httpie Request:

+-----------------------------------------------------------------------+
| > \$ echo \'{\                                                        |
| > \"id\" : null,\                                                     |
| > \"username\" : \"user.test@mail.com\",\                             |
| > \"password\" : \"P@55w07d4t35t\",\                                  |
| > \"roles\" : null,\                                                  |
| > \"enabled\" : false,\                                               |
| > \"accountNonLocked\" : false,\                                      |
| > \"credentialsNonExpired\" : false,\                                 |
| > \"accountNonExpired\" : false\                                      |
| > }\' \| http PATCH                                                   |
| > \'http://localhost:8080/users/b583b456-9300-4cbd-4bcd               |
| -199225f5d42c/accounts/password-reset?\_csrf=ApmB7tFQ2lj89oJQjoLvT8-\ |
| > \_q8pm9                                                             |
| 7anfZmBsLOhCBgF78NLMv2x2bJn7GzRx7dpvq_bLfyIhvIFwtCKTf-2g4KVaSAz3vR-\' |
| > \\ \'Content-Type:application/json;charset=UTF-8\'                  |
+=======================================================================+
|                                                                       |
+-----------------------------------------------------------------------+

> Http Request:

+-----------------------------------------------------------------------+
| > PATCH                                                               |
| > /users/b583b456-9300-4cbd-4bcd-199225f5d42c/accounts/password-\     |
| > reset?\_csrf=ApmB7tFQ2lj89oJQjoLvT8-\                               |
| > \_q8p                                                               |
| m97anfZmBsLOhCBgF78NLMv2x2bJn7GzRx7dpvq_bLfyIhvIFwtCKTf-2g4KVaSAz3vR- |
| > HTTP/1.1 Content-Type: application/json;charset=UTF-8\              |
| > Content-Length: 230\                                                |
| > Host: localhost:8080                                                |
+=======================================================================+
+-----------------------------------------------------------------------+

+-----------------------------------------------------------------------+
| > {\                                                                  |
| > \"id\" : null,                                                      |
+=======================================================================+
+-----------------------------------------------------------------------+

> 26

+-----------------------------------------------------------------------+
| > \"username\" : \"user.test@mail.com\",\                             |
| > \"password\" : \"P@55w07d4t35t\",\                                  |
| > \"roles\" : null,\                                                  |
| > \"enabled\" : false,\                                               |
| > \"accountNonLocked\" : false,\                                      |
| > \"credentialsNonExpired\" : false,\                                 |
| > \"accountNonExpired\" : false\                                      |
| > }                                                                   |
+=======================================================================+
|                                                                       |
+-----------------------------------------------------------------------+

+-----------------------------------------------------------------------+
| > HTTP/1.1 200 OK\                                                    |
| > X-Content-Type-Options: nosniff\                                    |
| > X-XSS-Protection: 0\                                                |
| > Cache-Control: no-cache, no-store, max-age=0, must-revalidate       |
| > Pragma: no-cache\                                                   |
| > Expires: 0\                                                         |
| > X-Frame-Options: DENY                                               |
+=======================================================================+
|                                                                       |
+-----------------------------------------------------------------------+

> Request Parameters:\
> *Table 9. /users/{id}/accounts/password-reset*

+-----------------------------------+-----------------------------------+
| > **Parameter**                   | > **Description**                 |
+===================================+===================================+
| > id                              | > Id of the user with password to |
|                                   | > reset                           |
+-----------------------------------+-----------------------------------+

> Requests and Responses:

+-----------------------------------------------------------------------+
| > {\                                                                  |
| > \"id\" : null,\                                                     |
| > \"username\" : \"user.test@mail.com\",\                             |
| > \"password\" : \"P@55w07d4t35t\",\                                  |
| > \"roles\" : null,\                                                  |
| > \"enabled\" : false,\                                               |
| > \"accountNonLocked\" : false,\                                      |
| > \"credentialsNonExpired\" : false,\                                 |
| > \"accountNonExpired\" : false\                                      |
| > }                                                                   |
+=======================================================================+
|                                                                       |
+-----------------------------------------------------------------------+

+-----------------------+-----------------------+-----------------------+
| > **Path**            | > **Type**            | > **Description**     |
+=======================+=======================+=======================+
| > username            | > class               | username of the user  |
|                       | > java.lang.String    | to be save            |
+-----------------------+-----------------------+-----------------------+
| > password            | > class               | > password of the     |
|                       | > java.lang.String    | > user to save        |
+-----------------------+-----------------------+-----------------------+

27

> **Deleting a User**\
> cURL Request:

+-----------------------------------------------------------------------+
| > \$ curl                                                             |
| >                                                                     |
|  \'http://localhost:8080/users/b583b456-9300-4cbd-4bcd-199225f5d42c\' |
| > -i -X DELETE \\\                                                    |
| > -H \'Content-Type: application/json;charset=UTF-8\'                 |
+=======================================================================+
|                                                                       |
+-----------------------------------------------------------------------+

> Httpie Request:

+-----------------------------------------------------------------------+
| > \$ http DELETE                                                      |
| >                                                                     |
|  \'http://localhost:8080/users/b583b456-9300-4cbd-4bcd-199225f5d42c\' |
| > \\ \'Content-Type:application/json;charset=UTF-8\'                  |
+=======================================================================+
|                                                                       |
+-----------------------------------------------------------------------+

> Http Request:

+-----------------------------------------------------------------------+
| > DELETE /users/b583b456-9300-4cbd-4bcd-199225f5d42c HTTP/1.1         |
| > Content-Type: application/json;charset=UTF-8\                       |
| > Host: localhost:8080                                                |
+=======================================================================+
|                                                                       |
+-----------------------------------------------------------------------+

+-----------------------------------------------------------------------+
| > HTTP/1.1 200 OK\                                                    |
| > X-Content-Type-Options: nosniff\                                    |
| > X-XSS-Protection: 0\                                                |
| > Cache-Control: no-cache, no-store, max-age=0, must-revalidate       |
| > Pragma: no-cache\                                                   |
| > Expires: 0\                                                         |
| > X-Frame-Options: DENY                                               |
+=======================================================================+
|                                                                       |
+-----------------------------------------------------------------------+

> Request Parameters:\
> *Table 10. /users/{id}*

+-----------------------------------+-----------------------------------+
| > **Parameter**                   | > **Description**                 |
+===================================+===================================+
| > id                              | > Id of the user to delete        |
+-----------------------------------+-----------------------------------+

> Requests and Responses:
>
> 28
>
> **Creating a Client**\
> cURL Request:

+-----------------------------------------------------------------------+
| > \$ curl\                                                            |
| >                                                                     |
|  \'http://localhost:8080/clients?\_csrf=QpzkTIHKtOPQ9SYD1YmqyNuCX5hzk |
| 5xc6VUz3bBwR4G-pYOdJ6XXL7Kv19b9xkU34qSe_Om2cqEXo6xxj2QC5IgWJbDYkLes\' |
| > -i -X POST \\\                                                      |
| > -H \'Content-Type: application/json;charset=UTF-8\' \\\             |
| > -d \'{\                                                             |
| > \"id\" : \"001i\",\                                                 |
| > \"clientId\" : \"001ci\",\                                          |
| > \"name\" : \"Client03\",\                                           |
| > \"secret\" : \"53cr3t50\$9feW1t6m3\",\                              |
| > \"authenticationMethods\" : \[ {\                                   |
| > \"id\" : 1,\                                                        |
| > \"method\" : \"client_secret_basic\"\                               |
| > } \],\                                                              |
| > \"grantTypes\" : \[ {\                                              |
| > \"id\" : 1,\                                                        |
| > \"grantType\" : \"authorization_code\"\                             |
| > } \],\                                                              |
| > \"redirectUris\" : \[ {\                                            |
| > \"id\" : 1,\                                                        |
| > \"uri\" : \"http://127.0.0.1:1000\"\                                |
| > } \],\                                                              |
| > \"scopes\" : \[ {\                                                  |
| > \"id\" : 1,\                                                        |
| > \"scope\" : \"openid\"\                                             |
| > } \],\                                                              |
| > \"tokenSettings\" : {\                                              |
| > \"id\" : 1,\                                                        |
| > \"format\" : \"self-contained\",\                                   |
| > \"accessTokenTTL\" : 30\                                            |
| > }\                                                                  |
| > }\'                                                                 |
+=======================================================================+
|                                                                       |
+-----------------------------------------------------------------------+

> Httpie Request:

+-----------------------------------------------------------------------+
| > \$ echo \'{\                                                        |
| > \"id\" : \"001i\",\                                                 |
| > \"clientId\" : \"001ci\",\                                          |
| > \"name\" : \"Client03\",\                                           |
| > \"secret\" : \"53cr3t50\$9feW1t6m3\",\                              |
| > \"authenticationMethods\" : \[ {\                                   |
| > \"id\" : 1,\                                                        |
| > \"method\" : \"client_secret_basic\"\                               |
| > } \],\                                                              |
| > \"grantTypes\" : \[ {                                               |
+=======================================================================+
|                                                                       |
+-----------------------------------------------------------------------+

29

+-----------------------------------------------------------------------+
| > \"id\" : 1,\                                                        |
| > \"grantType\" : \"authorization_code\"\                             |
| > } \],\                                                              |
| > \"redirectUris\" : \[ {\                                            |
| > \"id\" : 1,\                                                        |
| > \"uri\" : \"http://127.0.0.1:1000\"\                                |
| > } \],\                                                              |
| > \"scopes\" : \[ {\                                                  |
| > \"id\" : 1,\                                                        |
| > \"scope\" : \"openid\"\                                             |
| > } \],\                                                              |
| > \"tokenSettings\" : {\                                              |
| > \"id\" : 1,\                                                        |
| > \"format\" : \"self-contained\",\                                   |
| > \"accessTokenTTL\" : 30\                                            |
| > }\                                                                  |
| > }\' \| http POST\                                                   |
| >                                                                     |
|  \'http://localhost:8080/clients?\_csrf=QpzkTIHKtOPQ9SYD1YmqyNuCX5hzk |
| 5xc6VUz3bBwR4G-pYOdJ6XXL7Kv19b9xkU34qSe_Om2cqEXo6xxj2QC5IgWJbDYkLes\' |
| > \\\                                                                 |
| > \'Content-Type:application/json;charset=UTF-8\'                     |
+=======================================================================+
|                                                                       |
+-----------------------------------------------------------------------+

> Http Request:

+-----------------------------------------------------------------------+
| > POST                                                                |
| > /clients?\_csrf=QpzkTIHKtOPQ9SYD1YmqyNuCX5h                         |
| zk5xc6VUz3bBwR4G-pYOdJ6XXL7Kv19b9xkU34qSe_Om2cqEXo6xxj2QC5IgWJbDYkLes |
| > HTTP/1.1 Content-Type: application/json;charset=UTF-8\              |
| > Content-Length: 539\                                                |
| > Host: localhost:8080                                                |
+=======================================================================+
+-----------------------------------------------------------------------+

+-----------------------------------------------------------------------+
| > {\                                                                  |
| > \"id\" : \"001i\",\                                                 |
| > \"clientId\" : \"001ci\",\                                          |
| > \"name\" : \"Client03\",\                                           |
| > \"secret\" : \"53cr3t50\$9feW1t6m3\",\                              |
| > \"authenticationMethods\" : \[ {\                                   |
| > \"id\" : 1,\                                                        |
| > \"method\" : \"client_secret_basic\"\                               |
| > } \],\                                                              |
| > \"grantTypes\" : \[ {\                                              |
| > \"id\" : 1,\                                                        |
| > \"grantType\" : \"authorization_code\"\                             |
| > } \],\                                                              |
| > \"redirectUris\" : \[ {\                                            |
| > \"id\" : 1,\                                                        |
| > \"uri\" : \"http://127.0.0.1:1000\"\                                |
| > } \],\                                                              |
| > \"scopes\" : \[ {\                                                  |
| > \"id\" : 1,\                                                        |
| > \"scope\" : \"openid\"\                                             |
| > } \],                                                               |
+=======================================================================+
+-----------------------------------------------------------------------+

> 30

+-----------------------------------------------------------------------+
| > \"tokenSettings\" : {\                                              |
| > \"id\" : 1,\                                                        |
| > \"format\" : \"self-contained\",\                                   |
| > \"accessTokenTTL\" : 30\                                            |
| > }\                                                                  |
| > }                                                                   |
+=======================================================================+
|                                                                       |
+-----------------------------------------------------------------------+

+-----------------------------------------------------------------------+
| > HTTP/1.1 201 Created\                                               |
| > Content-Type: application/json\                                     |
| > X-Content-Type-Options: nosniff\                                    |
| > X-XSS-Protection: 0\                                                |
| > Cache-Control: no-cache, no-store, max-age=0, must-revalidate       |
| > Pragma: no-cache\                                                   |
| > Expires: 0\                                                         |
| > X-Frame-Options: DENY\                                              |
| > Content-Length: 337                                                 |
+=======================================================================+
+-----------------------------------------------------------------------+

+-----------------------------------------------------------------------+
| > {\"id\":\"001i\",\"clientId\":\"001                                 |
| ci\",\"name\":\"Client03\",\"authenticationMethods\":\[{\"id\":1,\"me |
| > thod\":\"client_secret_basic\"}                                     |
| \],\"grantTypes\":\[{\"id\":1,\"grantType\":\"authorization_code\"}\] |
| > ,\"redirectUris\":\[{\"id\":1,\"uri                                 |
| \":\"http://127.0.0.1:1000\"}\],\"scopes\":\[{\"id\":1,\"scope\":\"op |
| > enid\"}\],\"tokenSetti                                              |
| ngs\":{\"id\":1,\"format\":\"self-contained\",\"accessTokenTTL\":30}} |
+=======================================================================+
+-----------------------------------------------------------------------+

> Requests and Responses:

+-----------------------------------------------------------------------+
| > {\                                                                  |
| > \"id\" : \"001i\",\                                                 |
| > \"clientId\" : \"001ci\",\                                          |
| > \"name\" : \"Client03\",\                                           |
| > \"secret\" : \"53cr3t50\$9feW1t6m3\",\                              |
| > \"authenticationMethods\" : \[ {\                                   |
| > \"id\" : 1,\                                                        |
| > \"method\" : \"client_secret_basic\"\                               |
| > } \],\                                                              |
| > \"grantTypes\" : \[ {\                                              |
| > \"id\" : 1,\                                                        |
| > \"grantType\" : \"authorization_code\"                              |
+=======================================================================+
| > } \],\                                                              |
| > \"redirectUris\" : \[ {\                                            |
| > \"id\" : 1,\                                                        |
| > \"uri\" : \"http://127.0.0.1:1000\"\                                |
| > } \],\                                                              |
| > \"scopes\" : \[ {\                                                  |
| > \"id\" : 1,\                                                        |
| > \"scope\" : \"openid\"\                                             |
| > } \],\                                                              |
| > \"tokenSettings\" : {\                                              |
| > \"id\" : 1,\                                                        |
| > \"format\" : \"self-contained\",                                    |
+-----------------------------------------------------------------------+

31

+-----------------------------------------------------------------------+
| > \"accessTokenTTL\" : 30\                                            |
| > }\                                                                  |
| > }                                                                   |
+=======================================================================+
|                                                                       |
+-----------------------------------------------------------------------+

+-----------------------+-----------------------+-----------------------+
| > **Path**            | > **Type**            | > **Description**     |
+=======================+=======================+=======================+
| > name                | > class               | > Desired client name |
|                       | > java.lang.String    |                       |
+-----------------------+-----------------------+-----------------------+
| > secret              | > class               | > Desired client      |
|                       | > java.lang.String    | > secret              |
+-----------------------+-----------------------+-----------------------+
| >                     | > interface           | > Authentication      |
| authenticationMethods | > java.util.Set       | > Methods of the      |
|                       |                       | > client              |
+-----------------------+-----------------------+-----------------------+
| > authenticat         | > class               | > Authentication      |
| ionMethods.\[\].metho | > java.lang.String    | > method name of an   |
| > d                   |                       | > authentication      |
|                       |                       | > method of the       |
|                       |                       | > client              |
+-----------------------+-----------------------+-----------------------+
| > grantTypes          | > interface           | > Desired list/set of |
|                       | > java.util.Set       | > auth2.0             |
|                       |                       | > authentication      |
|                       |                       | > grant types         |
+-----------------------+-----------------------+-----------------------+
| > gran                | > class               | > authorization grant |
| tTypes.\[\].grantType | > java.lang.String    | > type name of a      |
|                       |                       | > grant type within a |
|                       |                       | > list of grant types |
+-----------------------+-----------------------+-----------------------+
| > redirectUris        | > class               | > Desired list/set of |
|                       | > java.lang.String    | > redirect URIs       |
+-----------------------+-----------------------+-----------------------+
| >                     | > class               | > redirect uri within |
| redirectUris.\[\].uri | > java.lang.String    | > a list of redirect  |
|                       |                       | > uris                |
+-----------------------+-----------------------+-----------------------+
| > scopes              | > interface           | > Desired oauth2.0    |
|                       | > java.util.Set       | > authentication      |
|                       |                       | > scopes              |
+-----------------------+-----------------------+-----------------------+
| > scopes.\[\].scope   | > class               | > Scope's name within |
|                       | > java.lang.String    | > a list of scopes    |
+-----------------------+-----------------------+-----------------------+
| > tokenSettings       | > class\              | > Desired oauth2.0    |
|                       | > com.davea           | > token settings      |
|                       | ce.greenspaces.tokens |                       |
|                       | >                     |                       |
|                       | ettings.TokenSettings |                       |
+-----------------------+-----------------------+-----------------------+
| >                     | > String              | > Format of the token |
|  tokenSettings.format |                       | > from the            |
|                       |                       | > authorization       |
|                       |                       | > server              |
+-----------------------+-----------------------+-----------------------+
| > tokenSe             | > Number              | > Time to live or     |
| ttings.accessTokenTTL |                       | > expiry time of an   |
|                       |                       | > access token        |
+-----------------------+-----------------------+-----------------------+

+-----------------------------------------------------------------------+
| > {\"id\":\"001i\",\"clientId\":\"001                                 |
| ci\",\"name\":\"Client03\",\"authenticationMethods\":\[{\"id\":1,\"me |
| > thod\":\"client_secret_basic\"}                                     |
| \],\"grantTypes\":\[{\"id\":1,\"grantType\":\"authorization_code\"}\] |
| > ,\"redirectUris\":\[{\"id\":1,\"uri                                 |
| \":\"http://127.0.0.1:1000\"}\],\"scopes\":\[{\"id\":1,\"scope\":\"op |
| > enid\"}\],\"tokenSetti                                              |
| ngs\":{\"id\":1,\"format\":\"self-contained\",\"accessTokenTTL\":30}} |
+=======================================================================+
|                                                                       |
+-----------------------------------------------------------------------+

+-----------------------+-----------------------+-----------------------+
| > **Path**            | > **Type**            | > **Description**     |
+=======================+=======================+=======================+
| > id                  | > String              | > id of created       |
|                       |                       | > client              |
+-----------------------+-----------------------+-----------------------+
| > clientId            | > String              | > client id of        |
|                       |                       | > created client      |
+-----------------------+-----------------------+-----------------------+

> 32

+-----------------------+-----------------------+-----------------------+
| > **Path**            | > **Type**            | > **Description**     |
+=======================+=======================+=======================+
| > name                | > String              | > name of created     |
|                       |                       | > client              |
+-----------------------+-----------------------+-----------------------+
| >                     | > interface           | > Authentication      |
| authenticationMethods | > java.util.Set       | > Methods of the      |
|                       |                       | > client              |
+-----------------------+-----------------------+-----------------------+
| > authenticat         | > class               | > Authentication      |
| ionMethods.\[\].metho | > java.lang.String    | > method name of an   |
| > d                   |                       | > authentication      |
|                       |                       | > method of the       |
|                       |                       | > client              |
+-----------------------+-----------------------+-----------------------+
| > grantTypes          | > Array               | > Grant types of the  |
|                       |                       | > created clients     |
+-----------------------+-----------------------+-----------------------+
| > gran                | > class               | > authorization grant |
| tTypes.\[\].grantType | > java.lang.String    | > type name of a      |
|                       |                       | > grant type within a |
|                       |                       | > list of grant types |
+-----------------------+-----------------------+-----------------------+
| > redirectUris        | > Array               | > Redirect URIs of    |
|                       |                       | > the created client  |
+-----------------------+-----------------------+-----------------------+
| >                     | > class               | > redirect uri within |
| redirectUris.\[\].uri | > java.lang.String    | > a list of redirect  |
|                       |                       | > uris                |
+-----------------------+-----------------------+-----------------------+
| > scopes              | > Array               | > Oauth2.0 Scopes of  |
|                       |                       | > the create client   |
+-----------------------+-----------------------+-----------------------+
| > scopes.\[\].scope   | > class               | > Scope's name within |
|                       | > java.lang.String    | > a list of scopes    |
+-----------------------+-----------------------+-----------------------+
| > tokenSettings       | > Object              | > Token Settings of   |
|                       |                       | > the create client   |
+-----------------------+-----------------------+-----------------------+
| >                     | > String              | > Format of the token |
|  tokenSettings.format |                       | > from the            |
|                       |                       | > authorization       |
|                       |                       | > server              |
+-----------------------+-----------------------+-----------------------+
| > tokenSe             | > Number              | > Time to live or     |
| ttings.accessTokenTTL |                       | > expiry time of an   |
|                       |                       | > access token        |
+-----------------------+-----------------------+-----------------------+

33

> **Retrieving a Client**\
> cURL Request:

+-----------------------------------------------------------------------+
| > \$ curl \'http://localhost:8080/clients/001i\' -i -X GET \\ -H      |
| > \'Content-Type: application/json;charset=UTF-8\'                    |
+=======================================================================+
|                                                                       |
+-----------------------------------------------------------------------+

> Httpie Request:

+-----------------------------------------------------------------------+
| > \$ http GET \'http://localhost:8080/clients/001i\' \\               |
| > \'Content-Type:application/json;charset=UTF-8\'                     |
+-----------------------------------------------------------------------+
|                                                                       |
+-----------------------------------------------------------------------+

> Http Request:

+-----------------------------------------------------------------------+
| > GET /clients/001i HTTP/1.1\                                         |
| > Content-Type: application/json;charset=UTF-8\                       |
| > Host: localhost:8080                                                |
+-----------------------------------------------------------------------+
|                                                                       |
+-----------------------------------------------------------------------+

+-----------------------------------------------------------------------+
| > HTTP/1.1 200 OK\                                                    |
| > Content-Type: application/json\                                     |
| > X-Content-Type-Options: nosniff\                                    |
| > X-XSS-Protection: 0\                                                |
| > Cache-Control: no-cache, no-store, max-age=0, must-revalidate       |
| > Pragma: no-cache\                                                   |
| > Expires: 0\                                                         |
| > X-Frame-Options: DENY\                                              |
| > Content-Length: 337                                                 |
+=======================================================================+
+-----------------------------------------------------------------------+

+-----------------------------------------------------------------------+
| > {\"id\":\"001i\",\"clientId\":\"001                                 |
| ci\",\"name\":\"Client03\",\"authenticationMethods\":\[{\"id\":1,\"me |
| > thod\":\"client_secret_basic\"}                                     |
| \],\"grantTypes\":\[{\"id\":1,\"grantType\":\"authorization_code\"}\] |
| > ,\"redirectUris\":\[{\"id\":1,\"uri                                 |
| \":\"http://127.0.0.1:1000\"}\],\"scopes\":\[{\"id\":1,\"scope\":\"op |
| > enid\"}\],\"tokenSetti                                              |
| ngs\":{\"id\":1,\"format\":\"self-contained\",\"accessTokenTTL\":30}} |
+=======================================================================+
+-----------------------------------------------------------------------+

> Request Parameters:\
> *Table 11. /clients/{id}*

+-----------------------------------+-----------------------------------+
| > **Parameter**                   | > **Description**                 |
+===================================+===================================+
| > id                              | > UUID of client to retrieve      |
+-----------------------------------+-----------------------------------+

> Requests and Responses:
>
> 34

+-----------------------------------------------------------------------+
| > {\"id\":\"001i\",\"clientId\":\"001                                 |
| ci\",\"name\":\"Client03\",\"authenticationMethods\":\[{\"id\":1,\"me |
| > thod\":\"client_secret_basic\"}                                     |
| \],\"grantTypes\":\[{\"id\":1,\"grantType\":\"authorization_code\"}\] |
| > ,\"redirectUris\":\[{\"id\":1,\"uri                                 |
| \":\"http://127.0.0.1:1000\"}\],\"scopes\":\[{\"id\":1,\"scope\":\"op |
| > enid\"}\],\"tokenSetti                                              |
| ngs\":{\"id\":1,\"format\":\"self-contained\",\"accessTokenTTL\":30}} |
+=======================================================================+
|                                                                       |
+-----------------------------------------------------------------------+

+-----------------------+-----------------------+-----------------------+
| > **Path**            | > **Type**            | > **Description**     |
+=======================+=======================+=======================+
| > id                  | > String              | > id of retrieved     |
|                       |                       | > client              |
+-----------------------+-----------------------+-----------------------+
| > clientId            | > String              | > client id of        |
|                       |                       | > retrieved client    |
+-----------------------+-----------------------+-----------------------+
| > name                | > String              | > name of retrieved   |
|                       |                       | > client              |
+-----------------------+-----------------------+-----------------------+
| >                     | > interface           | > Authentication      |
| authenticationMethods | > java.util.Set       | > Methods of the      |
|                       |                       | > retrieved client    |
+-----------------------+-----------------------+-----------------------+
| > authenti            | > class               | > Id of an            |
| cationMethods.\[\].id | > java.lang.Integer   | > authentication      |
|                       |                       | > method of the       |
|                       |                       | > retrieved client    |
+-----------------------+-----------------------+-----------------------+
| > authenticat         | > class               | > Authentication      |
| ionMethods.\[\].metho | > java.lang.String    | > method name of an   |
| > d                   |                       | > authentication      |
|                       |                       | > method in\          |
|                       |                       | > the retrieved       |
|                       |                       | > client              |
+-----------------------+-----------------------+-----------------------+
| > grantTypes          | > Array               | > Grant types of the  |
|                       |                       | > retrieved clients   |
+-----------------------+-----------------------+-----------------------+
| > gran                | > class               | > authorization grant |
| tTypes.\[\].grantType | > java.lang.String    | > type name of a      |
|                       |                       | > grant type within a |
|                       |                       | > list of grant types |
+-----------------------+-----------------------+-----------------------+
| > redirectUris        | > Array               | > Redirect URIs of    |
|                       |                       | > the retrieved       |
|                       |                       | > client              |
+-----------------------+-----------------------+-----------------------+
| >                     | > class               | > redirect uri within |
| redirectUris.\[\].uri | > java.lang.String    | > a list of redirect  |
|                       |                       | > uris                |
+-----------------------+-----------------------+-----------------------+
| > scopes              | > Array               | > Oauth2.0 Scopes of  |
|                       |                       | > the retrieved       |
|                       |                       | > client              |
+-----------------------+-----------------------+-----------------------+
| > scopes.\[\].scope   | > class               | > Scope's name within |
|                       | > java.lang.String    | > a list of scopes    |
+-----------------------+-----------------------+-----------------------+
| > tokenSettings       | > Object              | > Token Settings of   |
|                       |                       | > the retrieved       |
|                       |                       | > client              |
+-----------------------+-----------------------+-----------------------+
| > tokenSettings.id    | > Number              | > id of the token     |
|                       |                       | > setting from the    |
|                       |                       | > authorization       |
|                       |                       | > server              |
+-----------------------+-----------------------+-----------------------+
| >                     | > String              | > Format of the token |
|  tokenSettings.format |                       | > from the            |
|                       |                       | > authorization       |
|                       |                       | > server              |
+-----------------------+-----------------------+-----------------------+
| > tokenSe             | > Number              | > Time to live or     |
| ttings.accessTokenTTL |                       | > expiry time of an   |
|                       |                       | > access token        |
+-----------------------+-----------------------+-----------------------+

35

> **Retrieving all Clients**\
> cURL Request:

+-----------------------------------------------------------------------+
| > \$ curl \'http://localhost:8080/clients\' -i -X GET \\ -H           |
| > \'Content-Type: application/json;charset=UTF-8\'                    |
+-----------------------------------------------------------------------+

> Httpie Request:

+-----------------------------------------------------------------------+
| > \$ http GET \'http://localhost:8080/clients\' \\\                   |
| > \'Content-Type:application/json;charset=UTF-8\'                     |
+=======================================================================+
|                                                                       |
+-----------------------------------------------------------------------+

> Http Request:

+-----------------------------------------------------------------------+
| > GET /clients HTTP/1.1\                                              |
| > Content-Type: application/json;charset=UTF-8\                       |
| > Host: localhost:8080                                                |
+-----------------------------------------------------------------------+
|                                                                       |
+-----------------------------------------------------------------------+

+-----------------------------------------------------------------------+
| > HTTP/1.1 200 OK\                                                    |
| > Content-Type: application/json\                                     |
| > X-Content-Type-Options: nosniff\                                    |
| > X-XSS-Protection: 0\                                                |
| > Cache-Control: no-cache, no-store, max-age=0, must-revalidate       |
| > Pragma: no-cache\                                                   |
| > Expires: 0\                                                         |
| > X-Frame-Options: DENY\                                              |
| > Content-Length: 339                                                 |
+=======================================================================+
+-----------------------------------------------------------------------+

+-----------------------------------------------------------------------+
| > \[{\"id\":\"001i\",\"clientId\":\"00                                |
| 1ci\",\"name\":\"Client03\",\"authenticationMethods\":\[{\"id\":1,\"m |
| > ethod\":\"client_secret_basic\                                      |
| "}\],\"grantTypes\":\[{\"id\":1,\"grantType\":\"authorization_code\"} |
| > \],\"redirectUris\":\[{\"id\":1,\"ur                                |
| i\":\"http://127.0.0.1:1000\"}\],\"scopes\":\[{\"id\":1,\"scope\":\"o |
| > penid\"}\],\"tokenSetting                                           |
| s\":{\"id\":1,\"format\":\"self-contained\",\"accessTokenTTL\":30}}\] |
+=======================================================================+
+-----------------------------------------------------------------------+

> Requests and Responses:

+-----------------------------------------------------------------------+
| > \[{\"id\":\"001i\",\"clientId\":\"00                                |
| 1ci\",\"name\":\"Client03\",\"authenticationMethods\":\[{\"id\":1,\"m |
| > ethod\":\"client_secret_basic\                                      |
| "}\],\"grantTypes\":\[{\"id\":1,\"grantType\":\"authorization_code\"} |
| > \],\"redirectUris\":\[{\"id\":1,\"ur                                |
| i\":\"http://127.0.0.1:1000\"}\],\"scopes\":\[{\"id\":1,\"scope\":\"o |
| > penid\"}\],\"tokenSetting                                           |
| s\":{\"id\":1,\"format\":\"self-contained\",\"accessTokenTTL\":30}}\] |
+=======================================================================+
|                                                                       |
+-----------------------------------------------------------------------+

> 36

+-----------------------+-----------------------+-----------------------+
| > **Path**            | > **Type**            | > **Description**     |
+=======================+=======================+=======================+
| > \[\].id             | > class               | > id of retrieved     |
|                       | > java.lang.Integer   | > client within a     |
|                       |                       | > list of clients     |
+-----------------------+-----------------------+-----------------------+
| > \[\].clientId       | > class               | > client id of a      |
|                       | > java.lang.String    | > retrieved client    |
|                       |                       | > within a list of    |
|                       |                       | > clients             |
+-----------------------+-----------------------+-----------------------+
| > \[\].name           | > class               | > name of a retrieved |
|                       | > java.lang.String    | > client within a     |
|                       |                       | > list of clients     |
+-----------------------+-----------------------+-----------------------+
| > \[\].               | > interface           | > Authentication      |
| authenticationMethods | > java.util.Set       | > Methods of a        |
|                       |                       | > retrieved client    |
|                       |                       | > within a list of    |
|                       |                       | > clients             |
+-----------------------+-----------------------+-----------------------+
| \[\].authenti         | > class               | > id of one of the    |
| cationMethods.\[\].id | > java.lang.Integer   | > authentication      |
|                       |                       | > methods of a        |
|                       |                       | > retrieved client    |
|                       |                       | > within a list of    |
|                       |                       | > clients             |
+-----------------------+-----------------------+-----------------------+
| > \[\].authenti       | > class               | > One of the          |
| cationMethods.\[\].me | > java.lang.String    | > authentication\     |
| > thod                |                       | > methods\' name of a |
|                       |                       | > retrieved client    |
|                       |                       | > within a list of    |
|                       |                       | > clients             |
+-----------------------+-----------------------+-----------------------+
| > \[\].grantTypes     | > interface           | > Grant types of a    |
|                       | > java.util.Set       | > retrieved client    |
|                       |                       | > within a list of    |
|                       |                       | > clients             |
+-----------------------+-----------------------+-----------------------+
| > \[                  | > class               | > id of a Grant type  |
| \].grantTypes.\[\].id | > java.lang.Integer   | > of the retrieved    |
|                       |                       | > clients             |
+-----------------------+-----------------------+-----------------------+
| > \[\].gran           | > class               | > Grant type name of  |
| tTypes.\[\].grantType | > java.lang.String    | > one of the          |
|                       |                       | > retrieved clients   |
+-----------------------+-----------------------+-----------------------+
| > \[\].redirectUris   | > interface           | > Redirect URIs of a  |
|                       | > java.util.Set       | > retrieved client    |
|                       |                       | > within a list of    |
|                       |                       | > clients             |
+-----------------------+-----------------------+-----------------------+
| > \[\].               | > class               | > redirect uri within |
| redirectUris.\[\].uri | > java.lang.String    | > a list of redirect  |
|                       |                       | > uris                |
+-----------------------+-----------------------+-----------------------+
| > \[\].scopes         | > interface           | > Oauth2.0 Scopes of  |
|                       | > java.util.Set       | > a retrieved client  |
|                       |                       | > within a list of    |
|                       |                       | > clients             |
+-----------------------+-----------------------+-----------------------+
| > \                   | > class               | > Scope's name within |
| [\].scopes.\[\].scope | > java.lang.String    | > a list of scopes    |
+-----------------------+-----------------------+-----------------------+
| > \[\].tokenSettings  | > class\              | > Token Settings of a |
|                       | > com.davea           | > retrieved client    |
|                       | ce.greenspaces.tokens | > within a list of    |
|                       | >                     | > clients             |
|                       | ettings.TokenSettings |                       |
+-----------------------+-----------------------+-----------------------+
| >                     | > class               | > id of a token       |
| \[\].tokenSettings.id | > java.lang.Integer   | > setting of a\       |
|                       |                       | > retrieved client    |
|                       |                       | > within a list of    |
|                       |                       | > clients             |
+-----------------------+-----------------------+-----------------------+
| > \[\]                | > class               | > Format of the token |
| .tokenSettings.format | > java.lang.String    | > from the            |
|                       |                       | > authorization       |
|                       |                       | > server              |
+-----------------------+-----------------------+-----------------------+
| > \[\].tokenS         | > class               | > Time to live or     |
| ettings.accessTokenTT | > java.lang.Integer   | > expiry time of an   |
| > L                   |                       | > access token        |
+-----------------------+-----------------------+-----------------------+

37

> **Updating a Client**\
> cURL Request:

+-----------------------------------------------------------------------+
| > \$ curl\                                                            |
| > \'http://localhost:                                                 |
| 8080/clients/001i?\_csrf=uoszX_AiaIQNLnuuZazkNcaznjwaIAOt0DIvhfmnvZPw |
| > lK0I27wLbpYXUbMgFkPPVoHQB_eLs14rFTaAtVdJsZ-RjKDApstp\' -i -X PATCH  |
| > \\\                                                                 |
| > -H \'Content-Type: application/json;charset=UTF-8\' \\\             |
| > -d \'{\                                                             |
| > \"id\" : \"001i\",\                                                 |
| > \"clientId\" : \"001ci\",\                                          |
| > \"name\" : \"Client03\",\                                           |
| > \"secret\" : \"53cr3t50\$9feW1t6m3\",\                              |
| > \"authenticationMethods\" : \[ {\                                   |
| > \"id\" : 1,\                                                        |
| > \"method\" : \"client_secret_basic\"\                               |
| > } \],\                                                              |
| > \"grantTypes\" : \[ {\                                              |
| > \"id\" : 1,\                                                        |
| > \"grantType\" : \"authorization_code\"\                             |
| > } \],\                                                              |
| > \"redirectUris\" : \[ {\                                            |
| > \"id\" : 1,\                                                        |
| > \"uri\" : \"http://127.0.0.1:1000\"\                                |
| > } \],\                                                              |
| > \"scopes\" : \[ {\                                                  |
| > \"id\" : 1,\                                                        |
| > \"scope\" : \"openid\"\                                             |
| > } \],\                                                              |
| > \"tokenSettings\" : {\                                              |
| > \"id\" : 1,\                                                        |
| > \"format\" : \"self-contained\",\                                   |
| > \"accessTokenTTL\" : 30\                                            |
| > }\                                                                  |
| > }\'                                                                 |
+=======================================================================+
|                                                                       |
+-----------------------------------------------------------------------+

> Httpie Request:

+-----------------------------------------------------------------------+
| > \$ echo \'{\                                                        |
| > \"id\" : \"001i\",\                                                 |
| > \"clientId\" : \"001ci\",\                                          |
| > \"name\" : \"Client03\",\                                           |
| > \"secret\" : \"53cr3t50\$9feW1t6m3\",\                              |
| > \"authenticationMethods\" : \[ {\                                   |
| > \"id\" : 1,\                                                        |
| > \"method\" : \"client_secret_basic\"\                               |
| > } \],\                                                              |
| > \"grantTypes\" : \[ {                                               |
+=======================================================================+
|                                                                       |
+-----------------------------------------------------------------------+

> 38

+-----------------------------------------------------------------------+
| > \"id\" : 1,\                                                        |
| > \"grantType\" : \"authorization_code\"\                             |
| > } \],\                                                              |
| > \"redirectUris\" : \[ {\                                            |
| > \"id\" : 1,\                                                        |
| > \"uri\" : \"http://127.0.0.1:1000\"\                                |
| > } \],\                                                              |
| > \"scopes\" : \[ {\                                                  |
| > \"id\" : 1,\                                                        |
| > \"scope\" : \"openid\"\                                             |
| > } \],\                                                              |
| > \"tokenSettings\" : {\                                              |
| > \"id\" : 1,\                                                        |
| > \"format\" : \"self-contained\",\                                   |
| > \"accessTokenTTL\" : 30\                                            |
| > }\                                                                  |
| > }\' \| http PATCH\                                                  |
| > \'http://localhost:                                                 |
| 8080/clients/001i?\_csrf=uoszX_AiaIQNLnuuZazkNcaznjwaIAOt0DIvhfmnvZPw |
| > lK0I27wLbpYXUbMgFkPPVoHQB_eLs14rFTaAtVdJsZ-RjKDApstp\' \\\          |
| > \'Content-Type:application/json;charset=UTF-8\'                     |
+=======================================================================+
|                                                                       |
+-----------------------------------------------------------------------+

> Http Request:

+-----------------------------------------------------------------------+
| > PATCH\                                                              |
| > /clients/001i?\_cs                                                  |
| rf=uoszX_AiaIQNLnuuZazkNcaznjwaIAOt0DIvhfmnvZPwlK0I27wLbpYXUbMgFkPPVo |
| > HQB_eLs14rFTaAtVdJsZ-RjKDApstp HTTP/1.1\                            |
| > Content-Type: application/json;charset=UTF-8\                       |
| > Content-Length: 539\                                                |
| > Host: localhost:8080                                                |
+=======================================================================+
+-----------------------------------------------------------------------+

+-----------------------------------------------------------------------+
| > {\                                                                  |
| > \"id\" : \"001i\",\                                                 |
| > \"clientId\" : \"001ci\",\                                          |
| > \"name\" : \"Client03\",\                                           |
| > \"secret\" : \"53cr3t50\$9feW1t6m3\",\                              |
| > \"authenticationMethods\" : \[ {\                                   |
| > \"id\" : 1,\                                                        |
| > \"method\" : \"client_secret_basic\"\                               |
| > } \],\                                                              |
| > \"grantTypes\" : \[ {\                                              |
| > \"id\" : 1,\                                                        |
| > \"grantType\" : \"authorization_code\"\                             |
| > } \],\                                                              |
| > \"redirectUris\" : \[ {\                                            |
| > \"id\" : 1,\                                                        |
| > \"uri\" : \"http://127.0.0.1:1000\"\                                |
| > } \],\                                                              |
| > \"scopes\" : \[ {\                                                  |
| > \"id\" : 1,\                                                        |
| > \"scope\" : \"openid\"                                              |
+=======================================================================+
+-----------------------------------------------------------------------+

39

+-----------------------------------------------------------------------+
| > } \],\                                                              |
| > \"tokenSettings\" : {\                                              |
| > \"id\" : 1,\                                                        |
| > \"format\" : \"self-contained\",\                                   |
| > \"accessTokenTTL\" : 30\                                            |
| > }\                                                                  |
| > }                                                                   |
+=======================================================================+
|                                                                       |
+-----------------------------------------------------------------------+

+-----------------------------------------------------------------------+
| > HTTP/1.1 200 OK\                                                    |
| > X-Content-Type-Options: nosniff\                                    |
| > X-XSS-Protection: 0\                                                |
| > Cache-Control: no-cache, no-store, max-age=0, must-revalidate       |
| > Pragma: no-cache\                                                   |
| > Expires: 0\                                                         |
| > X-Frame-Options: DENY                                               |
+=======================================================================+
|                                                                       |
+-----------------------------------------------------------------------+

> Request Parameters:\
> *Table 12. /clients/{id}*

+-----------------------------------+-----------------------------------+
| > **Parameter**                   | > **Description**                 |
+===================================+===================================+
| > id                              | > UUID of the client to update    |
+-----------------------------------+-----------------------------------+

> Requests and Responses:

+-----------------------------------------------------------------------+
| > {\                                                                  |
| > \"id\" : \"001i\",\                                                 |
| > \"clientId\" : \"001ci\",\                                          |
| > \"name\" : \"Client03\",\                                           |
| > \"secret\" : \"53cr3t50\$9feW1t6m3\",\                              |
| > \"authenticationMethods\" : \[ {\                                   |
| > \"id\" : 1,\                                                        |
| > \"method\" : \"client_secret_basic\"\                               |
| > } \],\                                                              |
| > \"grantTypes\" : \[ {\                                              |
| > \"id\" : 1,\                                                        |
| > \"grantType\" : \"authorization_code\"\                             |
| > } \],\                                                              |
| > \"redirectUris\" : \[ {\                                            |
| > \"id\" : 1,\                                                        |
| > \"uri\" : \"http://127.0.0.1:1000\"\                                |
| > } \],\                                                              |
| > \"scopes\" : \[ {\                                                  |
| > \"id\" : 1,\                                                        |
| > \"scope\" : \"openid\"\                                             |
| > } \],\                                                              |
| > \"tokenSettings\" : {\                                              |
| > \"id\" : 1,\                                                        |
| > \"format\" : \"self-contained\",                                    |
+=======================================================================+
+-----------------------------------------------------------------------+

> 40

+-----------------------------------------------------------------------+
| > \"accessTokenTTL\" : 30\                                            |
| > }\                                                                  |
| > }                                                                   |
+=======================================================================+
|                                                                       |
+-----------------------------------------------------------------------+

+-----------------------+-----------------------+-----------------------+
| > **Path**            | > **Type**            | > **Description**     |
+=======================+=======================+=======================+
| > name                | > class               | > Desired client name |
|                       | > java.lang.String    | > of client to be     |
|                       |                       | > updated             |
+-----------------------+-----------------------+-----------------------+
| > secret              | > class               | > Desired client      |
|                       | > java.lang.String    | > secret of client to |
|                       |                       | > be updated          |
+-----------------------+-----------------------+-----------------------+
| >                     | > interface           | Desired oauth2.0      |
| authenticationMethods | > java.util.Set       | authentication        |
|                       |                       | methods of client to  |
|                       |                       | be updated            |
+-----------------------+-----------------------+-----------------------+
| > authenticat         | > class               | > Authentication      |
| ionMethods.\[\].metho | > java.lang.String    | > method name of      |
| > d                   |                       | > desired oauth2.0    |
|                       |                       | > authentication      |
|                       |                       | > methods of client   |
|                       |                       | > to be updated       |
+-----------------------+-----------------------+-----------------------+
| > grantTypes          | > interface           | > Desired list/set of |
|                       | > java.util.Set       | > auth2.0\            |
|                       |                       | > authentication      |
|                       |                       | > grant types of      |
|                       |                       | > client to be        |
|                       |                       | > updated             |
+-----------------------+-----------------------+-----------------------+
| > gran                | > String              | > Authorization grant |
| tTypes.\[\].grantType |                       | > type of the client  |
|                       |                       | > to be updated       |
+-----------------------+-----------------------+-----------------------+
| > redirectUris        | > class               | > Desired list/set of |
|                       | > java.lang.String    | > redirect URIs of    |
|                       |                       | > client to be        |
|                       |                       | > updated             |
+-----------------------+-----------------------+-----------------------+
| >                     | > class               | > A redirect Uri of   |
| redirectUris.\[\].uri | > java.lang.String    | > the client to be    |
|                       |                       | > updated             |
+-----------------------+-----------------------+-----------------------+
| > scopes              | > interface           | > Desired oauth2.o    |
|                       | > java.util.Set       | > authentication      |
|                       |                       | > scopes of client to |
|                       |                       | > be updated          |
+-----------------------+-----------------------+-----------------------+
| > scopes.\[\].scope   | > class               | > A scope of the      |
|                       | > java.lang.String    | > client to be        |
|                       |                       | > updated             |
+-----------------------+-----------------------+-----------------------+
| > tokenSettings       | > class\              | > Desired oauth2.0    |
|                       | > com.davea           | > token settings of   |
|                       | ce.greenspaces.tokens | > client to be        |
|                       | >                     | > updated             |
|                       | ettings.TokenSettings |                       |
+-----------------------+-----------------------+-----------------------+
| >                     | > class               | > Format of the       |
|  tokenSettings.format | > java.lang.String    | > access token of the |
|                       |                       | > client to be        |
|                       |                       | > updated             |
+-----------------------+-----------------------+-----------------------+
| > tokenSe             | > class               | > Expiry time of the  |
| ttings.accessTokenTTL | > java.lang.Integer   | > access token of the |
|                       |                       | > client to be        |
|                       |                       | > updated             |
+-----------------------+-----------------------+-----------------------+

41

> **Deleting a Client**\
> cURL Request:

+-----------------------------------------------------------------------+
| > \$ curl \'http://localhost:8080/clients/001i\' -i -X DELETE \\ -H   |
| > \'Content-Type: application/json;charset=UTF-8\'                    |
+=======================================================================+
|                                                                       |
+-----------------------------------------------------------------------+

> Httpie Request:

+-----------------------------------------------------------------------+
| > \$ http DELETE \'http://localhost:8080/clients/001i\' \\            |
| > \'Content-Type:application/json;charset=UTF-8\'                     |
+-----------------------------------------------------------------------+
|                                                                       |
+-----------------------------------------------------------------------+

> Http Request:

+-----------------------------------------------------------------------+
| > DELETE /clients/001i HTTP/1.1\                                      |
| > Content-Type: application/json;charset=UTF-8\                       |
| > Host: localhost:8080                                                |
+=======================================================================+
|                                                                       |
+-----------------------------------------------------------------------+

+-----------------------------------------------------------------------+
| > HTTP/1.1 200 OK\                                                    |
| > X-Content-Type-Options: nosniff\                                    |
| > X-XSS-Protection: 0\                                                |
| > Cache-Control: no-cache, no-store, max-age=0, must-revalidate       |
| > Pragma: no-cache\                                                   |
| > Expires: 0\                                                         |
| > X-Frame-Options: DENY                                               |
+=======================================================================+
|                                                                       |
+-----------------------------------------------------------------------+

> Request Parameters:\
> *Table 13. /clients/{id}*

+-----------------------------------+-----------------------------------+
| > **Parameter**                   | > **Description**                 |
+===================================+===================================+
| > id                              | > UUID of client to delete        |
+-----------------------------------+-----------------------------------+

> Requests and Responses:
>
> 42

