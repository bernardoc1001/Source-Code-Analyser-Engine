(ns scae-library.core
  "This namespace is the main 'engine' or entry point of the library, it receives
  requests and calls the other namespaces to perform the analysis, then returns the
  result to the caller."
  (:require [scae-library.tokeniser :as tokeniser]
            [scae-library.abstract-syntax-tree :as ast]
            [scae-library.symbol-table :as st]
            [scae-library.style-analyser :as sa]
            [scae-library.node-ops :as node-ops]
            [clojure.data.json :as json]))


(defn analyse-source-code
  "Expects a request containing a  hash-map with the following keys
  {:code     'string'
   :rulebook 'string'}
   Will return a collection of strings denoting any style suggestions to improve
   code quality."
  [request]
  (let [code-string (:code request)
        rulebook-map (json/read-str (:rulebook request) :key-fn keyword)
        abstract-syntax-tree
        (-> code-string
            (tokeniser/tokenise-code (:tokens rulebook-map))
            (ast/create-abstract-syntax-tree (:productions rulebook-map)))]
    (st/create-symbol-table abstract-syntax-tree)

    (let [suggestions
          (sa/analyse-style abstract-syntax-tree (:style-rules rulebook-map))]
      (st/reset-symbol-table!)
      suggestions)))


(defn generate-ast
  "Expects a request containing a  hash-map with the following keys
  {:code     'string'
  :rulebook 'string'}
  Will return the corresponding Abstract Syntax Tree"
  [request]
  (let [code-string (:code request)
        rulebook-map (json/read-str (:rulebook request) :key-fn keyword)]
    (-> code-string
        (tokeniser/tokenise-code (:tokens rulebook-map))
        (ast/create-abstract-syntax-tree (:productions rulebook-map))
        (st/strip-st-functions))))
