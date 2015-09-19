(ns datascript.test.transit
  (:require
    [datascript.transit :as dt]
    [datascript.core :as d]
    [cljs.test :refer-macros [is deftest]]))


(def test-db
  (-> (d/empty-db { :email { :db/unique :db.unique/identity } })
      (d/db-with [{:name "Ivan", :email "ivan@gmail.com", :id #uuid "de305d54-75b4-431b-adb2-eb6b9e546014"}
                  {:name "Oleg", :email "oleg@gmail.com"}])))


(deftest test-roundtrip
  (is (= test-db (dt/read-transit-str (dt/write-transit-str test-db)))))


(defn ^:export test_all []
  (enable-console-print!)
  (cljs.test/run-all-tests #"datascript\.test\.transit"))
