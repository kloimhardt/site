(ns log23)

(defn am [divid xname yname bbox]
  {:boundingbox    bbox
   :showCopyright  false
   :axis           true
   :grid           false
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
        am (am divid "Frequency [Hz]" name bb)]

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
  (let [bb [-1 5
            5 -3]
        am (am divid "" "" bb)]

    (set! (.. js/statejs -log23 -brd3)
          (.. js/JXG
              -JSXGraph
              (initBoard divid (clj->js am)))))

  (let [brd (.. js/statejs -log23 -brd3)]
    (.create brd
             "functiongraph"
             #js["log(x)"]
             #js{:strokeColor "red"
                 :strokeWidth 2}))
  )

(set! (.. js/statejs -log23 -log3) log3)

(comment
  (do
    (main "divlog23")
    (def brd (.. js/statejs -log23 -brd)))

  (do
    (log3 "divlog2x")
    (def brd3 (.. js/statejs -log23 -brd3)))

  )
