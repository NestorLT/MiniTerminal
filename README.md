

# MiniTerminal

MiniTerminal is a small **Java-based terminal emulator** that allows you to execute basic file system commands.


## ⚙️ Requirements

* **Java 11+** (JDK must be installed and available in your PATH)
* **Maven 3+** (for building and running the project)

## ✨ Available Commands

| Command                                           | Description                                                                     |
| ------------------------------------------------  | ------------------------------------------------------------------------------- |
| `pwd`                                             | Prints the current working directory                                            |
| `cd <DIR>`                                        | Changes the current directory to `<DIR>`                                        |
| `ls`                                              | Lists directories and files in the current directory                            |
| `ll`                                              | Lists directories and files with size and last modification date                |
| `mkdir <DIR>`                                     | Creates a new directory `<DIR>` in the current path                             |
| `rm <FILE>`                                       | Deletes the file `<FILE>`                                                       |
| `mv <FILE1> <FILE2>`                              | Moves or renames `<FILE1>` to `<FILE2>`                                         |
| `mostrar <FILE>`                                  | Displays the contents of the file                                               |
| `sustituir <FILE> <original_string> <new_string>` | Replaces all occurrences of `original_string` with `new_string` inside the file |
| `help`                                            | Displays information about all available commands                               |
| `exit`                                            | Exits the program                                                               |


##  How to Run

### 1. Build the project

From the project root, open a terminal and run:

```bash
mvn clean package
```

### 2. Run the application

* Using the compiled JAR:

  ```bash
  java -cp target/classes miniterminal.MiniTerminal
  ```

## 💻 Usage

Once the application is running, you can type commands at the prompt.
Use:

```bash
help
```

to see the list of all available commands.


## 📂 Example

```bash
> pwd
/home/user

> ls
Documents  Downloads  Pictures

> cd Documents

> mkdir Test

> show notes.txt
This is the content of the file.
```


## 📌 Notes

* This project is intended for **educational purposes**.
* It is **not** meant to replace a real terminal, but to demonstrate how basic shell commands can be implemented in Java.


