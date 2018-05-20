(ns scae-website.common
  (:require [reagent.core :as reagent]))

(defn onchange-swap-atom!
  [state path value]
  (swap! state assoc-in path (-> value .-target .-value)))

(defn text-pannel-template
  [grid-size header contents]
  [:div {:class grid-size}
   [:div {:class "panel panel-default"}
    [:div {:class "panel-heading"} header]
    [:div {:class "panel-body"}
     [:div {:class "row"}
      [:div {:class "col-sm-12"}
       contents]]]]])