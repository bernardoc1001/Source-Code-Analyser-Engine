(ns scae-website.views.code-submission
  (:require [reagent.core :as reagent]
            [ajax.core :refer [GET POST]]
            [scae-website.ajax-handlers :refer [handler error-handler]]
            [scae-website.sidebar :as sidebar]
            [scae-website.common :as common]))

(defonce page-state
         (reagent/atom {:data {:code     ""
                               :rulebook ""}
                        :rulebook-type ""
                        :returned-suggestions ""
                        :returned-errors ""}))


(defn code-submission-success-handler
  [response]
  (let [formatted-response
        (clojure.string/join "\n" response)]
    (.log js/console (str "Code Submission Success Handler Response: " formatted-response))
    (swap! page-state assoc-in [:returned-suggestions] formatted-response)))

(defn code-submission-error-handler
  [response]
  (let [formatted-response
        (str "Status Code: " (:status response)
             "\nStatus Text: " (:status-text response)
             "\nFailure Type: " (:failure response))]
    (.log js/console (str "Code Submission Error Handler Response: " response))
    (swap! page-state assoc-in [:returned-errors] formatted-response)))

(defn post-code-submission
  [submit-body]
  (POST "/scae-api"
        {:params  {:data submit-body}
         :handler code-submission-success-handler           ;;handler
         :error-handler code-submission-error-handler       ;;error-handler
         }))

(defn submit-procedure
  [submit-body]
  ;(.log js/console "Code: " (:code submit-body))
  ;(.log js/console "Rulebook: " (:rulebook submit-body))
  (.log js/console "Posting....")
  (post-code-submission submit-body)
  (.scrollTop (js/$ "html,body") 0))

(defn code-submission-input []
  [:div {:class "form-group"}
   [:label {:for "code-input"} "Code: "]
   [:textarea {:id          "code-input"
               :form        "code-submission-form"
               :rows        "15"
               :class       "form-control"
               :placeholder "Enter Code Here..."
               :on-change   #(common/onchange-swap-atom! page-state [:data :code] %)}]])

(defn set-rulebook-type []
  [:div {:class "form-group"}
   [:select {:id "rulebook-type-selection"
             :form "code-submission-form"
             :defaultValue "default"
             :required "required"
             :on-change #(common/onchange-swap-atom! page-state [:rulebook-type] %)}
    [:option {:disabled "disabled" :value "default"} "Please select rulebook type"]
    [:option {:value "existing-rulebook"} "Use Existing Rulebook"]
    [:option {:value "url"} "Link to a rulebook"]
    [:option {:value "manually-enter"} "Manually Enter Rulebook"]]])

(defn rulebook-submission-input []
  [:div {:class "form-group"}
   (let [rulebook-type (:rulebook-type @page-state)]
     (cond
       (= rulebook-type "existing-rulebook")
       [:select {:id "existing-rulebook-dropdown"
                 :form "code-submission-form"
                 :defaultValue "default"
                 :required "required"
                 :on-change #(common/onchange-swap-atom! page-state [:data :rulebook] %)}
        [:option {:disabled "disabled" :value "default"} "Please select rulebook"]
        [:option {:value "placeholder1"} "Placeholder 1"]
        [:option {:value "placeholder2"} "Placeholder 2"]
        [:option {:value "placeholder3"} "Placeholder 3"]]

       (= rulebook-type "url")
       [:input {:id "rulebook-url-input"
                :type "text"
                :form "code-submission-form"
                :class "form-control"
                :placeholder "Enter Rulebook URL..."
                :on-change #(common/onchange-swap-atom! page-state [:data :rulebook] %)}]

       (= rulebook-type "manually-enter")
       [:textarea {:id "rulebook-text-area"
                   :form "code-submission-form"
                   :rows "15"
                   :class "form-control"
                   :placeholder "Enter Rulebook Here..."
                   :on-change #(common/onchange-swap-atom! page-state [:data :rulebook] %)}]

       :else [:div]
       ))])

(defn code-submission-form []
  [:div
   [:form {:id       "code-submission-form"
           :onSubmit #(submit-procedure (:data @page-state))} ;;TODO look into ajax calls instead of form submission
    [code-submission-input]
    [set-rulebook-type]
    [rulebook-submission-input]
    [:div.btn.btn-primary {:type     "submit"
                           :on-click #(submit-procedure (:data @page-state))}
     "Submit"]]])

(defn returned-suggestions-panel
  [de-ref-page-state]
  (if (not (empty? (:returned-suggestions de-ref-page-state)))
    ;;if suggestions are there, render the panel
    [:div {:class "panel panel-default"}
     [:div {:class "panel-heading"} "Returned Suggestions"]
     [:div {:class "panel-body"}
      [:div {:class "row"}
       [:div {:class "col-sm-12"}
        [:pre
         (:returned-suggestions de-ref-page-state)]]]]]

    ;; else if any errors were returned, display them
    (if (not (empty? (:returned-errors de-ref-page-state)))
      [:div {:class "panel panel-default"}
       [:div {:class "panel-heading"} "Returned Errors"]
       [:div {:class "panel-body"}
        [:div {:class "row"}
         [:div {:class "col-sm-12"}
          [:pre (:returned-errors de-ref-page-state)]]]]]

      ;;else return an empty div (i.e don't render the panel).
      ;;Note return empty div so as not to return null, which generates an error
      [:div])))

(defn code-submission-page []
  [:div
   [:div#wrapper
    [sidebar/sidebar]
    [:div {:class "jumbotron"}
     [sidebar/menu-toggle]
     [:h2 "Code Submission"]]
    [:div.page-content-wrapper>div.container-fluid>div.row>div.col-xs-12
     [returned-suggestions-panel @page-state]
     [:div {:class "panel panel-default"}
      [:div {:class "panel-heading"} "Code Submission"]
      [:div {:class "panel-body"}
       [:div {:class "row"}
        [:div {:class "col-sm-12"}
         [code-submission-form]

         ;;todo remove debugging info below
         ;;[:p "pagestate: " @page-state]
         ;;[:p "returned suggestons: " (:returned-suggestions @page-state)]
         ;;[:p "returned errors: " (:returned-errors @page-state)]
         ;;[:p "value in dropdown: " (.val (js/$ "#rulebook-type-selection"))]
         ]]]]]]])

(defn code-submission []
  (reagent/create-class {:reagent-render code-submission-page}))
