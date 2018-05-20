(defn my-func []
  (+ 1 2)
  (def my-var 42)
  (+ 3 5))

(defn my-func-2 []
  (+ 1 2)
  (let [x (def my-var-2 42)]
    (println x))
  (+ 3 5))