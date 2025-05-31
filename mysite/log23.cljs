(ns log23)

(defn am [xname yname bbox &[grid]]
  {:boundingbox    bbox
   :showCopyright  false
   :axis           true
   :grid           (or grid false)
   :showNavigation false
   :defaultAxes
   {:x {:name      xname
        :withLabel true
        :label     {:position "rt"
                    :offset   [-5 15]
                    :anchorX  "right"}
        :ticks     {:visible     true
                    :majorHeight 5}}
    :y {:withLabel true
        :name      yname
        :label     {:position "rt"
                    :offset   [5 -5]
                    :anchorY  "top"}

        :ticks {:visible     true
                :majorHeight 5} }}}
  )

(defn mainlog [divid name]
  (let [bb [120 (- 48 notes/idx220)
            1080 (- 20 notes/idx220)]
        am (am "Frequency [Hz]" name bb)]

    (set! (.. js/statejs -log23 -brd)
          (.. js/JXG
              -JSXGraph
              (initBoard divid (clj->js am)))))

  (let [brd (.. js/statejs -log23 -brd)]
    (.create brd
             "functiongraph"
             #js["(7 * log(x / 220)) / log(2)"]
             #js{:strokeColor "red"
                 :strokeWidth 2})

    (run! (fn [l]
            (.create brd "line"
                     (clj->js l)
                     #js{:straightFirst false
                         :straightLast  false
                         :strokeWidth   1
                         :strokeColor   "black"}))
          [[[0 7] [440 7]]
           [[440 7] [440 0]]
           [[0 14] [880 14]]
           [[880 14] [880 0]]])

    ))

(defn main [divid]
  (mainlog divid "log2(x/220) * 7"))

(set! (.. js/statejs -log23) #js{})
(set! (.. js/statejs -log23 -main) main)

(defn anim2 []
  (let [brd (.. js/statejs -log23 -brd)]
    (.setBoundingBox brd #js[-320 33
                             1480 -18])))

(set! (.. js/statejs -log23 -anim2) anim2)

(defn log3 [divid]
  #_(log3 "divlog2x")
  (let [bb [-5 6
            35 -3]
        am (am "" "" bb true)]

    (set! (.. js/statejs -log23 -brd3)
          (.. js/JXG
              -JSXGraph
              (initBoard divid (clj->js am)))))

  (let [brd (.. js/statejs -log23 -brd3)]
    (.create brd
             "functiongraph"
             #js["log(x) / log(2)"]
             #js{:strokeColor "green"
                 :strokeWidth 2}))
  )

(set! (.. js/statejs -log23 -log3) log3)

(defn log2b [divid]
  (let [bb [-5 6
            35 -3]
        am (am "" "" bb true)]

    (set! (.. js/statejs -log23 -brd2b)
          (.. js/JXG
              -JSXGraph
              (initBoard divid (clj->js am)))))

  (let [brd (.. js/statejs -log23 -brd2b)]
    (.create brd
             "functiongraph"
             #js["log(x) / log(2)"]
             #js{:strokeColor "green"
                 :strokeWidth 2})

    (.create brd "text" #js[2.1 2.4 "steps=1"])
    #_(log2b "divlog20_2")
))

(defn log2banim2 []
  (let [brd (.. js/statejs -log23 -brd2b)]
    (.create brd
             "functiongraph"
             #js["0.7 * log(x) / log(2)"]
             #js{:strokeColor "blue"
                 :strokeWidth 2})

    (.create brd "text" #js[6 1.5 "steps=0.7"])))

(set! (.. js/statejs -log23 -log2b) log2b)
(set! (.. js/statejs -log23 -log2banim2) log2banim2)

(defn logtri [divid]
  (let [bb [-3 2
            7 -2]
        am (am "" "" bb true)]

    (set! (.. js/statejs -log23 -brdtri)
          (.. js/JXG
              -JSXGraph
              (initBoard divid (clj->js am)))))

  (let [brd (.. js/statejs -log23 -brdtri)]
    (.create brd
             "functiongraph"
             #js["0.7 * log(x) / log(2)"]
             #js{:strokeColor "blue"
                 :strokeWidth 2})))

(set! (.. js/statejs -log23 -logtri) logtri)

(defn triangles [b x dx f & [argzero]]
    (let [zero (or argzero (f x))
          t0 (.create b "point" #js[x zero])
          t1 (.create b "point"
                      #js[(fn [] (+ (.X t0) dx))
                                     (fn [] (.Y t0))]
                      #js{:visible false})
          t2 (.create b "point"
                      #js[(fn [] (+ (.X t0) dx))
                                     (fn [] (+ (.Y t0)
                                               (- (f (+ x dx)) zero)))]
                      #js{:visible false})]
      (run! (fn [p] (.setLabel p "")) [t0 t1 t2])
      [t0 t1 t2 (.create b "polygon" #js[t0 t1 t2])]))

  (defn repl-poly [b t]
    (let [xys  (map (fn [p] [(.X p) (.Y p)]) (butlast t))
          npts (mapv (fn [xy] (.create b "point" (clj->js xy) #js{:visible false})) xys)
          nply (.create b "polygon" (clj->js npts))
          ]

      (.setLabel (first npts) "")
      (.setAttribute (first npts) #js{:visible true})

      (run! (fn [o] (.removeObject b o)) (reverse t))
      (conj npts nply)))

  (defn shrink [b t]
    (let [tn (repl-poly b t)
          faktor 0.5
          hohe   (- (.Y (tn 2)) (.Y (tn 1)))]
      (.moveTo (tn 0) #js[(+ (.X (tn 0)) faktor) (+ (.Y (tn 0)) (* faktor hohe))] 1500)
      (.moveTo (tn 1) #js[(.X (tn 1)) (+ (.Y (tn 1)) (* faktor hohe))] 1500)))

  (defn trianim2 []
    (set! (.. js/statejs -log23 -tri_ts)
          (doall (map (fn [x] (triangles
                                (.. js/statejs -log23 -brdtri)
                                x
                                1
                                js/Math.log))
                      [2 4]))))

  (defn trianim3 []
    (run! (fn [[t]] (.moveTo t #js[(.X t) 0] 1500))
          (.. js/statejs -log23 -tri_ts)))

  (defn trianim4 []
    (.create (.. js/statejs -log23 -brdtri)
             "functiongraph"
             #js["1/x"]
             #js{:strokeColor "yellow"
                 :strokeWidth 1}))

(defn trianim5 []
  (set! (.. js/statejs -log23 -tri_t0)
        (triangles (.. js/statejs -log23 -brdtri)
                   0
                   1
                   (fn [x] (/ 1 x))
                   0)))

(defn trianim6 []
  (.moveTo (first (.. js/statejs -log23 -tri_t0)) #js[0 -1] 1500))

(defn trianim7 []
  (shrink (.. js/statejs -log23 -brdtri) (.. js/statejs -log23 -tri_t0)))

(set! (.. js/statejs -log23 -trianim2) trianim2)
(set! (.. js/statejs -log23 -trianim3) trianim3)
(set! (.. js/statejs -log23 -trianim4) trianim4)
(set! (.. js/statejs -log23 -trianim5) trianim5)
(set! (.. js/statejs -log23 -trianim6) trianim6)
(set! (.. js/statejs -log23 -trianim7) trianim7)

(comment
  (do
    (main "divlog23")
    (def brd (.. js/statejs -log23 -brd)))

  (do
    (log3 "divlog2x")
    (def brd3 (.. js/statejs -log23 -brd3)))

  (do
    (log2b "divlog20_2")
    (def brd2b (.. js/statejs -log23 -brd2b)))

  (do
    (logtri "divlogtri")
    (def brdtri (.. js/statejs -log23 -brdtri))

    :end)

  (trianim2)
  (trianim3)
  (trianim4)
  (trianim5)
  (trianim6)
  (trianim7)

  (apply mapv vector [[1 2] [3 4]])
  :end)

(defn log2a [steps intersect x]
  (* (/ (js/Math.log (/ x intersect)) (js/Math.log 2)) steps))

(defn many [divid]
  (let [bb [-320 33
            1480 -18]
        am (am "Frequency [Hz]" "" bb)]

    (set! (.. js/statejs -log23 -brdmany)
          (.. js/JXG
              -JSXGraph
              (initBoard divid (clj->js am)))))

  (let [brd (.. js/statejs -log23 -brdmany)]
    (.create brd
             "functiongraph"
             #js[(partial log2a 7 220)]
             #js{:strokeColor "red"
                 :strokeWidth 2})))

(defn manyanim2 []
  (let [brd (.. js/statejs -log23 -brdmany)]
    (.create brd
             "functiongraph"
             #js[(partial log2a
                          (+ 4 (rand-int 10))
                          (+ 170 (rand-int 100)))]
             #js{:strokeColor "green"
                 :strokeWidth 1})))

(set! (.. js/statejs -log23 -many) many)
(set! (.. js/statejs -log23 -manyanim2) manyanim2)

(comment
  (many "divmanylogs")
  (manyanim2)

  :end)
