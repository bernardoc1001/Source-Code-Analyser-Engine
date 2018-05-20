(ns scae-website.views.documentation
  (:require [reagent.core :as reagent]
            [scae-website.sidebar :as sidebar]
            [scae-website.common :as common]))

(defn documentation-page []
  [:div
   [:div#wrapper
    [sidebar/sidebar]
    [:div {:class "jumbotron"}
     [sidebar/menu-toggle]
     [:h2 "Documentation"]]
    [:div.page-content-wrapper>div.container-fluid
     [:div.row
      [common/text-pannel-template "col-xs-12"
       "Video"
       [:div {:class "text-center"}
        [:embed-responsive
         [:iframe {:width  "560"
                   :height "315"
                   :src    "https://www.youtube.com/embed/1J9sGMddxmo"
                   :allowFullScreen "allowFullScreen"}]]]
       ]]
     [:div.row
      [common/text-pannel-template "col-xs-12 col-lg-4"
       "Code Documentation"
       [:div
        [:p "You can find the code documentation for the library"
         [:a {:href "library-docs/doc/intro.html"} " here "]]]]
      [common/text-pannel-template "col-xs-12 col-lg-4"
       "RESTful API Docs"
       [:div
        [:p "You can find the RESTful API documentation"
         [:a {:href "https://documenter.getpostman.com/view/4021417/RW87op7E"} " here "]]]]
      [common/text-pannel-template "col-xs-12 col-lg-4"
       "Test Coverage Report"
       [:div
        [:p "You can find the test coverage documentation for the library"
         [:a {:href "library-docs/coverage/index.html"} " here "]]
        ]]
      ]
     [:div.row
      [common/text-pannel-template "col-xs-12 col-lg-6"
       "User Manual"
       [:div {:class "text-center"}
        [:embed-responsive
         [:iframe {:width  "640"
                   :height "720"
                   :src    "library-docs/User%20Manual.pdf"}]]]]
      [common/text-pannel-template "col-xs-12 col-lg-6"
       "Technical Guide"
       [:div {:class "text-center"}
        [:embed-responsive
         [:iframe {:width  "640"
                   :height "720"
                   :src    "library-docs/Technical%20Guide.pdf"}]]]]]]]])

(defn documentation []
  (reagent/create-class {:reagent-render documentation-page}))
