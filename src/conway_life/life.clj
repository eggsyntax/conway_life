(ns life
  (:require [vector2d]))

(defn rand-off-on
  []
  (rand-int 2))

(defn setup
  [num-cells cell-size] ;TD cell-size: necessary?
  "Returns a map of symbol definitions"
  {:second 0
   :num-cells num-cells
   :state (vector2d/index-v2d (vector2d/vector2d num-cells rand-off-on) :mult (cell-size 0)) ;TODO - maybe refactor index-v2d to handle different x & y sizes
   }
  )

(def v (vector2d/vector2d [4 4] #(rand-int 5))) ;TEMP for testing



(defn neighborhood
  [v2d x y]
  (let [neighborhood-1d (fn [v center] (subvec v (dec center) (+ center 2)))
        rows (neighborhood-1d v2d y)]
    (map neighborhood-1d rows (repeat x))
  ))

; test fns for neighborhood
v
(neighborhood v 0 3)
; end test fns for neighborhood

(defn dead?
  [cell]
  (zero? cell))

(defn tick
  [state]
  (let [old-cells (state :state)
        new-second (inc (state :second))]
    )

  ;(print state "  ")
  ;(println "Tick")
  state
  )


; probably delete
(defn n-random
  [n]
  (take n (repeatedly (fn [] (rand-int 2)))))
