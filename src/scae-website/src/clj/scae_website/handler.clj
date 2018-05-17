(ns scae-website.handler
  (:require [compojure.core :refer [GET POST defroutes]]
            [compojure.route :refer [not-found resources]]
            [hiccup.page :refer [include-js include-css html5]]
            [scae-website.middleware :refer [wrap-middleware]]
            [config.core :refer [env]]
            [scae-library.core :as scae-lib]
            [scae-website.sample-files :as scae-sample-files]))

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

   ;; === Bootstrap Select ===
   (include-css "/node_modules/bootstrap-select/dist/css/bootstrap-select.min.css")
   (include-js "/node_modules/bootstrap-select/dist/js/bootstrap-select.min.js")

   ;; === Simple Sidebar ===
   (include-css "/css/simple-sidebar.css")

   ;; === Includes for Highlighted Code Editor ===
   (include-css "/node_modules/codemirror-minified/lib/codemirror.css")
   (include-js "/node_modules/codemirror-minified/lib/codemirror.js" )
   (include-js "/node_modules/codemirror-minified/mode/clojure/clojure.js")
   (include-js "/node_modules/codemirror-minified/mode/javascript/javascript.js")

   ;; === SCAE-Website CSS ===
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
           (POST "/scae-api/analyse-source-code" request
             (let [response (scae-lib/analyse-source-code (get-in request [:params :data]))]
               {:status 200 :body response}))

           (POST "/scae-api/generate-ast" request
             (let [response (scae-lib/generate-ast (get-in request [:params :data]))]
               {:status 200 :body response}))


           ;;=== Misc ===
           (GET "/sample-files" []
             (let [response (scae-sample-files/get-sample-files )]
               {:status 200 :body response}))
           (resources "/")
           (not-found "Not Found"))

(def app (wrap-middleware #'routes))
