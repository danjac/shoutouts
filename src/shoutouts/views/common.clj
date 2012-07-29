(ns shoutouts.views.common
  (:use [noir.core :only [defpartial]]
        [hiccup.page-helpers :only [include-css 
                                    include-js 
                                    link-to 
                                    html5]]))

(defpartial navbar 
    []
    [:div.navbar.navbar-fixed-top
                 [:div.navbar-inner
                  [:div.container
                   [:a.btn.btn-navbar {:data-toggle "collapse" :data-target ".nav-collapse"}
                                       [:span.icon-bar]
                                       [:span.icon-bar]
                                       [:span.icon-bar]]
                   [:a.brand {:href "#"} "Shoutouts"]
                   [:div.nav-collapse
                    [:ul.nav
                     [:li.active (link-to "#" "Home")]
                     [:li (link-to "#" "Settings")]
                     [:li (link-to "#" "About")]
                     [:li (link-to "#" "Contact")]]]]]])

(defpartial js-includes
    []
    (include-js "/js/jquery.js")
    (include-js "/js/bootstrap-transition.js")
    (include-js "/js/bootstrap-alert.js")
    (include-js "/js/bootstrap-model.js")
    (include-js "/js/bootstrap-dropdown.js")
    (include-js "/js/bootstrap-scrollspy.js")
    (include-js "/js/bootstrap-tab.js")
    (include-js "/js/bootstrap-tooltip.js")
    (include-js "/js/bootstrap-popover.js")
    (include-js "/js/bootstrap-button.js")
    (include-js "/js/bootstrap-collapse.js")
    (include-js "/js/bootstrap-typeahead.js")
    (include-js "/js/cljs.js"))

(defpartial layout [& content]
            (html5
              [:head
               [:title "Shoutouts"]

               (include-css "/css/bootstrap.css")

               [:style "
                  body {
                  padding-top: 60px; /* 60px to make the container go all the way to the bottom of the topbar */
                  }"]
               
               (include-css "/css/bootstrap-responsive.css")

               [:link {:rel "shortcut icon" :href "/ico/favicon.ico"}]]

              [:body
               (navbar)

               [:div.container content]

               (js-includes)]))


