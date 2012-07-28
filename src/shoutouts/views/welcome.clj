(ns shoutouts.views.welcome
  (:require [shoutouts.views.common :as common])
  (:use [noir.core :only [defpage]]
        [hiccup.core :only [html]]))

(defpage "/" []
         (common/layout
           [:p "Welcome to shoutouts"]))
