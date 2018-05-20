(defproject scae-library "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/data.json "0.2.6"]
                 [com.rpl/specter "1.1.0"]]
  :plugins [[lein-eftest "0.5.1"]
            [lein-cloverage "1.0.10"]
            [viebel/lein-codox "0.10.2"]]
  :eftest {:multithread? false})
