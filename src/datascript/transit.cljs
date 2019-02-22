(ns datascript.transit
  (:require
    [datascript.core :as d]
    [datascript.db   :as db]
    [me.tonsky.persistent-sorted-set :as pss]
    [cognitect.transit :as t]))


(def read-handlers
  { "u"                uuid  ;; favor ClojureScript UUIDs instead of Transit UUIDs
                             ;; https://github.com/cognitect/transit-cljs/pull/10
    "datascript/DB"    db/db-from-reader
    "datascript/Datom" db/datom-from-reader })


(def write-handlers
  { db/DB    (t/write-handler (constantly "datascript/DB")
               (fn [db]
                 { :schema (:schema db)
                   :datoms (:eavt db) }))
    db/Datom (t/write-handler (constantly "datascript/Datom")
               (fn [d]
                 (if (db/datom-added d)
                   [(.-e d) (.-a d) (.-v d) (db/datom-tx d)]
                   [(.-e d) (.-a d) (.-v d) (db/datom-tx d) false])))
    pss/BTSet (t/ListHandler.) })


(defn read-transit-str [s]
  (t/read (t/reader :json { :handlers read-handlers }) s))


(defn write-transit-str [o]
  (t/write (t/writer :json { :handlers write-handlers }) o))
