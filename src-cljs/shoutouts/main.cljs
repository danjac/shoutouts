(ns shoutouts.main
    (:require [jayq.core :as jq]))


(def week-selector (jq/$ :#week-selector))

(defn select-week []
    (let [val (jq/val week-selector)]
        (set! (.-location.href js/window) (str "/?week=" val))))

(jq/bind week-selector :change select-week)
