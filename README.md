# Springboot Exercise:


1. Create a new spring-boot service to manage users; x

2. A user has only id and name; x

3. Implement a mocked repository, that keeps the user list in memory (ignore multi-threading issues), on which you will implement two methods:
   1. add new user, 
   2. get all users;

4. Create UserController with two REST endpoints: x

   1. create new user, 
   2. get all users;

5. Add logs; 

6. Document the endpoints with SpringDoc; x

7. Now let's create a new repository for the same operations as before, but this time connecting to a real DB (you can choose which one to use - H2, Mongo, etc…); x

8. This new repository should be used only when running with the production profile, otherwise will continue to use the mocked one; 

9. If using properties, create a class for those properties; 

10. Finally, add a local cache, which should be used by the user repository instead of getting users from the database. +/-
