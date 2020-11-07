# Assignment 2 - Logic Programming with Prolog


## 1 - Finding Relationship and Gender

#### Steps to run
```shell
swipl -l Relation.pl
```

#### Sample input data
```prolog
% parent(A, B) :- A is parent of B
parent(jatin,avantika).

% male(A) :- A is male
male(kattappa).

% female(A) :- A is female
female(shivkami).
```

#### 1.1 - UNCLE

The relation of uncle is defined as follows:

A is uncle of B if grandparent of B and parent of A is common, A is not parent of B and A is male.

#### Examples
```prolog
% NOTE: Press '.' to stop execution of current command.
% NOTE: Press ';' to find another possible solution to current command.
% NOTE: Press 'halt.' to exit the program.

?- uncle(kattappa, avantika).
true .

?- uncle(avantika, manisha).
false.

?- uncle(kattappa, A).
A = avantika ;
false.

?- uncle(jatin, avantika).
false.

?- uncle(A, B).
A = kattappa,
B = avantika ;
false.
```


#### 1.2 - HALF SISTER

The relation of half-sister is defined as follows:

A is half-sister of B if only one parent of A and B is common (other parent is not common) and A is female.

Following assumptions are taken while handling corner cases:

* If only one parent of both A and B exists, then that parent should be common.
* If only one parent of either A or B exists, then we check whether that parent is common with any parent of other person.

#### Examples
```prolog
% NOTE: Press '.' to stop execution of current command.
% NOTE: Press ';' to find another possible solution to current command.
% NOTE: Press 'halt.' to exit the program.

?- halfsister(avantika, shivkami).
true .

?- halfsister(A, shivkami).
A = avantika ;
false.

?- halfsister(A, B).
A = avantika,
B = shivkami ;
A = shivkami,
B = avantika ;
false.
```

---

## 2 - Bus Travel Planner

Dijkstra algorithm is used to find shortest path between nodes.

NOTE: Waiting time at bus stop is also considered while calculating shortest time paths.

#### Steps to run
```shell
swipl -l BusTravelPlanner.pl
```

#### Sample input data
```prolog
% Bus(Number, Origin, Destination Place, Departure Time, Arrival Time, Distance, Cost)
bus(1, 'A', 'J', 14.5, 15, 1, 10).
```


#### Examples
```prolog
% NOTE: Press '.' to stop execution of current command.
% NOTE: Press ';' to find another possible solution to current command.
% NOTE: Press 'halt.' to exit the program.

?- route('A', 'L').
Optimum Distance:
A,1->J,3->Pan,4->C,6->M,7->L
Distance=18,Time=74.0,Cost=42
Optimum Time:
A,2->C,6->M,7->L
Distance=29,Time=48.5,Cost=24
Optimum Cost:
A,2->C,6->M,7->L
Distance=29,Time=48.5,Cost=24
true .

?- route('L', 'A').
No path exists!
false.
```

---

## 3 - Prisoner Escape Problem

all_paths() prints all possible paths for a prisoner to escape from the jail.

optimal() prints all optimal paths with minimum distance.

valid(Path) checks whether the path is valid or invalid.

Depth First Search is used to find all possible paths and update the minimum distance and optimal path whenever found.

#### Steps to run
```shell
swipl -l PrisonerEscape.pl
```

#### Sample input data
```prolog
% edge(source gate, destination gate, distance)
edge('G1', 'G5', 4).

% Starting gate
src('G1').

% Ending gate
dest('G17').
```

#### Examples
```prolog
% NOTE: Press '.' to stop execution of current command.
% NOTE: Press ';' to find another possible solution to current command.
% NOTE: Press 'halt.' to exit the program.

?- all_paths().
G4-->G6-->G5-->G8-->G7-->G12-->G10-->G11-->G13-->G14-->G18-->G17
G4-->G6-->G5-->G8-->G7-->G12-->G11-->G15-->G13-->G14-->G17
G4-->G6-->G5-->G8-->G7-->G12-->G11-->G15-->G13-->G14-->G18-->G17
G4-->G6-->G5-->G8-->G7-->G12-->G11-->G13-->G14-->G17
G4-->G6-->G5-->G8-->G7-->G12-->G11-->G13-->G14-->G18-->G17
G4-->G6-->G5-->G8-->G7-->G12-->G11-->G10-->G15-->G13-->G14-->G17
G4-->G6-->G5-->G8-->G7-->G12-->G11-->G10-->G15-->G13-->G14-->G18-->G17
and so on...
false.

?- optimal().
G3-->G6-->G12-->G14-->G17
Minimum Distance: 19
true.

?- valid(['G1','G6','G8','G9','G8','G7','G10','G15','G13','G14','G18','G17']).
true.
```