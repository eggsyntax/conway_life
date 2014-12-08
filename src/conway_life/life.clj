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

;test fns
(def v (vector2d/vector2d [4 4] rand-off-on)) ;TEMP for testing
v
(def row [0 1 2 3])
row

(defn neighborhood
  "Return a 3x3 subvector of v2d, centered on (x, y)"
  [v2d x y]
  ; neighborhood-1d just returns a slice, with a bit of complication to handle wrapping.
  (let [neighborhood-1d (fn [v center] [(v (mod (dec center) (count v)))
                                        (v center)
                                        (v (mod (inc center) (count v)))])
        ; rows returns a subvector containing 3 rows of v2d
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

(if (= 1 1) 1 0)

(defn num-live-neighbors
  "Returns the # of live neighbors, *including* the cell itself"
  [v2d]
  ; Return a copy of the v2d with all positive numbers decreased to 1
  (letfn [(pos-to-1 [n] (if (pos? n) 1 0))]
    (reduce + (map pos-to-1 (flatten v2d)))))

; test
(neighborhood v 2 2)
(num-live-neighbors (neighborhood v 2 2))

(defn alive-next-tick?
  [v2d x y]
  (let [cur-live-neighbors (num-live-neighbors (neighborhood v2d x y))]
    (if (dead? ((v2d y) x))
      (= 3 cur-live-neighbors)
      ;else
      (<= 4 cur-live-neighbors 5) ; Note: increased by 1 because we include the current cell in the count
       )))

v
(alive-next-tick? v 1 0)

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
