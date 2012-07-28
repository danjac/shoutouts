(ns shoutouts.views.welcome
  (:require [shoutouts.views.common :as common]
            [noir.request :as req]
            [noir.response :as resp]
            [noir.session :as session]
            [hiccup.form-helpers :as form])
  (:use [noir.core :only [defpage defpartial pre-route render]]
        [hiccup.core :only [html]]))


(def allowed-urls #{"/login" "/css" "/ico" "/js" "/img"})

(def icon-plus [:i.icon-plus])
(def icon-minus [:i.icon-minus])

(def users [["Dan Jacob" 1] ["John Smith" 2]])

(defn is-logged-in? [] (= (session/get :user-id "1234")))

(defn allowed-url? []
  (let [current-url (:uri (req/ring-request))]
    (letfn [(matches-url [url] (.contains current-url url))]
      (some matches-url allowed-urls))))


(pre-route "/*" {} (when-not (or (allowed-url?) (is-logged-in?)) (resp/redirect "/login")))

(defpage "/" []
         (common/layout

           (form/form-to {:class "form-vertical"} [:post "/"]

            [:fieldset

             [:div.control-group (form/label :shoutout "My shout-out for this week goes to")
                                 (form/drop-down :shoutout users) " for " (form/text-field :shoutout-reason)]

             [:div.control-group (form/label :one-pc "My 1% for this week goes to")
                                 (form/drop-down :one-pc users) " for " (form/text-field :one-pc-reason)]

             [:div.control-group (form/label :lessons-learned "Lessons learned")
                                 (form/text-field :lessons-learned)]

             [:div.control-group (form/label :best-quote "Best quote")
                                 (form/text-field :best-quote)]

             [:div.control-group (form/label :accomplishment "Top accomplishments")
                                  (form/with-group :accomplishments
                                   [:ol
                                    [:li (form/text-field 0)]
                                    [:li (form/text-field 1)]
                                    [:li (form/text-field 2)]])]

             [:div.form-actions (form/submit-button {:class "btn btn-primary"} "I'm done")]])))

(defpage "/login" []
         (common/layout
           [:h2 "Sign into Shoutouts"]
           (form/form-to [:post "/login"]
                         [:fieldset
                          [:p (form/label :email "Email address") 
                              (form/text-field :email)]
                          [:p (form/label :password "Password")
                              (form/password-field :password)]
                          [:p (form/submit-button "Sign in")]])))


(defpage [:post "/login"] {:keys [email password]}
         (if (= "testpass" password)
           (do 
             (session/put! :user-id "1234")
             (resp/redirect "/"))
            (render "/login")))

