# Assignment 3 - Functional Programming with Haskell


## 1 - Implement Haskell Functions for Basic Set Operations


---

## 2 - IITG Football League

fixture "all" - Displays entire fixture details.

fixture "team" - Displays fixture details of specified team.

nextMatch day time - Displays next match details given day and time.

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