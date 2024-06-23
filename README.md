# BankAccountSystem

A simple bank account management system written in C.

## Features

- Create a bank account
- Log in to an existing account
- Transfer money between accounts
- Check account balance

## Usage

1. **Compile the code:**

    ```sh
    gcc -o BankAccountSystem main.c -lconio -lwindows
    ```

2. **Run the executable:**

    ```sh
    ./BankAccountSystem
    ```

## Project Structure

- `main.c`: The main source file containing the implementation of the bank account system.
- `username.txt`: The file used to store user account information.
- `mon.txt`: The file used to store transaction records.

## Functions

### Account Management

- `void account(void)`: Creates a new bank account.
- `void accountcreated(void)`: Displays a message after successfully creating an account.
- `void login(void)`: Handles user login.

### User Actions

- `void display(char* username)`: Displays account details and options after successful login.
- `void checkbalance(char* username)`: Checks the account balance.
- `void transfermoney(void)`: Transfers money from one account to another.
- `void logout(void)`: Logs the user out.

### Utility Functions

- `void gotoxy(int x, int y)`: Sets the cursor position in the console window.

## Dependencies

- This project uses standard C libraries: `stdio.h`, `stdlib.h`, `string.h`, and `windows.h` for Windows-specific functions.

## Contributing

Contributions are welcome! Please open an issue or submit a pull request.

## License

This project is licensed under the MIT License.
