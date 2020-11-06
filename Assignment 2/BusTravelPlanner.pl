/* Bus(Number, Origin, Destination Place, Departure Time, Arrival Time,
Distance, Cost) */
bus(1, 'A', 'J', 14.5, 15, 1, 10).
bus(2, 'A', 'C', 16, 16.5, 7, 8).
bus(3, 'J', 'Pan', 16, 16.5, 1, 8).
bus(4, 'Pan', 'C', 16, 16.5, 2, 8).
bus(5, 'Pan', 'Pal', 16, 16.5, 7, 8).
bus(6, 'C', 'M', 16, 16.5, 7, 8).
bus(7, 'M', 'L', 16, 16.5, 7, 8).


% Find minimum distance, time and cost between source and destination
route(Src, Dest) :-
	% If findMinWeight returns false, then no path exists
	(findMinWeight(Src, Dest, 'Distance') -> true ;
										write('No path exists!'), false),
	findMinWeight(Src, Dest, 'Time'),
	findMinWeight(Src, Dest, 'Cost').


% Compute minimum weight between source and destination given type of weights
findMinWeight(Src, Dest, Type) :-
	% Create initial Parent dictionary with parent of source as null
	dict_create(Parent, parent, [Src='']),
	% Call dijkstra for Src
	% Returns the final parent dictionary as NewParent
	dijkstra([Src-0], [], Type, _, Parent, NewParent),
	/* Check if Dest exists in the dictionary. If not, then no path from Src to
	Dest exists */
	get_dict(Dest, NewParent, _),
	% Print output
	write('Optimum '), write(Type), write(':\n'),
	% Print path and compute final dest, time and cost
	printPath(Src, Dest, NewParent, D, T, C), write('\n'),
	write('Distance='), write(D), write(','),
	write('Time='), write(T),  write(','),
	write('Cost='), write(C), write('\n').


/* Base case for dijkstra: If no vertex left, return the final weights and
Parent dictionary */
dijkstra([], VisSet, _, VisSet, Parent, Parent).
% Dijkstra algorithm to compute single source shortest paths
dijkstra(CurSet, VisSet, Type, MinDist, Parent, NewNewParent) :-
	% Compute the vertex with minimum weight
	% RestCurSet is the current set minus the min vertex
	minVertex(CurSet, V-D, RestCurSet),
	% Compute the set of adjacent vertices to min vertex V
	findAdjacentSet(V, AdjSet, Type),
	% Compute the set of vertices which are updatable (i.e. not visited yet)
	findUpdatableSet(AdjSet, VisSet, NewAdjSet),
	% Updates the weights of adjacent vertices
	% NewCurSet is the new current set with updated weights
	update(NewAdjSet, RestCurSet, V, D, NewCurSet, Parent, NewParent),
	% Call dijkstra recursively for remaining vertices 
	dijkstra(NewCurSet, [V-D|VisSet], Type, MinDist, NewParent, NewNewParent).


% Given the current set of vertices, returns the vertex with minimum weight
minVertex([H|T], MinV, RestCurSet) :-
	minVertexHelper(H, T, MinV, RestCurSet).


/* Base case for minVertexHelper: If no vertex left, assign current vertex as
the min vertex */
minVertexHelper(Cur, [], Cur, []).
% Helper function for minVertex
minVertexHelper(Cur, [H|T], MinV, [H2|RestCurSet]) :-
	H = _-D1, Cur = _-D,
	/* Depending on lesser weight, assign current vertex (probable candidate
	for min vertex) and add the other vertex to rest set */
	(D1 < D -> NextM = H, H2 = Cur
			 ; NextM = Cur, H2 = H),
	% Call minVertexHelper recursively for remaining vertices
	minVertexHelper(NextM, T, MinV, RestCurSet).


% Given a vertex U, returns the set of adjacent vertices V
findAdjacentSet(U, AdjSet, Type) :-
	% Return empty set if no such adjacent vertex found
	(setof(V-D, edge(U, V, D, Type), TempAdjSet) *-> TempAdjSet = AdjSet
												   ; AdjSet = []).


% Returns true if there exist an edge between vertices U --> V
% Returns distance as weight
edge(U, V, D, 'Distance') :-
	bus(_, U, V, _, _, D, _).

% Returns time as weight
edge(U, V, D, 'Time') :-
	bus(_, U, V, T1, T2, _, _),
	% Handles next day time differences
	(T2 > T1 -> D is T2-T1
			  ; D is 24+T2-T1).

% Returns cost as weight
edge(U, V, D, 'Cost') :-
	bus(_, U, V, _, _, _, D).

% Returns all the parameters associated with the edge
edge(U, V, B, D, T, C) :-
	bus(B, U, V, T1, T2, D, C),
	(T2 > T1 -> T is T2-T1
			  ; T is 24+T2-T1).

% Base case for findUpdatableSet: If no vertex left, return empty list
findUpdatableSet([], _, []).
/* Given a set of vertices, returns the vertices which are not yet visited in
the Dijkstra algorithm */
findUpdatableSet([H|T], VisSet, NewAdjSet) :-
	H = V-_,
	% If vertex V is a member of visited set, do not add it to the new set
	% Else, add it to the new set
	(member(V-_, VisSet) -> NewAdjSet = SubNewAdjSet
						  ; NewAdjSet = [H|SubNewAdjSet]),
	% Call findUpdatableSet recursively for remaining vertices
	findUpdatableSet(T, VisSet, SubNewAdjSet).


/* Base case for waitingTime: If parent is null (current vertex is source),
return waiting time as 0 */
waitingTime('', _, _, W) :-
	W is 0.
/* Given parent, current vertex and next vertex, finds the waiting time at
current vertex */
waitingTime(Par, U, V, W) :-
	% Find arrival time at current vertex
	bus(_, Par, U, _, T1, _, _),
	% Find departure time from current vertex
	bus(_, U, V, T2, _, _, _),
	% Waiting time is difference between the departure time and arrival time
	(T2 > T1 -> W is T2-T1
			  ; W is 24+T2-T1).


% Base case for update: If no vertex left, return the remaining set of vertices
update([], CurSet, _, _, CurSet, Parent, Parent).
/* Given the adjacent set and min weight D, updates the weights of the vertices
and returns the new current set */
update([V1-D1|T], CurSet, V, D, NewCurSet, Parent, NewNewParent) :-
	/* If V1 is present in the current set, update the weight, assign the new
	parent and remove from current set. If V1 is not present in the current set
	(has infinite weight), assign new weight and new parent */
	waitingTime(Parent.get(V), V, V1, W),
	(remove(CurSet, V1-D2, RestCurSet) -> (D+D1+W < D2 ->
						VD is D+D1+W, NewParent = Parent.put(V1, V);
						VD is D2, NewParent = Parent)
				; RestCurSet = CurSet, VD is D+D1+W, NewParent = Parent.put(V1, V)),
	NewCurSet = [V1-VD|SubNewCurSet],
	% Call update recursively for remaining vertices
	update(T, RestCurSet, V, D, SubNewCurSet, NewParent, NewNewParent).


/* Base case for remove: If current vertex is same as X, return the remaining
list (in which X is not present) */ 
remove([X|T], X, T).
/* Given a list and a vertex X, removes X from the list (if present) and returns
the remaining list */
remove([H|T], X, [H|NT]) :-
	% Check if H not equal to X
	H \= X,
	% Call remove recursively for remaining vertices
	remove(T, X, NT).


% Base case for printList: Print new line when empty list
printList([]) :-
	write('\n').
% Prints list (for debugging)
printList([H|T]) :-
	H = V-D,
	write(V), write(': '), write(D), write(', '),
	% Call printList recursively for remaining list
	printList(T).


% Base case for printPath: Print Src and initialize weights as 0
printPath(Src, Src, _, 0, 0, 0) :-
	write(Src).
% Prints path and computes final dest, time and cost
printPath(Src, Dest, Parent, D, T, C) :-
	% Call printPath recursively for parent of Dest
	printPath(Src, Parent.get(Dest), Parent, D1, T1, C1),
	% Compute current weights and add it to recursive weights
	edge(Parent.get(Dest), Dest, B, D2, T2, C2),
	% Check if parent of parent of destination exists. If exists, 
	(get_dict(Parent.get(Dest), Parent, _) -> 
		waitingTime(Parent.get(Parent.get(Dest)), Parent.get(Dest), Dest, W);
		W is 0),
	D is D1+D2, T is T1+T2+W, C is C1+C2,
	% Print output
	write(','), write(B), write('->'), write(Dest).
