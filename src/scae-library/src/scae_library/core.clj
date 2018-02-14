(ns scae-library.core
  "This namespace is the main 'engine' or entry point of the library, it receives
  requests and calls the other namespaces to perform the analysis, then returns the
  result to the caller."
  (:require [scae-library.tokeniser :as tokeniser]
            [clojure.data.json :as json]))

(defn analyse-source-code
  "Expects a map with the following keys {:code     'string'
                                          :rulebook 'string'}"
  [request]
  (let [code-string (:code request)
        rulebook-map (json/read-str (:rulebook request) :key-fn keyword)]
    (tokeniser/tokenise-code code-string (:tokens rulebook-map))))
