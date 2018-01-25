(ns scae-website.core
    (:require [reagent.core :as reagent :refer [atom]]
              [reagent.session :as session]
              [secretary.core :as secretary :include-macros true]
              [accountant.core :as accountant]
              [scae-website.views.home :refer [home]]
              [scae-website.views.documentation :refer [documentation]]
              [scae-website.views.download :refer [download]]
              [scae-website.views.code-submission :refer [code-submission]]))

(defn current-page []
  [:div [(session/get :current-page)]])

(secretary/defroute "/" []
  (session/put! :current-page #'home))

(secretary/defroute "/documentation" []
                    (session/put! :current-page #'documentation))

(secretary/defroute "/download" []
                    (session/put! :current-page #'download))

(secretary/defroute "/code-submission" []
                    (session/put! :current-page #'code-submission))


(defn mount-root []
  (reagent/render [current-page] (.getElementById js/document "app")))

(defn init! []
  (accountant/configure-navigation!
    {:nav-handler
     (fn [path]
       (secretary/dispatch! path))
     :path-exists?
     (fn [path]
       (secretary/locate-route path))})
  (accountant/dispatch-current!)
  (mount-root))
