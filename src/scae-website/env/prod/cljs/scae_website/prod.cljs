(ns scae-website.prod
  (:require [scae-website.core :as core]))

;;ignore println statements in prod
(set! *print-fn* (fn [& _]))

(core/init!)
