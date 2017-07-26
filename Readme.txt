* The following services are defined for the REST API
	1. Get All the Users 
		URL - http://localhost:8080/UserApi/api/users
		Method - GET
		
	2. Get an User
		URL - http://localhost:8080/UserApi/api/users/1
		Method - GET
		
	3. Create a new user 
		URL - http://localhost:8080/UserApi/api/users
		Method - POST
		Request Header - [{"key":"Content-Type","value":"application/json"}]
		Request Body - {"fName": "Balaji","lName": "Chandrasekaran","email": "balajic@abc.com","pinCode": "600001", "birthDate": "10-Mar-2011"}
		
	4. Update an existing user 
		URL - http://localhost:8080/UserApi/api/users/1
		Method - PUT
		Request Header - [{"key":"Content-Type","value":"application/json"}]
		Request Body - {"pinCode": "600001","birthDate": "10-Mar-2011"}
		
	5. Deactivate an existing user 
		URL - http://localhost:8080/UserApi/api/users/1
		Method - DELETE
		