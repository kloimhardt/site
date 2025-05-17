(ns notes)
(set! (.. js/statejs -notes) #js{})
(def pp (js->clj (.-frequencies js/statejs)))

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

(def vpp
  (map-indexed (fn [i [name x]]
                 {:name name :x x :y i}) pp))

(def mpp (into {} (map (juxt :name identity)) vpp))


(defn setAtt [bnotes2 vsp jsatt]
  (fn [idx]
    (.. bnotes2 (select (first (vsp idx))) (setAttribute jsatt))))

(defn plotpoints [bnotes2]
  (let [straight (fn [y] (* (- y 23.5) (/ 440 7)))
        sp       (into [] (map-indexed
                            (fn [i tu] [(first tu) (straight i)]) pp))
        violines [30 32 34 36 38]
        viogaps  [29 31 33 35 37 39]]
    (doall
      (map-indexed
        (fn [i, d]
          (. bnotes2 (create "point"
                             #js[(second d) i]
                             #js{:name (first d) :color "black"})))
        sp))

    (doall
      (map (fn [y]
             (. bnotes2 (create "line"
                                #js[#js[150 y] #js[1050 y]]
                                #js{:straightFirst false
                                    :straightLast  false
                                    :strokeWidth   1
                                    :strokeColor   "black"})))
           violines))

    (. bnotes2 (create "image"
                       #js["https://upload.wikimedia.org/wikipedia/commons/f/ff/GClef.svg"
                           #js[170 26.4]
                           #js[80 15.5]]))

    (run! (setAtt bnotes2 sp #js{:size 9}) (concat violines viogaps))

    (def stielobennames ["D4" "E4" "F4" "G4" "A4"])
    (run! (drawstiel1 bnotes2 9 7) stielobennames)
    (def stieluntennames ["B4" "C5" "D5" "E5" "F5" "G5"])
    (run! (drawstiel1 bnotes2 -9 -7) stieluntennames)

    ))

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

(set! (.. js/statejs -notes -anim1) anim1)

(defn anim2 []
  (let [bnotes2 (.. js/statejs -notes -bnotes2)]
    (doall
      (map-indexed
        (fn [i tu]
          (.. bnotes2
              (select (first tu))
              (moveTo #js[(second tu) i] 1500)))
        pp))))

(set! (.. js/statejs -notes -anim2) anim2)

(defn anim3 []
  (let [bnotes2 (.. js/statejs -notes -bnotes2)]
    (. bnotes2
       (create "functiongraph"
               #js["(7 * (log(x) - log(16.35)))/log(2)"]
               #js{:strokeColor "red" :strokeWidth 2}))))

(set! (.. js/statejs -notes -anim3) anim3)

(defn main [divid]
  (let [bnotes2
        (.. js/JXG
            -JSXGraph
            (initBoard divid
                       (clj->js {:boundingbox   [120 48 1080 20]
                                 :showCopyright false
                                 :axis          false
                                 :grid          false})))]

    (set! (.. js/statejs -notes -bnotes2) bnotes2)
    (plotpoints bnotes2)))

(set! (.. js/statejs -notes -main) main)

(comment
  (do
    (main "divnotes2")
    #_(anim1)
    #_(anim2)
    #_(anim3)

    (def bnotes2 (.. js/statejs -notes -bnotes2))

    )

  :end)



