(ns repl2.ns
  (:require [vector2d]))

(range -1 2)

(def v (vector2d/vector2d [3 2] #(rand-int 5)))
v

(defn foo [v]
  (if (= v 0)
    "Zip"
    ;else
    "Dah"))
(foo 0)
(foo :hi)
