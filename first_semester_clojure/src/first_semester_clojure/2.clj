(ns first-semester-clojure.2)

;import itertools
;def eratosthenes( ):
;    '''Yields the sequence of prime numbers via the Sieve of Eratosthenes.'''
;    D = {  }  # map each composite integer to its first-found prime factor
;    for q in itertools.count(2):     # q gets 2, 3, 4, 5, ... ad infinitum
;        p = D.pop(q, None)
;        if p is None:
;            # q not a key in D, so q is prime, therefore, yield it
;            yield q
;            # mark q squared as not-prime (with q as first-found prime factor)
;            D[q*q] = q
;        else:
;            # let x <- smallest (N*p)+q which wasn't yet known to be composite
;            # we just learned x is composite, with p first-found prime factor,
;            # since p is the first-found prime factor of q -- find and mark it
;            x = p + q
;            while x in D:
;                x += p
;            D[x] = p

;(def fibs
;  (lazy-cat '(0 1)
;            (map + fibs (rest fibs))))

(defn next_x [x p D]
  (if (D x)
    (next_x (+ x p) p D)
    x
    )
  )

(defn step [l]
  (let [d (first l) q (second l) p (d q) d (dissoc d q)]
    (if p
      (list (assoc d (next_x (+ p q) p d) p) (inc q) nil)
      (list (assoc d (* q q) q) (inc q) q))
    )
  )

(def primes (filter some? (map last (iterate step (list (hash-map) 2 nil)))))

(println (take 1000000 primes))


;(defn p-filter
;  [predicate coll]
;
;  (let [n 13,
;        n_parts 2,
;        parts (partition-all n_parts coll),
;        rets (map (fn [coll1] (future (filter predicate coll1))) parts),
;        step (fn step [[x & xs :as vs] fs]
;               (lazy-seq
;                (if-let [s (seq fs)]
;                  (lazy-cat (deref x) (step xs (rest s)))
;                  (map deref vs))))]
;
;    (do
;      (println "###start doall")
;      (doall (take n rets))
;      (println "###finish doall")
;      ;(step rets (drop n rets))
;      (println (doall(map deref (take n rets))))
;      )
;
;
;    ))