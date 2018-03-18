(ns scae-library.symbol-table
  (:require [clojure.string :refer [blank?]]
            [clojure.pprint :refer [pprint]]))

(def ^:const GLOBAL_SCOPE_NAME "global")  ;; define the global scope name

(def ^:private symbol-table
  (atom {:current-scope-name GLOBAL_SCOPE_NAME ;;Scopes are either "global", "main", or named after a function name (e.g "foobar")
         :previous-scope-name-stack [] ;;Keep track of previous scope on a stack (implemented with a vector)
                                        ;; So if func2 is called inside func1, after func2 is done scope can be returned to func1
         :scope-map {GLOBAL_SCOPE_NAME {}}   ;;Key = the string of the scope-name, value = the symbol table entry map
         }))

(defn reset-symbol-table! []
  (reset! symbol-table {:current-scope-name GLOBAL_SCOPE_NAME
                        :previous-scope-name-stack []
                        :scope-map {GLOBAL_SCOPE_NAME {}}
                        }))

(defn push-previous-scope-name-stack!
  "Push a scope name (usually the current scope) onto the previous-scope-name-stack"
  [scope-name]
  (let [new-stack-value (conj (:previous-scope-name-stack @symbol-table) scope-name)]
    (swap! symbol-table #(assoc % :previous-scope-name-stack new-stack-value))))

(defn pop-previous-scope-name-stack!
  "Removes the last scope-name from the stack and returns that name"
  []
  (let [popped-value (last (:previous-scope-name-stack @symbol-table))
        new-stack-value (into [] (drop-last (:previous-scope-name-stack @symbol-table)))]
    (swap! symbol-table #(assoc % :previous-scope-name-stack new-stack-value))
    popped-value))

;; Getter and Setters
(defn get-current-scope-name []
  (:current-scope-name @symbol-table))

(defn set-current-scope-name!
  ([scope-name save-previous?]
   "if there is already a current scope, and if the save previous bool is set,
   we'll push it to the previous scope stack first before setting the new scope name"
   (let [current-scope (get-current-scope-name)]

     (if (and (not (blank? current-scope))
              save-previous?)                 ;;
       (push-previous-scope-name-stack! current-scope))
     (swap! symbol-table #(assoc % :current-scope-name scope-name))))
  ([scope-name]
   " By default we will save the current scope to the previous stack, then update,
   the scope name"
   (set-current-scope-name! scope-name true)))

(defn set-current-scope-name-to-previous!
  "Set the current scope name to the previous scope name. Does not save the
  current scope name"
  []
  (set-current-scope-name!
    (pop-previous-scope-name-stack!) false))

(defn add-to-scope
  ([st-entry-name st-entry-map scope-name]
   "Adds the symbol-table-entry map to the specified scope under the st-entry-name"
    (swap! symbol-table #(assoc-in % [:scope-map scope-name st-entry-name] st-entry-map)))
  ([st-entry-name st-entry-map]
    "Adds the symbol-table-entry map to the current scope under the st-entry-name"
    (add-to-scope st-entry-name st-entry-map (get-current-scope-name))))

(defn get-from-scope
  ([st-entry-name scope-name]
   "Returns the st-entry-map for the st-entry-name in the specified scope. If
   entry is not found in the specified scope, this will try
   to retrieve it from the global scope"
    (let [st-entry-map (get-in @symbol-table [:scope-map scope-name st-entry-name])]
      (println "st-entry-map: " st-entry-map)
      (if (and (empty? st-entry-map)
               (not= scope-name GLOBAL_SCOPE_NAME))
        (get-from-scope st-entry-name GLOBAL_SCOPE_NAME)
        st-entry-map)))
  ([st-entry-name]
   "Returns the st-entry-map for the st-entry-name in the current scope"
   (get-from-scope st-entry-name (get-current-scope-name))))


(defn pretty-print-symbol-table []
  (pprint @symbol-table))


(defn create-symbol-table
  [ast-node]
  (doseq [entry (:parsed-node-result ast-node)]
    (do
      ;;we can have three types of map here, a token, another node and a st-func-call.
      ;;Here we only care for the last two

      ;;recursive call for inner node
      (if (:parsed-node-result entry)
        (create-symbol-table entry))

      ;;symbol table function call
      (if (:st-func-calls entry)
        (do
          (doseq [st-func (:st-func-calls entry)]
            (eval (st-func (:parsed-node-result ast-node)))))))))