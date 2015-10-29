-module(mergesort).
-compile(export_all).

% sep(L,N)
sep(L,0) -> {[], L};
sep([H|T],N) -> {L1, L2} = sep(T,N-1),
				{[H|L1], L2}.

% merge(L1,L2)
merge(L,[]) -> L;
merge([],L) -> L;
merge([H1|T1],[H2|T2]) when(H2 >= H1) -> [H1|merge(T1,[H2|T2])]; 
merge([H1|T1],[H2|T2]) -> [H2|merge([H1|T1],T2)].

ms([]) -> [];
ms([A]) -> [A];
ms(L) ->
	{Lleft, Lright} = sep(L, length(L) div 2),
	L1 = ms(Lleft), L2 = ms(Lright),
	merge(L1,L2).

rcvp(Pid) -> receive
				 {Pid, L} -> L
			 end.

pms(L) -> Pid = spawn(mergesort, p_ms, [self(), L]),
		  rcvp(Pid).

p_ms(Pid, L) when length(L) < 100 -> Pid ! {self(), ms(L)};
p_ms(Pid, L) -> {Lleft, Lright} = sep(L, length(L) div 2),
				Pid1 = spawn(mergesort, p_ms, [self(), Lleft]),
				Pid2 = spawn(mergesort, p_ms, [self(), Lright]),
				L1 = rcvp(Pid1),
				L2 = rcvp(Pid2),
				Pid ! {self(), merge(L1,L2)}.
