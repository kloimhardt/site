import basilisp.main
import importlib

basilisp.main.init()
importlib.import_module("basilisp.core")

code = """
(do
  (import [mymodule :as m])
  (defn ha [x] (+ (m/iinc x) 1))
  (ha 3))
"""

print(basilisp.core.eval_(basilisp.core.read_string(code)))
