(ns first-semester-clojure.2-test
  (:require [clojure.test :refer :all]
            [first-semester-clojure.2 :refer :all]))



(deftest a-test
  (let [fprimes (set (take 5000 primes))] (testing "Pimes"
    (is (contains? fprimes 3))
    (is (contains? fprimes 7))
    (is (contains? fprimes 19))
    (is (contains? fprimes 37))
    (is (contains? fprimes 1699))
    )
  (testing "Non-primes"
    (is (not (contains? fprimes 4)))
    (is (not (contains? fprimes 16)))
    (is (not (contains? fprimes 21)))
    (is (not (contains? fprimes 1337)))
    (is (not (contains? fprimes 228)))
    ))

  )
