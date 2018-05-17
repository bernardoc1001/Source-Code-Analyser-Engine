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
                        :code-url-address ""
                        :rulebook-method ""
                        :rulebook-url-address ""
                        :returned-suggestions ""
                        :returned-errors ""
                        :sample-files nil
                        :ast-flag false}))

(defn sample-files-success-handler
  [response]
  (swap! page-state assoc-in [:sample-files] response))

(defn get-sample-files []
  ;;Sample Files should not change once page is loaded,
  ;;so don't repeatedly make the get request if we've already gotten them
  (if (empty? (:sample-files @page-state))
    (GET "/sample-files"
         {:handler       sample-files-success-handler
          :error-handler error-handler})
    [:div]))

(defn update-editor
  [id input-type]
  (let [original-editor
        (aget
          (aget
            (.next
              (js/$ (str "#" id))
              ".CodeMirror")
            0)
          "CodeMirror")]
    (.setValue original-editor (get-in @page-state
                                       [:data (keyword input-type)]))))

(defn get-target-editor-element
  ([id index]
   "Return the editor DOM element matching the id and index, where index specifies
 which editor on an array of matched editors to return. Normally want first editor."
    ;; If check is messy but is necessary to check the existence of each component
    ;; separately, as the next component relies on the previous.
    ;; If we didn't, then if the previous doesn't exist then an error is thrown instead of just
    ;; failing the if. That is why we check individually in the AND, so we stop
    ;; as soon as we find a component that doesn't exist
   (if (and (.getElementById js/document id)
            (js/$ (str "#" id))
            (.next
              (js/$ (str "#" id))
              ".CodeMirror")
            (aget
              (.next
                (js/$ (str "#" id))
                ".CodeMirror")
              index)
            (aget
              (aget
                (.next
                  (js/$ (str "#" id))
                  ".CodeMirror")
                index)
              "CodeMirror"))
     (let [target-editor (aget
                           (aget
                             (.next
                               (js/$ (str "#" id))
                               ".CodeMirror")
                             index)
                           "CodeMirror")
           target-dom-node (.getWrapperElement target-editor)]
       target-dom-node)))
  ([id]
   "By default return the first editor matching the id"
   (get-target-editor-element id 0)))

(defn extract-bad-style-to-search
  "This extracts any samples of bad style from the from the response of suggestions.
  If any are found this will later be used to search the code-editor
  for lines to highlight."
  [suggestions-response]
  (let
    ;;regex matches from after "SCAE Bad-Styles: " to the end of line
    [bad-style-regex "(SCAE Bad-Style:)(.*$)"]
    (->>
      (for [suggestion suggestions-response]
        (->>
          (re-seq (re-pattern bad-style-regex) suggestion)
          (first)
          (last)
          (c-str/trim)))
      (distinct)
      (vec)
      (remove nil?)
      (remove empty?))))

(defn highlight-and-number-bad-lines
  [editor-lines bad-styles-to-search]
  (let [line-number-atom (reagent/atom {:current-line-num         1
                                        :lines-with-bad-style []})]
    (js/$.each editor-lines (fn
                              [index line]
                              (doseq [bad-style bad-styles-to-search]
                                (if (c-str/includes? (.-innerText line)
                                                     bad-style)
                                  (do
                                    ;;update the vector of bad style detection line numbers
                                    (swap! line-number-atom assoc-in
                                           [:lines-with-bad-style]
                                           (conj (:lines-with-bad-style @line-number-atom)
                                                 (str "Line " (:current-line-num @line-number-atom) ": ")))

                                    ;;Highlight the bad line style in orange
                                    (->
                                      line
                                      (js/$)
                                      (.css "background-color" "orange")))))

                              ;;increment the current line number count
                              (swap! line-number-atom assoc-in
                                     [:current-line-num]
                                     (inc (:current-line-num @line-number-atom)))))
    ;;Return vector of by style line numbers
    (:lines-with-bad-style @line-number-atom)))

(defn highlight-bad-styles-and-match-with-response
  "This will examine the suggestion response returned, search the code editor for
  matching bad styles, then highlight those lines."
  [suggestions-response]
  (let [bad-styles-to-search (extract-bad-style-to-search suggestions-response)
        code-editor-id "code-text-area"
        target-editor-element (get-target-editor-element code-editor-id)
        target-editor-lines (-> target-editor-element
                                (js/$)
                                (.find ".CodeMirror-line"))]
    (if (not-empty bad-styles-to-search)
      (->>
        (highlight-and-number-bad-lines target-editor-lines bad-styles-to-search)
        (map #(str %2 %1) suggestions-response)
        (c-str/join "\n"))
      suggestions-response)))

(defn code-submission-success-handler
  [response]
  (let [no-suggestion-message "Everything looks ok with your code. No suggestions returned."
        message-to-display (if (:ast-flag @page-state)
                             ;; Displaying an AST
                             (cljs.pprint/write response :stream nil)
                             ;;Displaying Suggestions
                             (if (empty? response)
                               no-suggestion-message
                               (highlight-bad-styles-and-match-with-response response)))]
    (swap! page-state assoc-in [:returned-errors] "")
    (swap! page-state assoc-in [:returned-suggestions] message-to-display)))

(defn code-submission-error-handler
  [response]
  (let [formatted-response
        (str "Status Code: " (:status response)
             "\nStatus Text: " (:status-text response)
             "\nFailure Type: " (:failure response))]
    (.error js/console (str "Code Submission Error Handler Response: " response))
    (swap! page-state assoc-in [:returned-errors] formatted-response)))

(defn post-code-submission
  [submit-body]
  (if (:ast-flag @page-state)
    (POST "/scae-api/generate-ast"
          {:params        {:data submit-body}
           :handler       code-submission-success-handler   ;;handler
           :error-handler code-submission-error-handler     ;;error-handler
           })

    (POST "/scae-api/analyse-source-code"
          {:params        {:data submit-body}
           :handler       code-submission-success-handler   ;;handler
           :error-handler code-submission-error-handler     ;;error-handler
           })))

(defn code-url-success-handler
  [response]
  ;; Swap data into page-state
  (swap! page-state assoc-in [:data :code] (str response))
  ;; Reflect the gotten data in the editors
  (update-editor "code-text-area" "code")
  (update-editor "rulebook-text-area" "rulebook")
  ;;post to the library
  (post-code-submission (:data @page-state)))

(defn submit-code-url-procedure []
  (GET (get-in @page-state [:code-url-address])
       {:handler       code-url-success-handler
        :error-handler error-handler}))

(defn rulebook-url-success-handler
  [response]
  (swap! page-state assoc-in [:data :rulebook] (str response))
  ;; now check if we have to retrieve any code from an external url. Else proceed
  ;; with submitting the data to the library
  (if (= (:code-method @page-state)
         "url")
    (submit-code-url-procedure)
    (post-code-submission (:data @page-state))))

(defn submit-rulebook-url-procedure []
  (GET (get-in @page-state [:rulebook-url-address])
       {:handler       rulebook-url-success-handler
        :error-handler error-handler}))

(defn submit-procedure
  "Submits the data to the backend. IF the AST Flag is set to false, will
  get the suggestions from applying style rules. If AST Flag is set to
  true, will instead get the generated Abstract Syntax Tree"
  [submit-body ast-flag]

  ;; set the ast flag in the page-state
  (swap! page-state assoc-in [:ast-flag] ast-flag)


  ;; set the result display to show a loading icon, and then bring
  ;; the display into view
  (swap! page-state assoc-in [:returned-suggestions] "loader")
  (swap! page-state assoc-in [:returned-errors] "loader")

  ;; Update the editor, which will get rid of any previous bad style highlighting
  (update-editor "code-text-area" "code")

  (.scrollTop (js/$ "html,body") 0)

  (cond
    ;;check if the rulebook is being submitted by url. If so call it's own method
    ;;to retrieve the data to submit. That method will then check is we need
    ;;to retrieve any code from a url
    (= (:rulebook-method @page-state)
       "url")
    (submit-rulebook-url-procedure)

    ;;only need to get code data from a url before submitting
    (= (:code-method @page-state)
       "url")
    (submit-code-url-procedure)

    ;;Don't need to get any data before submitting
    :else
    (post-code-submission submit-body)))

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
               :class "selectpicker"
               :title (str (c-str/capitalize input-type) " input type")
               :form         "code-submission-form"
               :defaultValue "default"
               :required     "required"
               :on-change    #(common/onchange-swap-atom! page-state [(keyword (str input-type "-method"))] %)}
      [:option {:value (str "existing-" input-type)} (str "Use existing " input-type)]
      [:option {:value "url"} (str "Link to external " input-type)]
      [:option {:value "manually-enter"} (str "Manually enter " input-type)]
      [:option {:value "file-upload"} (str "Upload " input-type " file")]]]
    (do
      (.error js/console "Invalid Input Type: " input-type)
      "ERROR INVALID INPUT TYPE: " input-type)))

(defn remove-editor
  ([id index]
   (let [target-dom-node (get-target-editor-element id index)]
     (if (not (nil? target-dom-node))
       (.remove target-dom-node))))
  ([id]
    (remove-editor id 0)))

(defn refresh-dropdown-by-id
  [dropdown-id]
  (->
    (js/$ (str "#" dropdown-id))
    (.selectpicker "refresh")))

(defn sample-files-dropdown
  [input-type]
  (if (valid-input-type? input-type)
    (let [sample-files (get-in @page-state
                               [:sample-files (keyword (str input-type "-samples"))])
          dropdown-id (str "existing-" input-type "-dropdown")]
      (->>
        (reduce #(conj %1 [:option {:value (second %2)} (first %2)]) [] sample-files)
        (sort #(compare (last %1)
                        (last %2)))
        (into [:select {:id           dropdown-id
                        :class        "selectpicker"
                        :title        (str "Select sample " input-type)
                        :form         "code-submission-form"
                        :defaultValue "default"
                        :required     "required"
                        :on-change    #(do
                                         ;;update the pagestate
                                         (common/onchange-swap-atom!
                                           page-state
                                           [:data (keyword input-type)] %)
                                         ;;update the contents of the editor to match gotten data
                                         (let [id (str input-type "-text-area")]
                                           (update-editor id input-type)))}])))
    (do
      (.error js/console "Invalid Input Type: " input-type)
      "ERROR INVALID INPUT TYPE: " input-type)))

(defn editor-did-mount
  [input-type id]
  (fn [this]
    (let [current-input (get-in @page-state
                                [:data (keyword input-type)])
          cm (.fromTextArea js/CodeMirror
                            (.getElementById js/document id)
                            #_(reagent/dom-node this)
                             #js {:mode        (cond
                                                 (= input-type "code")
                                                 "clojure"

                                                 (= input-type "rulebook")
                                                 "application/json")
                                  :lineNumbers true})
          sample-files-dropdown-id (str "existing-" input-type "-dropdown")]

      ;; When rendering the editor initialise its value to that stored in page-state
      (update-editor id input-type)

      ;; When rendering the editor, also refresh the sample-files dropdown to ensure it is rendered aswell
      (refresh-dropdown-by-id sample-files-dropdown-id)

      ;; on change func call
      (.on cm "change" #(swap! page-state
                               assoc-in
                               [:data (keyword input-type)]
                               (.getValue %))))))

(defn editor
  [input-type]
  (if (valid-input-type? input-type)
    (let [text-area-id (str input-type "-text-area")]
      ;;remove any old editors
      (remove-editor text-area-id)
      (reagent/create-class
        {:reagent-render      (fn []
                                [:textarea {:id          text-area-id
                                            :form        "code-submission-form"
                                            :rows        "15"
                                            :class       "form-control"
                                            :placeholder (str "Enter " input-type " here...")

                                            }])
         :component-did-mount (editor-did-mount input-type text-area-id)}))
    (do
      (.error js/console "Invalid Input Type: " input-type)
      "ERROR INVALID INPUT TYPE: " input-type)))

(defn submit-url
  [input-type]
  [:div {:id (str input-type "-url-area")}
   [:input {:id          (str input-type "-url-input")
            :type        "text"
            :form        "code-submission-form"
            :class       "form-control"
            :placeholder (str "Enter " (c-str/capitalize input-type) " URL...")
            :value       (get-in @page-state [:code-url-address]) ;; initially set to current value
            :on-change   #(common/onchange-swap-atom!
                            page-state [(keyword
                                          (str input-type "-url-address"))]
                            %)}]
   [editor input-type]])

(defn submit-file
  [input-type]
  [:div {:id (str input-type "-submit-file")}
   [:label {:for (str input-type "-upload-box")} "Upload one source file:"]
   [:input {:id        (str input-type "-upload-box")
            :type      "file"
            :on-change #(do
                          (let [editor-id (str input-type "-text-area")
                                files (.. % -target -files)
                                first-file (aget files 0)
                                reader (js/FileReader.)]
                            (set! (.-onload reader) (fn
                                                      [e]
                                                      (->> e
                                                           (.-target)
                                                           (.-result)
                                                           (swap! page-state assoc-in [:data (keyword input-type)]))
                                                      (update-editor editor-id input-type)))
                            (when first-file
                              (.readAsText reader first-file))))}]
   [editor input-type]])

(defn submit-sample-file
  [input-type]
  [:div
   [sample-files-dropdown input-type]
   [editor input-type]])

(defn submission-input
  [input-type]
  (if (valid-input-type? input-type)
    [:div {:class "form-group"}
     [:label {:for "code-input"} (str (c-str/capitalize input-type) ": ")]
     [set-input-method input-type]
     (let [submission-type (get @page-state (keyword (str input-type "-method")))]
       (cond
         (= submission-type (str "existing-" input-type))
         [submit-sample-file input-type]

         (= submission-type "url")
         [submit-url input-type]

         (= submission-type "manually-enter")
         [editor input-type]

         (= submission-type "file-upload")
         [submit-file input-type]

         :else [:div]))]
    (do
      (.error js/console "Invalid Input Type: " input-type)
      "ERROR INVALID INPUT TYPE: " input-type)))

(defn submission-form []
  [:div
   [:form {:id       "submission-form"
           :onSubmit #(submit-procedure (:data @page-state) false)} ;;TODO look into ajax calls instead of form submission
    [:div {:id "code"}
     [submission-input "code"]]
    [:div {:id "rulebook"}
     [submission-input "rulebook"]]
    [:div.btn.btn-primary {:type     "submit"
                           :on-click #(submit-procedure (:data @page-state) false)}
     "Get Suggestions"]
    [:div.btn.btn-primary {:type     "submit"
                           :on-click #(submit-procedure (:data @page-state) true)}
     "Get AST"]]])

(defn returned-suggestions-panel
  [de-ref-page-state]
  (cond
    (and (or (= (:returned-suggestions de-ref-page-state)
                "loader")
             (= (:returned-errors de-ref-page-state)
                "loader"))

         (or
           (= (:returned-errors de-ref-page-state)
              "loader")
           (empty? (:returned-errors de-ref-page-state))))
    [:div {:class "panel panel-default"}
     [:div {:class "panel-heading"} "Analysing Code"]
     [:div {:class "panel-body"}
      [:div {:class "row"}
       [:div {:class "loader"}]]]]

    ;; if suggestions are there, render the panel
    (and
      (not= (:returned-suggestions de-ref-page-state)
            "loader")
      (not (empty? (:returned-suggestions de-ref-page-state))))
    [:div {:class "panel panel-default"}
     [:div {:class "panel-heading"} (if (:ast-flag @page-state)
                                      "Returned Abstract Syntax Tree"
                                      "Returned Suggestions")]
     [:div {:class "panel-body"}
      [:div {:class "row"}
       [:div {:class "col-sm-12"}
        [:pre
         (:returned-suggestions de-ref-page-state)]]]]]

    ;; else if any errors were returned, display them
    (and
      (not= (:returned-errors de-ref-page-state)
            "loader")
      (not (empty? (:returned-errors de-ref-page-state))))
    [:div {:class "panel panel-default"}
     [:div {:class "panel-heading"} "Returned Errors"]
     [:div {:class "panel-body"}
      [:div {:class "row"}
       [:div {:class "col-sm-12"}
        [:pre (:returned-errors de-ref-page-state)]]]]]

    :else
    ;;else return an empty div (i.e don't render the panel).
    ;;Note return empty div so as not to return null, which generates an error
    [:div]))

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

(defn code-submission-did-mount []
  (refresh-dropdown-by-id "code-method-selection")
  (refresh-dropdown-by-id "rulebook-method-selection"))

(defn code-submission []
  (reagent/create-class {:reagent-render code-submission-page
                         :component-did-mount code-submission-did-mount}))