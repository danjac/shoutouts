(ns shoutouts.main
    (:require [jayq.core :as jq]
              [fetch.remotes :as remotes])
    (:require-macros [fetch.macros :as fm]))



(def week-selector (jq/$ :#week-selector))

; tabs 

(def tabs (jq/$ :#tab-navbar>li))
(def tab-content (jq/$ :div.tab-content))

(def priorities (jq/$ :#priorities))
(def priorities-tab (jq/$ :#priorities-tab))
(def priorities-tab-link (jq/$ :#priorities-tab>a))

(def sales-metrics (jq/$ :#sales-metrics))
(def sales-metrics-tab (jq/$ :#sales-metrics-tab))
(def sales-metrics-tab-link (jq/$ :#sales-metrics-tab>a))

(def community-metrics (jq/$ :#community-metrics))
(def community-metrics-tab (jq/$ :#community-metrics-tab))
(def community-metrics-tab-link (jq/$ :#community-metrics-tab>a))

(def ceo-announcements (jq/$ :#ceo-announcements))
(def ceo-announcements-tab (jq/$ :#ceo-announcements-tab))
(def ceo-announcements-tab-link (jq/$ :#ceo-announcements-tab>a))

(def priorities-form (jq/$ :#priorities-form))

(defn submit-priorities-form [] 
    (fm/remote (submit-priorities) [result] (js/alert result))
false)

(defn select-week []
    (let [val (jq/val week-selector)]
        (set! (.-location.href js/window) (str "/?week=" val))))


(defn select-tab [tab section]
    (.hide tab-content)
    (.show section)
    (.removeClass tabs "active")
    (.addClass tab "active"))

(jq/bind week-selector :change select-week)

(jq/bind priorities-tab-link :click (fn [] (select-tab priorities-tab priorities)))
(jq/bind sales-metrics-tab-link :click (fn [] (select-tab sales-metrics-tab sales-metrics)))
(jq/bind community-metrics-tab-link :click (fn [] (select-tab community-metrics-tab community-metrics)))
(jq/bind ceo-announcements-tab-link :click (fn [] (select-tab ceo-announcements-tab ceo-announcements)))

(jq/bind priorities-form :submit submit-priorities-form)
