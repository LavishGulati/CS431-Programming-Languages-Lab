/* Bus(Number, Origin, Destination Place, Departure Time, Arrival Time,
Distance, Cost) */
bus(1, 'Amingaon', 'Jalukbari', 14.5, 15, 1, 10).
bus(2, 'Amingaon', 'Chandmari', 16, 16.5, 7, 8).
bus(3, 'Jalukbari', 'Panbazar', 16, 16.5, 1, 8).
bus(4, 'Panbazar', 'Chandmari', 16, 16.5, 2, 8).
bus(5, 'Panbazar', 'Paltanbazar', 16, 16.5, 7, 8).
bus(6, 'Chandmari', 'Maligaon', 16, 16.5, 7, 8).
bus(7, 'Maligaon', 'Lokhra', 16, 16.5, 7, 8).


% Find minimum distance, time and cost between source and destination
route(Src, Dest) :-
	findMinWeight(Src, Dest, 'Distance'),
	findMinWeight(Src, Dest, 'Time'),
	findMinWeight(Src, Dest, 'Cost').


% Compute minimum weight between source and destination given type of weights
findMinWeight(Src, Dest, Type) :-
	dijkstra([Src-0], [], Type, MinDist),
	write(Type), write(': '), printList(MinDist).


% Base case for dijkstra: If no vertex left, return the final weights
dijkstra([], VisSet, _, VisSet).
% Dijkstra algorithm to compute single source shortest paths
dijkstra(CurSet, VisSet, Type, MinDist) :-
	% Compute the vertex with minimum weight
	% RestCurSet is the current set minus the min vertex
	minVertex(CurSet, V-D, RestCurSet),
	% Compute the set of adjacent vertices to min vertex V
	findAdjacentSet(V, AdjSet, Type),
	% Compute the set of vertices which are updatable (i.e. not visited yet)
	findUpdatableSet(AdjSet, VisSet, NewAdjSet),
	% Updates the weights of adjacent vertices
	% NewCurSet is the new current set with updated weights
	update(NewAdjSet, RestCurSet, D, NewCurSet),
	% Call dijkstra recursively for remaining vertices 
	dijkstra(NewCurSet, [V-D|VisSet], Type, MinDist).


% Given the current set of vertices, returns the vertex with minimum weight
minVertex([H|T], MinV, RestCurSet) :-
	minVertexHelper(H, T, MinV, RestCurSet).


/* Base case for minVertexHelper: If no vertex left, assign current vertex as
the min vertex */
minVertexHelper(Cur, [], Cur, []).
% Helper function for minVertex
minVertexHelper(Cur, [H|T], MinV, [H2|RestCurSet]) :-
	H = V1-D1, Cur = V-D,
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


% Base case for findUpdatableSet: If no vertex left, return empty list
findUpdatableSet([], _, []).
/* Given a set of vertices, returns the vertices which are not yet visited in
the Dijkstra algorithm */
findUpdatableSet([H|T], VisSet, NewAdjSet) :-
	H = V-D,
	% If vertex V is a member of visited set, do not add it to the new set
	% Else, add it to the new set
	(member(V-_, VisSet) -> NewAdjSet = SubNewAdjSet
						  ; NewAdjSet = [H|SubNewAdjSet]),
	% Call findUpdatableSet recursively for remaining vertices
	findUpdatableSet(T, VisSet, SubNewAdjSet).


% Base case for update: If no vertex left, return the remaining set of vertices
update([], CurSet, _, CurSet).
/* Given the adjacent set and min weight D, updates the weights of the vertices
and returns the new current set */
update([V1-D1|T], CurSet, D, NewCurSet) :-
	/* If V1 is present in the current set, update the weight accordingly and
	remove from current set. If V1 is not present in the current set (has
	infinite weight), assign new weight */
	(remove(CurSet, V1-D2, RestCurSet) -> VD is min(D2, D+D1)
										; RestCurSet = CurSet, VD is D+D1),
	NewCurSet = [V1-VD|SubNewCurSet],
	% Call update recursively for remaining vertices
	update(T, RestCurSet, D, SubNewCurSet).


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