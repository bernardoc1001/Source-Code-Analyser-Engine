(ns scae-website.sidebar
  (:require [reagent.core :as reagent]
            [reagent.session :as session]))

(defn sidebar []
  [:div#sidebar-wrapper
   [:ul.sidebar-nav
    (let [home-link [:li>a {:href "/"} "Home"]
          code-submission-link [:li>a {:href "/code-submission"} "Code Submission"]
          documentation-link [:li>a {:href "/documentation"} "Documentation"]
          download-link [:li>a {:href "/download"} "Download"]]
      [:div
       home-link
       code-submission-link
       documentation-link
       download-link])]])

(defn menu-toggle-render []
  [:div.btn.btn-default {:aria-label "Nav-Menu Toggle Button"}
   [:span {:class "glyphicon glyphicon-menu-hamburger"
           :aria-hidden "true"}]])

(defn menu-toggle-did-mount [this]
  (.click (js/$ (reagent/dom-node this))
          (fn [e]
            (.preventDefault e)
            (.toggleClass (js/$ "#wrapper") "toggled"))))

(defn menu-toggle []
  (reagent/create-class {:reagent-render menu-toggle-render
                         :component-did-mount menu-toggle-did-mount}))