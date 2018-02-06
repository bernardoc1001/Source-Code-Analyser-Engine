(ns scae-website.handler
  (:require [compojure.core :refer [GET POST defroutes]]
            [compojure.route :refer [not-found resources]]
            [hiccup.page :refer [include-js include-css html5]]
            [scae-website.middleware :refer [wrap-middleware]]
            [config.core :refer [env]]
            [scae-library.core :as scae-lib]))

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
   (include-js "/node_modules/jquery/dist/jquery.min.js")
   (include-css "/node_modules/jquery-ui-dist/jquery-ui.min.css")
   (include-js "/node_modules/jquery-ui-dist/jquery-ui.min.js")

   ;; === Bootstrap ===
   (include-css "/node_modules/bootstrap/dist/css/bootstrap.min.css")
   (include-js "/node_modules/bootstrap/dist/js/bootstrap.min.js")

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
             (let [response (scae-lib/analyse-source-code (get-in request [:params :data]))]
               {:status 200 :body response})) ;;for the time being just return 200 and the result of the scae library call

           (resources "/")
           (not-found "Not Found"))

(def app (wrap-middleware #'routes))
