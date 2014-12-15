(ns life
  "Logic specific to Conway's Life"
  (:require [vector2d]))

;; Approach to concurrency: agents
;;
;; Maintain a list of future states. The draw function just pops the
;; head from the list.
;; Update method takes the tail of the list of states and sends each row
;; to a different agent. The agents' outputs are a row of next-state
;; values, which are collected (?) together into a full v2d next-state,
;; which is added to the tail of the list.
;; If the list has, say, > 20 values, the update method just sleeps
;; briefly instead.
;; Requires special error-handling (pg 107, _Practical Clojure_).
;;
;; 'await' and 'await-for' let you block until a thread returns. Needed? Dunno.
;;
;; 'shutdown-agents' at end

(defn rand-off-on
  []
  (rand-int 2))

(defn setup
  [num-cells]
  "Returns a map of symbol definitions"
  {:second 0
   :num-cells num-cells
   :cells-state (vector2d/vector2d num-cells rand-off-on)
   }
  )

;test fns
(def v (vector2d/vector2d [4 4] rand-off-on)) ;TEMP for testing
v
(def row [0 1 2 3])
row

; TODO might think about how to make this easily parallelizable
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

(defn dead? [cell]
  (zero? cell))

(defn num-live-neighbors
  "Returns the # of live neighbors, *including* the cell itself"
  [v2d]
  ; Return a copy of the v2d with all positive numbers decreased to 1
  (letfn [(pos-to-1 [n] (if (pos? n) 1 0))]
    (reduce + (map pos-to-1 (flatten v2d)))))

; test
(neighborhood v 2 2)
(num-live-neighbors (neighborhood v 2 2))

(defn bool-to-int [v]
  (if (= true v)
    1
    0))
(bool-to-int true)

(defn next-cell-state
  "Returns the next state for a particular cell"
  [v2d x y]
  (let [cur-live-neighbors (num-live-neighbors (neighborhood v2d x y))]
    (if (dead? ((v2d y) x))
      (bool-to-int (= 3 cur-live-neighbors))
      ;else
      (bool-to-int (<= 3 cur-live-neighbors 4)) ; Note: increased by 1 because we include the current cell in the count
       )))
v
(next-cell-state v 1 1)

(defn next-row-state
  [v2d y]
  (let [row (v2d y)]
    (vec
     (for [x (vector2d/index row)]
       (next-cell-state v2d x y)))))
v
(next-row-state v 1)

(defn next-state
  "Return the next state for all cells in a vector2d"
  [v2d]
  (vec
    (for [y (vector2d/index v2d)]
      (next-row-state v2d y))))
v
(next-state v)

; TODO now create a version of next-state (and maybe next-row-state) which uses
; v2d-set to set each cell. More easily parallelizable.

(defn tick
  [state]
  (assoc state
    :old-cells (state :cells-state)
    :second (inc (state :second))
    :cells-state (next-state (state :cells-state))))

