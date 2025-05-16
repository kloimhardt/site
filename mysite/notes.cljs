(ns notes)
(set! (.. js/statejs -notes) #js{})
(def pp (js->clj (.-frequencies js/statejs)))

(defn drawstiel [bnotes2 vsp shift len]
  (fn [i]
    (. bnotes2
       (create "line"
               (clj->js [[(+ (second (vsp i)) shift) i]
                         [(+ (second (vsp i)) shift) (+ i len)]])
               #js{:name          (str "stl" (first (vsp i)))
                   :straightFirst false
                   :straightLast  false
                   :strokeWidth   4
                   :strokeColor   "black"}))))

(def stielunten [34 35 36 37 38 39])
(def stieloben [33 32 31 30 29])

(defn setAtt [bnotes2 vsp jsatt]
  (fn [idx]
    (.. bnotes2 (select (first (vsp idx))) (setAttribute jsatt))))

(defn plotpoints [bnotes2]
  (let [straight (fn [y] (* (- y 23.5) (/ 440 7)))
        sp       (into [] (map-indexed
                            (fn [i tu] [(first tu) (straight i)]) pp))
        violines [30 32 34 36 38]
        viogaps  [28 29 31 33 35 37 39]]
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
                                #js[#js[150 y] #js[920 y]]
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

    (run! (drawstiel bnotes2 sp -9 -7) stielunten)
    (run! (drawstiel bnotes2 sp 9 7) stieloben)))

(defn anim1 []
  (let [bnotes2 (.. js/statejs -notes -bnotes2)
        rm      (fn [d]
                  (run! (fn [i]
                          (. bnotes2
                             (removeObject (str "stl" (first (pp i))))))
                        d))]
    (rm stielunten)
    (rm stieloben)

    (doall
      (map-indexed
        (fn [i tu]
          (.. bnotes2
              (select (first tu))
              (moveTo #js[(second tu) i] 1500)))
        pp))))

(set! (.. js/statejs -notes -anim1) anim1)

(defn anim2 []
  (let [bnotes2 (.. js/statejs -notes -bnotes2)]
    (run! (fn [tu]
            (.. bnotes2
                (select (first tu))
                (setAttribute #js{:size 2})
                (setLabel "")))
          pp)
    (. bnotes2
       (create "functiongraph"
               #js["(7 * (log(x) - log(16.35)))/log(2)"]
               #js{:strokeColor "red"}))))

(set! (.. js/statejs -notes -anim2) anim2)

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

    (def bnotes2 (.. js/statejs -notes -bnotes2))

    )

  (defn octave [y o]
    (. bnotes2
       (create "line"
               (clj->js [[(second (pp y)) y]
                         [(second (pp (+ y o))) y]
                         ])
               #js{:name          (str "stl" (first (pp i)))
                   :straightFirst false
                   :straightLast  false
                   :strokeWidth   4
                   :strokeColor   "red"})))

  (octave 26 7)
  (octave 40 -7)
  :end)
