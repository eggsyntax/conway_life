(def state (into [] (range 8)))
(first state)

(defn update-state
  ([oldstate]
    (update-state oldstate 0))
  ([oldstate i]
    (do
      (println i)
      (if (= i (count oldstate))
        (do
        (println "end")
        (identity oldstate))
        ; else
        (update-state
         (assoc oldstate i (* i 2))
         (+ i 1)))))
)

(update-state state)

