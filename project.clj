(defproject datascript-transit "0.1.0"
  :description "Transit handlers for DataScript database and datoms"
  :license     { :name "Eclipse"
                 :url  "http://www.eclipse.org/legal/epl-v10.html" }
  :url         "https://github.com/tonsky/datascript-transit"
  
  
  :dependencies
  [ [org.clojure/clojure        "1.7.0"   :scope "provided"]
    [org.clojure/clojurescript  "1.7.122" :scope "provided"]
    [datascript                 "0.12.1"  :scope "provided"]
    [com.cognitect/transit-clj  "0.8.281"]
    [com.cognitect/transit-cljs "0.8.225"] ]
  
  
  :global-vars
  { *warn-on-reflection* true }

  
  :plugins [ [lein-cljsbuild "1.1.0"] ]

  
  :cljsbuild
  { :builds
    [{ :id "advanced"
       :source-paths ["src" "test"]
       :compiler
       { :main           datascript.test.transit
         :output-to      "target/main.js"
         :optimizations  :advanced
         :source-map     "target/main.js.map"
         :pretty-print   true
         :compiler-stats true
         :warnings       {:single-segment-namespace false} }}]})
