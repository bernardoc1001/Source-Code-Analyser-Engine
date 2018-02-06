(ns scae-library.core
  "This namespace is the main 'engine' or entry point of the library, it receives
  requests and calls the other namespaces to perform the analysis, then returns the
  result to the caller.")

(defn analyse-source-code
  "Expects a map with the following keys {:code     'string'
                                          :rulebook 'string'}"
  [request]
  (str "Value received by SCAE-Library: " request))
