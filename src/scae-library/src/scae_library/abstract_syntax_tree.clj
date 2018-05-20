(ns scae-library.abstract-syntax-tree
  "This namespace is responsible for generating the Abstract Syntax Tree"
  (:require [clojure.pprint :refer [pprint]]))

(defn code->node
  "Converts a piece of code into a temporary production node, which will
  then be converted into and Abstract Syntax Tree Node"
  [code]
  (let [unevaled-code (if (string? code)
                        (read-string code)
                        code)]
    (hash-map :node-name (name (first unevaled-code))
              :node-value (eval unevaled-code))))

;; Credit for the function count-tokens: https://stackoverflow.com/a/48389794
(defn count-tokens
  "Returns a count of the nested tokens in a collection"
  [coll]
  (->> coll
       (tree-seq coll? seq)
       (keep #(get-in % [:token-key]))
       count))

(declare parse-production-rule)
;;recursively check single elem of inner vec at a time to easily parse through tokens
(defn parse-single-option
  "Parses a single option / branch / inner vector of a production rule"
  [tokens option-vec]
  ;;(println "current parse token: " (first tokens))
  ;;(println "current parse option-vec: " option-vec)
  (if (seq option-vec)
    (do (if (and (list? (first option-vec)) (not-empty (first option-vec)))
          ;; If we see a list here, thats a function call to another production rule
          (let [inner-func-node (parse-production-rule tokens (first option-vec))]
            (if (:parsed-node-result inner-func-node)
              (do
                ;;(println "inner func node result: \n" (:parsed-node-result inner-func-node))
                ;;here i want to remove tokens in result from tokens, then remove head of option,
                ;; then continue parsing the rest of the current option vector
                (let [recursive-result-after-inner-func
                      (parse-single-option (drop
                                             (count-tokens (:parsed-node-result inner-func-node))
                                             tokens)
                                           (rest option-vec))]
                  ;;(println "recursive-result-after-inner-func: " recursive-result-after-inner-func)
                  (if recursive-result-after-inner-func
                    (concat [inner-func-node] recursive-result-after-inner-func)))
                )))
          (if (= (:token-key (first tokens)) (first option-vec))
            (do
              ;;(println (first tokens) " == " (first option-vec))
              (let [recursive-call-result (parse-single-option (rest tokens) (rest option-vec))]
                (if recursive-call-result
                  (concat (vector (first tokens)) recursive-call-result)
                  (do
                    ;;(println "recursive call result empty")
                    nil))))

            ;;check for epsilon condition
            (if (vector? (first option-vec))
              ;; If we see a vector here, it's an epsilon transition
              (do
                ;;(println "Epsilon condition, token:  " (first tokens) "  and option-vec: " option-vec)
                [])
              (if (map? (first option-vec))
                ;;If we see a map here, it contains symbol table function calls in a vector.
                ;;Return the map and continue with the parse without evaluating the symbol table calls
                (do
                  (let [continued-parsing-result (parse-single-option tokens (rest option-vec)) ;;recurisve call without consumming token
                        ]
                    (if continued-parsing-result
                      (concat (vector (first option-vec)) continued-parsing-result) ;;concat the symbol table function call map with the result of the parse
                      (do
                        ;;(println "recursive call result empty")
                        nil))))


                ;;else
                (do
                  ;;(println (first tokens) " != " (first option-vec))
                  nil))))))
    [];;returning empty vec here so as not to return nil
    ))


(defn parse-production-rule
  "Parses a single production rule."
  [tokenised-code production-rule]
  ;;(println "prod rule: " production-rule)
  (let [filtered-tokens (filter #(= (:token-type %) :token) tokenised-code)
        production-node (code->node production-rule)
        all-options-parsed
        (for [option-vec (:node-value production-node)]
          (do
            #_(println "node name: " (:node-name production-node))
            (hash-map :parsed-node-name (:node-name production-node)
                      :parsed-node-result (parse-single-option filtered-tokens option-vec))))]
    (first (filter #(not (nil? (:parsed-node-result %))) all-options-parsed))))

(defn create-abstract-syntax-tree
  "Creates the Abstract Syntax Tree as a nested map of Nodes"
  [tokenised-code production-rules]
  (eval (read-string (:scae-forward-declarations production-rules)))
  (eval (read-string (:scae-program production-rules)))
  (doseq [production (vals production-rules)]
    (eval (read-string production)))
  (parse-production-rule tokenised-code (:scae-entry-point production-rules)))