(ns scae-website.sample-files
  (:require [clojure.java.io :as io]))

(defn file-array->hash-map
  [file-array]
  (into {}
        (map #(hash-map (.getName %) (slurp %)) file-array)))

(defn get-file-array-from-dir
  [path]
  (-> path
      io/file
      .listFiles))

(defn get-sample-files  []
  "Returns the sample files located in resources/public/samples in the structure
  {:code-samples {\"fileName1\" content-string
                        ... }
   :rulebook-samples {\"fileName1\" content-string
                        ... }}"
  (let [code-sample-map (-> "resources/public/samples/code"
                            get-file-array-from-dir
                            file-array->hash-map)
        rulebook-sample-map (-> "resources/public/samples/rulebook"
                                get-file-array-from-dir
                                file-array->hash-map)]
    {:code-samples code-sample-map
     :rulebook-samples rulebook-sample-map}))
