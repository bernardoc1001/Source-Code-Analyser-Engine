(ns scae-website.views.documentation
  (:require [reagent.core :as reagent]
            [scae-website.sidebar :as sidebar]))

(defn documentation-page []
  [:div
   [:div#wrapper
    [sidebar/sidebar]
    [:div {:class "jumbotron"}
     [sidebar/menu-toggle]
     [:h2 "Documentation"]]
    [:div.page-content-wrapper>div.container-fluid>div.row>div.col-xs-12
     [:div {:class "panel panel-default"}
      [:div {:class "panel-heading"} "Documentation"]
      [:div {:class "panel-body"}
       [:div {:class "row"}
        [:div {:class "col-sm-12"}
         [:p "Lorem ipsum dolor sit amet, consectetur adipiscing elit.
   Sed vel sem erat. Pellentesque dapibus sem quis elementum blandit.
   Pellentesque risus arcu, scelerisque at egestas eget, pellentesque eget turpis.
   Vestibulum posuere mi sed nulla ullamcorper, nec pellentesque erat semper.
   Suspendisse luctus arcu nec justo consectetur dignissim. Aliquam ac aliquam
   ligula, sed placerat est. Ut ac malesuada urna. Praesent vel malesuada dui.
   Quisque viverra velit a nibh fermentum, vel sollicitudin neque sodales.
   Aliquam iaculis, ante dictum ornare blandit, dui dolor dictum urna,
   id dictum sem leo eget augue. In lobortis tortor at varius mollis.
   Aliquam aliquam velit in rutrum ultrices. Class aptent taciti sociosqu ad
   litora torquent per conubia nostra, per inceptos himenaeos. "]
         ]]]]]]])

(defn documentation []
  (reagent/create-class {:reagent-render documentation-page}))
