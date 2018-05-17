(ns scae-website.views.home
  (:require [reagent.core :as reagent]
            [scae-website.sidebar :as sidebar]))

(defn text-pannel-template
  [grid-size header contents]
  [:div {:class grid-size}
   [:div {:class "panel panel-default"}
    [:div {:class "panel-heading"} header]
    [:div {:class "panel-body"}
     [:div {:class "row"}
      [:div {:class "col-sm-12"}
       contents]]]]])

(defn home-page []
  [:div
   [:div#wrapper
    [sidebar/sidebar]
    [:div {:class "jumbotron"}
     [sidebar/menu-toggle]
     [:h2 "Home"]]
    [:div.page-content-wrapper>div.container-fluid
     [:div.row
      [text-pannel-template "col-xs-12"
       "Our Aim"
       [:div
        [:p "Welcome to the Source Code Analyser Engine Website"]
        [:p "Our aim is to promote the use of good coding style and practices, especially when learning how to code"]]]]
     [:div.row
      [text-pannel-template "col-xs-12 col-md-6 col-lg-3"
       "Receive Style Feedback"
       [:div
        [:ul {:class "list-unstyled"}
         [:li "Run your code along with a style rulebook"]
         [:li "Get suggestions on what to change or avoid"]]]]
      [text-pannel-template "col-xs-12 col-md-6 col-lg-3"
       "Use Premade Style Rulebooks"
       [:div
        [:ul {:class "list-unstyled"}
         [:li "Select from a range of pre-made style rulebooks"]
         [:li "Modify them to suit your own needs"]]]]
      [text-pannel-template "col-xs-12 col-md-6 col-lg-3"
       "Create Your Own Rulebooks"
       [:div
        [:ul {:class "list-unstyled"}
         [:li "Create your own customised rulebook"]
         [:li "See the" [:a {:href "/documentation"} " docs "] "for more"]]]]
      [text-pannel-template "col-xs-12 col-md-6 col-lg-3"
       "Multi-Language Support"
       [:div
        [:ul {:class "list-unstyled"}
         [:li "Select from a range of languages"]
         [:li "Create style rulebooks for any language you want"]]]]]
     [:div.row
      [text-pannel-template "col-xs-12 col-md-6 col-lg-3"
       "Use the Library in Your Own Projects"
       [:div
        [:ul {:class "list-unstyled"}
         [:li "WebApps, Teaching Platforms IDE plugins..."]
         [:li "Download the library " [:a {:href "/download"} "here"]]]]]
      [text-pannel-template "col-xs-12 col-md-6 col-lg-3"
       "Utilise our REST API"
       [:div
        [:ul {:class "list-unstyled"}
         [:li "Quickly prototype without having to install"]
         [:li "Make calls to the public API"]]]]
      [text-pannel-template "col-xs-12 col-md-6 col-lg-3"
       "Contribute to the project"
       [:div
        [:ul {:class "list-unstyled"}
         [:li "See the code on " [:a {:href "https://gitlab.computing.dcu.ie/oconnb47/2018-ca400-oconnb47"} " Gitlab "]]
         [:li "Feel free to make fork and pull"]]]]
      [text-pannel-template "col-xs-12 col-md-6 col-lg-3"
       "Powered by the Cloud"
       [:div
        [:ul {:class "list-unstyled"}
         [:li "Utilise the power of cloud based computing"]
         [:li "Powered by AWS"]]]]]]]])

(defn home []
  (reagent/create-class {:reagent-render home-page}))
