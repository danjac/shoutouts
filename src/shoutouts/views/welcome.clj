(ns shoutouts.views.welcome
  (:require [shoutouts.views.common :as common]
            [noir.request :as req]
            [noir.response :as resp]
            [noir.session :as session]
            [hiccup.form-helpers :as form])
  (:use [noir.core :only [defpage defpartial pre-route render]]
  	    [noir.fetch.remotes :only [defremote]]
        [hiccup.core :only [html]]
        [hiccup.page-helpers :only [link-to]]))


(def allowed-urls #{"/login" "/css" "/ico" "/js" "/img"})

(def icon-plus [:i.icon-plus])
(def icon-minus [:i.icon-minus])

(def users [["Select a colleague" ""] ["Dan Jacob" 1] ["John Smith" 2]])

(defn is-logged-in? [] (= (session/get :user-id "1234")))

(defn allowed-url? []
  (let [current-url (:uri (req/ring-request))]
    (letfn [(matches-url [url] (.contains current-url url))]
      (some matches-url allowed-urls))))


(def weeks [["30 July 2012" "25-2012"]
            ["23 July 2012" "24-2012"]])

(pre-route "/*" {} (when-not (or (allowed-url?) (is-logged-in?)) (resp/redirect "/login")))


(defpage [:post "/submit/"] {:as priorities}
          (println priorities)
          "OK")
          
(defremote submit-priorities [priorities]
           (println priorities)
           "done")

(defpage "/" {:keys [week]}

         (common/layout

           [:h2 "Priorities for " (form/drop-down :user-selector users 1)
                " in week starting from " (form/drop-down :week-selector weeks (or week "25-2012"))]

           [:div.alert.alert-warning "You have 2 hours to complete your priorities!"]

           [:ul.nav.nav-tabs {:id "tab-navbar"}
            [:li.active {:id "priorities-tab"} (link-to "#" "Dan Jacob")]
            [:li#sales-metrics-tab (link-to "#" "Sales Metrics")]
            [:li#community-metrics-tab (link-to "#" "Community Metrics")]
            [:li#ceo-announcements-tab (link-to "#" "CEO Announcements")]]

           [:div.tab-content {:id "sales-metrics" :style "display:none;"} "Sales metrics go here"]
           [:div.tab-content {:id "community-metrics" :style "display:none;"} "Community metrics go here"]
           [:div.tab-content {:id "ceo-announcements" :style "display:none;"} "CEO announcements go here"]

           [:div.tab-content {:id "priorities"}
             (form/form-to {:class "form-vertical" :id "priorities-form"} [:post "/submit/"]

              [:fieldset

               [:legend "Shout-outs and 1%'s"]

               [:div.control-group (form/label :shoutout "My shout-out for this week goes to")
                                   (form/drop-down :shoutout users) " for " (form/text-field :shoutout-reason)]

               [:div.control-group (form/label :one-pc "My 1% for this week goes to")
                                   (form/drop-down :one-pc users) " for " (form/text-field :one-pc-reason)]]

              [:fieldset 
              
               [:legend "Lessons learned"]

               [:div.control-group (form/label :lessons-learned "Lessons learned")
                                   (form/text-field :lessons-learned)]

               [:div.control-group (form/label :best-mistake "Best mistake")
                                   (form/text-field :best-mistake)]

               [:div.control-group (form/label :best-quote "Best quote")
                                   (form/text-field :best-quote)]]

               [:fieldset

                [:legend "Tasks &amp; accomplishments"]

                [:div.control-group (form/label :accomplishments "Top accomplishments")
                                    [:ol
                                      (map (fn [n] (form/with-group :accomplishments
                                                      (html [:li (form/text-field n)])))
                                      (range 3))]]

                [:div.control-group (form/label :tasks "Tasks for this week")
                                    [:ol
                                      (map (fn [n] (form/with-group :tasks
                                                      (html [:li (form/text-field {:class "task-field"} n)])))
                                      (range 3))]]]


   
               [:div.form-actions (form/submit-button {:class "btn btn-primary"} "I'm done")])]))

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

