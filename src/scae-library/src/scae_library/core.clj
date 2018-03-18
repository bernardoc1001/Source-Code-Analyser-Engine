(ns scae-library.core
  "This namespace is the main 'engine' or entry point of the library, it receives
  requests and calls the other namespaces to perform the analysis, then returns the
  result to the caller."
  (:require [scae-library.tokeniser :as tokeniser]
            [scae-library.abstract-syntax-tree :as ast]
            [scae-library.symbol-table :as st]
            [clojure.data.json :as json]))

(defn analyse-source-code
  "Expects a map with the following keys {:code     'string'
                                          :rulebook 'string'}"
  [request]
  (let [code-string (:code request)
        rulebook-map (json/read-str (:rulebook request) :key-fn keyword)]
    (-> code-string
        (tokeniser/tokenise-code (:tokens rulebook-map))
        (ast/create-abstract-syntax-tree (:productions rulebook-map))
        (st/create-symbol-table))

    (st/pretty-print-symbol-table)
    (st/reset-symbol-table!)
    true))
