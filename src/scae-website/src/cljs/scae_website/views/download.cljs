(ns scae-website.views.download
  (:require [reagent.core :as reagent]
            [scae-website.sidebar :as sidebar]))

(defn download-page []
  [:div
   [:div#wrapper
    [sidebar/sidebar]
    [:div {:class "jumbotron"}
     [sidebar/menu-toggle]
     [:h2 "Download"]]
    [:div.page-content-wrapper>div.container-fluid>div.row>div.col-xs-12
     [:div {:class "panel panel-default"}
      [:div {:class "panel-heading"} "Download"]
      [:div {:class "panel-body"}
       [:div {:class "row"}
        [:div {:class "col-sm-12"}

         ;;Library Download
         [:label {:for "library-download"}
          "Download Latest SCAE Library: "]
         [:a {:id       "library-download"
              :href     "/downloads/scae-library-0.1.0-SNAPSHOT.jar"
              :download "scae-library-latest.jar"}
          " scae-library-latest.jar"]
         [:div
          ;;Website Download
          [:label {:for "library-download"}
           "Download Latest SCAE Website: "]
          [:a {:id       "website-download"
               :href     "/downloads/scae-website-0.1.0-SNAPSHOT-standalone.jar"
               :download "scae-website-latest-uberjar.jar"}
           " scae-website-latest-uberjar.jar"]]]]]]]]])

(defn download []
  (reagent/create-class {:reagent-render download-page}))
