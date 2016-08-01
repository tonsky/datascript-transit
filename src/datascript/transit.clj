(ns datascript.transit
  (:require
    [datascript.core :as d]
    [datascript.db   :as db]
    [cognitect.transit :as t])
  (:import
    [datascript.db DB Datom]
    [datascript.btset BTSet]
    [java.io ByteArrayInputStream ByteArrayOutputStream]))


(def read-handlers
  { "datascript/DB"    (t/read-handler db/db-from-reader)
    "datascript/Datom" (t/read-handler db/datom-from-reader) })


(def write-handlers
  { DB    (t/write-handler "datascript/DB"
            (fn [db]
              { :schema (:schema db)
                :datoms (:eavt db) }))
    Datom (t/write-handler "datascript/Datom"
            (fn [^Datom d]
              (if (.-added d)
                [(.-e d) (.-a d) (.-v d) (.-tx d)]
                [(.-e d) (.-a d) (.-v d) (.-tx d) false])))
    BTSet (get t/default-write-handlers java.util.List) })


(defn read-transit [is]
  (t/read (t/reader is :json { :handlers read-handlers })))


(defn read-transit-str [^String s]
  (read-transit (ByteArrayInputStream. (.getBytes s "UTF-8"))))


(defn write-transit [o os]
  (t/write (t/writer os :json { :handlers write-handlers }) o))


(defn write-transit-bytes ^bytes [o]
  (let [os (ByteArrayOutputStream.)]
    (write-transit o os)
    (.toByteArray os)))
    

(defn write-transit-str [o]
  (String. (write-transit-bytes o) "UTF-8"))
