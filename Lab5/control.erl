-module(control).
-export([go/2, start_worker/1, start_worker/2]).

% Generate a list of random numbers
gen(0, _) -> [];
gen(I, N) -> [random:uniform(N) | gen(I-1, N)].

% Worker loop
worker(Next) ->
	receive
		% Receive stop signal
		stop ->
			Next ! stop;
		% Edge case, nothing more to eat
		{_, 0} -> worker(Next); % Do nothing
		{Ctrl, N} ->
			% Go to eat
			Ctrl ! {self(), eat},
			% Send token
			Next ! {Ctrl, N-1},
			% Loop
			worker(Next)
	end.

% Note:
% -----
% We could also send a literal 'token'
%    Next ! {Ctrl, token}
% The problem with this approach is that it produces a dangling message.
% The last worker will send an 'eat' message to control.
% The next time we invocate the program this message will arrive!
%
% Another approach would be to wait for confirmation from the Control,
% before sending the token to the next worker.

% First worker
start_worker(1) ->
	worker(self());
start_worker(N) ->
	Next = spawn(?MODULE, start_worker, [N-1,self()]),
	worker(Next).

% Pass along the first worker.
% The last one links with the first one.
start_worker(1, First) ->
	worker(First);
start_worker(N, First) ->
	Next = spawn(?MODULE, start_worker, [N-1,First]),
	worker(Next).

% Manage list:
% Last element
manage([H|[]], Res) ->
	receive {Pid, eat} ->
		Pid ! stop,
		[{Pid,H}|Res]
	end;
% General case
manage([H|T], Res) ->
	receive
		{Pid, eat} ->
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
	% Send first token
	First ! {self(), M},
	% Start control
	manage(L, []).

