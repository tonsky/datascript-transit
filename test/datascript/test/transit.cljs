(ns datascript.test.transit
  (:require
    [datascript.transit :as dt]
    [datascript.core :as d]
    [cljs.test :refer-macros [is deftest]]))


(def test-db
  (-> (d/empty-db { :email { :db/unique :db.unique/identity } })
      (d/db-with [{:name  "Ivan"
                   :email "ivan@gmail.com"
                   :id    #uuid "de305d54-75b4-431b-adb2-eb6b9e546014"}
                  {:name  "Oleg"
                   :email "oleg@gmail.com"}])))


(def test-tx-data
  [ (d/datom 1 :name "Ivan" d/tx0 false)
    (d/datom 1 :name "Oleg" d/tx0 true) ])


(defn roundtrip  [o]
  (dt/read-transit-str (dt/write-transit-str o)))


(defn equiv-datom [d1 d2]
  (is (= (:e d1)     (:e d2)))
  (is (= (:a d1)     (:a d2)))
  (is (= (:v d1)     (:v d2)))
  (is (= (:tx d1)    (:tx d2)))
  (is (= (:added d1) (:added d2))))


(defn equiv-datoms [ds1 ds2]
  (is (= (count ds1) (count ds2)))
  (doseq [[d1 d2] (map vector ds1 ds2)]
    (equiv-datom d1 d2)))


(deftest test-roundtrip
  (let [db (roundtrip test-db)]
    (is (= (:schema db)  (:schema test-db)))
    (is (= (:rschema db) (:rschema test-db)))
    (is (= (:max-eid db) (:max-eid test-db)))
    (is (= (:max-tx db)  (:max-tx test-db)))
    (equiv-datoms (:eavt db) (:eavt test-db))
    (equiv-datoms (:aevt db) (:aevt test-db))
    (equiv-datoms (:avet db) (:avet test-db)))
  
  (equiv-datoms (roundtrip test-tx-data) test-tx-data))


(defn ^:export test_all []
  (enable-console-print!)
  (cljs.test/run-all-tests #"datascript\.test\.transit"))
