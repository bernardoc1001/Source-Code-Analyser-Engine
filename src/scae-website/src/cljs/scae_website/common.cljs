(ns scae-website.common
  (:require [reagent.core :as reagent]))

(defn onchange-swap-atom!
  [state path value]
  (swap! state assoc-in path (-> value .-target .-value)))
