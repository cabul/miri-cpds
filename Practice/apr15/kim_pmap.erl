-module(kim_pmap).
-compile(export_all).

map(F, L) -> [F(X) || X <- L].

pmap(F, L) ->
	Parent = self(),
	Pids = [ spawn(fun() -> Parent ! {self(),F(X)} end) || X <- L ],
	[ receive {Pid,Res} -> Res end || Pid <- Pids ].
