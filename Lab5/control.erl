-module(control).
-export([go/2, start_worker/1, start_worker/2]).

% Generate a list of random numbers
gen(0, _) -> [];
gen(I, N) -> [random:uniform(N) | gen(I-1, N)].

% Worker loop
worker(Next) ->
	receive
		stop ->
			Next ! stop;
		{Ctrl, token} ->
			Ctrl ! {self(), eat},
			receive
				% Wait for confirmation
				% Pass token
				ok -> Next ! {Ctrl, token},
					  worker(Next);
				stop -> Next ! stop
			end
	end.

% First worker
start_worker(1) ->
	worker(self());
start_worker(N) ->
	Next = spawn(?MODULE, start_worker, [N-1,self()]),
	worker(Next).

% Pass along the first worker
start_worker(1, First) ->
	worker(First);
start_worker(N, First) ->
	Next = spawn(?MODULE, start_worker, [N-1,First]),
	worker(Next).

% Manage list
manage([H|[]], Res) ->
	receive {Pid, eat} ->
		Pid ! stop,
		[{Pid,H}|Res]
	end;
manage([H|T], Res) ->
	receive
		{Pid, eat} ->
			Pid ! ok,
			manage(T,[{Pid,H}|Res])
	end.

% Edge cases
go(0,_) -> [];
go(_,0) -> [];
% General case
go(N,M) ->
	L = gen(M,M),
	io:format("~p~n", [L]),
	First = spawn(?MODULE,  start_worker, [N]),
	First ! {self(), token},
	manage(L, []).

