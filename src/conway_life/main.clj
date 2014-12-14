(ns main
  (:require [quil.core :as q]
            [quil.middleware :as m]
            [life]
            [vector2d]))

(def size {:x 500 :y 500}) ; Physical width of screen
(def num-cells [50 50])
(def cell-size [(/ (size :x) (num-cells 0)) (/ (size :y) (num-cells 1))])

(defn setup []
  ; Set frame rate to 30 frames per second.
  (q/frame-rate 10)
  ; Set color mode to HSB (HSV) instead of default RGB.
  (q/color-mode :hsb)
  (q/fill 80)
  ; setup function returns initial randomized state.
  (life/setup num-cells cell-size)
  )

(defn update [state]
  ; Update sketch state by changing circle color and position.
  (life/tick state)
)

(defn draw-cell
  [x y value]
  (let [x-mult (cell-size 0)
        y-mult (cell-size 1)
        x-pos (* x x-mult)
        y-pos (* y y-mult)]
    (if (pos? value)
        (q/rect x-pos y-pos x-mult y-mult))))

(defn draw-cells
  [v2d]
  (vec
    (for [row (vector2d/index v2d)
          col (vector2d/index (v2d 0))]
      (draw-cell row col ((v2d row) col)))))


(defn draw [state]
  (println "Drawing.")
  ; Clear the sketch by filling it with light-grey color.
  (q/background 240)
  ;
  ;(q/fill (:color state) 255 255)
  (draw-cells (state :cells-state))


)



(q/defsketch main
  :title "Functional parallel life"
  :size [(size :x) (size :y)]
  ; setup function called only once, during sketch initialization.
  :setup setup
  ; update is called on each iteration before draw is called.
  ; It updates sketch state.
  :update update
  :draw draw
  ; This sketch uses functional-mode middleware.
  ; Check quil wiki for more info about middlewares and particularly
  ; fun-mode.
  :middleware [m/fun-mode])
