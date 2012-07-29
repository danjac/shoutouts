(ns shoutouts.server
  (:require [noir.server :as server])
  (:use [noir.fetch.remotes]))

(server/load-views "src/shoutouts/views/")
(server/add-middleware wrap-remotes)


(defn -main [& m]
  (let [mode (keyword (or (first m) :dev))
        port (Integer. (get (System/getenv) "PORT" "8080"))]
    (server/start port {:mode mode
                        :ns 'shoutouts})))

