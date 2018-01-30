(ns scae-website.ajax-handlers)

;; Common use success and error handlers
(defn handler
  [response]
  (.log js/console (str "Success Handler Response: " response)))

(defn error-handler
  [response]
  (.error js/console (str "Error Handler Response:" response)))