# site
## Quarto Website
The site was generated once using `quarto create project website mysite`

```
~/klmtemp/site$ source python_venv/bin/activate
```

```
(python_venv) ~/klmtemp/site/mysite$ quarto preview
```

```
(python_venv) ~/klmtemp/site/mysite$ quarto render
```

```
(python_venv) ~/klmtemp/site/mysite$ quarto publish gh-pages
```
https://kloimhardt.github.io/site/

## Basilisp

```
(python_venv) ~/klmtemp/site$ python3 mybasilisp.py
```

```
 basilisp nrepl-server
```

## Scittle

```
bb -cp . -e "(require '[browser-server :as nrepl]) (nrepl/start! {:nrepl-port 1339 :websocket-port 1340}) (deref (promise))"
```
This depends on `browser-server.clj` being in the current directory.

Also possible with Java:
```
clj -Sdeps "{:deps {io.github.babashka/sci.nrepl {:mvn/version \"0.0.2\"} org.babashka/http-server {:mvn/version \"0.1.13\"}}}" -M -e "(require '[sci.nrepl.browser-server :as nrepl]) (nrepl/start! {:nrepl-port 1339 :websocket-port 1340})"
```

Then start an http-server (e.g. `python3 cors_server.py` which open server port 8003).

In the browser open: `http://localhost:8003/scittlenrepl.html`

In Cider, choose cider-connect-cljs, select port 1339, followed by the nbb REPL type.
