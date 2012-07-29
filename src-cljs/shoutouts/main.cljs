(ns shoutouts.main
    (:require [jayq.core :as jq]))


(def week-selector (jq/$ :#week-selector))

; tabs 

(def priorities (jq/$ :#priorities))
(def priorities-tab (jq/$ :#priorities-tab))
(def priorities-tab-link (jq/$ :#priorities-tab>a))

(def sales-metrics (jq/$ :#sales-metrics))
(def sales-metrics-tab (jq/$ :#sales-metrics-tab))
(def sales-metrics-tab-link (jq/$ :#sales-metric-tab>a))

(defn select-week []
    (let [val (jq/val week-selector)]
        (set! (.-location.href js/window) (str "/?week=" val))))

(defn select-sales-metrics []
    (.hide priorities)
    (.show sales-metrics)
    (.removeClass priorities-tab "active")
    (.addClass sales-metrics-tab "active"))

(defn select-priorities []
    (.show priorities)
    (.hide sales-metrics)
    (.addClass priorities-tab "active")
    (.removeClass sales-metrics-tab "active"))


(jq/bind week-selector :change select-week)

(jq/bind priorities-tab-link :click select-priorities)
(jq/bind sales-metrics-tab-link :click select-sales-metrics)
