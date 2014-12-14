(ns main
  "Handle Processing-specific stuff and graphics logic"
  (:require [quil.core :as q]
            [quil.middleware :as m]
            [life]
            [vector2d]))

(def screen-size {:x 500 :y 500}) ; Physical width of screen
(def num-cells [60 60])
(def cell-size [(/ (screen-size :x) (num-cells 0)) (/ (screen-size :y) (num-cells 1))])

(defn setup []
  ; frame rate for sketch
  (q/frame-rate 10)
  ; Set color mode to HSB (HSV) instead of default RGB.
  (q/color-mode :hsb)
  (q/fill 80)
  (q/stroke 80)
  ; setup function returns initial randomized state.
  (life/setup num-cells)
  )

(defn update [state]
  ; Update sketch state by changing circle color and position.
  (life/tick state)
)

(defn draw-cell
  [x y value]
  (let [x-size (cell-size 0)
        y-size (cell-size 1)
        x-pos (* x x-size)
        y-pos (* y y-size)]
    (if (pos? value)
        (q/rect x-pos y-pos x-size y-size))))

(defn draw-cells
  [v2d]
  (vec
    (for [row (vector2d/index v2d)
          col (vector2d/index (v2d 0))]
      (draw-cell row col ((v2d row) col)))))

(defn draw [state]
  ;(println "Drawing tick" (state :second) ".")
  ; Clear the sketch by filling it with light-grey color.
  (q/background 240)
  (draw-cells (state :cells-state))
)



(q/defsketch main
  :title "Functional parallel life"
  :size [(screen-size :x) (screen-size :y)]
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
