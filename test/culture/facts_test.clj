(ns culture.facts-test
  (:require [clojure.edn :as edn]
            [clojure.string :as str]
            [clojure.test :refer [deftest is]]
            [culture.facts :as facts]))

(deftest deu-has-culture-basis
  (let [sb (facts/spec-basis "DEU")]
    (is (= 9 (count sb)))
    (is (= (count sb) (count (set (map :culture/id sb)))))
    (is (every? #(str/starts-with? (:culture/url %) "https://") sb))
    (is (every? #(= "DEU" (:culture/country %)) sb))
    (is (every? #(nil? (:culture/municipality %)) sb))
    (is (every? #(seq (:culture/summary %)) sb))
    (is (every? #(string? (:culture/retrieved-at %)) sb))))

(deftest unknown-jurisdiction-has-no-basis
  (is (nil? (facts/spec-basis "AUT")))
  (is (nil? (facts/spec-basis "zzz"))))

(deftest coverage-is-honest
  (let [c (facts/coverage ["DEU" "AUT"])]
    (is (= 2 (:requested c)))
    (is (= 1 (:covered c)))
    (is (= ["AUT"] (:missing-jurisdictions c)))))

(deftest by-kind-filters
  (is (= 4 (count (facts/by-kind "DEU" :dish))))
  (is (= ["deu.festival.oktoberfest"]
         (mapv :culture/id (facts/by-kind "DEU" :festival))))
  (is (empty? (facts/by-kind "DEU" :other)))
  (is (empty? (facts/by-kind "AUT" :dish))))

(deftest tx-file-matches-catalog
  (let [tx (edn/read-string (slurp "data/culture-tx.edn"))
        flat (mapcat val (sort-by key facts/catalog))]
    (is (= (vec flat) (vec tx)))))
