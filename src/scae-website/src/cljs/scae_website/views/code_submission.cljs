(ns scae-website.views.code-submission
  (:require [reagent.core :as reagent]
            [ajax.core :refer [GET POST]]
            [clojure.string :as c-str]
            [scae-website.ajax-handlers :refer [handler error-handler]]
            [scae-website.sidebar :as sidebar]
            [scae-website.common :as common]))

(defonce page-state
         (reagent/atom {:data {:code     ""
                               :rulebook ""}
                        :code-method ""
                        :rulebook-method ""
                        :returned-suggestions ""
                        :returned-errors ""
                        :sample-files nil}))

(defn sample-files-success-handler
  [response]
  (.log js/console (str "Sample Files Get Success Response: " response))
  (swap! page-state assoc-in [:sample-files] response))

(defn get-sample-files []
  ;;Sample Files should not change once page is loaded,
  ;;so don't repeatedly make the get request if we've already gotten them
  (if (empty? (:sample-files @page-state))
    (GET "/sample-files"
         {:handler       sample-files-success-handler
          :error-handler error-handler})
    [:div]))

(defn code-submission-success-handler
  [response]
  (let [formatted-response
        (clojure.string/join "\n" response)
        no-suggestion-message "Everything looks ok with your code. No suggestions returned."]
    (.log js/console (str "Code Submission Success Handler Response: " formatted-response))
    (swap! page-state assoc-in [:returned-suggestions] (if (empty? formatted-response)
                                                         no-suggestion-message
                                                         formatted-response))))

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

(defn valid-input-type?
  [input-type]
  (if (or (= input-type "code")
          (= input-type "rulebook"))
    true
    nil))

(defn set-input-method
  [input-type]
  (if (valid-input-type? input-type)
    [:div {:class "form-group"}
     [:select {:id           (str input-type "-method-selection")
               :form         "code-submission-form"
               :defaultValue "default"
               :required     "required"
               :on-change    #(common/onchange-swap-atom! page-state [(keyword (str input-type "-method"))] %)}
      [:option {:disabled "disabled" :value "default"} (str "Please select " input-type " input method")]
      [:option {:value (str "existing-" input-type)} (str "Use existing " input-type)]
      [:option {:value "url"} (str "Link to external " input-type)]
      [:option {:value "manually-enter"} (str "Manually enter " input-type)]]]
    (do
      (.error js/console "Invalid Input Type: " input-type)
      "ERROR INVALID INPUT TYPE: " input-type)))

(defn sample-files-dropdown
  [input-type]
  (if (valid-input-type? input-type)
    (let [sample-files (get-in @page-state [:sample-files (keyword (str input-type "-samples"))])]
      (->>
        (reduce #(conj %1 [:option {:value (second %2)} (first %2)]) [] sample-files)
        (into [:select {:id           (str "existing-" input-type "-dropdown")
                        :form         "code-submission-form"
                        :defaultValue "default"
                        :required     "required"
                        :on-change    #(common/onchange-swap-atom! page-state [:data (keyword input-type)] %)}
               [:option {:disabled "disabled" :value "default"} (str "Please select a " input-type " sample")]])))
    (do
      (.error js/console "Invalid Input Type: " input-type)
      "ERROR INVALID INPUT TYPE: " input-type)))

(defn submission-text-area
  [input-type]
  (if (valid-input-type? input-type)
    [:textarea {:id (str input-type "-text-area")
                :form "code-submission-form"
                :rows "15"
                :class "form-control"
                :placeholder (str "Enter " input-type " here...")
                :value (let [current-input (get-in @page-state [:data (keyword input-type)])]
                         (if (not-empty current-input)
                           ;; If there is a value for the current input
                           ;; (rulebook or code) in the atom, display it as
                           ;; a default value in the text area
                           current-input
                           ""))
                :on-change #(common/onchange-swap-atom! page-state [:data (keyword input-type)] %)}]
    (do
      (.error js/console "Invalid Input Type: " input-type)
      "ERROR INVALID INPUT TYPE: " input-type)))

(defn submission-input
  [input-type]
  (if (valid-input-type? input-type)
    [:div {:class "form-group"}
     [:label {:for "code-input"} (str (c-str/capitalize input-type) ": ")]
     [set-input-method input-type]
     (let [submission-type (get @page-state (keyword (str input-type "-method")))]
       (cond
         (= submission-type (str "existing-" input-type))
         [:div
          [sample-files-dropdown input-type]
          [submission-text-area input-type]]

         (= submission-type "url")
         [:input {:id (str input-type "-url-input")
                  :type "text"
                  :form "code-submission-form"
                  :class "form-control"
                  :placeholder "Enter Rulebook URL..."
                  :on-change #(common/onchange-swap-atom! page-state [:data (keyword input-type)] %)}]

         (= submission-type "manually-enter")
         [submission-text-area input-type]

         :else [:div]))]
    (do
      (.error js/console "Invalid Input Type: " input-type)
      "ERROR INVALID INPUT TYPE: " input-type)))

(defn submission-form []
  [:div
   [:form {:id       "submission-form"
           :onSubmit #(submit-procedure (:data @page-state))} ;;TODO look into ajax calls instead of form submission
    [submission-input "code"]
    [submission-input "rulebook"]
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
  (get-sample-files)
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
         [submission-form]

         ;;todo remove debugging info below
         ;;[:p "pagestate: " @page-state]
         ;;[:p "returned suggestons: " (:returned-suggestions @page-state)]
         ;;[:p "returned errors: " (:returned-errors @page-state)]
         ;;[:p "value in dropdown: " (.val (js/$ "#rulebook-type-selection"))]
         ]]]]]]])

(defn code-submission []
  (reagent/create-class {:reagent-render code-submission-page}))
