# Assignment 3 - Functional Programming with Haskell


## 1 - Implement Haskell Functions for Basic Set Operations

isEmpty - Checks whether a set is empty

unionSet - Returns union of two sets

intersection - Returns intersection of two sets

subtraction - Returns difference of two sets, i.e. A-B

addition - Returns addition of two sets

NOTE: All inputs are given as lists.

#### Steps to run
```shell
ghci SetOperations.hs
```

#### Examples
```haskell
-- NOTE: Press ':q' to exit the program.

*Main> isEmpty []
True

*Main> isEmpty [1, 2, 3]
False

*Main> unionSet [1,2,3,4,5,6] [4,5,6,7,8,9,10]
[1,2,3,4,5,6,7,8,9,10]

*Main> intersection [1,2,3,4,5,6] [4,5,6,7,8,9,10]
[4,5,6]

*Main> subtraction [1,2,3,4,5,6] [4,5,6,7,8,9,10]
[1,2,3]

*Main> addition [1,2] [1,2,4]
[2,5,3,4,6]
```

---

## 2 - IITG Football League

fixture "all" - Generates fixtures according to random draw and displays entire fixture details.

fixture "team" - Displays fixture details of specified team.

nextMatch day time - Displays next match details given day and time.

#### Install dependencies
```shell
cabal install random --lib
```

#### Steps to run
```shell
ghci IITGFootballLeague.hs
```

#### Examples
```haskell
-- NOTE: Press ':q' to exit the program.

*Main> fixture "all"
CH vs BS     1-12-2020     9:30
CS vs HU     1-12-2020     7:30
CM vs MA     2-12-2020     9:30
CV vs ME     2-12-2020     7:30
EE vs PH     3-12-2020     9:30
DS vs ST     3-12-2020     7:30

*Main> fixture "ST"
DS vs ST     3-12-2020     7:30

*Main> fixture "TEAM"
Team does not exist!

*Main> nextMatch 1 8.50
CH vs BS     1-12-2020     9:30

*Main> nextMatch 3 19.75
No match ahead!

*Main> nextMatch 32 23.59
Invalid day!

*Main> nextMatch 1 24.50
Invalid time!
```

---

## 3 - House Planner

design area num_bedroom num_hall - Prints dimensions of all components given area, number of bedrooms and number of halls.

#### Steps to run
```shell
ghci HousePlanner.hs
```

#### Examples
```haskell
-- NOTE: Press ':q' to exit the program.

*Main> design 1000 3 2
Bedroom: 3 (10,10)
Hall: 2 (15,10)
Kitchen: 1 (7,5)
Bathroom: 4 (4,5)
Garden: 1 (12,17)
Balcony: 1 (9,9)
Unused Space: 0
```
