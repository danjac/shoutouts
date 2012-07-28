(defproject shoutouts "0.1.0-SNAPSHOT"
            :description "FIXME: write this!"
            :dependencies [[org.clojure/clojure "1.3.0"]
                           [com.novemberain/monger "1.1.0"]
                           [cheshire "2.2.0"]
                           [jayq "0.1.0-SNAPSHOT"]
                           [crate "0.1.0-SNAPSHOT"]
                           [fetch "0.1.0-SNAPSHOT"]
                           [noir "1.2.1"]]
			:dev-dependencies [[lein-cljsbuild "0.0.13"]]                           
			:cljsbuild {:source-path "src-cljs"
		                :compiler {:output-to "resources/public/js/cljs.js"
		                           :optimizations :simple
		                           :pretty-print true}}
            :main shoutouts.server)

