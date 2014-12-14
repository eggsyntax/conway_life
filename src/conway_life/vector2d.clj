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
(vector2d [3 2] #(int 0))

(defn rand-off-on []
  "Return 0 or 1 at random."
  (rand-int 2))
(rand-off-on)

(def v (vector2d [4 3] rand-off-on))
v
((v 1) 0)


(defn map-row
  "helper fn for map2d. Return a function which applies f to all members of a 1-d sequence."
  [f]
  (fn [& cols] (vec (map f cols))))

; test functions for map-row
v
(defn doub [i] (* i 2))
(doub 3)
(defn doub-all [v]
  (map doub v))
(let [f (map-row doub-all)]
  (f (v 0)))
; end test functions for map-row

(defn map2d
  "Apply a function to each cell of a vector2d, returning another vector2d."
  [f v2d]
  (let [g (map-row f)]
    (vec (map g v2d))
  ))

; test functions for map2d
(defn doub [i] (* i 2))
v
(map2d doub-all v)

(defn index
  "Return an index sequence for a (1d) vector"
  [v]
  (range (count v)))
(index [1 2 3])

