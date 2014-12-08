(ns main
  (:require [quil.core :as q]
            [quil.middleware :as m]
            [life]
            [vector2d]))

(def size {:x 500 :y 400}) ; Physical width of screen
(def num-cells [50 40])
(def cell-size [(/ (size :x) (num-cells 0)) (/ (size :y) (num-cells 1))])

(defn setup []
  ; Set frame rate to 30 frames per second.
  (q/frame-rate 1)
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
  [args]
  (let [[x y val] args]
    (if (pos? val)
        (q/rect x y (cell-size 0) (cell-size 1)))))

(defn draw-cells
  [v2d-indexed]
  (vector2d/map2d draw-cell v2d-indexed)
  )

(defn draw [state]
  ; Clear the sketch by filling it with light-grey color.
  (q/background 240)
  ;
  ;(q/fill (:color state) 255 255)
  (draw-cells (:state state))


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
