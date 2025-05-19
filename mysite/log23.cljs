(ns log23)

(defn main [divid]
  (let [bb [120 (- 48 notes/idx220)
            1080 (- 20 notes/idx220)]
        am {:boundingbox bb
            :showCopyright true
            :axis          true
            :grid          false
            :defaultAxes
            {:x {:name      "Frequency [Hz]"
                 :withLabel true
                 :label     {:position "rt"
                             :offset   [-5 15]
                             :anchorX  "right"}}
             :y {:withLabel true
                 :name      "log2(x/220) * 7"
                 :label     {:position "rt"
                             :offset   [5 -5]
                             :anchorY  "top"}}}}]

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

(set! (.. js/statejs -log23) #js{})
(set! (.. js/statejs -log23 -main) main)

(defn anim2 []
  (let [brd (.. js/statejs -log23 -brd)]
    (.setBoundingBox brd #js[-320 33
                             1480 -18])))

(set! (.. js/statejs -log23 -anim2) anim2)

(comment
  (main "divlog23")
  (def brd (.. js/statejs -log23 -brd))
  (.. brd (zoomOut #js[3 3]))
  (.. brd (setBoundingBox #js[-320 33
                              1480 -18]) )
  )
