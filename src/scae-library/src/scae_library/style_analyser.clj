(ns scae-library.style-analyser
  "This namespace is responsible for analysing an Abstract Syntax Tree for
  Style Suggestions"
  (:require [scae-library.node-ops :as node-ops]
            [clojure.string :as string]
            [com.rpl.specter :refer :all]
            [clojure.walk :as walk]))

(defn quote-first-parsed-node-result
  "Quotes the parsed node result of a node. This is needed to prevent the
  Clojure Reader/Evaluater from pre-emptively evaluating the node as a function"
  [ast-node]
  (transform [:parsed-node-result] #(str "'" (reverse (into (list) %))) ast-node))

(defn parse-style-rules
  "Evaluate a particular node on the Abstract Syntax Tree for any style suggestions"
  [ast-node node-based-style-rules]
  (let [current-node-suggestions
        (for [node-key-style-vector-val-pair node-based-style-rules]
          ;;if current node matches any style rules
          (if (= (name (first node-key-style-vector-val-pair))
                 (node-ops/get-node-name ast-node))

            ;;eval all style rules
            (let [style-rule-vector (read-string (second node-key-style-vector-val-pair))]
              (for [style-rule style-rule-vector]
                ;;note when putting a vector into a list it is done in reverse order
                ;;also note I have to insert a quoted string to prevent unintended evaluation
                (eval (into (list) [(quote-first-parsed-node-result ast-node) style-rule]))))

            ;;Else return an empty string
            ""))
        children-node-suggestions
        (for [child (node-ops/get-node-children ast-node)
              :when (node-ops/node? child)]
          (parse-style-rules child node-based-style-rules))]


    (filter #(not (string/blank? %))
            (flatten
              (concat
                current-node-suggestions
                children-node-suggestions)))))

(defn analyse-style
  "Analyses the Abstract Syntax Tree and returns a collection of strings
  denoting any style suggestions that were returned."
  [root-ast-node style-rules]
  ;;define the style rules in the namespace
  (doseq [style-function-definition (vals (:style-rules-function-definitions style-rules))]
    (eval (read-string style-function-definition)))

  ;;parse through the AST, evaluating style rules for the nodes that have them
  (parse-style-rules root-ast-node (:node-based-style-rules style-rules)))