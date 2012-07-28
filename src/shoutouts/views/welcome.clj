(ns shoutouts.views.welcome
  (:require [shoutouts.views.common :as common]
            [noir.request :as req]
            [noir.response :as resp]
            [noir.session :as session]
            [hiccup.form-helpers :as form])
  (:use [noir.core :only [defpage defpartial pre-route render]]
        [hiccup.core :only [html]]))


(def allowed-urls #{"/login" "/css" "/ico" "/js" "/img"})

(defn is-logged-in? [] (= (session/get :user-id "1234")))

(defn allowed-url? []
  (let [current-url (:uri (req/ring-request))]
    (letfn [(matches-url [url] (.contains current-url url))]
      (some matches-url allowed-urls))))


(pre-route "/*" {} (when-not (or (allowed-url?) (is-logged-in?)) (resp/redirect "/login")))

(defpage "/" []
         (common/layout
           [:p "Welcome to shoutouts"]))

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

