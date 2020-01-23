# Setting Up Keycloack Clients

1. visit http://localhost:8081/auth/admin
1. login with admin credentials (see docker-compose)
1. setup new client with name "angular-spa" and root URL "http://localhost:4200"
1. in the following settings page, enable "implicit flow"
1. setup new client with name "spring-rest-app" and root URL "http://localhost:8080/api"
1. Define a mapping under "Mappers", select "create"
1. set a name, e.g. "role_mapper", select type "User Realm Role", set "Token Claim Name" to e.g. "roles"

# Addings Roles

1. select "roles" menu and click "add role"
1. user a clear name, e.g. "licensed_user"

# Adding groups

1. select "groups" menu and click "new"
1. user a clear name, e.g. "licensed_user_group"
1. under "role mappings", assign the "licensed_user" role

# Adding Users

1. select the "user" menu and click "add user"
1. set details, finish
1. under "credentials", set a password
1. under "groups", join the "licensed_user_group"
1. add another user, set password, but do not join the group

# Open ID Connect Endpoints

Keycloack sets up OIDC endpoints by default for a realm, i.e. also to the default realm ("master").

The discovery document is available at http://localhost:8081/auth/realms/master/.well-known/openid-configuration.
It provides all required information to set up the client applications to connect to keycloak.