(ns datascript.transit
  (:require
    [datascript :as d]
    [datascript.core :as dc]
    [datascript.btset :as db]
    [cognitect.transit :as t]))


(def read-handlers
  { "u"                uuid  ;; https://github.com/cognitect/transit-cljs/pull/10
    "datascript/DB"    dc/db-from-reader
    "datascript/Datom" dc/datom-from-reader })


(def write-handlers
  { dc/DB    (t/write-handler (constantly "datascript/DB")
               (fn [db]
                 { :schema (:schema db)
                   :datoms (:eavt db) }))
    dc/Datom (t/write-handler (constantly "datascript/Datom")
               (fn [d]
                 [(.-e d) (.-a d) (.-v d) (.-tx d)]))
    db/BTSet (t/ListHandler.) })


(defn read-transit-str [s]
  (t/read (t/reader :json { :handlers read-handlers }) s))


(defn write-transit-str [o]
  (t/write (t/writer :json { :handlers write-handlers }) o))
