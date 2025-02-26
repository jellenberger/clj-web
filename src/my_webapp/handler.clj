(ns my-webapp.handler
  (:require [compojure.core :refer [defroutes GET POST]]
            [compojure.route :as route]
            [my-webapp.views :as views]
            [ring.adapter.jetty :as jetty]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]))


(defroutes app-routes
  (GET "/"
    []
    (views/home-page))
  (GET "/add-location"
    []
    (views/add-location-page))
  (POST "/add-location"
    {params :params}
    (views/add-location-results-page params))
  (GET "/location/:loc-id"
    [loc-id]
    (views/location-page loc-id))
  (GET "/all-locations"
    []
    (views/all-locations-page))
  (route/resources "/")
  (route/not-found "Not Found"))


(def app
  ;; use #' prefix for REPL-friendly code -- see note below
  (wrap-defaults #'app-routes site-defaults))


(defn -main []
  (jetty/run-jetty #'app {:port 3000}))


(comment
;; Start the server from the repl without locking it up
;; use (.stop server) and (.start server) to stop and start the server 
  (defonce server (jetty/run-jetty #'app {:port 3000 :join? false}))
;;   
;;   use the #'prefix on var names so that we can update the definitions 
;;   while the program is running, without needing to restart our program -- 
;;   see https://clojure.org/guides/repl/enhancing_your_repl_workflow#writing-repl-friendly-programs

;;   the #:namespace{:key value} notation is shorthand for {:namespace/key value} and
;;   is something you'll see a lot in Clojure. Namespace-qualified keys provide additional 
;;   context: in the first case above :next.jdbc/update-count is produced by next.jdbc itself 
;;   whereas :LOCATIONS/ID indicates the table and column name of the auto-increment key
;;   from the database.
  )
