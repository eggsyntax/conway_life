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


(defn mapcols-fn
  "helper fn for map2d. Return a function which applies f to all members of a 1-d sequence."
  [f]
  (fn [& cols] (vec (map f cols))))

; test functions for mapcols-fn
a
;(letfn [(doub [i] (* i 2))]
(defn doub [i] (* i 2))
(doub 3)
(defn doub-all [v]
  (map doub v))
(let [f (mapcols-fn doub-all)]
  (f [3] [5]))

; end test functions for mapcols-fn

(defn map2d
  "Apply a function to each cell of a vector2d, returning another vector2d."
  ; for each row:
  ;   map cells to function, return the result
  [f v2d]
  (let [g (mapcols-fn f)]
    (vec (map g v2d))
  ))


; test functions for map2d

(defn doub [i] (* i 2))
(def v2d (vector2d [4 3] rand-off-on))
v2d
(map2d doub-all v2d)

(defn index
  "Return an index sequence for a (1d) vector"
  [v]
  (range (count v)))
(index [1 2 3])

