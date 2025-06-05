(ns notes)

(defmacro deepdef [v e] `(def ~v ~e))

(defn drawstiel1 [bnotes2 shift len]
  (fn [name]
    (let [bp (.select bnotes2 name)]
      (. bnotes2
         (create "line"
                 #js[#js[(fn [] (+ (.X bp) shift)) (fn [] (.Y bp))]
                     #js[(fn [] (+ (.X bp) shift)) (fn [] (+ (.Y bp) len))]]
                 #js{:name          (str "stlm" name)
                     :straightFirst false
                     :straightLast  false
                     :strokeWidth   4
                     :strokeColor   "black"})))))


(defn plotpoints1 [bnotes2 vpp]
  (run! (fn [d]
          (. bnotes2 (create "point"
                             #js[(:sx d) (:y d)]
                             #js{:name  (:name d)
                                 :color "black"})))
        vpp))

(defn drawlines [bnotes2 names]
  (run! (fn [name]
          (let [p (.select bnotes2 name)]
            (. bnotes2 (create "line"
                               #js[#js[150 (.Y p)] #js[1050 (.Y p)]]
                               #js{:straightFirst false
                                   :straightLast  false
                                   :strokeWidth   1
                                   :strokeColor   "black"}))))
        names)
  (.. bnotes2 (select "G3") (setLabel "")))

(def idx220 26)

(defn drawclef [brd]
  (drawlines brd ["E4" "G4" "B4" "D5" "F5"])
  (. brd (create "image"
                     #js["https://upload.wikimedia.org/wikipedia/commons/f/ff/GClef.svg"
                         #js[166 0.5]
                         #js[76 -15]])))

(defn plotpoints [bnotes2 vpp]
  (plotpoints1 bnotes2 vpp)
  (drawclef bnotes2)

  (run! (fn [name]
          (.. bnotes2 (select name) (setAttribute #js{:size 9})))
        ["D4" "E4" "F4" "G4" "A4" "B4" "C5" "D5" "E5" "F5" "G5"])

  (run! (drawstiel1 bnotes2 9 7)
        ["D4" "E4" "F4" "G4" "A4"])

  (run! (drawstiel1 bnotes2 -9 -7)
        ["B4" "C5" "D5" "E5" "F5" "G5"]))

(defn straight1 [y d] (* (- y d) (/ 440 7)))

(defn vpp [frequencies]
  (into []
        (map-indexed (fn [i [name x]]
                       (let [y (- i idx220)]
                         {:name name
                          :x    x :sx (straight1 y -2.7)
                          :y    y}))
                     frequencies)))

(defn brdspec [bb &[axis xname yname]]
  {:boundingbox   bb
   :showNavigation false
   :showCopyright true
   :axis          (or axis false)
   :grid          false
   :defaultAxes
   {:x {:name      (or xname "")
        :withLabel true
        :label     {:position "rt"
                    :offset   [-5 15]
                    :anchorX  "right"}
        :ticks     {:visible     true
                    :majorHeight 5}}
    :y {:withLabel true
        :name      (or yname "")
        :label     {:position "rt"
                    :offset   [5 -5]
                    :anchorY  "top"}

        :ticks {:visible     true
                :majorHeight 5} }}}
  )

(defn main [divid]
  (set! (.. js/statejs -notes -bnotes2)
        (.. js/JXG
            -JSXGraph
            (initBoard divid
                       (clj->js (brdspec [120 (- 48 idx220)
                                          1080 (- 20 idx220)])))))

  (plotpoints (.. js/statejs -notes -bnotes2)
              (vpp (js->clj (.-frequencies js/statejs)))))

((js->clj (.-frequencies js/statejs)) 26)
((vpp (js->clj (.-frequencies js/statejs))) 26)

(defn octave [y o]
  (let [bnotes2 (.. js/statejs -notes -bnotes2)
        pp      (vpp (js->clj (.-frequencies js/statejs)))]
    (. bnotes2
       (create "line"
               (let [p1 (.select bnotes2 (:name (pp y)))
                     p2 (.select bnotes2 (:name (pp (+ y o))))]
                 #js[p1
                     #js[(fn [] (.X p2)) (fn [] (.Y p1))]])
               #js{:name          (str "oct" (:name (pp y)))
                   :straightFirst false
                   :straightLast  false
                   :strokeWidth   2
                   :strokeColor   "red"}))))

(defn anim1 []
  (octave 26 7)
  (octave 40 -7))

(defn anim2 []
  (let [bnotes2 (.. js/statejs -notes -bnotes2)
        pp      (vpp (js->clj (.-frequencies js/statejs)))]
    (run!
      (fn [tu]
        (.. bnotes2
            (select (:name tu))
            (moveTo #js[(:x tu) (:y tu)] 1500)))
      pp)))

(defn anim3 []
  #_(anim3)
  (let [bnotes2 (.. js/statejs -notes -bnotes2)]
    (run! (fn [[l f]]
            (.create bnotes2
                     "text"
                     #js[f -2 (str l f " Hz")]
                     #js{:fontsize 20 :color "black"}))
          [["" 220] ["" 440] ["" 880]])))

(defn anim5 []
  (let [bnotes2 (.. js/statejs -notes -bnotes2)]
    (.create bnotes2
             "functiongraph"
             #js["(7 * log(x / 220)) / log(2)"]
             #js{:strokeColor "red" :strokeWidth 2})))

(defn anim6 []
  (set! (.. js/statejs -notes -bnotes2)
        (.. js/JXG
            -JSXGraph
            (initBoard "divnotes2"
                       (clj->js (brdspec [-320 33
                                          1480 -18]
                                         true
                                         "Frequency"
                                         "log2")))))

  (run! (fn [d]
          (.. js/statejs -notes -bnotes2
              (create "point"
                      #js[(:x d) (:y d)]
                      #js{:name  (:name d)
                          :color "black"
                          })
              (setLabel "")))
        (vpp (js->clj (.-frequencies js/statejs))))

  (doto (.. js/statejs -notes -bnotes2)
    (drawclef )
    (.create
      "functiongraph"
      #js["(7 * log(x / 220)) / log(2)"]
      #js{:strokeColor "red" :strokeWidth 2})
    (.create
      "text"
      #js[1100 22 "Logarithm"]
      #js{:fontSize 24}))
  (anim1))


(set! (.. js/statejs -notes) #js{})
(set! (.. js/statejs -notes -main) main)
(set! (.. js/statejs -notes -anim1) anim1)
(set! (.. js/statejs -notes -anim2) anim2)
(set! (.. js/statejs -notes -anim3) anim3)
(set! (.. js/statejs -notes -anim5) anim5)
(set! (.. js/statejs -notes -anim6) anim6)

(comment
  (do
    (main "divnotes2")
    (def bnotes2 (.. js/statejs -notes -bnotes2))
    )
  (anim1)
  (anim2)
  (anim3)
  (anim5)
  (anim6)

  :end)
