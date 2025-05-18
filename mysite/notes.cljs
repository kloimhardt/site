(ns notes)

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

(defmacro deepdef [v e] `(def ~v ~e))

(comment
  (def mpp (into {} (map (juxt :name identity)) vpp))

  (get mpp "A4")
  (sp 30)
  (nth vpp 33)
  (nth ssp 33)
  :end)

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



(defn plotpoints [bnotes2 vpp]
  (plotpoints1 bnotes2 vpp)

  (drawlines bnotes2 ["E4" "G4" "B4" "D5" "F5"])

  (. bnotes2 (create "image"
                     #js["https://upload.wikimedia.org/wikipedia/commons/f/ff/GClef.svg"
                         #js[170 26.4]
                         #js[80 15.5]]))

  (run! (fn [name]
          (.. bnotes2 (select name) (setAttribute #js{:size 9})))
        ["D4" "E4" "F4" "G4" "A4" "B4" "C5" "D5" "E5" "F5" "G5"])

  (run! (drawstiel1 bnotes2 9 7)
        ["D4" "E4" "F4" "G4" "A4"])

  (run! (drawstiel1 bnotes2 -9 -7)
        ["B4" "C5" "D5" "E5" "F5" "G5"]))


(defn straight1 [y d] (* (- y d) (/ 440 7)))

(defn vpp [frequencies]
  (map-indexed (fn [i [name x]]
                 (let [zero 0
                       y    (- i zero)]
                   {:name name :x x :sx (straight1 y 23.5) :y y }))
               frequencies))

(defn main [divid]
  (set! (.. js/statejs -notes -bnotes2)
        (.. js/JXG
            -JSXGraph
            (initBoard divid
                       (clj->js {:boundingbox   [120 48 1080 20]
                                 :showCopyright false
                                 :axis          false
                                 :grid          false}))))

  (plotpoints (.. js/statejs -notes -bnotes2)
              (vpp (js->clj (.-frequencies js/statejs)))))


(def pp (js->clj (.-frequencies js/statejs)))

(defn anim1 []
  (let [bnotes2 (.. js/statejs -notes -bnotes2)
        octave  (fn [y o]
                  (. bnotes2
                     (create "line"
                             (let [p1 (.select bnotes2 (first (pp y)))
                                   p2 (.select bnotes2 (first (pp (+ y o))))]
                               #js[p1
                                   #js[(fn [] (.X p2)) (fn [] (.Y p1))]])
                             #js{:name          (str "oct" (first (pp y)))
                                 :straightFirst false
                                 :straightLast  false
                                 :strokeWidth   4
                                 :strokeColor   "red"})))]
    (octave 26 7)
    (octave 40 -7)))


(defn anim2 []
  (let [bnotes2 (.. js/statejs -notes -bnotes2)]
    (doall
      (map-indexed
        (fn [i tu]
          (.. bnotes2
              (select (first tu))
              (moveTo #js[(second tu) i] 1500)))
        pp))))


(defn anim3 []
  (let [bnotes2 (.. js/statejs -notes -bnotes2)]
    (. bnotes2
       (create "functiongraph"
               #js["(7 * (log(x) - log(16.35)))/log(2)"]
               #js{:strokeColor "red" :strokeWidth 2}))))

(set! (.. js/statejs -notes) #js{})
(set! (.. js/statejs -notes -main) main)
(set! (.. js/statejs -notes -anim1) anim1)
(set! (.. js/statejs -notes -anim2) anim2)
(set! (.. js/statejs -notes -anim3) anim3)

(comment
  (do
    (main "divnotes2")
    #_(anim1)
    #_(anim2)
    #_(anim3)

    )

  :end)



