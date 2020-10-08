# Assignment 1 - Concurrent Programming with Java

## 1. Sock Matching Robot

#### Steps to run
```shell
javac -d . *.java
java sockmatchingrobot.SockMatchingRobot input.txt
```

#### Input file format
1. First line contains the number of Robotic Arms
2. Second line contains n integers ci (1 <= ci <= 4) separated by space indicating the colour of the sock

---

## 2. Data Modification in Distributed System

#### Steps to run
```shell
javac -d . *.java
java distributedsystem.DistributedSystem
# Follow the instructions for various operations
```

#### Output
* Final output is printed in the ./output directory containing the files sorted_name.txt and sorted_roll.txt.
* Changes are also reflected in the file stud_info.txt.

---

## 3.1 Calculator 1.0 for Differently Abled Persons

#### Steps to run
```shell
javac -d . *.java
java calculator.Calculator
```

#### Instructions
* The number keys will be periodically highlighted first. Once a number is selected using the “enter” key , the function keys will be periodically highlighted. After a function key is selected, the periodic highlighting returns to the number keys for the selection of the second number. Thus, to perform the calculation 3+2, the user makes the following sequence of operations: wait for 3 to be highlighted --> press ‘enter’ --> wait for ‘+’ to be highlighted --> press ‘enter’ --> wait for 2 to be highlighted --> press ‘enter’.
* Pressing 'enter' key on '=' button evaluates the expression.
* Pressing 'enter' key on 'C' button clears the display.

---

## 3.2 Calculator 2.0 for Differently Abled Persons

#### Steps to run
```shell
javac -d . *.java
java calculator2.Calculator2
```

#### Instructions
* To select a number, the user has to press “enter” key. To select a function, the user has to press the “space” key. Thus, to perform the calculation 33+25, the user makes the following sequence of operations: wait for 3 to be highlighted --> press ‘enter’ --> wait for 3 to be highlighted --> press ‘enter’ --> wait for‘+’ to be highlighted --> press ‘space’ --> wait for 2 to be highlighted --> press ‘enter’ --> wait for 5 to be highlighted --> press ‘enter’.
* Pressing 'space' key on '=' button evaluates the expression.
* Pressing 'space' key on 'C' button clears the display.