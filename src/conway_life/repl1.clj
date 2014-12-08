;; Anything you type in here will be executed
;; immediately with the results shown on the
;; right.
(ns repl1.ns
  (:require [vector2d]))
(range 4)
(vec (range 4))
(defn row [] (vec (range 4)))
(row)
(take 3 (repeatedly row))

(defn v2d [] (vec (take 3 (repeatedly row))))
(v2d)

(map vector (repeat 1) (v2d))
(iterate inc 0)
(map vector (repeat 1) (iterate inc 0))
(map vector (take 3 (iterate inc 0)) (map vector (iterate inc 0)))

0
(rand-int 5)
(def v (vector2d/vector2d [3 2] #(rand-int 5)))
v

(def r [0 1 2 3])
r

(map-indexed vector r)

(map vector (map vector [[1 2 ] [3 4]] [[5 6] [7 8]]))

(defn enum [s]
  (map vector (range) s))

(enum v)
(iterate inc 0)

v

; What I want:
; For each cell in a v2d, return [x y val]. Can be lazy.

(defn foo
  [v2d]
  (let [h (count v2d)
        w (count (v2d 0))]
    [w h]
    ))
(foo v)


v
(row-indexed (v 0))

(conj [7] [1 2])
(def vs (map vector (repeat 7) (v 0)))
vs

v
(defn prepend
  [valu sequenc]
  (vec (concat [valu] sequenc)))
(prepend 3 [1 2])

(map (partial prepend 7) v)
(map vector (repeat 7) v)



; For every row:
;   For every col:
;     Return (col, row, val)


(defn v2d-foo
  [v2d]
  ; OR YOU ARE HERE. Is this one the top-level or is v2d-bar? Probably this one.
  (let [h (count v2d), w (count (v2d 0))]
  (println [w h])
  (println (v2d 0))
  (map vector (range w) (v2d 0))
  ))

(v2d-foo v)

(map vector (range (count (v 0))) (v 0))

(defn v2d-bar
  [v2d]
  (println v2d)
  (println (v2d 0))
  (println (v2d-foo v2d))
  )
(v2d-bar v)

(def r-i (map row-indexed v))
r-i
(count r-i)
(range (count r-i))
(map row-indexed (map row-indexed v))
(defn v2d-indexed
  [v2d]
  (println v2d)
  (map row-indexed (map row-indexed v2d))
  )
(v2d-indexed v)





v

; OK, these ones work:
(defn row-indexed
  "Given a row and j, the index of that row, return the indexed row: [[j 0 val] [j 1 val] [j 2 val]...]"
  [row j]
  (vec (map vector (repeat j) (iterate inc 0) row)))

(row-indexed (v 0) 9)

(defn v2d-indexed
  "Given a v2d, [[val val val...] [val val val...]...], return an indexed version:
  [[[0 0 val] [0 1 val] [0 2 val]...]
   [[1 0 val] [1 1 val] [1 2 val]...]
   ...]"
  [v2d]
  (loop [v v2d indexed-v2d []]
    (if (empty? v)
      indexed-v2d
      (let [row-i (- (count v2d) (count v))
            new-row (row-indexed (first v) row-i)]
        (recur
           (rest v)
           (conj indexed-v2d new-row)))
      )))

(v2d-indexed v)

(map vector (iterate inc 0) v)

7 7 7
7 7 7
->
r
(row-indexed r 3)

v
(conj [1 2] 3)


(defn index-v2d
  "Given an input v2d, [[val val val...] [val val val...]...], return an indexed version:
  [[[0 0 val] [0 1 val] [0 2 val]...]
   [[1 0 val] [1 1 val] [1 2 val]...]
   ...]"
  [v2d]
  (letfn [(index-row
            ;Given a row and j, the index of that row, return the indexed row: [[j 0 val] [j 1 val] [j 2 val]...]
            [j row]
            (vec (map vector (repeat j) (iterate inc 0) row)))]

    (map-indexed index-row v2d)))
(index-v2d v)
