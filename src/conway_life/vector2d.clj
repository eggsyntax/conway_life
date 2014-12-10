(ns vector2d)

; a 2d vector is a vector of vectors

; vector2d: pass x and y

(defn vector2d
  "Create a vector of vectors representing a 2d grid. Pass [xsize ysize] and a
  function which generates the contents of each cell."
  [[xsize ysize] cell-function]

  (vec (take ysize
    (repeatedly
      #(vec (take xsize
        (repeatedly cell-function)))))))

; some test functions for vector2d

(defn rand-off-on
  "Return 0 or 1 at random."
  []
  (rand-int 2))

(rand-off-on)

(def a (vector2d [4 3] rand-off-on))

a

((a 1) 0)

;;;;;

(defn mapcols-fn
  "helper fn for map2d. Return a function which applies f to all members of a 1-d sequence."
  [f]
  (fn [cols] (vec (map f cols))))

(defn map2d
  "Apply a function to each cell of a vector2d, returning another vector2d."
  ; for each row:
  ;   map cells to function, return the result
  [f v2d]
  (vec (map (mapcols-fn f) v2d))
  )


; test functions for map2d

(defn doub [i] (* i 2))
(def v2d (vector2d [4 3] rand-off-on))
v2d
(map2d doub v2d)

;;;;

(defn index-v2d
  "Given an input v2d, [[val val val...] [val val val...]...], return an indexed version:
  [[[0 0 val] [0 1 val] [0 2 val]...]
   [[1 0 val] [1 1 val] [1 2 val]...]
   ...]
  Optionally, pass a multiplier which wil be applied to all index values."
  [v2d & {:keys [mult] :or {mult 1}}]
  (letfn [(index-row
            ;Given a row and j, the index of that row, return the indexed row: [[j 0 val] [j 1 val] [j 2 val]...]
            [j row]
            (vec (map vector (iterate (partial + mult) 0) (repeat (* j mult)) row)))]

    (vec (map-indexed index-row v2d))))

; test
(let [indexed-v2d (index-v2d v2d :mult 10)]
  indexed-v2d
  ((indexed-v2d 2) 1)

  )
; /test

(defn make-index
  [v2d]
  (let [y (count v2d)
        x (count (v2d 0))]
    (loop [i y
           i-v2d []]
      (if (zero? i)
        i-v2d
        (recur (dec i) (conj i-v2d (vec (map vector (repeat (- y i)) (range x)))))

    ))))

; test
(def v (vector2d/vector2d [4 4] rand-off-on))
(make-index v)
; /test
