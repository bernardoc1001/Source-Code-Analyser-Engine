(ns scae-website.sample-files
  (:require [clojure.string :as c-str]
            [clojure.java.io :as io]
            [cpath-clj.core :as cp]))

(defn- file-uri-map->file-hash-map
  [file-array]
  (into {}
        (map #(hash-map (c-str/join "" (rest (first %)))
                        (slurp (first (second %))))
             file-array)))

(defn- get-file-uri-map
  [path]
  (-> path
      io/resource
      cp/resources))

(defn- get-file-hash-map
  [path]
  (-> path
      get-file-uri-map
      file-uri-map->file-hash-map))

(defn get-sample-files  []
  "Returns the sample files located in resources/public/samples in the structure
  {:code-samples {\"fileName1\" content-string
                        ... }
   :rulebook-samples {\"fileName1\" content-string
                        ... }}"
  (let [code-sample-map (get-file-hash-map "public/samples/code")
        rulebook-sample-map (get-file-hash-map "public/samples/rulebook")]
    {:code-samples code-sample-map
     :rulebook-samples rulebook-sample-map}))
