(ns datascript.test.transit
  (:require
    [datascript.transit :as dt]
    [datascript :as d]
    [clojure.test :refer [is deftest]]))


(def test-db
  (-> (d/empty-db { :email { :db/unique :db.unique/identity } })
      (d/db-with [{:name "Ivan", :email "ivan@gmail.com"}
                  {:name "Oleg", :email "oleg@gmail.com"}])))


(deftest test-roundtrip
  (is (= test-db (dt/read-transit-str (dt/write-transit-str test-db)))))
