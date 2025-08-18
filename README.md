# Secure-Authentication
A simple authentication system that allows user account creation and securely verifies credentials during login.
## User Creation
- Prompts the user for their first name, last name, email, password, and password confirmation.
- Validates that the password and confirmation match.
- Hashes the password using Argon2 with a unique salt.
- Stores the user’s information (including the salted hash) in a SQLite database.
## User Verification
- Prompts the user to enter their email/username and password.
- Retrieves the stored hashed password and salt for the given account.
- Hashes the entered password with the same salt.
- Compares the result with the stored hash.
- If they match, the user is logged in; otherwise, an error message is displayed.
## Language and Tools
- Java
- JDK
- Argon2
- SQLite
## Contributions
Contributions are welcome.
## Disclaimer
This project is intended for learning purposes only. Please verify and strengthen security measures before using it in production.
