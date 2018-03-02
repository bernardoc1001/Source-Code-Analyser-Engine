(ns scae-library.abstract-syntax-tree)

(defn eval-code
  [code]
  (if (string? code)
    (eval (read-string code))
    (eval code)))

(declare parse-production-rule)
;;todo investigate loop - recur combo for tail optimisation
;;recusively check single elem of inner vec at a time to easily parse through tokens
(defn parse-single-option
  [tokens option-vec]
  (println "current parse token: " (first tokens))
  (println "current parse option-vec: " option-vec)
  (if (seq option-vec)
    (do (if (list? (first option-vec))
          (do
            (println "performing function call: " (first option-vec))
            (let [inner-func-recursive-result  (parse-production-rule tokens (eval (first option-vec)))]
              (if inner-func-recursive-result
                (do
                  ;;here i want to remove tokens in result from tokens, then remove head of option, then continue
                  (println "inner func recursive result: " (type inner-func-recursive-result))
                  (println "about to remove parsed stuff")
                  (let [recursive-result-after-inner-func
                        (parse-single-option (drop (count inner-func-recursive-result) tokens) (rest option-vec))]
                    (if recursive-result-after-inner-func
                      (concat inner-func-recursive-result recursive-result-after-inner-func)))
                  ))))
          (if (= (:token-key (first tokens)) (first option-vec))
            (do (println (first tokens) " == " (first option-vec))
                (let [recursive-call-result (parse-single-option (rest tokens) (rest option-vec))]
                  (if recursive-call-result
                    (concat (vector (first tokens)) recursive-call-result)
                    (do
                      (println "recursive call result empty")
                      nil))))
            (if (vector? (first option-vec)) ;;check for epsilon condition
              (do
                (println "Epsilon condition, token:  " (first tokens) "  and option-vec: " option-vec)
                [])
              (do
                (println (first tokens) " != " (first option-vec))
                nil)))))
    []    ;;returning empty vec here so as not to return nil
    ))


(defn parse-production-rule
  [tokenised-code production-rule]
  (println "prod rule: " production-rule)
  (let [filtered-tokens (filter #(= (:token-type %) :token) tokenised-code)
        all-options-parsed
        (for [option-vec production-rule]
          (parse-single-option filtered-tokens option-vec))]
    (first (filter #(not (nil? %)) all-options-parsed))))

(defn create-abstract-syntax-tree
  [tokenised-code production-rules]
  (eval (read-string (:scae-forward-declarations production-rules)))
  (eval (read-string (:scae-program production-rules)))
  (doseq [production (vals production-rules)]
    (eval (read-string production)))
  (let [for-printout (filter #(= (:token-type %) :token) tokenised-code)]
    (println "Tokenised code:")
    (doseq [tok for-printout]
      (println tok))
    (println "======================================================"))
  (parse-production-rule tokenised-code (eval-code (:scae-entry-point production-rules))))
