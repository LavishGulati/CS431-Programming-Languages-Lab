% edge(source gate, destination gate, distance)
edge('G1', 'G5', 4).
edge('G2', 'G5', 6).
edge('G3', 'G5', 8).
edge('G4', 'G5', 9).
edge('G1', 'G6', 10).
edge('G2', 'G6', 9).
edge('G3', 'G6', 3).
edge('G4', 'G6', 5).
edge('G5', 'G7', 3).
edge('G5', 'G10', 4).
edge('G5', 'G11', 6).
edge('G5', 'G12', 7).
edge('G5', 'G6', 7).
edge('G5', 'G8', 9).
edge('G6', 'G8', 2).
edge('G6', 'G12', 3).
edge('G6', 'G11', 5).
edge('G6', 'G10', 9).
edge('G6', 'G7', 10).
edge('G7', 'G10', 2).
edge('G7', 'G11', 5).
edge('G7', 'G12', 7).
edge('G7', 'G8', 10).
edge('G8', 'G9', 3).
edge('G8', 'G12', 3).
edge('G8', 'G11', 4).
edge('G8', 'G10', 8).
edge('G10', 'G15', 5).
edge('G10', 'G11', 2).
edge('G10', 'G12', 5).
edge('G11', 'G15', 4).
edge('G11', 'G13', 5).
edge('G11', 'G12', 4).
edge('G12', 'G13', 7).
edge('G12', 'G14', 8).
edge('G15', 'G13', 3).
edge('G13', 'G14', 4).
edge('G14', 'G17', 5).
edge('G14', 'G18', 4).
edge('G17', 'G18', 8).

% Starting gates
src('G1').
src('G2').
src('G3').
src('G4').

% Ending gate
dest('G17').

% Dynamic predicate indicating the set of visited vertices
:- dynamic (isVisited/1).


% Print all possible paths from starting gate to ending gate
all_paths() :-
    % Find a starting gate
    src(G1),
    % Set G1 as visited
    asserta(isVisited(G1)),
    % Find all paths starting from G1
    not(find_path(G1, [])),
    % Unset visited G1
	retract(isVisited(G1)),
	fail.


% Base case for find_path: If G1 is ending gate, print the path
find_path(G1, Path) :-
    % G1 should be ending gate
    dest(G1),
    % Append current path and G1 to get full path 
    append(Path, [G1], FullPath),
    % Print final path
	print_list(FullPath),
	fail.
% Finds a path starting from gate G1
find_path(G1, Path) :-
    % G1 should not be ending gate
    not(dest(G1)),
    % Append current path and G1 to get full path
    append(Path, [G1], FullPath),
    % Check if some G2 exists such that there is an edge from G1 to G2
    isEdge(G1, G2, _),
    % G2 should not be visited. If visited, there will be a loop
    not(isVisited(G2)),
    % Set G2 as visited
    asserta(isVisited(G2)),
    % Recursively call find_path with G2 as starting gate
    not(find_path(G2, FullPath)),
    % Unset visited G2
	retract(isVisited(G2)),
	fail.


% Check if there exists edge from G1 to G2 or G2 to G1
isEdge(G1, G2, D) :-
    edge(G1, G2, D).
isEdge(G1, G2, D) :-
    edge(G2, G1, D).


% Base case for print_list: If no value in list, print newline
print_list([]) :-
    write('\n').
% Prints list
print_list([H|T]) :-
    % Print current value
    (is_empty(T) -> write(H);
            write(H), write('-->')),
    % Call print_list recursively for tail
	print_list(T).


% Checks if the list is empty
is_empty(List) :-
    not(member(_, List)).


% Dynamic predicate indicating minimum distance
:- dynamic (minDistance/1).
% Set initial minimum distance to be INFINITY
minDistance(2147483647).

% Dynamic predicate indicating optimal path
:- dynamic (optimalPath/1).


% Finds optimal path with minimum distance for the given problem
optimal() :-
    % Initiate depth first search for optimal path
    not(initiate_depth_first_search()),
    % Print all optimal paths
    not(print_optimal_paths()),
    % Get MinDistance
    minDistance(MinDistance),
    % Print MinDistance
    (MinDistance =:= 2147483647 -> write('No path exists!'), false
                                ; write('Minimum Distance: '), write(MinDistance)).


% Initiate depth first search from all starting gates
initiate_depth_first_search() :-
    % G1 should be starting gate
    src(G1),
    % Set G1 as visited
    asserta(isVisited(G1)),
    % Call depth first search from G1
    not(depth_first_search(G1, 0, [])),
    % Unset visited G1
	retract(isVisited(G1)),
	fail.


/* Base case for depth_first_search: If distance is greater than min distance,
stop the DFS */
depth_first_search(_, Distance, _) :-
    % Get min distance
    minDistance(MinDistance),
    % Check if distance is greater than min distance
	Distance > MinDistance,
	fail.

/* Base case for depth_first_search: If G1 is ending gate, update minimum
distance */
depth_first_search(G1, Distance, Path):-
    % G1 should be ending gate
    dest(G1),
    % Append current path and G1 to get full path
    append(Path, [G1], FullPath),	
    % Get current minimum distance
    minDistance(MinDistance),
    % Update the minimum distance
	updateMinDistance(MinDistance, Distance, FullPath),
	fail.

% Call depth first search from G1
depth_first_search(G1, Distance, Path) :-
    % G1 should not be ending gate
    not(dest(G1)),
    % Append current path and G1 to get full path
    append(Path, [G1], FullPath),
    % Check if some G2 exists such that there is an edge from G1 to G2
    % If the edge exists, get the weight of that edge
    isEdge(G1, G2, D),
    % G2 should not be visited. If visited, there will be a loop
    not(isVisited(G2)),
    % Set G2 as visited
    asserta(isVisited(G2)),
    % Update total distance
    TotalDistance is Distance+D,
    % Recursively call depth first search from G2
    not(depth_first_search(G2, TotalDistance, FullPath)),
    % Unset visited G2
	retract(isVisited(G2)),
	fail.


/* If distance is less than current min distance, reset optimal paths and add
path to the list of optimal paths */
updateMinDistance(MinDistance, Distance, Path) :-
    % Check if distance is less than min distance
    MinDistance > Distance,
    % Unset minimum distance as MinDistance
    retract(minDistance(MinDistance)),
    % Set minimum distance as Distance
    asserta(minDistance(Distance)),
    % Unset all paths in optimalPath
    retractall(optimalPath(_)),
    % Add path to list of optimal paths
	assertz(optimalPath(Path)).

/* If current min distance is same as distance, then add path to the list of
optimal paths */
updateMinDistance(MinDistance, Distance, Path) :-
    % Check if min distance is same as distance
    MinDistance =:= Distance,
    % Add path to list of optimal paths
	assertz(optimalPath(Path)).


% Prints all optimal paths
print_optimal_paths() :-
    % Get all instances of optimal path
    optimalPath(OptimalPath),
    % Print optimal path
    print_list(OptimalPath),
    fail.


% Check if given path is valid for the problem
valid(Path) :-
    % Get first gate in the path
    [G1|_] = Path,
    % First gate should be starting gate
    src(G1),
    % Check if the path is valid
    isValid(Path),
    !.


/* Base case for isValid: If only one gate exists in path, that gate should be
ending gate */
isValid(Path) :-
    [G1|T] = Path,
    % Check if T is empty
    is_empty(T),
    % G1 should be ending gate
	dest(G1).
/* Checks if the path is valid by ensuring that there exist edges between the
gates */
isValid(Path) :-
    % Get first two gates G1 and G2 from the path
    [G1,G2|_] = Path,
    % Check if there exists an edge between G1 and G2
    isEdge(G1, G2, _),
    [_|T] = Path,
    % Call isValid recursively for rest of the path
	isValid(T).