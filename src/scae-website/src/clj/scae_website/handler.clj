(ns scae-website.handler
  (:require [compojure.core :refer [GET POST defroutes]]
            [compojure.route :refer [not-found resources]]
            [hiccup.page :refer [include-js include-css html5]]
            [scae-website.middleware :refer [wrap-middleware]]
            [config.core :refer [env]]))

(def mount-target
  [:div#app
      [:h3 "ClojureScript has not been compiled!"]
      [:p "please run "
       [:b "lein figwheel"]
       " in order to start the compiler"]])

(defn head []
  [:head
   [:meta {:charset "utf-8"}]
   [:meta {:name "viewport"
           :content "width=device-width, initial-scale=1"}]
   [:title "Source Code Analyser Engine"]

   ;; === JQuery ===
   (include-js "https://code.jquery.com/jquery-2.1.1.min.js")
   (include-css "http://code.jquery.com/ui/1.11.2/themes/smoothness/jquery-ui.min.css")
   (include-js "http://code.jquery.com/ui/1.11.2/jquery-ui.min.js")

   ;; === Bootstrap ===
   (include-css "/bootstrap-3.3.7-dist/css/bootstrap.min.css")
   (include-js "/bootstrap-3.3.7-dist/js/bootstrap.min.js")

   ;; === Simple Sidebar ===
   (include-css "/css/simple-sidebar.css")


   (include-css (if (env :dev) "/css/site.css" "/css/site.min.css"))])

(defn loading-page []
  (html5
    (head)
    [:body
     mount-target
     (include-js "/js/app.js")]))


(defroutes routes
           ;;=== Views ===
           (GET "/" [] (loading-page))
           (GET "/documentation" [] (loading-page))
           (GET "/download" [] (loading-page))
           (GET "/code-submission" [] (loading-page))

           ;;=== Web-API ===
           (POST "/scae-api" request
             (let [response {:status 200
                             :body   request}]  ;;for the time being just return the request with status code 200
               response))

           (resources "/")
           (not-found "Not Found"))

(def app (wrap-middleware #'routes))
