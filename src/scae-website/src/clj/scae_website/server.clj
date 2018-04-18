(ns scae-website.server
  (:require [scae-website.handler :refer [app]]
            [config.core :refer [env]]
            [ring.adapter.jetty :refer [run-jetty]])
  (:gen-class))

 (defn -main [& args]
   (run-jetty app {:join? false}))
