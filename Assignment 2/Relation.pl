% parent(A, B) :- A is parent of B
parent(jatin,avantika).
parent(jolly,jatin).
parent(jolly,kattappa).
parent(manisha,avantika).
parent(manisha,shivkami).
parent(bahubali,shivkami).

% male(A) :- A is male
male(kattappa).
male(jolly).
male(bahubali).

% female(A) :- A is female
female(shivkami).
female(avantika).


% uncle(A, B) :- A is uncle of B
uncle(A, B) :-
    % Parent of A and grandparent of B should be common
    parent(C, B),
    parent(D, C),
    parent(D, A),
    % A should not be B's parent
    not(parent(A, B)),
    % A should be male
    male(A).


% halfsister(A, B) :- A is half-sister of B
halfsister(A, B) :-
    % A and B should have only one common parent
    parent(C, A),
    parent(C, B),
    parent(E, A),
    parent(F, B),
    % Check whether other parent is different
    Cnt1 is 0,
    (not(E = C) -> X = E, Cnt2 is 0; Cnt2 is Cnt1+1),
    (not(F = C) -> Y = F, Cnt3 is 0; Cnt3 is Cnt2+1),
    (Cnt3 =:= 2 -> not(X == Y), true),
    not(A = B),
    % A should be female
    female(A).