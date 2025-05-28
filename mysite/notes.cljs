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
       names))

(def idx220 26)

(defn plotpoints [bnotes2 vpp]
  (plotpoints1 bnotes2 vpp)

  (drawlines bnotes2 ["E4" "G4" "B4" "D5" "F5"])
  (. bnotes2 (create "image"
                     #js["https://upload.wikimedia.org/wikipedia/commons/f/ff/GClef.svg"
                         #js[166 0.5]
                         #js[76 -15]]))

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

(defn main [divid]
  (set! (.. js/statejs -notes -bnotes2)
        (.. js/JXG
            -JSXGraph
            (initBoard divid
                       (clj->js {:boundingbox   [120 (- 48 idx220)
                                                 1080 (- 20 idx220)]
                                 :showCopyright true
                                 :axis          false
                                 :grid          false}))))

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
    (doall
      (map-indexed
        (fn [i tu]
          (.. bnotes2
              (select (:name tu))
              (moveTo #js[(:x tu) (:y tu)] 1500)))
        pp))))

(defn anim3 []
  (let [bnotes2 (.. js/statejs -notes -bnotes2)]
    (run! (fn [[l f]]
            (.create bnotes2
                     "text"
                     #js[f -1.2 (str l f " Hz")]
                     #js{:fontsize 20 :color "black"}))
          [["" 220] ["" 440] ["" 880]])))

(defn anim5 []
  (let [bnotes2 (.. js/statejs -notes -bnotes2)]
    (.create bnotes2
             "functiongraph"
             #js["(7 * log(x / 220)) / log(2)"]
             #js{:strokeColor "red" :strokeWidth 2})))

(set! (.. js/statejs -notes) #js{})
(set! (.. js/statejs -notes -main) main)
(set! (.. js/statejs -notes -anim1) anim1)
(set! (.. js/statejs -notes -anim2) anim2)
(set! (.. js/statejs -notes -anim3) anim3)
(set! (.. js/statejs -notes -anim5) anim5)

(comment
  (do
    (main "divnotes2")
    #_(anim1)
    #_(anim2)
    #_(anim3)
    (def bnotes2 (.. js/statejs -notes -bnotes2))
    )
  :end)
