(ns first-semester-clojure.1)

(defn step [alphabet, words]
  (reduce concat ()
          (map (fn [word]
                 (map (fn [c] (.concat word c)) (filter (fn [c] (not (.endsWith word c))) alphabet))) words)))

(defn generate [alphabet n]
  (reduce (fn [xs x] (step alphabet xs)) (list "") (range 0 n)))

(println (generate (list "a" "b" "c") 2))


