(ns scae-library.tokeniser
  (:require [clojure.data.json :as json]))

(defn insert-token-type
  "Insert the token type into the token"
  [token-definitions]
  (hash-map :tokens (apply merge (map #(into {} (for [entry (get token-definitions %)]
                                                 {(first entry) {:regex (second entry)
                                                                 :type  %}}))
                                     (keys token-definitions)))))

(defn get-token-definitions
  "Reads token definitions as a vector of token or skip hash-maps,
  returns hashmap of the type of token and a corresponding hash-map of tokens
  input: [{\"token\": {\"token1\": \"token-regex1\",
                     \"token2\": \"token-regex2\"}},
          {\"skip\": {\"skip1\": \"skip-regex1\"}},
          {\"token\": {\"token3\": \"token-regex3\"}}]

  result: {:token {:token1 \"token-val1\",
                   :token2 \"token-val2\",
                   :token3 \"token-val3\"},
           :skip  {:skip1 \"skip-val1\"}}"
  [token-definitions-json]
  (->> token-definitions-json
    (apply merge-with conj)
    (insert-token-type)))

(defn val->token
  "Convert value to its corresponding token. Return nil if there is no
  corresponding token"
  [val token-definitions]
  (->>
    (map (fn
           [token]
           (if (re-matches (re-pattern (:regex (second token))) val)
             {:token-key (first token)
              :token-value    val
              :token-type (:type (second token))}))
          (:tokens token-definitions))
    (filter (complement nil?))
    (first)  ;; for an empty collection returns nil
    ))

(defn tokenise-code
  "Tokenises the Source Code"
  [code token-vector]
  (let [token-definitions (get-token-definitions token-vector)]
    (as-> token-definitions tokens
          ;;join regexes separated by | to get in order matches
          (clojure.string/join "|" (map :regex (vals (:tokens tokens))))
          (re-seq (re-pattern tokens) code)
          (map #(val->token %1 token-definitions) tokens))))