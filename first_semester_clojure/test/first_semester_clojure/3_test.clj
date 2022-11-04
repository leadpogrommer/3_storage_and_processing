(ns first-semester-clojure.3-test
  (:require [clojure.test :refer :all]
            [first-semester-clojure.3 :refer :all]))


(def seqs
  (list
    (iterate inc 1)
    (take 60 (iterate inc 1))
    )
  )

(def preds
  (list
    even?
    odd?
    )
  )

(deftest a
  (testing "Filter"
    (run!
      (fn [s]
        (run!
          (fn [pred]
            (is (= (take 1000 (filter pred s)) (take 1000 (p-filter pred s)))

                       )
                       ) preds)
        ) seqs
    )
  ))